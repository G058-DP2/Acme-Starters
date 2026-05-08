
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.project.Project;
import acme.realms.Inventor;

@Service
public class InventorInventionUpdateService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventionRepository	repository;

	private Invention			invention;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && //
			this.invention.isDraftMode() && //
			this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int projectId;
		Project project;

		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		projectId = super.getRequest().getData("project", int.class);
		if (projectId != 0) {
			project = this.repository.findProjectById(projectId);
			this.invention.setProject(project);
		} else
			this.invention.setProject(null);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		{
			Project project = this.invention.getProject();
			if (project != null) {
				int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
				Collection<Project> allowedProjects = this.repository.findProjectsByInventorId(inventorId);
				super.state(allowedProjects.contains(project), "project", "acme.validation.project.not-allowed");
			}
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		Collection<Project> projects;
		SelectChoices projectChoices;

		int inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		projects = this.repository.findProjectsByInventorId(inventorId);
		projectChoices = SelectChoices.from(projects, "title", this.invention.getProject());

		tuple = super.unbindObject(this.invention, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo", //
			"draftMode", "monthsActive", "cost");
		tuple.put("projectChoices", projectChoices);
		tuple.put("project", projectChoices.getSelected().getKey());
	}

}
