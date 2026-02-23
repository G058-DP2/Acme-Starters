
package acme.features.inventor.invention;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;

@Repository
public interface InventionRepository extends AbstractRepository {

	@Query("SELECT i FROM Invention i WHERE i.ticker=:ticker")
	Invention findInventionByTicker(final String ticker);

}
