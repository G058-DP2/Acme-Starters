
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;
import acme.entities.strategies.TacticKind;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticUpdateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.tactic != null && //
			this.tactic.getStrategy() != null && //
			this.tactic.getStrategy().getFundraiser().isPrincipal() && //
			this.tactic.getStrategy().isDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);
		{
			Double sumOfOthers;
			Boolean isValidPercentage;
			Double currentPercentage;

			currentPercentage = this.tactic.getExpectedPercentage() == null ? 0.00 : this.tactic.getExpectedPercentage();
			sumOfOthers = this.repository.sumPercentageByStrategyIdAndNotTacticId(this.tactic.getStrategy().getId(), this.tactic.getId());

			if (sumOfOthers == null)
				sumOfOthers = 0.00;

			isValidPercentage = sumOfOthers + currentPercentage <= 100.00;

			super.state(isValidPercentage, "expectedPercentage", "acme.validation.tactic.sumPercentages");
		}
		/*
		 * {
		 * boolean validPercentage;
		 * Double currentPercentage;
		 * 
		 * currentPercentage = this.tactic.getStrategy().getExpectedPercentage();
		 * validPercentage = currentPercentage + this.tactic.getExpectedPercentage() <= 100;
		 * super.state(validPercentage, "expectedPercentage", "acme.validation.tactic.sumPercentages");
		 * }
		 */
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(TacticKind.class, this.tactic.getKind());

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("strategyId", this.tactic.getStrategy().getId());
		tuple.put("draftMode", this.tactic.getStrategy().isDraftMode());
		tuple.put("kinds", choices);
	}

}
