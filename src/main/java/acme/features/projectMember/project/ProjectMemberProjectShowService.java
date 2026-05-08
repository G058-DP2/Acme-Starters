package acme.features.projectMember.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberProjectShowService extends AbstractService<ProjectMember, Project> {

	@Autowired
	private ProjectMemberProjectRepository repository;

	private Project project;

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		// A ProjectMember can see the project only if they belong to it
		// (as Manager, Inventor, Spokesperson, or Fundraiser).
		int accountId;
		Collection<Project> accessible;

		accountId = super.getRequest().getPrincipal().getAccountId();
		accessible = this.repository.findProjectsByAccountId(accountId);

		boolean status = this.project != null && accessible.stream().anyMatch(p -> p.getId() == this.project.getId());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.project, "title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo", "draftMode", "publishedOn");
		tuple.put("managerId", this.project.getManager().getId());
		super.getResponse().addData(tuple);
	}

}
