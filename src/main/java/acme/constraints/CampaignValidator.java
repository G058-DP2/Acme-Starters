
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.CampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	@Autowired
	private CampaignRepository repository;


	@Override
	public void initialize(final ValidCampaign constraintAnnotation) {
		assert constraintAnnotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {

		assert context != null;

		if (campaign == null)
			return true;

		// 1) ticker único
		if (campaign.getTicker() != null) {
			Campaign existing = this.repository.findByTicker(campaign.getTicker());
			boolean unique = existing == null || existing.getId() == campaign.getId();
			super.state(context, unique, "ticker", "acme.validation.campaign.ticker.duplicated");
		}

		// Reglas que SOLO aplican cuando se publica (draftMode = false)
		if (!campaign.isDraftMode()) {

			// 2) Debe tener al menos 1 milestone
			int id = campaign.getId();
			Integer milestoneCount = this.repository.countMilestonesByCampaignId(id);
			boolean hasMilestones = milestoneCount != null && milestoneCount >= 1;
			super.state(context, hasMilestones, "draftMode", "acme.validation.campaign.no-milestones");

			// 3) start/end: intervalo válido en futuro a la hora de publicar
			Date start = campaign.getStartMoment();
			Date end = campaign.getEndMoment();

			boolean validDates = start != null && end != null && MomentHelper.isAfter(end, start);
			super.state(context, validDates, "startMoment", "acme.validation.campaign.invalid-interval");

		}

		return !super.hasErrors(context);
	}
}
