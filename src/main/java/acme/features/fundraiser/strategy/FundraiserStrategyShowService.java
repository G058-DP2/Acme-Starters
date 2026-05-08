
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyShowService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && (this.strategy.getFundraiser().isPrincipal() || !this.strategy.isDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		Collection<Project> projects;
		SelectChoices projectChoices;

		int fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		projects = this.repository.findProjectsByFundraiserId(fundraiserId);
		projectChoices = SelectChoices.from(projects, "title", this.strategy.getProject());

		tuple = super.unbindObject(this.strategy, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo", //
			"draftMode", "monthsActive", "expectedPercentage");
		tuple.put("projectChoices", projectChoices);
		tuple.put("project", projectChoices.getSelected().getKey());
	}

}
