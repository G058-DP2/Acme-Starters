
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.entities.campaign.Campaign;
import acme.entities.invention.Invention;
import acme.entities.project.Project;
import acme.entities.project.ProjectMembership;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.strategies.Strategy;
import acme.realms.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository	repository;

	private Project						project;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && this.project.isDraftMode() && this.project.getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo");
	}

	@Override
	public void validate() {
		// No extra validations needed for delete
	}

	@Override
	public void execute() {
		Collection<ProjectMembership> memberships;
		Collection<Invention> inventions;
		Collection<Campaign> campaigns;
		Collection<Strategy> strategies;
		Collection<Sponsorship> sponsorships;
		Collection<AuditReport> auditReports;

		// Remove project references from components
		inventions = this.repository.findInventionsByProjectId(this.project.getId());
		for (Invention i : inventions) {
			i.setProject(null);
			this.repository.save(i);
		}

		campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
		for (Campaign c : campaigns) {
			c.setProject(null);
			this.repository.save(c);
		}

		strategies = this.repository.findStrategiesByProjectId(this.project.getId());
		for (Strategy s : strategies) {
			s.setProject(null);
			this.repository.save(s);
		}

		sponsorships = this.repository.findSponsorshipsByProjectId(this.project.getId());
		for (Sponsorship s : sponsorships) {
			s.setProject(null);
			this.repository.save(s);
		}

		auditReports = this.repository.findAuditReportsByProjectId(this.project.getId());
		for (AuditReport ar : auditReports) {
			ar.setProject(null);
			this.repository.save(ar);
		}

		// Delete memberships
		memberships = this.repository.findMembershipsByProjectId(this.project.getId());
		this.repository.deleteAll(memberships);

		// Delete project
		this.repository.delete(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keyWords", "description", "kickOffMoment", "closeOutMoment", "moreInfo", "draftMode");
	}
}
