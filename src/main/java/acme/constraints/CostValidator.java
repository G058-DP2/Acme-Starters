
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.datatypes.Money;
import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;

@Validator
public class CostValidator extends AbstractValidator<ValidCost, Money> {

	@Override
	protected void initialise(final ValidCost annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Money cost, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (cost == null)
			result = true;
		else {
			{
				boolean isEUR = cost.getCurrency() != null && cost.getCurrency().trim().equalsIgnoreCase("EUR");

				super.state(context, isEUR, "cost", "acme.validation.money.eur.error");
			}
			{
				boolean isPositive = cost.getAmount() != null && cost.getAmount() >= 0.00;

				super.state(context, isPositive, "cost", "acme.validation.money.amount.error");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
