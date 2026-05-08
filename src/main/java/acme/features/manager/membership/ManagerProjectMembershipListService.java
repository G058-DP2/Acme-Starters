
package acme.features.manager.membership;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.project.ProjectMembership;
import acme.realms.Manager;

@Service
public class ManagerProjectMembershipListService extends AbstractService<Manager, ProjectMembership> {

	@Autowired
	private ManagerProjectMembershipRepository	repository;

	private Collection<ProjectMembership>		memberships;

	private Project								project;


	@Override
	public void authorise() {
		boolean status;
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		status = this.project != null && this.project.getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		this.memberships = this.repository.findMembershipsByProjectId(projectId);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		// memberName and memberType are @Transient computed getters on ProjectMembership
		showCreate = this.project.isDraftMode() && this.project.getManager().isPrincipal();

		super.unbindObjects(this.memberships, "memberName", "memberType");
		super.getResponse().addGlobal("showCreate", showCreate);
		super.getResponse().addGlobal("projectId", this.project.getId());
	}

}
