/**
 * BmConfig.java
 *
 * Created by Gan Jianping on 12/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.jweb.entity;


/**
 * <p>BmConfig</p>
 * 
 * @author GanJianping
 * @since 1.0
 */
public class BmConfig extends BaseModel{
	private String configId;
	private String configCd;
	private String configName;
	private String configValue;
	private String description;
		
	//----------------------------------------------- default constructor --------------------------
    public BmConfig() {
    	super();
    }
    
    //------------------------------------------------ Property accessors --------------------------
    /**
	 * @return String
	 */
	public String getConfigId() {
        return this.configId;
    }
    
    /**
	 * @param String configId
	 */
    public void setConfigId(String configId) {
		this.configId = configId;
    }
    /**
	 * @return String
	 */
	public String getConfigCd() {
        return this.configCd;
    }
    
    /**
	 * @param String configCd
	 */
    public void setConfigCd(String configCd) {
		this.configCd = configCd;
    }
    /**
	 * @return String
	 */
	public String getConfigName() {
        return this.configName;
    }
    
    /**
	 * @param String configName
	 */
    public void setConfigName(String configName) {
		this.configName = configName;
    }
    /**
	 * @return String
	 */
	public String getConfigValue() {
        return this.configValue;
    }
    
    /**
	 * @param String configValue
	 */
    public void setConfigValue(String configValue) {
		this.configValue = configValue;
    }
    /**
	 * @return String
	 */
	public String getDescription() {
        return this.description;
    }
    
    /**
	 * @param String description
	 */
    public void setDescription(String description) {
		this.description = description;
    }

}