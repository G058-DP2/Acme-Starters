
package acme.features.any.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.realms.Sponsor;

@Service
public class AnySponsorShowService extends AbstractService<Any, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorRepository	repository;

	private Sponsor					sponsor;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsor = this.repository.findSponsorById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;

		if (this.sponsor != null) {

			Integer publishedCount = this.repository.countPublishedSponsorshipsBySponsorId(this.sponsor.getId());
			status = publishedCount != null && publishedCount > 0;
		}

		super.setAuthorised(status);
	}

	private SelectChoices getGoldChoices(final Boolean selected) {
		SelectChoices result;

		result = new SelectChoices();
		result.add("false", "any.sponsor.form.value.false", Boolean.FALSE.equals(selected));
		result.add("true", "any.sponsor.form.value.true", Boolean.TRUE.equals(selected));

		return result;
	}

	@Override
	public void unbind() {
		SelectChoices goldChoices;

		goldChoices = this.getGoldChoices(this.sponsor.getGold());

		super.unbindObject(this.sponsor, "address", "im", "gold", "identity.name", "identity.surname", "identity.email");
		super.getResponse().addGlobal("goldChoices", goldChoices);
	}
}
