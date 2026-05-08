package acme.features.spokesperson.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.realms.Spokesperson;

@Service
public class SpokespersonProjectListService extends AbstractService<Spokesperson, Project> {

	@Autowired
	private SpokespersonProjectRepository repository;

	private Collection<Project> projects;

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int spokespersonId;

		spokespersonId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.projects = this.repository.findProjectsBySpokespersonId(spokespersonId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "kickOffMoment", "closeOutMoment", "draftMode");
	}
}
