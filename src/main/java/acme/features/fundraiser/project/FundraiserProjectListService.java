package acme.features.fundraiser.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.realms.Fundraiser;

@Service
public class FundraiserProjectListService extends AbstractService<Fundraiser, Project> {

	@Autowired
	private FundraiserProjectRepository repository;

	private Collection<Project> projects;

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int fundraiserId;

		fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.projects = this.repository.findProjectsByFundraiserId(fundraiserId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "kickOffMoment", "closeOutMoment", "draftMode");
	}
}
