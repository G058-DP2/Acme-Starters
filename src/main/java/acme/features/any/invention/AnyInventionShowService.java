
package acme.features.any.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	@Autowired
	private AnyInventionRepository	repository;

	private Invention				invention;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && !this.invention.isDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", //
			"description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("cost", this.invention.getCost());
		tuple.put("monthsActive", this.invention.getMonthsActive());
		tuple.put("inventorId", this.invention.getInventor().getId());
	}

}
