
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Auditor;

@Service
public class AuthenticatedAuditorCreateService extends AbstractService<Authenticated, Auditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAuditorRepository	repository;

	private Auditor							Auditor;

	// AbstractService inteface -----------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		this.Auditor = super.newObject(Auditor.class);
		this.Auditor.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !this.getRequest().getPrincipal().hasRealmOfType(Auditor.class);

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.Auditor, "firm", "highlights", "solicitor");
	}

	@Override
	public void validate() {
		super.validateObject(this.Auditor);
	}

	@Override
	public void execute() {
		this.repository.save(this.Auditor);
	}

	private SelectChoices getsolicitorChoices(final Boolean selected) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("false", "authenticated.auditor.form.value.false", Boolean.FALSE.equals(selected));
		result.add("true", "authenticated.auditor.form.value.true", Boolean.TRUE.equals(selected));

		return result;
	}

	@Override
	public void unbind() {
		SelectChoices solicitorChoices;

		solicitorChoices = this.getsolicitorChoices(this.Auditor.getSolicitor());

		super.unbindObject(this.Auditor, "firm", "highlights", "solicitor");
		super.getResponse().addGlobal("solicitorChoices", solicitorChoices);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
