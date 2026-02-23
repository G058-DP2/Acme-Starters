
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.invention.Part;

@Validator
public class PartValidator extends AbstractValidator<ValidPart, Part> {

	@Override
	public void initialize(final ValidPart constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final Part part, final ConstraintValidatorContext context) {

		assert context != null;

		if (part == null)
			return true;
		else {
			boolean validCurrency = part.getCost().getCurrency().equalsIgnoreCase("EUR");

			super.state(context, validCurrency, "cost", "acme.validation.part.cost.error-currency");
		}

		return !super.hasErrors(context);
	}

}
