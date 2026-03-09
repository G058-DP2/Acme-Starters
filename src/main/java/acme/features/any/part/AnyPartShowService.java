
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Part;

@Service
public class AnyPartShowService extends AbstractService<Any, Part> {

	@Autowired
	private AnyPartRepository	repository;

	private Part				part;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");
		int inventionId = this.part.getInvention().getId();
		tuple.put("inventionId", inventionId);

	}

}
