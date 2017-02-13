package edu.uw.beardcl.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

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
 * The Class JsonAccountDao.
 */
public class JsonAccountDao implements AccountDao{

	/** The logger. */
	static Logger logger =
			LoggerFactory.getLogger(JsonAccountDao.class);
	
	/** The Constant storageDirectory. */
	static final String storageDirectory = "accounts";
	
	/** The Constant storageDirectoryPrefix. */
	static final String storageDirectoryPrefix = storageDirectory + File.separator;
	
	/** The Constant storageFileSuffix. */
	static final String storageFileSuffix = ".json";
	
	/**
	 * Make file name.
	 *
	 * @param name the name
	 * @return the string
	 */
	private static String makeFileName(String name){
			return storageDirectoryPrefix + name + storageFileSuffix;
		}
	
	/** The module. */
	SimpleModule module = null;
	
	/**
	 * Instantiates a new json account dao.
	 */
	public JsonAccountDao(){
		File dir = new File(storageDirectory);
		if(!dir.exists()){
			dir.mkdir();
		}
		//if dir exists but is not a directory log message
		module = new SimpleModule();
		module.addAbstractTypeMapping(Address.class, ThisAddress.class);
		module.addAbstractTypeMapping(CreditCard.class, ThisCreditCard.class);
	}
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#close()
	 */
	@Override
	public void close() throws AccountException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#deleteAccount(java.lang.String)
	 */
	@Override
	public void deleteAccount(String name) throws AccountException {
			File account = new File(makeFileName(name));
			account.delete();
		
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String name) {
			Account retval = null;
			try {
				FileInputStream input = new FileInputStream(makeFileName(name));
				ObjectMapper deserial = new ObjectMapper();
				deserial.registerModule(module);
				try {
					retval = deserial.readValue(input, ThisAccount.class);
				} catch (JsonParseException e) {
					logger.debug("Cannot ParseJson, error in JsonAccountDao.");
				} catch (JsonMappingException e) {
					logger.debug("Error in getAccount method of JsonAccountDao, JsonMapping");
				} catch (IOException e) {
					logger.debug("Error in getAccount method of JsonAccountDao, IoException");
				}
			} catch (FileNotFoundException e) {
				logger.error("File not found error in JsonAccountDao");
			}
		return retval;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#reset()
	 */
	@Override
	public void reset() throws AccountException {
				File dir = new File(JsonAccountDao.storageDirectory);
				for(File f: dir.listFiles()){
					f.delete();
				}
		
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.AccountDao#setAccount(edu.uw.ext.framework.account.Account)
	 */
	@Override
	public void setAccount(Account account) throws AccountException {
			try {
				FileOutputStream output = new FileOutputStream(makeFileName(account.getName()));
				ObjectMapper serial = new ObjectMapper();
				try {
					serial.writeValue(output, account);
				} catch (JsonGenerationException e) {
					logger.debug("Cannot execute Json generation");
				} catch (JsonMappingException e) {
					logger.debug("Cannot map file to JsonDao.");
				} catch (IOException e) {
					logger.debug("Problem with file input, JsonAccountDao");
				}
			} catch (FileNotFoundException e) {
				logger.debug("No file to set. Error in setAccount JsonAccountDao.");
			}
			
	}

}
