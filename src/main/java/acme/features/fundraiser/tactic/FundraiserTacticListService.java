
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticListService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	FundraiserTacticRepository	repository;

	private Strategy			stragey;
	private Collection<Tactic>	tactics;


	@Override
	public void load() {
		int strategyId;
		strategyId = super.getRequest().getData("strategyId", int.class);
		this.stragey = this.repository.findStrategyBiId(strategyId);
		this.tactics = this.repository.findTacticsByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.stragey != null && //
			(this.stragey.getFundraiser().isPrincipal() || !this.stragey.isDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		super.unbindObjects(this.tactics, "name", "expectedPercentage", "kind");
		showCreate = this.stragey.isDraftMode() && this.stragey.getFundraiser().isPrincipal();
		super.unbindGlobal("strategyId", this.stragey.getId());
		super.unbindGlobal("showCreate", showCreate);
	}

}
