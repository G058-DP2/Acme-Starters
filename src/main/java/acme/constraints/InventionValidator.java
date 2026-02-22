
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.invention.Invention;
import acme.features.inventor.part.PartRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	@Autowired
	private PartRepository repository;


	@Override
	public void initialize(final ValidInvention constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {

		assert context != null;

		if (invention == null)
			return true;

		Date now = MomentHelper.getBaseMoment();
		Date start = invention.getStartMoment();
		Date end = invention.getEndMoment();

		boolean validDates = start != null && end != null && !start.before(now) && end.after(start);
		super.state(context, validDates, "*", "acme.validation.invention.dates.error");

		if (invention.getDraftMode() != null && !invention.getDraftMode()) {
			Integer partsCount = this.repository.countPartsByInventionId(invention.getId());
			boolean hasParts = partsCount != null && partsCount > 0;

			super.state(context, hasParts, "draftMode", "acme.validation.invention.parts.error");
		}

		return !super.hasErrors(context);

	}

}
