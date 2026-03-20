
package acme.features.any.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AnySpokespersonShowService extends AbstractService<Any, Spokesperson> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySpokespersonRepository	repository;

	private Spokesperson				spokesperson;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.spokesperson = this.repository.findSpokespersonById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.spokesperson != null;

		super.setAuthorised(status);
	}

	private SelectChoices getLicensedChoices(final Boolean selected) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("false", "any.spokesperson.form.value.false", Boolean.FALSE.equals(selected));
		result.add("true", "any.spokesperson.form.value.true", Boolean.TRUE.equals(selected));

		return result;
	}

	@Override
	public void unbind() {
		SelectChoices licensedChoices;

		licensedChoices = this.getLicensedChoices(this.spokesperson.getLicensed());

		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed", "identity.name", "identity.surname", "identity.email");
		super.getResponse().addGlobal("licensedChoices", licensedChoices);
	}
}
