
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;

@Service
public class AnyInventionListService extends AbstractService<Any, Invention> {

	@Autowired
	private AnyInventionRepository	repository;

	private Collection<Invention>	inventions;


	@Override
	public void load() {
		this.inventions = this.repository.findPublishedInventions();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, //
			"ticker", "name", "description", //
			"startMoment", "endMoment", //
			"moreInfo", "inventor.identity.fullName");
	}

}
