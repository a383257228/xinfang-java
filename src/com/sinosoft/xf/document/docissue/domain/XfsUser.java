package com.sinosoft.xf.document.docissue.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公文批示领导信息实体类
 * @author wanghang
 * @createDat 2011-05-5
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "XFSUSER")
public class XfsUser implements java.io.Serializable {
	private String usercode;
	private String username;
	private String password;
	private String userdescription;
	private String leadercode;
	private String userstate;
	private String importantflag;
	private String operator;
	private String deptcode;
	private Date makedate;
	private String maketime;
	private Date validstartdate;
	private Date validenddate;
	private String queryflag;
	private String parentusercode;

	/**
	 * @return the usercode
	 */
	@Id
	public String getUsercode() {
		return usercode;
	}

	/**
	 * @param usercode
	 *            the usercode to set
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *  the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userdescription
	 */
	public String getUserdescription() {
		return userdescription;
	}

	/**
	 * @param userdescription  the userdescription to set
	 */
	public void setUserdescription(String userdescription) {
		this.userdescription = userdescription;
	}

	/**
	 * @return the leadercode
	 */
	public String getLeadercode() {
		return leadercode;
	}

	/**
	 * @param leadercode
	 *            the leadercode to set
	 */
	public void setLeadercode(String leadercode) {
		this.leadercode = leadercode;
	}

	/**
	 * @return the userstate
	 */
	public String getUserstate() {
		return userstate;
	}

	/**
	 * @param userstate
	 *            the userstate to set
	 */
	public void setUserstate(String userstate) {
		this.userstate = userstate;
	}

	/**
	 * @return the importantflag
	 */
	public String getImportantflag() {
		return importantflag;
	}

	/**
	 * @param importantflag
	 *            the importantflag to set
	 */
	public void setImportantflag(String importantflag) {
		this.importantflag = importantflag;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the deptcode
	 */
	public String getDeptcode() {
		return deptcode;
	}

	/**
	 * @param deptcode
	 *            the deptcode to set
	 */
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	/**
	 * @return the makedate
	 */
	public Date getMakedate() {
		return makedate;
	}

	/**
	 * @param makedate
	 *            the makedate to set
	 */
	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	/**
	 * @return the maketime
	 */
	public String getMaketime() {
		return maketime;
	}

	/**
	 * @param maketime
	 *            the maketime to set
	 */
	public void setMaketime(String maketime) {
		this.maketime = maketime;
	}

	/**
	 * @return the validstartdate
	 */
	public Date getValidstartdate() {
		return validstartdate;
	}

	/**
	 * @param validstartdate
	 *            the validstartdate to set
	 */
	public void setValidstartdate(Date validstartdate) {
		this.validstartdate = validstartdate;
	}

	/**
	 * @return the validenddate
	 */
	public Date getValidenddate() {
		return validenddate;
	}

	/**
	 * @param validenddate
	 *            the validenddate to set
	 */
	public void setValidenddate(Date validenddate) {
		this.validenddate = validenddate;
	}

	/**
	 * @return the queryflag
	 */
	public String getQueryflag() {
		return queryflag;
	}

	/**
	 * @param queryflag
	 *            the queryflag to set
	 */
	public void setQueryflag(String queryflag) {
		this.queryflag = queryflag;
	}

	/**
	 * @return the parentusercode
	 */
	public String getParentusercode() {
		return parentusercode;
	}

	/**
	 * @param parentusercode
	 *            the parentusercode to set
	 */
	public void setParentusercode(String parentusercode) {
		this.parentusercode = parentusercode;
	}

}
