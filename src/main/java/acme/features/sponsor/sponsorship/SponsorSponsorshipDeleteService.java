// src/main/java/acme/features/sponsor/sponsorship/SponsorSponsorshipDeleteService.java

package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		// Only the owner can delete, and only while in draftMode
		status = this.sponsorship != null && //
			this.sponsorship.isDraftMode() && //
			this.sponsorship.getSponsor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		// No extra validations needed for delete
	}

	@Override
	public void execute() {
		Collection<Donation> donations;

		// Cascade-delete all linked donations first
		donations = this.repository.findDonationsBySponsorshipId(this.sponsorship.getId());
		this.repository.deleteAll(donations);
		this.repository.delete(this.sponsorship);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.sponsorship, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo", //
			"draftMode", "monthsActive", "totalMoney");

	}
}
