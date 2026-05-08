package acme.features.fundraiser.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.realms.Fundraiser;

@Service
public class FundraiserProjectShowService extends AbstractService<Fundraiser, Project> {

	@Autowired
	private FundraiserProjectRepository repository;

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
		int fundraiserId;
		Collection<Project> accessible;

		fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		accessible = this.repository.findProjectsByFundraiserId(fundraiserId);
		status = this.project != null && accessible.stream().anyMatch(p -> p.getId() == this.project.getId());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.project, "title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo", "draftMode");
		tuple.put("managerId", this.project.getManager().getId());
	}
}
