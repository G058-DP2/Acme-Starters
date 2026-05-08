
package acme.features.manager.membership;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.project.ProjectMembership;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Manager;
import acme.realms.Spokesperson;

@Service
public class ManagerProjectMembershipCreateService extends AbstractService<Manager, ProjectMembership> {

	@Autowired
	private ManagerProjectMembershipRepository	repository;

	private ProjectMembership					membership;


	@Override
	public void load() {
		int projectId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);

		this.membership = new ProjectMembership();
		this.membership.setProject(project);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.membership.getProject() != null && this.membership.getProject().isDraftMode() && this.membership.getProject().getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int inventorId;
		int spokespersonId;
		int fundraiserId;

		inventorId = super.getRequest().getData("inventor", int.class);
		spokespersonId = super.getRequest().getData("spokesperson", int.class);
		fundraiserId = super.getRequest().getData("fundraiser", int.class);

		if (inventorId != 0) {
			Inventor inventor = this.repository.findInventorById(inventorId);
			this.membership.setInventor(inventor);
		}
		if (spokespersonId != 0) {
			Spokesperson spokesperson = this.repository.findSpokespersonById(spokespersonId);
			this.membership.setSpokesperson(spokesperson);
		}
		if (fundraiserId != 0) {
			Fundraiser fundraiser = this.repository.findFundraiserById(fundraiserId);
			this.membership.setFundraiser(fundraiser);
		}
	}

	@Override
	public void validate() {
		int rolesSelected;

		rolesSelected = (this.membership.getInventor() != null ? 1 : 0) //
			+ (this.membership.getSpokesperson() != null ? 1 : 0) //
			+ (this.membership.getFundraiser() != null ? 1 : 0);

		super.state(rolesSelected > 0, "*", "manager.project-membership.form.error.no-member");
		super.state(rolesSelected <= 1, "*", "manager.project-membership.form.error.multiple-roles");

		// Check for duplicate membership only when exactly one role is selected
		if (rolesSelected == 1) {
			if (this.membership.getInventor() != null) {
				ProjectMembership existing = this.repository.findMembershipByProjectAndInventor(this.membership.getProject().getId(), this.membership.getInventor().getId());
				super.state(existing == null, "inventor", "manager.project-membership.form.error.duplicate");
			}
			if (this.membership.getSpokesperson() != null) {
				ProjectMembership existing = this.repository.findMembershipByProjectAndSpokesperson(this.membership.getProject().getId(), this.membership.getSpokesperson().getId());
				super.state(existing == null, "spokesperson", "manager.project-membership.form.error.duplicate");
			}
			if (this.membership.getFundraiser() != null) {
				ProjectMembership existing = this.repository.findMembershipByProjectAndFundraiser(this.membership.getProject().getId(), this.membership.getFundraiser().getId());
				super.state(existing == null, "fundraiser", "manager.project-membership.form.error.duplicate");
			}
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.membership);
	}

	@Override
	public void unbind() {
		Collection<Inventor> inventors;
		Collection<Spokesperson> spokespeople;
		Collection<Fundraiser> fundraisers;
		SelectChoices inventorChoices;
		SelectChoices spokespersonChoices;
		SelectChoices fundraiserChoices;

		inventors = this.repository.findAllInventors();
		spokespeople = this.repository.findAllSpokespeople();
		fundraisers = this.repository.findAllFundraisers();

		inventorChoices = SelectChoices.from(inventors, "userAccount.identity.fullName", this.membership.getInventor());
		spokespersonChoices = SelectChoices.from(spokespeople, "userAccount.identity.fullName", this.membership.getSpokesperson());
		fundraiserChoices = SelectChoices.from(fundraisers, "userAccount.identity.fullName", this.membership.getFundraiser());

		super.unbindObject(this.membership);
		super.getResponse().addGlobal("inventorChoices", inventorChoices);
		super.getResponse().addGlobal("spokespersonChoices", spokespersonChoices);
		super.getResponse().addGlobal("fundraiserChoices", fundraiserChoices);
		super.getResponse().addGlobal("projectId", this.membership.getProject().getId());
	}
}
