
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditReport.AuditSection;
import acme.entities.project.Project;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.auditor.id = :id")
	Collection<AuditReport> findAuditReportsByAuditorId(int id);

	@Query("select a from AuditReport a where a.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select a from AuditSection a where a.auditReport.id = :id")
	Collection<AuditSection> findAuditSectionsByAuditReportId(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findPublishedProjects();

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
}
