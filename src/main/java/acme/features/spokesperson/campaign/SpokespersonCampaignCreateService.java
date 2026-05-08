
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.project.Project;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignCreateService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;


	@Override
	public void load() {
		Spokesperson spokesperson;

		spokesperson = (Spokesperson) super.getRequest().getPrincipal().getActiveRealm();

		this.campaign = super.newObject(Campaign.class);
		this.campaign.setDraftMode(true);
		this.campaign.setManager(spokesperson);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		int projectId;
		Project project;

		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		projectId = super.getRequest().getData("project", int.class);
		if (projectId != 0) {
			project = this.repository.findProjectById(projectId);
			this.campaign.setProject(project);
		} else
			this.campaign.setProject(null);
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);

		{
			Project project = this.campaign.getProject();
			if (project != null) {
				int spokespersonId = super.getRequest().getPrincipal().getActiveRealm().getId();
				Collection<Project> allowedProjects = this.repository.findProjectsBySpokespersonId(spokespersonId);
				super.state(allowedProjects.contains(project), "project", "acme.validation.project.not-allowed");
			}
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		Collection<Project> projects;
		SelectChoices projectChoices;

		int spokespersonId = super.getRequest().getPrincipal().getActiveRealm().getId();
		projects = this.repository.findProjectsBySpokespersonId(spokespersonId);
		projectChoices = SelectChoices.from(projects, "title", this.campaign.getProject());

		tuple = super.unbindObject(this.campaign, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo", //
			"draftMode", "monthsActive", "effort");
		tuple.put("projectChoices", projectChoices);
		tuple.put("project", projectChoices.getSelected().getKey());
	}

}
