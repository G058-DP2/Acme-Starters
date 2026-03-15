
package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.Milestone;
import acme.features.spokesperson.milestone.SpokespersonMilestoneRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneListService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Campaign						campaign;
	private Collection<Milestone>			milestones;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);
		this.milestones = this.repository.findMilestonesByCampaignId(campaignId);
	}

	@Override
	public void authorise() {
		boolean status;

		// Owner always sees; others only if published
		status = this.campaign != null && //
			(this.campaign.getManager().isPrincipal() || !this.campaign.isDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		super.unbindObjects(this.milestones, "title", "effort", "kind");

		// Show create button only if owner and still in draft
		showCreate = this.campaign.isDraftMode() && this.campaign.getManager().isPrincipal();
		super.unbindGlobal("campaignId", this.campaign.getId());
		super.unbindGlobal("showCreate", showCreate);
	}
}
