package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;

	private Project project;

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null &&
			this.project.isDraftMode() &&
			this.project.getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
	}

	@Override
	public void execute() {
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project,
			"title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo",
			"draftMode");
	}
}
