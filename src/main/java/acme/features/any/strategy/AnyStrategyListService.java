
package acme.features.any.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;

@Service
public class AnyStrategyListService extends AbstractService<Any, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyStrategyRepository	repository;

	private Collection<Strategy>	strategies;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.strategies = this.repository.findPublishedStrategies();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "fundraiser.identity.fullName");
	}
}
