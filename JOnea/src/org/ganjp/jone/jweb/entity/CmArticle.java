/**
 * CmArticle.java
 * 
 * Created by Gan Jianping on 15/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.jweb.entity;


/**
 * <p>CmArticle</p>
 * 
 * @author GanJianping
 * @since 1.0
 */
public class CmArticle extends BaseModel{
	private String articleId;
	private String articleCd;
	private String title;
	private String summary;
	private String content;
	private String author;
	private String imageUrl;
	private String originUrl;
	private String tag;
	private Integer displayNo;
	private String roleIds;
	private String operatorId;
	private String operatorName;
	
		
	//----------------------------------------------- default constructor --------------------------
    public CmArticle() {
    	super();
    }
    
    //------------------------------------------------ Property accessors --------------------------
/**
	 * @return String
	 */
	public String getArticleId() {
        return this.articleId;
    }
    
    /**
	 * @param String articleId
	 */
    public void setArticleId(String articleId) {
		this.articleId = articleId;
    }
    /**
	 * @return String
	 */
	public String getArticleCd() {
        return this.articleCd;
    }
    
    /**
	 * @param String articleCd
	 */
    public void setArticleCd(String articleCd) {
		this.articleCd = articleCd;
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
	public String getContent() {
        return this.content;
    }
    
    /**
	 * @param String content
	 */
    public void setContent(String content) {
		this.content = content;
    }
    /**
	 * @return String
	 */
	public String getAuthor() {
        return this.author;
    }
    
    /**
	 * @param String author
	 */
    public void setAuthor(String author) {
		this.author = author;
    }
    /**
	 * @return String
	 */
	public String getImageUrl() {
        return this.imageUrl;
    }
    
    /**
	 * @param String imageUrl
	 */
    public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
    }
    /**
	 * @return String
	 */
	public String getOriginUrl() {
        return this.originUrl;
    }
    
    /**
	 * @param String originUrl
	 */
    public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
    }
    /**
	 * @return Integer
	 */
	public Integer getDisplayNo() {
        return this.displayNo;
    }
    
    /**
	 * @param Integer displayNo
	 */
    public void setDisplayNo(Integer displayNo) {
		this.displayNo = displayNo;
    }
    /**
	 * @return String
	 */
	public String getRoleIds() {
        return this.roleIds;
    }
    
    /**
	 * @param String roleIds
	 */
    public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
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

    public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
 
}