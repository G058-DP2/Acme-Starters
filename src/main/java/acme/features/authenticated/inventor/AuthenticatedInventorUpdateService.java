
package acme.features.authenticated.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Inventor;

@Service
public class AuthenticatedInventorUpdateService extends AbstractService<Authenticated, Inventor> {

	@Autowired
	private AuthenticatedInventorRepository	repository;

	private Inventor						inventor;


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.inventor = this.repository.findInventorByUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Inventor.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.inventor, "bio", "keyWords", "licensed");
	}

	@Override
	public void validate() {
		super.validateObject(this.inventor);
	}

	@Override
	public void execute() {
		this.repository.save(this.inventor);
	}

	// Aux method
	private SelectChoices getLicensedChoices(final Boolean selected) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("true", "authenticated.inventor.form.value.true", Boolean.TRUE.equals(selected));
		result.add("false", "authenticated.inventor.form.value.false", Boolean.FALSE.equals(selected));

		return result;
	}

	@Override
	public void unbind() {
		SelectChoices licensedChoices;
		licensedChoices = this.getLicensedChoices(this.inventor.getLicensed());

		super.unbindObject(this.inventor, "bio", "keyWords", "licensed");
		super.getResponse().addGlobal("licensedChoices", licensedChoices);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
