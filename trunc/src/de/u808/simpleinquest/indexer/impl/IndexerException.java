package de.u808.simpleinquest.indexer.impl;

public class IndexerException extends Exception {

	public IndexerException (Exception e){
		super(e);
	}
	
	public IndexerException (String msg, Exception e){
		super(msg, e);
	}
}
