
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;

@Repository
public interface InventionRepository extends AbstractRepository {

	// Entity queries ----------------------------------------
	@Query("SELECT i FROM Invention i WHERE i.ticker=:ticker")
	Invention findInventionByTicker(final String ticker);

	@Query("SELECT i FROM Invention i WHERE i.id = :id")
	Invention findInventionById(int id);

	// Feature queries ----------------------------------------
	@Query("SELECT i FROM Invention i WHERE i.inventor.id = :inventorId")
	Collection<Invention> findInventionsByInventorId(int inventorId);

	@Query("SELECT p FROM Part p WHERE p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

}
