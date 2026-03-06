
package acme.features.any.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Sponsor;

@Service
public class AnySponsorListService extends AbstractService<Any, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorRepository	repository;

	private Sponsor					sponsor;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsor = this.repository.findSponsorById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsor, "address", "im", "gold", "identity.name", "identity.surname", "identity.email");
	}
}
