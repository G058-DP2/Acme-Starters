package acme.features.any.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.project.Project;

@Service
public class AnyProjectShowService extends AbstractService<Any, Project> {

	@Autowired
	private AnyProjectRepository repository;

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

		status = this.project != null && !this.project.isDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		tuple = super.unbindObject(this.project, "title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo", "draftMode");

		tuple.put("managerId", this.project.getManager().getId());
	}
}
