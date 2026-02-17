
package acme.features.inventor.part;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface PartRepository extends AbstractRepository {

	@Query("SELECT SUM(p.cost.amount) FROM Part p WHERE p.invention.id=:inventionId")
	Double getSumCostsByInvention(int inventionId);

}
