package de.u808.simpleinquest.repository;

import java.util.List;

import de.u808.simpleinquest.domain.File;

public interface FileDAO extends GenericDAO<File, Long>{

	public List<File> findByExample(File file);
}
