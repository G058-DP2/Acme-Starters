
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;
import acme.realms.Inventor;

@Service
public class InventorInventionDeleteService extends AbstractService<Inventor, Invention> {

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
			this.invention.isDraftMode() && //
			this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		// No extra validations needed for delete
	}

	@Override
	public void execute() {
		Collection<Part> parts;

		parts = this.repository.findPartsByInventionId(this.invention.getId());
		this.repository.deleteAll(parts);
		this.repository.delete(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo", //
			"draftMode", "monthsActive", "cost");
	}

}
