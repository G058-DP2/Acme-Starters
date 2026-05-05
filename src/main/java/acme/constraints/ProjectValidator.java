
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.project.Project;

@Validator
public class ProjectValidator extends AbstractValidator<ValidProject, Project> {

	@Override
	public void initialize(final ValidProject constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final Project project, final ConstraintValidatorContext context) {

		assert context != null;

		if (project == null)
			return true;

		// Data-model constraint: kickOff must be strictly before closeOut when draftMode is false
		if (!project.isDraftMode()) {
			Date kickOff = project.getKickOffMoment();
			Date closeOut = project.getCloseOutMoment();

			if (kickOff != null && closeOut != null) {
				boolean validInterval = MomentHelper.isAfter(closeOut, kickOff);
				super.state(context, validInterval, "kickOffMoment", "acme.validation.project.invalid-interval");
			}
		}

		// NOTE: Publication-time constraints (future dates, component intervals, ≥1 invention)
		// are enforced in ManagerProjectPublishService.validate(), not here.

		return !super.hasErrors(context);
	}
}
