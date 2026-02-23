
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.invention.Invention;
import acme.features.inventor.invention.InventionRepository;
import acme.features.inventor.part.PartRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	@Autowired
	private PartRepository		partRepository;

	@Autowired
	private InventionRepository	inventionRepository;


	@Override
	public void initialize(final ValidInvention constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {

		assert context != null;

		if (invention == null)
			return true;
		else {
			{
				Invention existingInvention = this.inventionRepository.findInventionByTicker(invention.getTicker());
				boolean uniqueInvention = existingInvention != null && existingInvention.equals(invention);

				super.state(context, uniqueInvention, "ticker", "acme.validation.invention.ticker.non-unique");

			}
			{
				if (invention.getDraftMode() != null && !invention.getDraftMode()) {
					Integer partsCount = this.partRepository.countPartsByInventionId(invention.getId());
					boolean hasParts = partsCount != null && partsCount > 0;

					super.state(context, hasParts, "draftMode", "acme.validation.invention.parts.error");
				}
			}
			{
				Date now = MomentHelper.getBaseMoment();
				Date start = invention.getStartMoment();
				Date end = invention.getEndMoment();
				boolean datesInFuture = start != null && end != null && !start.before(now) && end.after(start);

				boolean validPublishedInvention = !invention.getDraftMode() && datesInFuture;

				super.state(context, validPublishedInvention, "*", "acme.validation.invention.dates.error");
			}

		}

		return !super.hasErrors(context);

	}

}
