
package acme.entities.strategies;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Strategy extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	//@Valid Se supone que está por defecto
	@Column
	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private StrategyRepository	repository;


	@Transient
	public Double getMonthsActive() {
		return (double) MomentHelper.computeDuration(this.startMoment, this.endMoment).get(ChronoUnit.MONTHS);
	}

	@Valid
	@Transient
	public Double getExpectedPercentage() {
		Double result;
		Double expectedPercentage;

		expectedPercentage = this.repository.expectedPercentage(this.getId());
		result = expectedPercentage == null ? 0 : expectedPercentage.doubleValue();

		return result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser fundraiser;

}
