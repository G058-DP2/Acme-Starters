
package acme.entities.campaign;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface CampaignRepository extends AbstractRepository {

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :id")
	Double calculateTotalEffortByCampaignId(int id);

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findByTicker(String ticker);

	@Query("select count(m) from Milestone m where m.campaign.id = :id")
	Integer countMilestonesByCampaignId(int id);
}
