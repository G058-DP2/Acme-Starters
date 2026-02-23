
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private StrategyRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (strategy == null)
			result = true;
		else {
			{
				boolean uniqueStrategy;
				Strategy existingStrategy;

				existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.duplicated-ticker.message");
			}
			{
				boolean hasTactic;

				hasTactic = strategy.isDraftMode() || this.repository.countTacticsByStrategy(strategy.getId()) != 0;

				super.state(context, hasTactic, "*", "acme.validation.strategy.tactics.error.message");
			}
			{
				Date now = MomentHelper.getBaseMoment();
				Date start = strategy.getStartMoment();
				Date end = strategy.getEndMoment();
				boolean validTime;

				if (start != null && end != null)
					validTime = MomentHelper.isAfter(strategy.getStartMoment(), now) && MomentHelper.isAfter(strategy.getEndMoment(), strategy.getStartMoment());
				else
					validTime = false;

				boolean validPublishedStrategy = strategy.isDraftMode() || validTime;

				super.state(context, validPublishedStrategy, "startMoment", "acme.validation.strategy.dates.error");

			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
