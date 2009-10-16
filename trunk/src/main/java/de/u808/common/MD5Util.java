/*
 * Copyright 2008-2009 Andreas Friedel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.u808.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class to create MD5 hashes.
 * @author Andreas Friedel
 *
 */
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
	
	/**
	 * Singleton instantiate method.
	 * 
	 * @return The singleton instance of the class.
	 */
	public static MD5Util getInstance(){
		if(INSTANCE == null){
			INSTANCE = new MD5Util();
		}
		return INSTANCE;
	}
	
	/**
	 * Creates MD5 Hex String for the input.  
	 * 
	 * @param s The string to create the hash for.
	 * @return The MD5 Hex String for the input string.
	 */
	public String getMD5Hex(String s){
		md.update(s.getBytes(),0,s.length());
		String md5Hex = new BigInteger(1,md.digest()).toString(16);
		md.reset();
		return md5Hex;
	}

}