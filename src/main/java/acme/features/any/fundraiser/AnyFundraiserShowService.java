
package acme.features.any.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.realms.Fundraiser;

@Service
public class AnyFundraiserShowService extends AbstractService<Any, Fundraiser> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyFundraiserRepository	repository;

	private Fundraiser				fundraiser;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.fundraiser = this.repository.findFundraiserById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.fundraiser != null;

		super.setAuthorised(status);
	}

	private SelectChoices getAgentChoices(final Boolean selected) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("false", "any.fundraiser.form.value.false", Boolean.FALSE.equals(selected));
		result.add("true", "any.fundraiser.form.value.true", Boolean.TRUE.equals(selected));

		return result;
	}

	@Override
	public void unbind() {
		SelectChoices agentChoices;

		agentChoices = this.getAgentChoices(this.fundraiser.getAgent());

		super.unbindObject(this.fundraiser, "bank", "statement", "agent", "identity.name", "identity.surname", "identity.email");
		super.getResponse().addGlobal("agentChoices", agentChoices);
	}

}
