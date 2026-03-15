
package acme.features.auditor.auditreport;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditReport.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportPublishService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


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

		super.validateObject(this.auditReport);
		{
			Collection<AuditSection> auditSections;
			boolean hasAuditSectionts;

			auditSections = this.repository.findAuditSectionsByAuditReportId(this.auditReport.getId());
			hasAuditSectionts = auditSections != null && !auditSections.isEmpty();
			super.state(hasAuditSectionts, "hours", "acme.validation.auditReport.auditSections.error.message");
		}

		{
			Date start;
			Date end;
			boolean validInterval;

			start = this.auditReport.getStartMoment();
			end = this.auditReport.getEndMoment();
			validInterval = start != null && end != null && MomentHelper.isAfter(end, start);
			super.state(validInterval, "startMoment", "acme.validation.auditReport.dates.error");
		}
		{
			Date now;
			Date start;
			Date end;
			boolean startInFuture;
			boolean endInFuture;

			now = MomentHelper.getCurrentMoment();
			start = this.auditReport.getStartMoment();
			end = this.auditReport.getEndMoment();

			startInFuture = start != null && MomentHelper.isAfter(start, now);
			super.state(startInFuture, "startMoment", "acme.validation.auditReport.startMoment.future");

			endInFuture = end != null && MomentHelper.isAfter(end, now);
			super.state(endInFuture, "endMoment", "acme.validation.auditReport.endMoment.future");
		}
	}

	@Override
	public void execute() {
		this.auditReport.setDraftMode(false);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.auditReport, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		tuple.put("draftMode", this.auditReport.isDraftMode());
		tuple.put("monthsActive", this.auditReport.getMonthsActive());
		tuple.put("hours", this.auditReport.getHours());

	}

}
