package edu.uw.beardcl.account;

import edu.uw.ext.framework.account.CreditCard;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleCreditCard.
 */
public class ThisCreditCard implements CreditCard{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2555146323878773473L;
	  
  	/** The account number. */
  	String accountNumber = null;
	  
  	/** The expiration date. */
  	String expirationDate = null;
	  
  	/** The holder. */
  	String holder = null;
	  
  	/** The issuer. */
  	String issuer = null;
	  
  	/** The type. */
  	String type = null;
	
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#getAccountNumber()
	 */
	@Override
	public String getAccountNumber() {
		return accountNumber;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#getExpirationDate()
	 */
	@Override
	public String getExpirationDate() {
		return expirationDate;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#getHolder()
	 */
	@Override
	public String getHolder() {
		return holder;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#getIssuer()
	 */
	@Override
	public String getIssuer() {
		return issuer;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#setAccountNumber(java.lang.String)
	 */
	@Override
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#setExpirationDate(java.lang.String)
	 */
	@Override
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#setHolder(java.lang.String)
	 */
	@Override
	public void setHolder(String holder) {
		this.holder = holder;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#setIssuer(java.lang.String)
	 */
	@Override
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.CreditCard#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	

}
