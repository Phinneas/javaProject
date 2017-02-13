package edu.uw.beardcl.account;

import org.slf4j.LoggerFactory;

import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.AccountFactory;



// TODO: Auto-generated Javadoc
/**
 * A factory for creating SimpleAccount objects.
 */
public final class ThisAccountFactory implements AccountFactory{
	
	/** The logger. */
	private static org.slf4j.Logger logger =
			LoggerFactory.getLogger(ThisAccountFactory.class);
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.AccountFactory#newAccount(java.lang.String, byte[], int)
	 */
	public ThisAccount newAccount(String accountName, byte[] hashedPassword, int initialBalance) {
		ThisAccount retval = null;
		if(initialBalance < 100000){
			return retval;
		}
		retval = new ThisAccount();
		try {
			retval.setName(accountName);
			retval.setPasswordHash(hashedPassword);
			retval.setBalance(initialBalance);
		} catch (AccountException e) {
			retval = null;
			logger.warn("Error creating new account", e);
} 
		
		
		
		return retval;
	}

	/**
	 * Gets the logger.
	 *
	 * @return the logger
	 */
	public static org.slf4j.Logger getLogger() {
		return logger;
	}

	/**
	 * Sets the logger.
	 *
	 * @param logger the new logger
	 */
	public static void setLogger(org.slf4j.Logger logger) {
		ThisAccountFactory.logger = logger;
	}

}
