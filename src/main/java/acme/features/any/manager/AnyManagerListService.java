package acme.features.any.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Manager;

@Service
public class AnyManagerListService extends AbstractService<Any, Manager> {

	@Autowired
	private AnyManagerRepository	repository;

	private Collection<Manager>		managers;

	@Override
	public void load() {
		this.managers = this.repository.findAllManagers();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.managers, "position", "skills", "executive");
	}
}
