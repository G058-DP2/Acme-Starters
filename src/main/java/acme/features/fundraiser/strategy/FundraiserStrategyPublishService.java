
package acme.features.fundraiser.strategy;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyPublishService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && //
			this.strategy.isDraftMode() && //
			this.strategy.getFundraiser().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {

		super.validateObject(this.strategy);
		{
			Collection<Tactic> tactics;
			boolean hasTacticts;

			tactics = this.repository.findTacticsByStrategyId(this.strategy.getId());
			hasTacticts = tactics != null && !tactics.isEmpty();
			super.state(hasTacticts, "expectedPercentage", "acme.validation.strategy.tactics.error.message");
		}

		{
			Date start;
			Date end;
			boolean validInterval;

			start = this.strategy.getStartMoment();
			end = this.strategy.getEndMoment();
			validInterval = start != null && end != null && MomentHelper.isAfter(end, start);
			super.state(validInterval, "startMoment", "acme.validation.strategy.dates.error");
		}
		{
			Date now;
			Date start;
			Date end;
			boolean startInFuture;
			boolean endInFuture;

			now = MomentHelper.getCurrentMoment();
			start = this.strategy.getStartMoment();
			end = this.strategy.getEndMoment();

			startInFuture = start != null && MomentHelper.isAfter(start, now);
			super.state(startInFuture, "startMoment", "acme.validation.strategy.startMoment.future");

			endInFuture = end != null && MomentHelper.isAfter(end, now);
			super.state(endInFuture, "endMoment", "acme.validation.strategy.endMoment.future");
		}
	}

	@Override
	public void execute() {
		this.strategy.setDraftMode(false);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.strategy, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		tuple.put("draftMode", this.strategy.isDraftMode());
		tuple.put("monthsActive", this.strategy.getMonthsActive());
		tuple.put("expectedPercentage", this.strategy.getExpectedPercentage());

	}

}
