
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
public class InventorInventionShowService extends AbstractService<Inventor, Invention> {

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
			(this.invention.getInventor().isPrincipal() || !this.invention.isDraftMode());
		super.setAuthorised(status);
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
