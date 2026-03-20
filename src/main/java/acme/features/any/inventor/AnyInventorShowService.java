
package acme.features.any.inventor;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.realms.Inventor;

public class AnyInventorShowService extends AbstractService<Any, Inventor> {

	@Autowired
	private AnyInventorRepository	repository;

	private Inventor				inventor;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.inventor = this.repository.findInventorById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.inventor != null;
		super.setAuthorised(status);
	}

	private SelectChoices getLicensedChoices(final Boolean selected) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("false", "any.inventor.form.value.false", Boolean.FALSE.equals(selected));
		result.add("true", "any.inventor.form.value.true", Boolean.TRUE.equals(selected));

		return result;
	}

	@Override
	public void unbind() {
		SelectChoices licensedChoices;

		licensedChoices = this.getLicensedChoices(this.inventor.getLicensed());

		super.unbindObject(this.inventor, "bio", "keyWords", "licensed", //
			"identity.name", "identity.surname", "identity.email");
		super.getResponse().addGlobal("licensedChoices", licensedChoices);
	}

}
