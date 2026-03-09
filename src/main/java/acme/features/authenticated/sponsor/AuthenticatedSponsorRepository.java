
package acme.features.authenticated.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Sponsor;

@Repository
public interface AuthenticatedSponsorRepository extends AbstractRepository {

	@Query("select s from Sponsor s where s.userAccount.id = :id")
	Sponsor findSponsorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);

}
