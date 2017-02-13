package edu.uw.beardcl.dao;

import org.slf4j.LoggerFactory;

import edu.uw.ext.framework.dao.AccountDao;
import edu.uw.ext.framework.dao.DaoFactory;
import edu.uw.ext.framework.dao.DaoFactoryException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating JsonDao objects.
 */
public class JsonDaoFactory implements DaoFactory{
	
	/** The logger. */
	private static org.slf4j.Logger logger =
			LoggerFactory.getLogger(ThisDaoFactory.class);
	
	/** The dao. */
	static AccountDao dao; 
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.dao.DaoFactory#getAccountDao()
	 */
	@Override
	public AccountDao getAccountDao() throws DaoFactoryException {
		if(dao == null){
			dao = new JsonAccountDao();
		}
		return dao;
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
		JsonDaoFactory.logger = logger;
	}
	
}