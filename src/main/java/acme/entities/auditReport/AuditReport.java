
package acme.entities.auditReport;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidTicker;
import acme.features.auditor.auditReport.AuditReportRepository;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditReport extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String					ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String					name;

	@Mandatory
	//@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					endMoment;

	@Optional
	@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean					draftMode;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor					auditor;

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private AuditReportRepository	repository;


	@Transient
	public Double getMonthsActive() {
		Duration d = MomentHelper.computeDuration(this.startMoment, this.endMoment);
		return (double) d.get(ChronoUnit.MONTHS);
	}

	@Transient
	public Integer getHours() {

		if (this.getId() == 0)
			return 0;

		Integer total = this.repository.sumHoursByReportId(this.getId());

		return total == null ? 0 : total;
	}

}
