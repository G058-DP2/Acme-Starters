// src/main/java/acme/features/any/banner/AnyBannerRepository.java

package acme.features.any.banner;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

@Repository
public interface AnyBannerRepository extends AbstractRepository {

	// Returns all stored banners.
	// Random selection, if required, is implemented in the service layer.
	@Query("select b from Banner b")
	List<Banner> findAllBanners();
}
