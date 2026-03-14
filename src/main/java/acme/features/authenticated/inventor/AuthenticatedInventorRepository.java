
package acme.features.authenticated.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;

@Repository
public interface AuthenticatedInventorRepository extends AbstractRepository {

	@Query("SELECT ua FROM UserAccount ua WHERE ua.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("SELECT i FROM Inventor i WHERE i.userAccount.id = :id")
	Inventor findInventorByUserAccountId(int id);

}
