package edu.uw.beardcl.account;

import edu.uw.ext.framework.account.Address;


// TODO: Auto-generated Javadoc
/**
 * The Class SimpleAddress.
 */
public class ThisAddress implements Address{

	/** The Constant serialVersionUID. */
	private static final  long serialVersionUID = 90;
	 
 	/** The city. */
 	String city = null;
	  
  	/** The state. */
  	String state = null;
	  
  	/** The street address. */
  	String streetAddress = null;
	  
  	/** The zip code. */
  	String zipCode = null;
	
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Address#getCity()
	 */
	@Override
	public String getCity() {
		return city;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Address#getState()
	 */
	@Override
	public String getState() {
		return state;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Address#getStreetAddress()
	 */
	@Override
	public String getStreetAddress() {
		return streetAddress;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Address#getZipCode()
	 */
	@Override
	public String getZipCode() {
		return zipCode;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Address#setCity(java.lang.String)
	 */
	@Override
	public void setCity(String city) {
		this.city = city;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Address#setState(java.lang.String)
	 */
	@Override
	public void setState(String state) {
		this.state = state;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Address#setStreetAddress(java.lang.String)
	 */
	@Override
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.account.Address#setZipCode(java.lang.String)
	 */
	@Override
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
	
}
