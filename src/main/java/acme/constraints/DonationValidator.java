
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.sponsorship.Donation;

@Validator
public class DonationValidator extends AbstractValidator<ValidDonation, Donation> {

	@Override
	public void initialize(final ValidDonation constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final Donation donation, final ConstraintValidatorContext context) {

		assert context != null;
		if (donation == null)
			return true;

		boolean isEur = donation.getMoney() != null && donation.getMoney().getCurrency() != null && donation.getMoney().getCurrency().trim().equalsIgnoreCase("EUR");

		super.state(context, isEur, "money", "acme.validation.money.eur.error");

		return !super.hasErrors(context);
	}
}
