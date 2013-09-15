/**
 * JpApplication.java
 *
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import org.ganjp.jlib.core.BaseApplication;
import org.ganjp.jlib.core.dao.SystemDAO;
import org.ganjp.jone.jweb.dao.JWebDaoFactory;
import org.ganjp.jone.jweb.dao.JWebDaoFactory.DAOType;


/**
 * <p>Base Application</p>
 * 
 * @author GanJianping
 * @since v1.0.0
 */
public class JOneApplication extends BaseApplication {
	
	/**
	 * <p>Get System DAO</p>
	 */
	public static SystemDAO getSystemDAO() {
		return (SystemDAO) (JWebDaoFactory.getInstance().getDAO(DAOType.SYSTEM, JOneApplication.getAppContext()));
	}
}	
