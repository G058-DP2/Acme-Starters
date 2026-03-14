
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditReport.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportDeleteService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && //
			this.auditReport.isDraftMode() && //
			this.auditReport.getAuditor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {

	}

	@Override
	public void execute() {
		Collection<AuditSection> auditSections;

		auditSections = this.repository.findAuditSectionsByAuditReportId(this.auditReport.getId());
		this.repository.deleteAll(auditSections);
		this.repository.delete(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo", //
			"draftMode", "monthsActive", "hours");
	}
}
