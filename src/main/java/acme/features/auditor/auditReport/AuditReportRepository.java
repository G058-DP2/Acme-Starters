
package acme.features.auditor.auditReport;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReport.AuditReport;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select sum(s.hours) from AuditSection s where s.auditReport.id = :id")
	Integer sumHoursByReportId(int id);

	@Query("select a from AuditReport a where a.ticker = :ticker")
	AuditReport findByTicker(String ticker);

	@Query("select count(s) from AuditSection s where s.auditReport.id = :id")
	Integer countAuditSectionsByAuditReportId(int id);

}
