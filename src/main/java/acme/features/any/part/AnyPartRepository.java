
package acme.features.any.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;

@Repository
public interface AnyPartRepository extends AbstractRepository {

	@Query("SELECT p FROM Part p WHERE p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

	@Query("SELECT p FROM Part p WHERE p.id = :id")
	Part findPartById(int id);

	@Query("SELECT i FROM Invention i WHERE i.id = :inventionId")
	Invention findInventionById(int inventionId);

}
