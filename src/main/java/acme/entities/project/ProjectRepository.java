
package acme.entities.project;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.invention.Invention;
import acme.entities.strategies.Strategy;

@Repository
public interface ProjectRepository extends AbstractRepository {

	@Query("select count(i) from Invention i where i.project.id = :projectId")
	Integer countInventionsByProjectId(int projectId);

	@Query("select i from Invention i where i.project.id = :projectId")
	List<Invention> findInventionsByProjectId(int projectId);

	@Query("select c from Campaign c where c.project.id = :projectId")
	List<Campaign> findCampaignsByProjectId(int projectId);

	@Query("select s from Strategy s where s.project.id = :projectId")
	List<Strategy> findStrategiesByProjectId(int projectId);

	@Query("select count(pm) from ProjectMembership pm where pm.project.id = :projectId")
	Integer countMembersByProjectId(int projectId);

	@Query("select sum( timestampdiff(MONTH, i.startMoment, i.endMoment) * 1.0 ) from Invention i where i.project.id = :projectId")
	Double computeInventionMonthsByProjectId(int projectId);

	@Query("select sum( timestampdiff(MONTH, c.startMoment, c.endMoment) * 1.0 ) from Campaign c where c.project.id = :projectId")
	Double computeCampaignMonthsByProjectId(int projectId);

	@Query("select sum( timestampdiff(MONTH, s.startMoment, s.endMoment) * 1.0 ) from Strategy s where s.project.id = :projectId")
	Double computeStrategyMonthsByProjectId(int projectId);

	default Double computeTotalActiveMonthsByProjectId(final int projectId) {
		Double inv = this.computeInventionMonthsByProjectId(projectId);
		Double cam = this.computeCampaignMonthsByProjectId(projectId);
		Double str = this.computeStrategyMonthsByProjectId(projectId);

		return (inv != null ? inv : 0.0) + (cam != null ? cam : 0.0) + (str != null ? str : 0.0);
	}

}
