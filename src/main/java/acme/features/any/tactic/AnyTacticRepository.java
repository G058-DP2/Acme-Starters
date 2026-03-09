
package acme.features.any.tactic;

import java.util.Collection;

import org.checkerframework.common.util.report.qual.ReportCreation;
import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Tactic;

@ReportCreation
public interface AnyTacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	Collection<Tactic> findTacticByStrategyId(int strategyId);

	@Query("select t from Tactic t where t.id = :id")
	Tactic findTacticById(int id);

}
