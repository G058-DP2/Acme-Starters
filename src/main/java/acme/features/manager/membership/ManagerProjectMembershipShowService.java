
package acme.features.manager.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.ProjectMembership;
import acme.realms.Manager;

@Service
public class ManagerProjectMembershipShowService extends AbstractService<Manager, ProjectMembership> {

	@Autowired
	private ManagerProjectMembershipRepository	repository;

	private ProjectMembership					membership;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.membership = this.repository.findMembershipById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.membership != null && this.membership.getProject().getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		// memberName and memberType are @Transient computed getters on ProjectMembership
		super.unbindObject(this.membership, "memberName", "memberType");
		super.getResponse().addGlobal("projectId", this.membership.getProject().getId());
		super.getResponse().addGlobal("draftMode", this.membership.getProject().isDraftMode());
	}
}
