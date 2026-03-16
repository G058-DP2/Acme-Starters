
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

	private Auditor							auditor;

	// AbstractService inteface -----------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = this.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		this.auditor = super.newObject(Auditor.class);
		this.auditor.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status;

		status = !this.getRequest().getPrincipal().hasRealmOfType(Auditor.class);

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditor, "firm", "highlights", "solicitor");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditor);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditor);
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

		solicitorChoices = this.getsolicitorChoices(this.auditor.getSolicitor());

		super.unbindObject(this.auditor, "firm", "highlights", "solicitor");
		super.getResponse().addGlobal("solicitorChoices", solicitorChoices);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
