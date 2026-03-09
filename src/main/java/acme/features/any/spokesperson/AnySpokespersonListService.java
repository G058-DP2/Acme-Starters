
package acme.features.any.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AnySpokespersonListService extends AbstractService<Any, Spokesperson> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySpokespersonRepository	repository;

	private Spokesperson				spokesperson;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("campaignId", int.class);
		this.spokesperson = this.repository.findSpokespersonById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed", "identity.name", "identity.surname", "identity.email");
	}
}
