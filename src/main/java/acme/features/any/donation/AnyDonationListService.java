
package acme.features.any.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.Sponsorship;

@Service
public class AnyDonationListService extends AbstractService<Any, Donation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyDonationRepository	repository;

	private Collection<Donation>	donations;

	private Sponsorship				sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int sponsorshipId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		this.donations = this.repository.findDonationsBySponsorshipId(sponsorshipId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && !this.sponsorship.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.donations, "name", "notes", "money", "kind");
	}
}
