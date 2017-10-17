package com.sinosoft.xf.petition.petitionaccept.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sinosoftframework.core.domain.IdEntity;

/**
 * 重复件判定 entity.
 * @author ljx
 * @createDate 2011-10-12
 */
@Entity
@Table(name = "REPEATINFO")
public class RepeatJudge extends IdEntity {

		private static final long serialVersionUID = 1L;

		private String xfno;
		private String directXfno;
		private String mainXfno;
//		private String repeatType;
		private Date makeDate;
		private String makeTime;
		private String operator;
		private String sameDealFlag;
		private String moreCast;
		private String sue;
		private String repeatArchive;
		private Integer repeatNum;
		private String repeatLetter;

		
		@Column(name = "Repeat_Letter")
		public String getRepeatLetter() {
			return repeatLetter;
		}

		public void setRepeatLetter(String repeatLetter) {
			this.repeatLetter = repeatLetter;
		}

		@Column(name = "Repeat_Num")
		public Integer getRepeatNum() {
			return repeatNum;
		}

		public void setRepeatNum(Integer repeatNum) {
			this.repeatNum = repeatNum;
		}

		@Column(name = "XFNO", length = 20)
		public String getXfno() {
			return this.xfno;
		}

		public void setXfno(String xfno) {
			this.xfno = xfno;
		}

		@Column(name = "DIRECT_XFNO", length = 20)
		public String getDirectXfno() {
			return this.directXfno;
		}

		public void setDirectXfno(String directXfno) {
			this.directXfno = directXfno;
		}

		@Column(name = "MAIN_XFNO", length = 20)
		public String getMainXfno() {
			return this.mainXfno;
		}

		public void setMainXfno(String mainXfno) {
			this.mainXfno = mainXfno;
		}

//		@Column(name = "REPEAT_TYPE", length = 1)
//		public String getRepeatType() {
//			return this.repeatType;
//		}
//
//		public void setRepeatType(String repeatType) {
//			this.repeatType = repeatType;
//		}

		@Temporal(TemporalType.DATE)
		@Column(name = "MAKE_DATE", length = 10)
		public Date getMakeDate() {
			return this.makeDate;
		}

		public void setMakeDate(Date makeDate) {
			this.makeDate = makeDate;
		}

		@Column(name = "MAKE_TIME", length = 8)
		public String getMakeTime() {
			return this.makeTime;
		}

		public void setMakeTime(String makeTime) {
			this.makeTime = makeTime;
		}

		@Column(name = "OPERATOR", length = 50)
		public String getOperator() {
			return this.operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		@Column(name = "SAME_DEAL_FLAG", length = 1)
		public String getSameDealFlag() {
			return this.sameDealFlag;
		}

		public void setSameDealFlag(String sameDealFlag) {
			this.sameDealFlag = sameDealFlag;
		}

		@Column(name = "MORE_CAST", length = 1)
		public String getMoreCast() {
			return this.moreCast;
		}

		public void setMoreCast(String moreCast) {
			this.moreCast = moreCast;
		}

		@Column(name = "SUE", length = 1)
		public String getSue() {
			return this.sue;
		}

		public void setSue(String sue) {
			this.sue = sue;
		}

		@Column(name = "REPEAT_ARCHIVE", length = 1)
		public String getRepeatArchive() {
			return this.repeatArchive;
		}

		public void setRepeatArchive(String repeatArchive) {
			this.repeatArchive = repeatArchive;
		}

}