
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface FundraiserStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.fundraiser.id = :id")
	Collection<Strategy> findStrategyByFundraiserId(int id);

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

	@Query("select t from Tactic t where t.strategy.id = :id")
	Collection<Tactic> findTacticsByStrategyId(int id);

	@Query("select p from Project p join ProjectMembership pm on pm.project.id = p.id where pm.fundraiser.id = :fundraiserId")
	Collection<Project> findProjectsByFundraiserId(int fundraiserId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
}
