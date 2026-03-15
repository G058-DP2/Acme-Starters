
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditReport.AuditSection;

@Repository
public interface AuditorAuditSectionRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select a from AuditSection a where a.auditReport.id = :id")
	Collection<AuditSection> findAuditSectionsByAuditReportId(int id);

	@Query("select a from AuditSection a where a.id = :id")
	AuditSection findAuditSectionById(int id);

}
