
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionListService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventionRepository		repository;

	private Collection<Invention>	inventions;


	@Override
	public void authorise() {
		boolean status;

		status = this.getRequest().getPrincipal().hasRealmOfType(Inventor.class);

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int inventorId;

		inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.inventions = this.repository.findInventionsByInventorId(inventorId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "startMoment", "endMoment", "draftMode");
	}

}
