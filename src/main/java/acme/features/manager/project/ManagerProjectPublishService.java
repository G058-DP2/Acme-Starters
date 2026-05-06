
package acme.features.manager.project;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.invention.Invention;
import acme.entities.project.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository	repository;

	private Project						project;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && this.project.isDraftMode() && this.project.getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);

		// Must have at least one invention
		{
			Integer inventionCount = this.repository.countInventionsByProjectId(this.project.getId());
			boolean hasInventions = inventionCount != null && inventionCount >= 1;
			super.state(hasInventions, "*", "acme.validation.project.no-inventions");
		}

		// Valid date interval
		{
			Date kickOff = this.project.getKickOffMoment();
			Date closeOut = this.project.getCloseOutMoment();
			boolean validInterval = kickOff != null && closeOut != null && MomentHelper.isAfter(closeOut, kickOff);
			super.state(validInterval, "kickOffMoment", "acme.validation.project.invalid-interval");
		}

		// Both dates must be in the future at publish time
		{
			Date now;
			Date kickOff;
			Date closeOut;
			boolean kickOffInFuture;
			boolean closeOutInFuture;

			now = MomentHelper.getCurrentMoment();
			kickOff = this.project.getKickOffMoment();
			closeOut = this.project.getCloseOutMoment();

			kickOffInFuture = kickOff != null && MomentHelper.isAfter(kickOff, now);
			super.state(kickOffInFuture, "kickOffMoment", "acme.validation.project.kickOffMoment.future");

			closeOutInFuture = closeOut != null && MomentHelper.isAfter(closeOut, now);
			super.state(closeOutInFuture, "closeOutMoment", "acme.validation.project.closeOutMoment.future");
		}

		// All draft components must meet their own publish requirements
		{
			Date now = MomentHelper.getCurrentMoment();

			Collection<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
			for (Invention i : inventions)
				if (i.isDraftMode())
					super.state(this.canPublishInvention(i, now), "*", "acme.validation.project.unpublishable-component");

			Collection<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
			for (Campaign c : campaigns)
				if (c.isDraftMode())
					super.state(this.canPublishCampaign(c, now), "*", "acme.validation.project.unpublishable-component");

			Collection<Strategy> strategies = this.repository.findStrategiesByProjectId(this.project.getId());
			for (Strategy s : strategies)
				if (s.isDraftMode())
					super.state(this.canPublishStrategy(s, now), "*", "acme.validation.project.unpublishable-component");
		}

		// Component time intervals must be contained within the project's kick-off/close-out interval
		// (publication/functionality constraint - must live here, not in ProjectValidator, because
		//  the Project is still in draftMode when validateObject is called above)
		{
			Date kickOff = this.project.getKickOffMoment();
			Date closeOut = this.project.getCloseOutMoment();

			if (kickOff != null && closeOut != null) {
				Collection<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
				for (Invention i : inventions)
					if (i.getStartMoment() != null && i.getEndMoment() != null) {
						boolean contained = !MomentHelper.isBefore(i.getStartMoment(), kickOff) && !MomentHelper.isAfter(i.getEndMoment(), closeOut);
						super.state(contained, "*", "acme.validation.project.component-out-of-interval");
					}

				Collection<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
				for (Campaign c : campaigns)
					if (c.getStartMoment() != null && c.getEndMoment() != null) {
						boolean contained = !MomentHelper.isBefore(c.getStartMoment(), kickOff) && !MomentHelper.isAfter(c.getEndMoment(), closeOut);
						super.state(contained, "*", "acme.validation.project.component-out-of-interval");
					}

				Collection<Strategy> strategies = this.repository.findStrategiesByProjectId(this.project.getId());
				for (Strategy s : strategies)
					if (s.getStartMoment() != null && s.getEndMoment() != null) {
						boolean contained = !MomentHelper.isBefore(s.getStartMoment(), kickOff) && !MomentHelper.isAfter(s.getEndMoment(), closeOut);
						super.state(contained, "*", "acme.validation.project.component-out-of-interval");
					}
			}
		}
	}

	// Helper: returns true only if start and end form a valid future interval relative to now.
	private boolean hasValidFutureInterval(final Date start, final Date end, final Date now) {
		return start != null && end != null //
			&& MomentHelper.isAfter(end, start) //
			&& MomentHelper.isAfter(start, now) //
			&& MomentHelper.isAfter(end, now);
	}

	// Helper: returns true only if the invention satisfies all its own publish constraints.
	private boolean canPublishInvention(final Invention invention, final Date now) {
		if (!this.hasValidFutureInterval(invention.getStartMoment(), invention.getEndMoment(), now))
			return false;
		Integer partCount = this.repository.countPartsByInventionId(invention.getId());
		return partCount != null && partCount >= 1;
	}

	// Helper: returns true only if the campaign satisfies all its own publish constraints.
	private boolean canPublishCampaign(final Campaign campaign, final Date now) {
		if (!this.hasValidFutureInterval(campaign.getStartMoment(), campaign.getEndMoment(), now))
			return false;
		Integer milestoneCount = this.repository.countMilestonesByCampaignId(campaign.getId());
		return milestoneCount != null && milestoneCount >= 1;
	}

	// Helper: returns true only if the strategy satisfies all its own publish constraints.
	private boolean canPublishStrategy(final Strategy strategy, final Date now) {
		if (!this.hasValidFutureInterval(strategy.getStartMoment(), strategy.getEndMoment(), now))
			return false;
		Integer tacticCount = this.repository.countTacticsByStrategyId(strategy.getId());
		return tacticCount != null && tacticCount >= 1;
	}

	@Override
	public void execute() {
		// Publishing a project implies auto-publishing its components only when they
		// satisfy their own publication constraints. Invalid components remain in draft mode.
		Date now = MomentHelper.getCurrentMoment();

		Collection<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
		for (Invention i : inventions)
			if (i.isDraftMode() && this.canPublishInvention(i, now)) {
				i.setDraftMode(false);
				this.repository.save(i);
			}

		Collection<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
		for (Campaign c : campaigns)
			if (c.isDraftMode() && this.canPublishCampaign(c, now)) {
				c.setDraftMode(false);
				this.repository.save(c);
			}

		Collection<Strategy> strategies = this.repository.findStrategiesByProjectId(this.project.getId());
		for (Strategy s : strategies)
			if (s.isDraftMode() && this.canPublishStrategy(s, now)) {
				s.setDraftMode(false);
				this.repository.save(s);
			}

		this.project.setDraftMode(false);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo", "draftMode");
	}
}
