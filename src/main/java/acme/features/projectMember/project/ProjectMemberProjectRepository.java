package acme.features.projectMember.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface ProjectMemberProjectRepository extends AbstractRepository {

	/*
	 * Returns all projects that the given user account can access as a ProjectMember:
	 * - projects they manage (Manager role), OR
	 * - projects where they appear in a ProjectMembership as Inventor, Spokesperson, or Fundraiser.
	 * Both draft and published projects are returned (members can always see their own projects).
	 */
	@Query("select distinct p from Project p " + //
		"where p.manager.userAccount.id = :accountId " + //
		"   or exists (" + //
		"     select pm from ProjectMembership pm " + //
		"     where pm.project.id = p.id " + //
		"       and (" + //
		"         pm.inventor.userAccount.id      = :accountId" + //
		"      or pm.spokesperson.userAccount.id  = :accountId" + //
		"      or pm.fundraiser.userAccount.id     = :accountId" + //
		"       )" + //
		"   )")
	Collection<Project> findProjectsByAccountId(int accountId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

}
