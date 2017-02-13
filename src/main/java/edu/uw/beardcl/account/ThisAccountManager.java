package edu.uw.beardcl.account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.LoggerFactory;

import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.AccountFactory;
import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.dao.AccountDao;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleAccountManager.
 */
public final class ThisAccountManager implements AccountManager{
	
	/** The logger. */
	private static org.slf4j.Logger logger =
			LoggerFactory.getLogger(ThisAccountManager.class);
	
	
	/** The sadao. */
	AccountDao sadao;
	
	/** The md. */
	MessageDigest md;
	
	/** The factory. */
	AccountFactory factory;
	
	
	/**
	 * Instantiates a new simple account manager.
	 *
	 * @param sadao the sadao
	 */
	public ThisAccountManager(AccountDao sadao){
		this.sadao = sadao;
		this.factory = new ThisAccountFactory();
		logger.info("Need to implement logger", getLogInfo());

		if (logger.isInfoEnabled()) {
		logger.info( "Logger not enabled", getLogInfo());
		
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			  logger.debug("Error with MessageDigest");
		}
			
		}
	}
	
	/**
	 * Gets the log info.
	 *
	 * @return the log info
	 */
	private Object getLogInfo() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.AccountManager#close()
	 */
	@Override
	public void close() throws AccountException {
			try{
				sadao.close();
			}catch(AccountException e){
				logger.debug("Cannot close the file");
			}
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.AccountManager#createAccount(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Account createAccount(String name, String passWord, int balance)
			throws AccountException {
		if(getAccount(name) != null){
			throw new AccountException("Account already exists");
		}
		Account retval = factory.newAccount(name, md.digest(passWord.getBytes()), balance);
		persist(retval);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.AccountManager#deleteAccount(java.lang.String)
	 */
	@Override
	public void deleteAccount(String name) throws AccountException {
		sadao.deleteAccount(name);
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.AccountManager#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String name) throws AccountException {

		return sadao.getAccount(name);
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.AccountManager#persist(edu.uw.ext.framework.account.Account)
	 */
	@Override
	public void persist(Account account) throws AccountException {
		if(account == null){
			throw new AccountException("Cannot persist a null account");
		}
		sadao.setAccount(account);
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.AccountManager#validateLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validateLogin(String name, String passWord)
			throws AccountException {
		Account account = getAccount(name);
		if(account == null){
			return false;
		}
		byte[] expected = account.getPasswordHash();
		byte[] actual = md.digest(passWord.getBytes());
		if(expected.length != actual.length){
			return false;
		}
		for(int i = 0; i < expected.length; i++){
			if(expected[i] != actual[i]){
				return false;
			}
		}
		
		return true;
	}
	
	
	
}
