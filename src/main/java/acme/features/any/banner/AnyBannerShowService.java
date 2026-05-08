
package acme.features.any.banner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AnyBannerShowService extends AbstractService<Any, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyBannerRepository	repository;

	private Banner				banner;

	private Random				rand;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.rand = new Random();
		Collection<Banner> banners = this.repository.findAllBanners();

		if (banners != null && !banners.isEmpty()) {
			List<Banner> list = new ArrayList<>(banners);
			this.banner = list.get(this.rand.nextInt(list.size()));
		}
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		if (this.banner != null) {
			acme.client.components.models.Tuple tuple;
			tuple = super.unbindObject(this.banner, "slogan", "targetUrl", "pictureUrl");
			super.getResponse().addData(tuple);
		}
	}
}
