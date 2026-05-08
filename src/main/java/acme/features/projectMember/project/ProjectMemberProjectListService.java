package acme.features.projectMember.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberProjectListService extends AbstractService<ProjectMember, Project> {

	@Autowired
	private ProjectMemberProjectRepository repository;

	private Collection<Project> projects;

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int accountId;

		accountId = super.getRequest().getPrincipal().getAccountId();
		this.projects = this.repository.findProjectsByAccountId(accountId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "kickOffMoment", "closeOutMoment", "draftMode");
	}

}
