
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	@Autowired
	private PartRepository		repository;

	private Invention			invention;
	private Collection<Part>	parts;


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repository.findInventionById(inventionId);
		this.parts = this.repository.findPartsByInventionId(inventionId);
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
		boolean showCreate;

		super.unbindObjects(this.parts, "name", "cost", "kind");

		showCreate = this.invention.isDraftMode() && this.invention.getInventor().isPrincipal();
		super.unbindGlobal("inventionId", this.invention.getId());
		super.unbindGlobal("showCreate", showCreate);
	}

}
