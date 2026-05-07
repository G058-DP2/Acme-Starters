
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(p) from Project p where p.manager.id = :managerId")
	Integer countProjectsByManagerId(int managerId);

	@Query("select count(p) * 1.0 / count(distinct p.manager) from Project p")
	Double averageProjectsPerManager();

	@Query("select count(p) * 1.0 / nullif(count(distinct p.manager), 0) from Project p where p.manager.id != :managerId")
	Double averageProjectsOfOtherManagers(int managerId);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select count(pm) from ProjectMembership pm where pm.project.id = :projectId")
	Integer countMembersByProjectId(int projectId);

	@Query("select sum( timestampdiff(MONTH, i.startMoment, i.endMoment) * 1.0 ) from Invention i where i.project.id = :projectId")
	Double computeInventionMonthsByProjectId(int projectId);

	@Query("select sum( timestampdiff(MONTH, c.startMoment, c.endMoment) * 1.0 ) from Campaign c where c.project.id = :projectId")
	Double computeCampaignMonthsByProjectId(int projectId);

	@Query("select sum( timestampdiff(MONTH, s.startMoment, s.endMoment) * 1.0 ) from Strategy s where s.project.id = :projectId")
	Double computeStrategyMonthsByProjectId(int projectId);

	default Double computeEffortByProjectId(final int projectId) {
		Double inv = this.computeInventionMonthsByProjectId(projectId);
		Double cam = this.computeCampaignMonthsByProjectId(projectId);
		Double str = this.computeStrategyMonthsByProjectId(projectId);
		Integer members = this.countMembersByProjectId(projectId);

		double totalMonths = (inv != null ? inv : 0.0) + (cam != null ? cam : 0.0) + (str != null ? str : 0.0);
		if (members == null || members == 0)
			return 0.0;

		return Math.round(totalMonths / members * 100.0) / 100.0;
	}
}
