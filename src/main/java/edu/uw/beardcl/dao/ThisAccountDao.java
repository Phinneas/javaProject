package edu.uw.beardcl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uw.beardcl.account.ThisAccount;
import edu.uw.beardcl.account.ThisAddress;
import edu.uw.beardcl.account.ThisCreditCard;
import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.Address;
import edu.uw.ext.framework.account.CreditCard;
import edu.uw.ext.framework.dao.AccountDao;


// TODO: Auto-generated Javadoc
/**
 * The Class SimpleAccountDao.
 */
public final class ThisAccountDao implements AccountDao {

	/** The db. */
	DataSource db;
	
	/** The con. */
	Connection con;
	
	/** The logger. */
	static Logger logger =
			LoggerFactory.getLogger(ThisAccountDao.class);
	

	/**
	 * Make statement.
	 *
	 * @param statement the statement
	 * @return the prepared statement
	 */
	private PreparedStatement makeStatement(String statement) {
		try {
			return con.prepareStatement(statement);
		} catch (SQLException e) {
			logger.debug("Error in makeStatement Prepared Statement method, SimpleAccountDao");
return null;
		}
	}
	
	/**
	 * Close prepared statement.
	 *
	 * @param statement the statement
	 */
	private void closePreparedStatement(PreparedStatement statement){
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				logger.debug("Error in closePreparedStatement method, SimpleAccountDao");
			}
		}
	}

	/**
	 * Make account exists statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeAccountExistsStatement() {
		return makeStatement("SELECT accountid" + " FROM account"
				+ " WHERE accountid = ?");
	}

	/**
	 * Make get account statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeGetAccountStatement() {
		return makeStatement("SELECT a.password_hash, a.balance, a.fullname, a.phone, a.email,"
				+ "       b.street, b.city, b.state, b.zip,"
				+ "       c.card_number, c.issuer, c.cardtype, c.holder, c.expires"
				+ "  FROM account a"
				+ "  LEFT JOIN address b ON a.accountid = b.accountid"
				+ "  LEFT JOIN creditcard c ON a.accountid = c.accountid"
				+ " WHERE a.accountid = ?");
	}

	/**
	 * Make new account statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeNewAccountStatement() {
		return makeStatement("INSERT INTO account ( password_hash, balance, fullname, phone, email, accountid )"
				+ " VALUES ( ?, ?, ?, ?, ?, ? ) ");
	}

	/**
	 * Make set account statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeSetAccountStatement() {
		return makeStatement("UPDATE account" + "   SET password_hash =?,"
				+ "       balance = ?," + "       fullname = ?,"
				+ "       phone = ?," + "       email = ?"
				+ " WHERE accountid = ?");
	}

	/**
	 * Make address exists statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeAddressExistsStatement() {
		return makeStatement("SELECT accountid" + "  FROM address"
				+ " WHERE accountid = ?");
	}

	/**
	 * Make new address statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeNewAddressStatement() {
		return makeStatement("INSERT INTO address ( street, city, state, zip, accountid )"
				+ " VALUES ( ?, ?, ?, ?, ? )");
	}

	/**
	 * Make set address statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeSetAddressStatement() {
		return makeStatement("UPDATE address" + " SET street = ?,"
				+ "     city = ?," + "     state = ?," + "     zip = ?"
				+ " WHERE accountid = ?");
	}

	/**
	 * Make card exists statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeCardExistsStatement() {
		return makeStatement("SELECT accountid" + "  FROM creditcard"
				+ " WHERE accountid = ?");
	}

	/**
	 * Make new card statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeNewCardStatement() {
		return makeStatement("INSERT INTO creditcard ( card_number, issuer, cardtype, holder, expires, accountid )"
				+ " VALUES ( ?, ?, ?, ?, ?, ? )");
	}

	/**
	 * Make set card statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeSetCardStatement() {
		return makeStatement("UPDATE creditcard" + "  SET card_number = ?,"
				+ "     issuer = ?," + "     cardtype = ?,"
				+ "     holder = ?," + "     expires = ?"
				+ " WHERE accountid = ?");
	}

	/**
	 * Make delete account statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeDeleteAccountStatement() {
		return makeStatement("DELETE from account" + " WHERE accountid = ?");
	}

	/**
	 * Make delete address statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeDeleteAddressStatement() {
		return makeStatement("DELETE from address" + " WHERE accountid = ?");
	}

	/**
	 * Make delete card statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeDeleteCardStatement() {
		return makeStatement("DELETE from creditcard" + " WHERE accountid = ?");
	}

	/**
	 * Make delete all statement.
	 *
	 * @return the prepared statement
	 */
	private PreparedStatement makeDeleteAllStatement() {
		return makeStatement("DELETE from account");
	}
	
	/**
	 * Instantiates a new simple account dao.
	 */
	public ThisAccountDao(){
		InitialContext ctxt;
		try {
			ctxt = new InitialContext();
			db = (DataSource) ctxt.lookup("jdbc/AccountDb");

		} catch (NamingException e) {
			logger.debug("NamingException error at SimpleAccountDao constructor.");
		}
		try {
			con = db.getConnection();
		} catch (SQLException e) {
			logger.error("SQLException in SimpleAccountDao constructor.");
			}
	}
	
	/**
	 * Checks if is account.
	 *
	 * @param name the name
	 * @return true, if is account
	 */
	private boolean isAccount(String name){
		PreparedStatement statement = this.makeAccountExistsStatement();
		try {
			statement.setString(1, name);
			return statement.executeQuery().next();
		} catch (SQLException e) {
			logger.error("Error in isAccount, SQLException thrown in isAccount catch block.");
			return false;
		}finally{
			closePreparedStatement(statement);
		}
		
	}
	
	/**
	 * Checks if is address.
	 *
	 * @param name the name
	 * @return true, if is address
	 */
	private boolean isAddress(String name){
		PreparedStatement statement = this.makeAddressExistsStatement();
		try {
			statement.setString(1, name);
			return statement.executeQuery().next();
		} catch (SQLException e) {
			logger.error("Cannot execute query in isAddress method of SimpleAccountDao.");
			return false;
		}finally{
			closePreparedStatement(statement);
		}
		
	}
	
	/**
	 * Checks if is card.
	 *
	 * @param name the name
	 * @return true, if is card
	 */
	private boolean isCard(String name){
		PreparedStatement statement = this.makeCardExistsStatement();
		try {
			statement.setString(1, name);
			return statement.executeQuery().next();
		} catch (SQLException e) {
			logger.error("Error with execute query of isCard method.");
			return false;
		}finally{
			closePreparedStatement(statement);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#close()
	 */
	@Override
	public void close() throws AccountException {
			
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#deleteAccount(java.lang.String)
	 */
	@Override
	public void deleteAccount(String name) throws AccountException {
		PreparedStatement doAccount = this.makeDeleteAccountStatement();
		PreparedStatement doAddress = this.makeDeleteAddressStatement();
		PreparedStatement doCreditCard = this.makeDeleteCardStatement();
		
		try{
			if(!isAccount(name)){
				throw new AccountException("This is not a current account");
			}
			doAccount.setString(1, name);
			doAddress.setString(1, name);
			doCreditCard.setString(1, name);
			doAddress.executeUpdate();
			doCreditCard.executeUpdate();
			doAccount.executeUpdate();
			
		}catch (SQLException e){
			logger.error("SQLEXception thrown at isAccount check of SimpleAccountDao."
					+ " Cannot open account");
throw new AccountException();
		}finally{
			closePreparedStatement(doAccount);
			closePreparedStatement(doAddress);
			closePreparedStatement(doCreditCard);
		}
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String name) {
		PreparedStatement statement = this.makeGetAccountStatement();
		if(!isAccount(name)){
			return null;
		}
		Account retval = new ThisAccount();
		try{
			statement.setString(1, name);
			ResultSet result = statement.executeQuery();
			result.next();
			retval.setPasswordHash(result.getBytes(1));
			retval.setBalance(result.getInt(2));
			retval.setFullName(result.getString(3));
			retval.setPhone(result.getString(4));
			retval.setEmail(result.getString(5));
			try {
				retval.setName(name);
			} catch (AccountException e) {
				retval = null;
				logger.error("Error creating account in getAccount method.");
			}
			if(isAddress(name)){
				Address address = new ThisAddress();
				address.setStreetAddress(result.getString(6));
				address.setCity(result.getString(7));
				address.setState(result.getString(8));
				address.setZipCode(result.getString(9));
				retval.setAddress(address);
			}
			if(isCard(name)){
				CreditCard card = new ThisCreditCard();
				card.setAccountNumber(result.getString(10));
				card.setIssuer(result.getString(11));
				card.setType(result.getString(12));
				card.setHolder(result.getString(13));
				card.setExpirationDate(result.getString(14));
				retval.setCreditCard(card);
			}
			
		
		}catch (SQLException e){
			retval = null;
			logger.error("SQLException ");
		}finally{
			closePreparedStatement(statement);
		}
		return retval;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#reset()
	 */
	@Override
	public void reset() throws AccountException {
		PreparedStatement statement = null;
		statement = this.makeDeleteAllStatement();
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.debug("Cannot reset account");
			throw new AccountException("This account cannot be updated");
		}finally{
			closePreparedStatement(statement);
		}
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#setAccount(edu.uw.ext.framework.account.Account)
	 */
	@Override
	public void setAccount(Account account) throws AccountException {
		if(account == null){
			throw new AccountException();
		}
		PreparedStatement doAccount = null;
		PreparedStatement doAddress = null;
		PreparedStatement doCreditCard = null;
		PreparedStatement doDeleteAddress = this.makeDeleteAddressStatement();
		PreparedStatement doDeleteCreditCard = this.makeDeleteCardStatement();
		
		try{
			if(isAccount(account.getName())){
				doAccount = this.makeSetAccountStatement();
				doAddress = this.makeSetAddressStatement();
				doCreditCard = this.makeSetCardStatement();
			}else{
				doAccount = this.makeNewAccountStatement();
				doAddress = this.makeNewAddressStatement();
				doCreditCard = this.makeNewCardStatement();
			}
			doAccount.setBytes(1, account.getPasswordHash());
			doAccount.setInt(2, account.getBalance());
			doAccount.setString(3, account.getFullName());
			doAccount.setString(4, account.getPhone());
			doAccount.setString(5, account.getEmail());
			doAccount.setString(6, account.getName());
			doAccount.executeUpdate();
			Address address = account.getAddress();
			if(address != null){
				doAddress.setString(1, address.getStreetAddress());
				doAddress.setString(2, address.getCity());
				doAddress.setString(3, address.getState());
				doAddress.setString(4, address.getZipCode());
				doAddress.setString(5, account.getName());
				doAddress.executeUpdate();
			}else{
				doDeleteAddress.setString(1, account.getName());
				doDeleteAddress.executeUpdate();
			}
			
			CreditCard card = account.getCreditCard();
			if(card != null){
				doCreditCard.setString(1, card.getAccountNumber());
				doCreditCard.setString(2, card.getIssuer());
				doCreditCard.setString(3, card.getType());
				doCreditCard.setString(4, card.getHolder());
				doCreditCard.setString(5, card.getExpirationDate());
				doCreditCard.setString(6, account.getName());
				doCreditCard.executeUpdate();
			}else{
				doDeleteCreditCard.setString(1, account.getName());
				doDeleteCreditCard.executeUpdate();
			}
		}catch (SQLException e) {
			
		}finally {
			closePreparedStatement(doAccount);
			closePreparedStatement(doAddress);
			closePreparedStatement(doCreditCard);
			closePreparedStatement(doDeleteAddress);
			closePreparedStatement(doDeleteCreditCard);

		}
	}
	
	/**
	 * Gets the logger.
	 *
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}
	
	/**
	 * Sets the logger.
	 *
	 * @param logger the new logger
	 */
	public static void setLogger(Logger logger) {
		ThisAccountDao.logger = logger;
	}

}
