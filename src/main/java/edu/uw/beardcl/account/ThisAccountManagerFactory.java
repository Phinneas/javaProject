package edu.uw.beardcl.account;

import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.account.AccountManagerFactory;
import edu.uw.ext.framework.dao.AccountDao;



// TODO: Auto-generated Javadoc
/**
 * A factory for creating SimpleAccountManager objects.
 */
public final class ThisAccountManagerFactory implements AccountManagerFactory{
	

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.AccountManagerFactory#newAccountManager(edu.uw.ext.framework.dao.AccountDao)
	 */
	@Override
	public AccountManager newAccountManager(AccountDao arg0) {
		return new ThisAccountManager(arg0);
		
	}


}
