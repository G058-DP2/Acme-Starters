
package acme.features.any.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
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

	@Override
	public void unbind() {
		super.unbindObject(this.sponsor, "address", "im", "gold", "identity.name", "identity.surname", "identity.email");
	}
}
