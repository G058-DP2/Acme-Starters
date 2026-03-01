
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
		if (campaign.getDraftMode() != null && !campaign.getDraftMode()) {

			// 2) Debe tener al menos 1 milestone
			int id = campaign.getId();
			Integer milestoneCount = this.repository.countMilestonesByCampaignId(id);
			boolean hasMilestones = milestoneCount != null && milestoneCount >= 1;
			super.state(context, hasMilestones, "draftMode", "acme.validation.campaign.no-milestones");

			// 3) start/end: intervalo válido en futuro respecto a "ahora"
			Date now = MomentHelper.getBaseMoment();
			Date start = campaign.getStartMoment();
			Date end = campaign.getEndMoment();

			boolean validInterval = start != null && end != null && !start.before(now) && end.after(start);
			super.state(context, validInterval, "startMoment", "acme.validation.campaign.invalid-interval");

			// 4) effort (suma milestones) debe ser > 0 cuando se publica
			Double sum = this.repository.calculateTotalEffortByCampaignId(id);
			boolean positive = sum != null && sum >= 0.0;
			super.state(context, positive, "draftMode", "acme.validation.campaign.effort-not-positive");
		}

		return !super.hasErrors(context);
	}
}
