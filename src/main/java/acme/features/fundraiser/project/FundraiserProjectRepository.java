package acme.features.fundraiser.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface FundraiserProjectRepository extends AbstractRepository {

	@Query("select p from Project p join ProjectMembership pm on pm.project.id = p.id where pm.fundraiser.id = :fundraiserId")
	Collection<Project> findProjectsByFundraiserId(int fundraiserId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
}
