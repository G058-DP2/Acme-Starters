
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface SponsorshipRepository extends AbstractRepository {

	// Esta consulta busca todos los Sponsorships de un Sponsor y calcula el totalMoney al vuelo
	@Query("select s, " + "COALESCE((select sum(d.money.amount) from Donation d where d.sponsorship = s and d.money.currency = 'EUR'), 0.0) " + "from Sponsorship s where s.sponsor.id = :sponsorId")
	Collection<Sponsorship> findSponsorshipsWithTotalMoneyBySponsorId(int sponsorId);
}
