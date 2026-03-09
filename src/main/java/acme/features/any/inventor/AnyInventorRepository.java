
package acme.features.any.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;

@Repository
public interface AnyInventorRepository extends AbstractRepository {

	@Query("SELECT i FROM Inventor i WHERE i.id = :id")
	Inventor findInventorById(int id);

}
