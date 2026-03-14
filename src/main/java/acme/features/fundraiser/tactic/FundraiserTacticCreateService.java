
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.entities.strategies.TacticKind;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;


	@Override
	public void load() {
		int strategyId;
		Strategy stragey;

		strategyId = super.getRequest().getData("strategyId", int.class);
		stragey = this.repository.findStrategyBiId(strategyId);

		this.tactic = super.newObject(Tactic.class);
		this.tactic.setStrategy(stragey);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.tactic.getStrategy() != null && //
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
			boolean validPercentage;
			Double currentPercentage;

			currentPercentage = this.tactic.getStrategy().getExpectedPercentage();
			validPercentage = currentPercentage + this.tactic.getExpectedPercentage() <= 100;
			super.state(validPercentage, "expectedPercentage", "acme.validation.tactic.sumPercentages");
		}
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
		tuple.put("strategyId", super.getRequest().getData("strategyId", int.class));
		tuple.put("draftMode", this.tactic.getStrategy().isDraftMode());
		tuple.put("kinds", choices);
	}
}
