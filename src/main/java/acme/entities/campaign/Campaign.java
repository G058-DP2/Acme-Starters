/*
 * Campaign.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.entities.campaign;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Campaign extends AbstractEntity {

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
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date				startMoment;

	@Mandatory
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date				endMoment;

	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return null;

		double months = this.computeMonthsBetween(this.startMoment, this.endMoment);
		return Math.round(months * 10.0) / 10.0;
	}

	@Transient
	public Double getEffort() {
		if (this.milestones == null)
			return 0.0;

		return this.milestones.stream().map(Milestone::getEffort).filter(value -> value != null).mapToDouble(Double::doubleValue).sum();
	}

	private double computeMonthsBetween(final Date start, final Date end) {
		Instant startInstant = start.toInstant();
		Instant endInstant = end.toInstant();
		long days = Duration.between(startInstant, endInstant).toDays();
		return days / 30.0;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Spokesperson			manager;

	@Valid
	@OneToMany(mappedBy = "campaign")
	private Collection<Milestone>	milestones;
}
