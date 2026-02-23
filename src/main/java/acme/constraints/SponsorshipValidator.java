
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

		if (sponsorship.getDraftMode() != null && !sponsorship.getDraftMode()) {

			Integer donationsCount = this.repository.countDonationsBySponsorshipId(sponsorship.getId());
			boolean hasDonations = donationsCount != null && donationsCount >= 1;

			super.state(context, hasDonations, "draftMode", "acme.validation.sponsorship.donations.error");

			Date now = MomentHelper.getBaseMoment();
			Date start = sponsorship.getStartMoment();
			Date end = sponsorship.getEndMoment();

			boolean validDates = start != null && end != null && !start.before(now) && end.after(start);

			super.state(context, validDates, "startMoment", "acme.validation.sponsorship.dates.error");
		}

		return !super.hasErrors(context);
	}
}
