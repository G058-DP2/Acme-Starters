
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.Milestone;
import acme.entities.project.Project;

@Repository
public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.manager.id = :spokespersonId")
	Collection<Campaign> findCampaignsBySpokespersonId(int spokespersonId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select p from Project p join ProjectMembership pm on pm.project.id = p.id where pm.spokesperson.id = :spokespersonId")
	Collection<Project> findProjectsBySpokespersonId(int spokespersonId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
}
