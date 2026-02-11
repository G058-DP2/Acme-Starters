
package acme.entities.sponsorship;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.client.components.basis.AbstractEntity;
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
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

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

	/*
	 * startMoment/endMoment must be a valid time interval in future wrt.
	 * the moment when a sponsorship is published.
	 */

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	//Sponsorships cannot be published unless they have at least one donation
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	@Mandatory
	@Valid
	@Transient
	/*
	 * monthsActive is computed as the number of months in interval
	 * startMoment/endMoment rounded to the nearest decimal
	 */
	private Double				monthsActive;

	@Mandatory
	@ValidMoney(min = 0.0)
	@Transient
	/*
	 * The total money of a sponsorship is the sum of money in the corresponding donations.
	 * Only Euros are accepted.
	 */
	private Money				totalMoney;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Sponsor				sponsor;

}
