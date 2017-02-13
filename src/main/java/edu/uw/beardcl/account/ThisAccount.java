package edu.uw.beardcl.account;

//@author Chester Beard
//@version 07-29-2014

import org.slf4j.LoggerFactory;

import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.account.Address;
import edu.uw.ext.framework.account.CreditCard;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleAccount.
 */
public final class ThisAccount implements Account {

	/** The logger. */
	static org.slf4j.Logger logger = LoggerFactory
			.getLogger(ThisAccount.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4051656675017988801L;

	/** The address. */
	Address address = null;
	
	/** The balance. */
	int balance = 0;
	
	/** The credit card. */
	CreditCard creditCard = null;
	
	/** The email. */
	String email = null;
	
	/** The full name. */
	String fullName = null;
	
	/** The phone. */
	String phone = null;
	
	/** The name. */
	String name = null;
	
	/** The pass word hash. */
	byte[] passWordHash = null;
	
	/** The account manager. */
	AccountManager accountManager = null;

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#getAddress()
	 */
	@Override
	public Address getAddress() {
		return address;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#getBalance()
	 */
	@Override
	public int getBalance() {
		return balance;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#getCreditCard()
	 */
	@Override
	public CreditCard getCreditCard() {
		return creditCard;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#getFullName()
	 */
	@Override
	public String getFullName() {
		return fullName;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#getPasswordHash()
	 */
	@Override
	public byte[] getPasswordHash() {
		return passWordHash;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#getPhone()
	 */
	@Override
	public String getPhone() {
		return phone;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#reflectOrder(edu.uw.ext.framework.order.Order, int)
	 */
	@Override
	public void reflectOrder(edu.uw.ext.framework.order.Order order, int amount) {
		if (order.isBuyOrder()) {
			balance = balance - amount;
		} else {
			balance += amount;
		}
		try {
			if (this.accountManager != null) {
				this.accountManager.persist(this);
			}
		} catch (AccountException e) {

				logger.debug("Error in SimpleAccount at reflectOrder()");
}
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#registerAccountManager(edu.uw.ext.framework.account.AccountManager)
	 */
	@Override
	public void registerAccountManager(AccountManager accountManager) {
		if (this.accountManager == null) {
			this.accountManager = accountManager;
		} else {
			logger.error("Attempting to set the account manage, after it has been initialized");
		}

	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#setAddress(edu.uw.ext.framework.account.Address)
	 */
	@Override
	public void setAddress(Address address) {
		this.address = address;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#setBalance(int)
	 */
	@Override
	public void setBalance(int balance) {
		this.balance = balance;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#setCreditCard(edu.uw.ext.framework.account.CreditCard)
	 */
	@Override
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#setFullName(java.lang.String)
	 */
	@Override
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) throws AccountException {
		if (name == null || name.length() < 8) {
			throw new AccountException();
		}
		this.name = name;

	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#setPasswordHash(byte[])
	 */
	@Override
	public void setPasswordHash(byte[] passWordHash) {
		this.passWordHash = passWordHash;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Account#setPhone(java.lang.String)
	 */
	@Override
	public void setPhone(String phone) {
		this.phone = phone;
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
		ThisAccount.logger = logger;
	}

}
