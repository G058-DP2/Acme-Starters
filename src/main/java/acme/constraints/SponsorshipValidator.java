
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.SponsorshipRepository;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	@Autowired
	private SponsorshipRepository repository;


	@Override
	public void initialize(final ValidSponsorship constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {

		assert context != null;

		if (sponsorship == null)
			return true;

		if (sponsorship.getTicker() != null) {
			Sponsorship existing = this.repository.findByTicker(sponsorship.getTicker());

			boolean uniqueTicker = existing == null || existing.getId() == sponsorship.getId();

			super.state(context, uniqueTicker, "ticker", "acme.validation.sponsorship.ticker.non-unique");
		}

		if (!sponsorship.isDraftMode()) {

			Integer donationsCount = this.repository.countDonationsBySponsorshipId(sponsorship.getId());
			boolean hasDonations = donationsCount != null && donationsCount >= 1;

			super.state(context, hasDonations, "draftMode", "acme.validation.sponsorship.donations.error");

			Date start = sponsorship.getStartMoment();
			Date end = sponsorship.getEndMoment();

			boolean validDates = start != null && end != null && MomentHelper.isAfter(end, start);

			super.state(context, validDates, "startMoment", "acme.validation.sponsorship.dates.error");
		}

		// Model constraint: sponsorship can only be attached to published projects
		if (sponsorship.getProject() != null) {
			boolean projectPublished = !sponsorship.getProject().isDraftMode();
			super.state(context, projectPublished, "*", "acme.validation.sponsorship.project.must-be-published");
		}

		// Model constraint: sponsorship startMoment must be after project closeOutMoment
		if (sponsorship.getProject() != null && sponsorship.getStartMoment() != null) {
			Date projectCloseOut = sponsorship.getProject().getCloseOutMoment();
			if (projectCloseOut != null) {
				boolean sponsorshipAfterCloseOut = MomentHelper.isAfter(sponsorship.getStartMoment(), projectCloseOut);
				super.state(context, sponsorshipAfterCloseOut, "startMoment", "acme.validation.sponsorship.start-before-project-closeout");
			}
		}

		return !super.hasErrors(context);
	}
}
