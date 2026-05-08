
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
public class FundraiserStrategyCreateService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;


	@Override
	public void load() {
		Fundraiser fundraiser;

		fundraiser = (Fundraiser) super.getRequest().getPrincipal().getActiveRealm();

		this.strategy = super.newObject(Strategy.class);
		this.strategy.setDraftMode(true);
		this.strategy.setFundraiser(fundraiser);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		int projectId;
		Project project;

		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		projectId = super.getRequest().getData("project", int.class);
		if (projectId != 0) {
			project = this.repository.findProjectById(projectId);
			this.strategy.setProject(project);
		} else
			this.strategy.setProject(null);
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);

		{
			Project project = this.strategy.getProject();
			if (project != null) {
				int fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
				Collection<Project> allowedProjects = this.repository.findProjectsByFundraiserId(fundraiserId);
				super.state(allowedProjects.contains(project), "project", "acme.validation.project.not-allowed");
			}
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.strategy);
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
