
package acme.features.any.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Service
public class AnyTacticListService extends AbstractService<Any, Tactic> {

	@Autowired
	private AnyTacticRepository	repository;

	private Collection<Tactic>	tactics;


	@Override
	public void load() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findTacticByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		boolean status;

		int strategyId = super.getRequest().getData("strategyId", int.class);
		Strategy strategy = this.repository.findStrategyById(strategyId);

		status = strategy != null && !strategy.isDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "notes", "expectedPercentage", "kind");
	}

}
