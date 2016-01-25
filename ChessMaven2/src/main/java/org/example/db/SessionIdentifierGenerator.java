package org.example.db;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SessionIdentifierGenerator {
	private SecureRandom random = new SecureRandom();

	  public String nextSessionId() {
	    String id = new BigInteger(130, random).toString(32);
	    return id;
	  }

}
