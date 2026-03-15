
package acme.features.authenticated.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AuthenticatedSpokespersonUpdateService extends AbstractService<Authenticated, Spokesperson> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedSpokespersonRepository	repository;

	private Spokesperson						spokesperson;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.spokesperson = this.repository.findSpokespersonByUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

	@Override
	public void validate() {
		super.validateObject(this.spokesperson);
	}

	@Override
	public void execute() {
		this.repository.save(this.spokesperson);
	}

	private SelectChoices getLicensedChoices(final Boolean licensed) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("false", "authenticated.spokesperson.form.value.false", Boolean.FALSE.equals(licensed));
		result.add("true", "authenticated.spokesperson.form.value.true", Boolean.TRUE.equals(licensed));

		return result;
	}

	@Override
	public void unbind() {
		SelectChoices licensedChoices;

		licensedChoices = this.getLicensedChoices(this.spokesperson.getLicensed());

		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");
		super.getResponse().addGlobal("licensedChoices", licensedChoices);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
