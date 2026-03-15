
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyListService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Collection<Strategy>			strategies;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int fundraiserId;

		fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategies = this.repository.findStrategyByFundraiserId(fundraiserId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, "ticker", "name", "startMoment", "endMoment", "draftMode");
	}
}
