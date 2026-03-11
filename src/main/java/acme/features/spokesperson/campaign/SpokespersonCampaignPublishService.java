
package acme.features.spokesperson.campaign;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && //
			this.campaign.isDraftMode() && //
			this.campaign.getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {

		super.validateObject(this.campaign);
		{
			Collection<Milestone> milestones;
			boolean hasMilestones;

			milestones = this.repository.findMilestonesByCampaignId(this.campaign.getId());
			hasMilestones = milestones != null && !milestones.isEmpty();
			super.state(hasMilestones, "totalEffort", "acme.validation.campaign.milestones.error");
		}

		{
			Date start;
			Date end;
			boolean validInterval;

			start = this.campaign.getStartMoment();
			end = this.campaign.getEndMoment();
			validInterval = start != null && end != null && MomentHelper.isAfter(end, start);
			super.state(validInterval, "startMoment", "acme.validation.campaign.dates.error");
		}

		{
			Date now;
			Date start;
			Date end;
			boolean startInFuture;
			boolean endInFuture;

			now = MomentHelper.getCurrentMoment();
			start = this.campaign.getStartMoment();
			end = this.campaign.getEndMoment();

			startInFuture = start != null && MomentHelper.isAfter(start, now);
			super.state(startInFuture, "startMoment", "acme.validation.campaign.startMoment.future");

			endInFuture = end != null && MomentHelper.isAfter(end, now);
			super.state(endInFuture, "endMoment", "acme.validation.campaign.endMoment.future");
		}
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {

		Tuple tuple;

		tuple = super.unbindObject(this.campaign, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		tuple.put("draftMode", this.campaign.isDraftMode());
		tuple.put("monthsActive", this.campaign.getMonthsActive());
		tuple.put("effort", this.campaign.getEffort());

	}
}
