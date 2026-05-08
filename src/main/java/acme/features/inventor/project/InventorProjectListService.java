package acme.features.inventor.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.realms.Inventor;

@Service
public class InventorProjectListService extends AbstractService<Inventor, Project> {

	@Autowired
	private InventorProjectRepository repository;

	private Collection<Project> projects;

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int inventorId;

		inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.projects = this.repository.findProjectsByInventorId(inventorId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "kickOffMoment", "closeOutMoment", "draftMode");
	}
}
