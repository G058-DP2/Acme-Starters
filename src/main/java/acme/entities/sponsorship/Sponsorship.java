
package acme.entities.sponsorship;

import java.util.concurrent.TimeUnit;

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
import acme.client.components.datatypes.Moment;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.sponsor.sponsorship.SponsorshipRepository;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

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
	@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Moment					startMoment;

	/*
	 * startMoment/endMoment must be a valid time interval in future wrt.
	 * the moment when a sponsorship is published.
	 */

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Moment					endMoment;

	@Optional
	@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Valid
	@Column
	//Sponsorships cannot be published unless they have at least one donation
	private Boolean					draftMode;

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private SponsorshipRepository	repository;

	@Mandatory
	@Valid
	@Transient
	private Double					monthsActive;

	@Mandatory
	@ValidMoney(min = 0.0)
	@Transient
	private Money					totalMoney;


	public Double getMonthsActive() {

		double result = 0.0;

		if (this.startMoment != null && this.endMoment != null) {
			long diffInMillies = Math.abs(this.endMoment.getTime() - this.startMoment.getTime());
			long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

			double months = diffInDays / 30.0;
			result = Math.round(months * 10.0) / 10.0;
		}

		return result;
	}

	public Money getTotalMoney() {

		Double totalAmount = this.repository.calculateTotalAmountBySponsorshipId(this.getId());

		if (totalAmount == null)
			totalAmount = 0.0;

		Money res = new Money();
		res.setAmount(totalAmount);
		res.setCurrency("EUR");

		return res;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;

}
