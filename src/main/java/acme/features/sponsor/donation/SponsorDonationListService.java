// src/main/java/acme/features/sponsor/donation/SponsorDonationListService.java

package acme.features.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationListService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private SponsorDonationRepository	repository;

	private Sponsorship					sponsorship;
	private Collection<Donation>		donations;

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

		// Owner always sees; others only if published
		status = this.sponsorship != null && //
			(this.sponsorship.getSponsor().isPrincipal() || !this.sponsorship.isDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		super.unbindObjects(this.donations, "name", "money", "kind");

		// Show create button only if owner and still in draft
		showCreate = this.sponsorship.isDraftMode() && this.sponsorship.getSponsor().isPrincipal();
		super.unbindGlobal("sponsorshipId", this.sponsorship.getId());
		super.unbindGlobal("showCreate", showCreate);
	}
}
