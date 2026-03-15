
package acme.features.authenticated.spokesperson;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Spokesperson;

@Repository
public interface AuthenticatedSpokespersonRepository extends AbstractRepository {

	@Query("select s from Spokesperson s where s.userAccount.id = :id")
	Spokesperson findSpokespersonByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);

}
