
package acme.entities.project;

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
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidProject;
import acme.constraints.ValidText;
import acme.realms.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidProject
public class Project extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidHeader
	@Column
	private String				title;

	@Mandatory
	@ValidHeader
	@Column
	private String				keyWords;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				kickOffMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				closeOutMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Column
	private boolean				draftMode;

	@Optional
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				publishedOn;

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private ProjectRepository	repository;

	/*
	 * Effort is computed in PMs (person-months) as the sum of active months
	 * of its components divided by the number of people involved.
	 */


	@Transient
	public Double getEffort() {
		if (this.getId() > 0 && this.repository != null) {
			Double totalActiveMonths = this.repository.computeTotalActiveMonthsByProjectId(this.getId());
			Integer memberCount = this.repository.countMembersByProjectId(this.getId());

			if (totalActiveMonths == null)
				totalActiveMonths = 0.0;
			if (memberCount == null || memberCount == 0)
				return 0.0;

			return Math.round(totalActiveMonths / memberCount * 100.0) / 100.0;
		}
		return 0.0;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Manager manager;

}
