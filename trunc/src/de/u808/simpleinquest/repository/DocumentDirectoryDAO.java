package de.u808.simpleinquest.repository;

import java.util.List;

import de.u808.simpleinquest.domain.DocumentDirectory;

public interface DocumentDirectoryDAO extends GenericDAO<DocumentDirectory, Long>{
	
	public DocumentDirectory findByPath(String path);
	
	public List<DocumentDirectory> findByExample(DocumentDirectory documentDirectory);

}
