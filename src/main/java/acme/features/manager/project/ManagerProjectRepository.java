package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.invention.Invention;
import acme.entities.project.Project;
import acme.entities.project.ProjectMembership;
import acme.entities.strategies.Strategy;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.auditReport.AuditReport;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select pm from ProjectMembership pm where pm.project.id = :projectId")
	Collection<ProjectMembership> findMembershipsByProjectId(int projectId);

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> findInventionsByProjectId(int projectId);

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);

	@Query("select s from Strategy s where s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProjectId(int projectId);

	@Query("select s from Sponsorship s where s.project.id = :projectId")
	Collection<Sponsorship> findSponsorshipsByProjectId(int projectId);

	@Query("select ar from AuditReport ar where ar.project.id = :projectId")
	Collection<AuditReport> findAuditReportsByProjectId(int projectId);

	@Query("select count(i) from Invention i where i.project.id = :projectId")
	Integer countInventionsByProjectId(int projectId);

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	Integer countPartsByInventionId(int inventionId);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	Integer countMilestonesByCampaignId(int campaignId);

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	Integer countTacticsByStrategyId(int strategyId);
}
