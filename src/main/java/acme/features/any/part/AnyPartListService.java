
package acme.features.any.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;

public class AnyPartListService extends AbstractService<Any, Part> {

	@Autowired
	private AnyPartRepository	repository;

	private Collection<Part>	parts;


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.parts = this.repository.findPartsByInventionId(inventionId);
	}

	@Override
	public void authorise() {
		int inventionId = super.getRequest().getData("inventionId", int.class);
		Invention invention = this.repository.findInventionById(inventionId);

		boolean status = invention != null && !invention.isDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
	}

}
