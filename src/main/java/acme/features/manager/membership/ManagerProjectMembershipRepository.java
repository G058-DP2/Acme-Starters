
package acme.features.manager.membership;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project.ProjectMembership;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Spokesperson;

@Repository
public interface ManagerProjectMembershipRepository extends AbstractRepository {

	@Query("select pm from ProjectMembership pm where pm.id = :id")
	ProjectMembership findMembershipById(int id);

	@Query("select pm from ProjectMembership pm where pm.project.id = :projectId")
	Collection<ProjectMembership> findMembershipsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select i from Inventor i where i.id = :id")
	Inventor findInventorById(int id);

	@Query("select s from Spokesperson s where s.id = :id")
	Spokesperson findSpokespersonById(int id);

	@Query("select f from Fundraiser f where f.id = :id")
	Fundraiser findFundraiserById(int id);

	@Query("select i from Inventor i")
	Collection<Inventor> findAllInventors();

	@Query("select s from Spokesperson s")
	Collection<Spokesperson> findAllSpokespeople();

	@Query("select f from Fundraiser f")
	Collection<Fundraiser> findAllFundraisers();

	@Query("select pm from ProjectMembership pm where pm.project.id = :projectId and pm.inventor.id = :inventorId")
	ProjectMembership findMembershipByProjectAndInventor(int projectId, int inventorId);

	@Query("select pm from ProjectMembership pm where pm.project.id = :projectId and pm.spokesperson.id = :spokespersonId")
	ProjectMembership findMembershipByProjectAndSpokesperson(int projectId, int spokespersonId);

	@Query("select pm from ProjectMembership pm where pm.project.id = :projectId and pm.fundraiser.id = :fundraiserId")
	ProjectMembership findMembershipByProjectAndFundraiser(int projectId, int fundraiserId);
}
