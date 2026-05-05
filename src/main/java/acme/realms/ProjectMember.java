
package acme.realms;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import acme.client.components.basis.AbstractRole;
import acme.client.components.basis.AbstractSquad;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectMember extends AbstractSquad {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;

	// AbstractSquad interface ------------------------------------------------

	/*
	 * ProjectMember is a Squad realm that groups all principals who can participate
	 * in a project: Managers (who own it), Inventors, Spokespersons, and Fundraisers.
	 *
	 * Members of this Squad can view all components (draft and published) of projects
	 * they belong to, but cannot modify components owned by other members.
	 */


	@Mandatory
	// @Valid  // HINT: inherited from the ancestor.
	@Transient
	@Override
	public Set<Class<? extends AbstractRole>> getMembers() {
		return Set.of(Manager.class, Inventor.class, Spokesperson.class, Fundraiser.class);
	}

}
