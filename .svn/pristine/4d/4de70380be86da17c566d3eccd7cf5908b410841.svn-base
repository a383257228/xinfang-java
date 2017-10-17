package com.sinosoftframework.core.domain.AudiEntity;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 含审计信息的Entity基类.
 * 
 * @author Lirb
 */
@MappedSuperclass
public class AudiEntity extends IdEntity {

	private static final long serialVersionUID = 1L;

	public Timestamp createDate;
	public String creatorName;
	public String creatorId;
	public Timestamp modifyDate;
	public String modifyName;
	public String modifyId;

	/**
	 * 创建时间. 本属性只在save时有效,update时无效.
	 * 
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * 创建的操作员的登录名.
	 * 
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId
	 *            the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * 最后修改时间. 本属性只在update时有效,save时无效.
	 * 
	 * @return the modifyDate
	 */
	public Timestamp getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate
	 *            the modifyDate to set
	 */
	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 最后修改的操作员的登录名.
	 * 
	 * @return the modifyId
	 */
	public String getModifyId() {
		return modifyId;
	}

	/**
	 * @param modifyId
	 *            the modifyId to set
	 */
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName
	 *            the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return the modifyName
	 */
	public String getModifyName() {
		return modifyName;
	}

	/**
	 * @param modifyName
	 *            the modifyName to set
	 */
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

}
