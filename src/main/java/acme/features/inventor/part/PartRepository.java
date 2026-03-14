
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;

@Repository
public interface PartRepository extends AbstractRepository {

	// Entity queries ----------------------------------------
	@Query("SELECT SUM(p.cost.amount) FROM Part p WHERE p.invention.id=:inventionId")
	Double getSumCostsByInvention(int inventionId);

	@Query("SELECT COUNT(p) FROM Part p WHERE p.invention.id=:inventionId")
	Integer countPartsByInventionId(int inventionId);

	// Feature queries ----------------------------------------
	@Query("SELECT p FROM Part p WHERE p.id = :id")
	Part findPartById(int id);

	@Query("SELECT p FROM Part p WHERE p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

	@Query("SELECT i FROM Invention i WHERE i.id = :id")
	Invention findInventionById(int id);

}
