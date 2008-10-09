package de.u808.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MD5Util {
	
	private MessageDigest md = null;
	
	private static MD5Util INSTANCE = null;
	
	private static Log log = LogFactory.getLog(MD5Util.class);
	
	private MD5Util(){
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("Failed to create MD5Util", e);
		}
	}
	
	public static MD5Util getInstance(){
		if(INSTANCE == null){
			INSTANCE = new MD5Util();
		}
		return INSTANCE;
	}
	
	public String getMD5Hex(String s){
		md.update(s.getBytes(),0,s.length());
		String md5Hex = new BigInteger(1,md.digest()).toString(16);
		md.reset();
		return md5Hex;
	}

}