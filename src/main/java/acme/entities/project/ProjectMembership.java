
package acme.entities.project;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectMembership extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@Optional
	@Valid
	@ManyToOne(optional = true)
	private Inventor			inventor;

	@Optional
	@Valid
	@ManyToOne(optional = true)
	private Spokesperson		spokesperson;

	@Optional
	@Valid
	@ManyToOne(optional = true)
	private Fundraiser			fundraiser;

	// Derived attributes -----------------------------------------------------


	@Transient
	public String getMemberName() {
		if (this.inventor != null)
			return this.inventor.getIdentity().getFullName();
		if (this.spokesperson != null)
			return this.spokesperson.getIdentity().getFullName();
		if (this.fundraiser != null)
			return this.fundraiser.getIdentity().getFullName();
		return "";
	}

	@Transient
	public String getMemberType() {
		if (this.inventor != null)
			return "Inventor";
		if (this.spokesperson != null)
			return "Spokesperson";
		if (this.fundraiser != null)
			return "Fundraiser";
		return "";
	}

}
