package acme.features.manager.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.ProjectMembership;
import acme.realms.Manager;

@Service
public class ManagerProjectMembershipDeleteService extends AbstractService<Manager, ProjectMembership> {

	@Autowired
	private ManagerProjectMembershipRepository repository;

	private ProjectMembership membership;

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.membership = this.repository.findMembershipById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.membership != null &&
			this.membership.getProject().isDraftMode() &&
			this.membership.getProject().getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// Nothing to bind for delete
	}

	@Override
	public void validate() {
		// No extra validations
	}

	@Override
	public void execute() {
		this.repository.delete(this.membership);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.membership);
	}
}
