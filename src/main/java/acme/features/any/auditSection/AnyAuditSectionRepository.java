
package acme.features.any.auditSection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReport.AuditSection;

@Repository
public interface AnyAuditSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.auditReport.id = :auditReportId")
	Collection<AuditSection> findAuditSectionsByAuditReportId(int auditReportId);

	@Query("select s from AuditSection s where s.id = :id")
	AuditSection findAuditSectionById(int id);
}
