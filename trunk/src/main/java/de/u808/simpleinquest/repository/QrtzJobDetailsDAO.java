package de.u808.simpleinquest.repository;

import java.util.List;

public interface QrtzJobDetailsDAO {

	public List<String> getJobGroupNames();

	public List<String> getJobNames();
}
