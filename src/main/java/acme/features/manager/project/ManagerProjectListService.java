package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectListService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;

	private Collection<Project> projects;

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.projects = this.repository.findProjectsByManagerId(managerId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "kickOffMoment", "closeOutMoment", "draftMode");
	}
}
