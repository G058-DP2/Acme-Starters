// src/main/java/acme/features/sponsor/donation/SponsorDonationUpdateService.java

package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.DonationKind;
import acme.realms.Sponsor;

@Service
public class SponsorDonationUpdateService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private SponsorDonationRepository	repository;

	private Donation					donation;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.donation = this.repository.findDonationById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.donation != null && //
			this.donation.getSponsorship().isDraftMode() && //
			this.donation.getSponsorship().getSponsor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.donation);
	}

	@Override
	public void execute() {
		this.repository.save(this.donation);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(DonationKind.class, this.donation.getKind());

		tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tuple.put("sponsorshipId", this.donation.getSponsorship().getId());
		tuple.put("draftMode", this.donation.getSponsorship().isDraftMode());
		tuple.put("kinds", choices);

	}
}
