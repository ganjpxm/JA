/**
 * CmArticle.java
 * 
 * Created by Gan Jianping on 15/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.jweb.entity;

/**
 * <p>CmPhoto</p>
 * 
 * @author GanJianping
 * @since 1.0
 */
public class CmPhoto extends BaseModel{
	private String photoId;
	private String photoName;
	private String title;
	private String thumbUrl;
	private String url;
	private String originUrl;
	private String tag;
	private String remark;
	private Integer displayNo;
	private String roleIds;
	private String operatorId;
	private String operatorName;
	private String refArticleId;
	
	//----------------------------------------------- default constructor --------------------------
    public CmPhoto() {
    	super();
    }
    
    //------------------------------------------------ Property accessors --------------------------
/**
	 * @return String
	 */
	public String getPhotoId() {
        return this.photoId;
    }
    
    /**
	 * @param String photoId
	 */
    public void setPhotoId(String photoId) {
		this.photoId = photoId;
    }
    /**
	 * @return String
	 */
	public String getPhotoName() {
        return this.photoName;
    }
    
    /**
	 * @param String photoName
	 */
    public void setPhotoName(String photoName) {
		this.photoName = photoName;
    }
    /**
	 * @return String
	 */
	public String getTitle() {
        return this.title;
    }
    
    /**
	 * @param String title
	 */
    public void setTitle(String title) {
		this.title = title;
    }
    /**
	 * @return String
	 */
	public String getUrl() {
        return this.url;
    }
    
    /**
	 * @param String url
	 */
    public void setUrl(String url) {
		this.url = url;
    }
    /**
	 * @return String
	 */
	public String getTag() {
        return this.tag;
    }
    
    /**
	 * @param String tag
	 */
    public void setTag(String tag) {
		this.tag = tag;
    }
    /**
	 * @return String
	 */
	public String getRemark() {
        return this.remark;
    }
    
    /**
	 * @param String remark
	 */
    public void setRemark(String remark) {
		this.remark = remark;
    }
    /**
	 * @return String
	 */
	public String getOperatorId() {
        return this.operatorId;
    }
    
    /**
	 * @param String operatorId
	 */
    public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
    }
    /**
	 * @return String
	 */
	public String getOperatorName() {
        return this.operatorName;
    }
    
    /**
	 * @param String operatorName
	 */
    public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
    }

    public Integer getDisplayNo() {
		return displayNo;
	}

	public void setDisplayNo(Integer displayNo) {
		this.displayNo = displayNo;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public String getRefArticleId() {
		return refArticleId;
	}

	public void setRefArticleId(String refArticleId) {
		this.refArticleId = refArticleId;
	}
	
}