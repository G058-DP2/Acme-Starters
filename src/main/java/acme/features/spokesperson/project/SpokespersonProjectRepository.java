package acme.features.spokesperson.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface SpokespersonProjectRepository extends AbstractRepository {

	@Query("select p from Project p join ProjectMembership pm on pm.project.id = p.id where pm.spokesperson.id = :spokespersonId")
	Collection<Project> findProjectsBySpokespersonId(int spokespersonId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
}
