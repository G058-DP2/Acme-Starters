
package acme.features.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditSection;
import acme.entities.auditReport.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionDeleteService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);

		this.auditSection = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditSection.getAuditReport() != null && //
			this.auditSection.getAuditReport().getAuditor().isPrincipal() && //
			this.auditSection.getAuditReport().isDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {

	}

	@Override
	public void execute() {
		this.repository.delete(this.auditSection);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("strategyId", super.getRequest().getData("strategyId", int.class));
		tuple.put("draftMode", this.auditSection.getAuditReport().isDraftMode());
		tuple.put("kinds", choices);
	}
}
