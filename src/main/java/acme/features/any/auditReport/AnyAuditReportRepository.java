
package acme.features.any.auditReport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReport.AuditReport;

@Repository
public interface AnyAuditReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select a from AuditReport a where a.draftMode = false")
	Collection<AuditReport> findPublishedAuditReports();
}
