
package acme.features.authenticated.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Fundraiser;

@Service
public class AuthenticatedFundraiserCreateService extends AbstractService<Authenticated, Fundraiser> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedFundraiserRepository	repository;

	private Fundraiser							fundraiser;

	// AbstractService inteface -----------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		this.fundraiser = super.newObject(Fundraiser.class);
		this.fundraiser.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !this.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class);

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.fundraiser, "bank", "statement", "agent");
	}

	@Override
	public void validate() {
		super.validateObject(this.fundraiser);
	}

	@Override
	public void execute() {
		this.repository.save(this.fundraiser);
	}

	private SelectChoices getAgentChoices(final Boolean selected) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("false", "authenticated.fundraiser.form.value.false", Boolean.FALSE.equals(selected));
		result.add("true", "authenticated.fundraiser.form.value.true", Boolean.TRUE.equals(selected));

		return result;
	}

	@Override
	public void unbind() {
		SelectChoices agentChoices;

		agentChoices = this.getAgentChoices(this.fundraiser.getAgent());

		super.unbindObject(this.fundraiser, "bank", "statement", "agent");
		super.getResponse().addGlobal("agentChoices", agentChoices);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
