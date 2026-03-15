
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditReport.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionListService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	AuditorAuditSectionRepository		repository;

	private AuditReport					auditorReport;
	private Collection<AuditSection>	auditSections;


	@Override
	public void load() {
		int auditReportId;
		auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditorReport = this.repository.findAuditReportById(auditReportId);
		this.auditSections = this.repository.findAuditSectionsByAuditReportId(auditReportId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.auditorReport != null && //
			(this.auditorReport.getAuditor().isPrincipal() || !this.auditorReport.isDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		super.unbindObjects(this.auditSections, "name", "hours", "kind");
		showCreate = this.auditorReport.isDraftMode() && this.auditorReport.getAuditor().isPrincipal();
		super.unbindGlobal("auditReportId", this.auditorReport.getId());
		super.unbindGlobal("showCreate", showCreate);
	}

}
