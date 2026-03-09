
package acme.features.any.auditSection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditSection;

@Service
public class AnyAuditSectionListService extends AbstractService<Any, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditSectionRepository	repository;

	private Collection<AuditSection>	auditSections;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int auditSectionId;

		auditSectionId = super.getRequest().getData("auditReportId", int.class);
		this.auditSections = this.repository.findAuditSectionsByAuditReportId(auditSectionId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");
	}
}
