
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.realms.Auditor;

@Service
public class AuthenticatedAuditorUpdateService extends AbstractService<Authenticated, Auditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAuditorRepository	repository;

	private Auditor							Auditor;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.Auditor = this.repository.findAuditorByUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		boolean status;

		int id = super.getRequest().getPrincipal().getAccountId();

		status = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class); // && id == this.Auditor.getUserAccount().getId();

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

}
