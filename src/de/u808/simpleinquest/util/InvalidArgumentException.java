package de.u808.simpleinquest.util;

public class InvalidArgumentException extends Exception {
	
	public InvalidArgumentException(){
		
	}
	
	public InvalidArgumentException(String msg){
		super(msg);
	}
	
	public InvalidArgumentException(String msg, Throwable throwable){
		super(msg, throwable);
	}
	
	public InvalidArgumentException(Throwable throwable){
		super(throwable);
	}

}
