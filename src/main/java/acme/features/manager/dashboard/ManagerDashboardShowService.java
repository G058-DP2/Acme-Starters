
package acme.features.manager.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.dashboard.Dashboard;
import acme.entities.project.Project;
import acme.realms.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, Dashboard> {

	@Autowired
	private ManagerDashboardRepository	repository;

	private Dashboard					dashboard;
	private Collection<Project>			projects;


	@Override
	public void load() {
		int managerId;

		this.dashboard = super.newObject(Dashboard.class);
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.projects = this.repository.findProjectsByManagerId(managerId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		tuple = super.unbindObject(this.dashboard);

		// Total number of projects
		Integer totalProjects = this.repository.countProjectsByManagerId(managerId);
		if (totalProjects == null)
			totalProjects = 0;
		tuple.put("totalProjects", totalProjects);

		// Deviation = managerProjects - mean(rest of managers' projects); N/A if sole manager
		Double avgOthers = this.repository.averageProjectsOfOtherManagers(managerId);
		if (avgOthers == null)
			tuple.put("deviationFromAverage", "N/A");
		else
			tuple.put("deviationFromAverage", Math.round((totalProjects - avgOthers) * 100.0) / 100.0);

		// Compute effort stats using the repository directly (avoids @Transient @Autowired injection issues)
		List<Double> efforts = new ArrayList<>();
		for (Project p : this.projects) {
			Double effort = this.repository.computeEffortByProjectId(p.getId());
			if (effort != null && effort > 0.0)
				efforts.add(effort);
		}

		double minEffort = 0.0;
		double maxEffort = 0.0;
		double avgEffort = 0.0;
		double devEffort = 0.0;

		if (!efforts.isEmpty()) {
			minEffort = efforts.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
			maxEffort = efforts.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
			avgEffort = efforts.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

			double sumSquares = 0.0;
			for (Double e : efforts)
				sumSquares += (e - avgEffort) * (e - avgEffort);
			devEffort = Math.sqrt(sumSquares / efforts.size());
		}

		tuple.put("minEffort", Math.round(minEffort * 100.0) / 100.0);
		tuple.put("maxEffort", Math.round(maxEffort * 100.0) / 100.0);
		tuple.put("avgEffort", Math.round(avgEffort * 100.0) / 100.0);
		tuple.put("devEffort", Math.round(devEffort * 100.0) / 100.0);

		super.getResponse().addData(tuple);
	}

}
