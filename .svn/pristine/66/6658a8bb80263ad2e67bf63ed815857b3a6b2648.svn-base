package com.sinosoft.xf.petition.domainutil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 用于封装委领导首页说明信息变量
 * @date 2013-8-7
 * @author jk
 */
public class LeaderInfo implements  Serializable{
	private static final long serialVersionUID = 1L;
	private int cityYearAcceptNum;//全市本年累计受理量
	private int cityLastYearAcceptNum;//全市去年累计受理量
	private int cityMonthAcceptNum;//全市本月累计受理量
	private int cityLastMonthAcceptNum;//全市上月累计受理量
	/**
	 * 本月上级转交办受理量
	 */
	private int monthTransAssignNum;
	/**
	 * 本年上级转交办受理量
	 */
	private int yearTransAssignNum;
	/**
	 * 上年本月上级转交办受理量
	 */
	private int lastYearMonthTransAssignNum;
	/**
	 * 去年上级转交办受理量
	 */
	private int lastYearTransAssignNum;
	private int allDeptYearAcceptNum;//本委本年累计受理量
	private int allDeptLastYearAcceptNum;//本委去年累计受理量
	private int allDeptMonthAcceptNum;//本委本月累计受理量
	private int allDeptLastMonthAcceptNum;//本委上月同比累计受理量
	private int allDeptYearDealNum;//本委本年办理累计量
	
	private int allDeptMonthDealNum;//本委本月办理累计量
	private int allDeptEndNum;//本年办结累计
	private String endRatio;//办结率
	private String realityRatio;//属实率
	
	//by hjh 文字总体统计页面数字
	private int bwJujiYearNum;//本委局级本年总量
	private int bwJujiMonthNum;//本委局级本月总量
	private int bwJujiLastYearNum;//本委局级去年总量
	private int bwJujiLastMonthNum;//本委局级上月同比总量
	private int qsMonthJJKGNum; //全市本月检举控告总量	
	private int qsMonthSS;//全市本月申诉总量
	private int qsMonthPPJY;//全市本月批评建议总量
	private int qsMonthYWFWW;////全市本月业务范围外总量
	
	private int bwMonthJJKGNum; //本委本月检举控告总量
	private int bwMonthSS;//本委本月申诉总量
	private int bwMonthPPJY;//本委本月批评建议总量
	private int bwMonthYWFWW;////本委本月业务范围外总量
	
	private int qsYearJJKGNum; //全市本年检举控告总量
	private int qsYearSS;//全市本年申诉总量
	private int qsYearPPJY;//全市本年批评建议总量
	private int qsYearYWFWW;////全市本年业务范围外总量
	
	private int bwYearJJKGNum; //本委本年检举控告总量
	private int bwYearSS;//本委本年申诉总量
	private int bwYearPPJY;//本委本年批评建议总量
	private int bwYearYWFWW;////本委本月业务范围外总量
	
	private int qsLastMonthJJKGNum; //全市上月同比检举控告总量
	private int qsLastMonthSS;//全市上月同比申诉总量
	private int qsLastMonthPPJY;//全市上月同比批评建议总量
	private int qsLastMonthYWFWW;////全市上月同比业务范围外总量
	
	private int bwLastMonthJJKGNum; //本委上月同比检举控告总量
	private int bwLastMonthSS;//本委上月同比申诉总量
	private int bwLastMonthPPJY;//本委上月同比批评建议总量
	private int bwLastMonthYWFWW;////本委上月同比业务范围外总量
	
	private int qsLastYearJJKGNum; //全市去年检举控告总量
	private int qsLastYearSS;//全市去年申诉总量
	private int qsLastYearPPJY;//全市去年批评建议总量
	private int qsLastYearYWFWW;////全市去年业务范围外总量
	
	private int bwLastYearJJKGNum; //本委去年检举控告总量
	private int bwLastYearSS;//本委去年申诉总量
	private int bwLastYearPPJY;//本委去年批评建议总量
	private int bwLastYearYWFWW;////本委本月业务范围外总量
	
	

	//by liub 用于领导文字显示页右侧部门工作量统计
	private String deptName;//承办部门
	private String deptCode;//承办部门
	private String notXFSDeptCode;//除了信访室的其他有效承办部门编码
	private int deptShouldDealNum;//承办部门应办理量
	private int deptCurrDealNum;//承办部门当前在办件
	private int deptEndNum;//承办部门已办结数 
	private int deptNotKnot;//承办部门历年未结  
	private int deptEndAvgTime;//承办部门平均办结时间 
	private int deptDealAvgTime;//承办部门平均办理时间 
	private int deptEndTime;//承办部办结时间 
	private int deptDealTime;//承办部办理时间 
	
	private int bwShouldDealNum;//本委应办理量
	private int bwCurrDealNum;//本委当前在办件
	private int bwEndNum;//本委已办结数 
	private int bwEndAvgTime;//本委平均办结时间 
	private int bwDealAvgTime;//本委平均办理时间 
	private int bwNotKnot;//本委部门历年未结 
	
	private List<Top5Issue> qsYearTop5Issue = new ArrayList<Top5Issue>();//全市本年前5类问题排行
	private List<Top5Issue> qsMonthTop5Issue = new ArrayList<Top5Issue>();//全市本月前5类问题排行
	private List<Top5Issue> bwYearTop5Issue = new ArrayList<Top5Issue>();//全市本委前5类问题排行
	private List<Top5Issue> bwMonthTop5Issue = new ArrayList<Top5Issue>();//全市本委前5类问题排行
	

	public int getCityYearAcceptNum() {
		return cityYearAcceptNum;
	}
	public void setCityYearAcceptNum(int cityYearAcceptNum) {
		this.cityYearAcceptNum = cityYearAcceptNum;
	}
	public int getCityMonthAcceptNum() {
		return cityMonthAcceptNum;
	}
	public void setCityMonthAcceptNum(int cityMonthAcceptNum) {
		this.cityMonthAcceptNum = cityMonthAcceptNum;
	}
	
	public int getAllDeptYearAcceptNum() {
		return allDeptYearAcceptNum;
	}
	public void setAllDeptYearAcceptNum(int allDeptYearAcceptNum) {
		this.allDeptYearAcceptNum = allDeptYearAcceptNum;
	}
	public int getAllDeptMonthAcceptNum() {
		return allDeptMonthAcceptNum;
	}
	public void setAllDeptMonthAcceptNum(int allDeptMonthAcceptNum) {
		this.allDeptMonthAcceptNum = allDeptMonthAcceptNum;
	}
	public int getAllDeptYearDealNum() {
		return allDeptYearDealNum;
	}
	public void setAllDeptYearDealNum(int allDeptYearDealNum) {
		this.allDeptYearDealNum = allDeptYearDealNum;
	}
	public int getAllDeptMonthDealNum() {
		return allDeptMonthDealNum;
	}
	public void setAllDeptMonthDealNum(int allDeptMonthDealNum) {
		this.allDeptMonthDealNum = allDeptMonthDealNum;
	}
	public int getAllDeptEndNum() {
		return allDeptEndNum;
	}
	public void setAllDeptEndNum(int allDeptEndNum) {
		this.allDeptEndNum = allDeptEndNum;
	}
	public String getEndRatio() {
		return endRatio;
	}
	public void setEndRatio(String endRatio) {
		this.endRatio = endRatio;
	}
	public String getRealityRatio() {
		return realityRatio;
	}
	public void setRealityRatio(String realityRatio) {
		this.realityRatio = realityRatio;
	}
	public int getBwJujiYearNum() {
		return bwJujiYearNum;
	}
	public void setBwJujiYearNum(int bwJujiYearNum) {
		this.bwJujiYearNum = bwJujiYearNum;
	}
	public int getBwJujiMonthNum() {
		return bwJujiMonthNum;
	}
	public void setBwJujiMonthNum(int bwJujiMonthNum) {
		this.bwJujiMonthNum = bwJujiMonthNum;
	}

	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getQsMonthJJKGNum() {
		return qsMonthJJKGNum;
	}
	public void setQsMonthJJKGNum(int qsMonthJJKGNum) {
		this.qsMonthJJKGNum = qsMonthJJKGNum;
	}
	public int getQsMonthSS() {
		return qsMonthSS;
	}
	public void setQsMonthSS(int qsMonthSS) {
		this.qsMonthSS = qsMonthSS;
	}
	public int getQsMonthPPJY() {
		return qsMonthPPJY;
	}
	public void setQsMonthPPJY(int qsMonthPPJY) {
		this.qsMonthPPJY = qsMonthPPJY;
	}
	public int getQsMonthYWFWW() {
		return qsMonthYWFWW;
	}
	public void setQsMonthYWFWW(int qsMonthYWFWW) {
		this.qsMonthYWFWW = qsMonthYWFWW;
	}
	public int getBwMonthJJKGNum() {
		return bwMonthJJKGNum;
	}
	public void setBwMonthJJKGNum(int bwMonthJJKGNum) {
		this.bwMonthJJKGNum = bwMonthJJKGNum;
	}
	public int getBwMonthSS() {
		return bwMonthSS;
	}
	public void setBwMonthSS(int bwMonthSS) {
		this.bwMonthSS = bwMonthSS;
	}
	public int getBwMonthPPJY() {
		return bwMonthPPJY;
	}
	public void setBwMonthPPJY(int bwMonthPPJY) {
		this.bwMonthPPJY = bwMonthPPJY;
	}
	public int getBwMonthYWFWW() {
		return bwMonthYWFWW;
	}
	public void setBwMonthYWFWW(int bwMonthYWFWW) {
		this.bwMonthYWFWW = bwMonthYWFWW;
	}
	public int getQsYearJJKGNum() {
		return qsYearJJKGNum;
	}
	public void setQsYearJJKGNum(int qsYearJJKGNum) {
		this.qsYearJJKGNum = qsYearJJKGNum;
	}
	public int getQsYearSS() {
		return qsYearSS;
	}
	public void setQsYearSS(int qsYearSS) {
		this.qsYearSS = qsYearSS;
	}
	public int getQsYearPPJY() {
		return qsYearPPJY;
	}
	public void setQsYearPPJY(int qsYearPPJY) {
		this.qsYearPPJY = qsYearPPJY;
	}
	public int getQsYearYWFWW() {
		return qsYearYWFWW;
	}
	public void setQsYearYWFWW(int qsYearYWFWW) {
		this.qsYearYWFWW = qsYearYWFWW;
	}
	public int getBwYearJJKGNum() {
		return bwYearJJKGNum;
	}
	public void setBwYearJJKGNum(int bwYearJJKGNum) {
		this.bwYearJJKGNum = bwYearJJKGNum;
	}
	public int getBwYearSS() {
		return bwYearSS;
	}
	public void setBwYearSS(int bwYearSS) {
		this.bwYearSS = bwYearSS;
	}
	public int getBwYearPPJY() {
		return bwYearPPJY;
	}
	public void setBwYearPPJY(int bwYearPPJY) {
		this.bwYearPPJY = bwYearPPJY;
	}
	public int getBwYearYWFWW() {
		return bwYearYWFWW;
	}
	public void setBwYearYWFWW(int bwYearYWFWW) {
		this.bwYearYWFWW = bwYearYWFWW;
	}
	public int getQsLastMonthJJKGNum() {
		return qsLastMonthJJKGNum;
	}
	public void setQsLastMonthJJKGNum(int qsLastMonthJJKGNum) {
		this.qsLastMonthJJKGNum = qsLastMonthJJKGNum;
	}
	public int getQsLastMonthSS() {
		return qsLastMonthSS;
	}
	public void setQsLastMonthSS(int qsLastMonthSS) {
		this.qsLastMonthSS = qsLastMonthSS;
	}
	public int getQsLastMonthPPJY() {
		return qsLastMonthPPJY;
	}
	public void setQsLastMonthPPJY(int qsLastMonthPPJY) {
		this.qsLastMonthPPJY = qsLastMonthPPJY;
	}
	public int getQsLastMonthYWFWW() {
		return qsLastMonthYWFWW;
	}
	public void setQsLastMonthYWFWW(int qsLastMonthYWFWW) {
		this.qsLastMonthYWFWW = qsLastMonthYWFWW;
	}
	public int getBwLastMonthJJKGNum() {
		return bwLastMonthJJKGNum;
	}
	public void setBwLastMonthJJKGNum(int bwLastMonthJJKGNum) {
		this.bwLastMonthJJKGNum = bwLastMonthJJKGNum;
	}
	public int getBwLastMonthSS() {
		return bwLastMonthSS;
	}
	public void setBwLastMonthSS(int bwLastMonthSS) {
		this.bwLastMonthSS = bwLastMonthSS;
	}
	public int getBwLastMonthPPJY() {
		return bwLastMonthPPJY;
	}
	public void setBwLastMonthPPJY(int bwLastMonthPPJY) {
		this.bwLastMonthPPJY = bwLastMonthPPJY;
	}
	public int getBwLastMonthYWFWW() {
		return bwLastMonthYWFWW;
	}
	public void setBwLastMonthYWFWW(int bwLastMonthYWFWW) {
		this.bwLastMonthYWFWW = bwLastMonthYWFWW;
	}
	public int getQsLastYearJJKGNum() {
		return qsLastYearJJKGNum;
	}
	public void setQsLastYearJJKGNum(int qsLastYearJJKGNum) {
		this.qsLastYearJJKGNum = qsLastYearJJKGNum;
	}
	public int getQsLastYearSS() {
		return qsLastYearSS;
	}
	public void setQsLastYearSS(int qsLastYearSS) {
		this.qsLastYearSS = qsLastYearSS;
	}
	public int getQsLastYearPPJY() {
		return qsLastYearPPJY;
	}
	public void setQsLastYearPPJY(int qsLastYearPPJY) {
		this.qsLastYearPPJY = qsLastYearPPJY;
	}
	public int getQsLastYearYWFWW() {
		return qsLastYearYWFWW;
	}
	public void setQsLastYearYWFWW(int qsLastYearYWFWW) {
		this.qsLastYearYWFWW = qsLastYearYWFWW;
	}
	public int getBwLastYearJJKGNum() {
		return bwLastYearJJKGNum;
	}
	public void setBwLastYearJJKGNum(int bwLastYearJJKGNum) {
		this.bwLastYearJJKGNum = bwLastYearJJKGNum;
	}
	public int getBwLastYearSS() {
		return bwLastYearSS;
	}
	public void setBwLastYearSS(int bwLastYearSS) {
		this.bwLastYearSS = bwLastYearSS;
	}
	public int getBwLastYearPPJY() {
		return bwLastYearPPJY;
	}
	public void setBwLastYearPPJY(int bwLastYearPPJY) {
		this.bwLastYearPPJY = bwLastYearPPJY;
	}
	public int getBwLastYearYWFWW() {
		return bwLastYearYWFWW;
	}
	public void setBwLastYearYWFWW(int bwLastYearYWFWW) {
		this.bwLastYearYWFWW = bwLastYearYWFWW;
	}
	public int getCityLastYearAcceptNum() {
		return cityLastYearAcceptNum;
	}
	public void setCityLastYearAcceptNum(int cityLastYearAcceptNum) {
		this.cityLastYearAcceptNum = cityLastYearAcceptNum;
	}
	public int getCityLastMonthAcceptNum() {
		return cityLastMonthAcceptNum;
	}
	public void setCityLastMonthAcceptNum(int cityLastMonthAcceptNum) {
		this.cityLastMonthAcceptNum = cityLastMonthAcceptNum;
	}
	public int getAllDeptLastYearAcceptNum() {
		return allDeptLastYearAcceptNum;
	}
	public void setAllDeptLastYearAcceptNum(int allDeptLastYearAcceptNum) {
		this.allDeptLastYearAcceptNum = allDeptLastYearAcceptNum;
	}
	public int getAllDeptLastMonthAcceptNum() {
		return allDeptLastMonthAcceptNum;
	}
	public void setAllDeptLastMonthAcceptNum(int allDeptLastMonthAcceptNum) {
		this.allDeptLastMonthAcceptNum = allDeptLastMonthAcceptNum;
	}

	public int getBwJujiLastYearNum() {
		return bwJujiLastYearNum;
	}
	public void setBwJujiLastYearNum(int bwJujiLastYearNum) {
		this.bwJujiLastYearNum = bwJujiLastYearNum;
	}
	public int getBwJujiLastMonthNum() {
		return bwJujiLastMonthNum;
	}
	public void setBwJujiLastMonthNum(int bwJujiLastMonthNum) {
		this.bwJujiLastMonthNum = bwJujiLastMonthNum;
	}

	public int getDeptShouldDealNum() {
		return deptShouldDealNum;
	}
	public void setDeptShouldDealNum(int deptShouldDealNum) {
		this.deptShouldDealNum = deptShouldDealNum;
	}
	public int getDeptCurrDealNum() {
		return deptCurrDealNum;
	}
	public void setDeptCurrDealNum(int deptCurrDealNum) {
		this.deptCurrDealNum = deptCurrDealNum;
	}
	public int getDeptEndNum() {
		return deptEndNum;
	}
	public void setDeptEndNum(int deptEndNum) {
		this.deptEndNum = deptEndNum;
	}
	public int getDeptEndAvgTime() {
		return deptEndAvgTime;
	}
	public void setDeptEndAvgTime(int deptEndAvgTime) {
		this.deptEndAvgTime = deptEndAvgTime;
	}
	public int getDeptDealAvgTime() {
		return deptDealAvgTime;
	}
	public void setDeptDealAvgTime(int deptDealAvgTime) {
		this.deptDealAvgTime = deptDealAvgTime;
	}
	
	

	/**
	 * 存放信访检举控告问题大类的前五类问题
	 * @author hjh
	 *
	 * 2014-7-16下午09:20:12
	 */
	public class Top5Issue implements Serializable{
		private String issueTypeCode;
		private String issueTypeName;
		private int count;
		public String getIssueTypeCode() {
			return issueTypeCode;
		}
		public void setIssueTypeCode(String issueTypeCode) {
			this.issueTypeCode = issueTypeCode;
//			this.issueTypeName = CodeSwitchUtil.systemCodeSwitch(SysCodeType.WTXZ.toString(), this.issueTypeCode, true);
		}
		public String getIssueTypeName() {
			return issueTypeName;
		}
		public void setIssueTypeName(String issueTypeName) {
			this.issueTypeName = issueTypeName;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
	}

	public List<Top5Issue> getQsYearTop5Issue() {
		return qsYearTop5Issue;
	}
	public void setQsYearTop5Issue(List<Top5Issue> qsYearTop5Issue) {
		this.qsYearTop5Issue = qsYearTop5Issue;
	}
	public List<Top5Issue> getQsMonthTop5Issue() {
		return qsMonthTop5Issue;
	}
	public void setQsMonthTop5Issue(List<Top5Issue> qsMonthTop5Issue) {
		this.qsMonthTop5Issue = qsMonthTop5Issue;
	}
	public List<Top5Issue> getBwYearTop5Issue() {
		return bwYearTop5Issue;
	}
	public void setBwYearTop5Issue(List<Top5Issue> bwYearTop5Issue) {
		this.bwYearTop5Issue = bwYearTop5Issue;
	}
	public List<Top5Issue> getBwMonthTop5Issue() {
		return bwMonthTop5Issue;
	}
	public void setBwMonthTop5Issue(List<Top5Issue> bwMonthTop5Issue) {
		this.bwMonthTop5Issue = bwMonthTop5Issue;
	}
	
	public String gengRate(double original, double current){
		if(original==0.0)
			return "";
		else {
			String color = "green";
			double rate = (current-original)/original;
			if(current-original>0)
				color = "red";
			DecimalFormat df = new DecimalFormat("0.00%");
			df.setNegativePrefix("↓");
			df.setPositivePrefix("↑"); 
			String data = df.format(rate);
			String decorateStr = "<span style='color: "+color+";'>"+data+"</span>";
			return decorateStr;
		}
	}
	public static void main(String[] args){
		System.out.println("=========="+new LeaderInfo().gengRate(100,110));
		System.out.println("=========="+new LeaderInfo().gengRate(0,110));
	}
	public int getBwShouldDealNum() {
		return bwShouldDealNum;
	}
	public void setBwShouldDealNum(int bwShouldDealNum) {
		this.bwShouldDealNum = bwShouldDealNum;
	}
	public int getBwCurrDealNum() {
		return bwCurrDealNum;
	}
	public void setBwCurrDealNum(int bwCurrDealNum) {
		this.bwCurrDealNum = bwCurrDealNum;
	}
	public int getBwEndNum() {
		return bwEndNum;
	}
	public void setBwEndNum(int bwEndNum) {
		this.bwEndNum = bwEndNum;
	}
	public int getBwEndAvgTime() {
		return bwEndAvgTime;
	}
	public void setBwEndAvgTime(int bwEndAvgTime) {
		this.bwEndAvgTime = bwEndAvgTime;
	}
	public int getBwDealAvgTime() {
		return bwDealAvgTime;
	}
	public void setBwDealAvgTime(int bwDealAvgTime) {
		this.bwDealAvgTime = bwDealAvgTime;
	}
	public int getDeptEndTime() {
		return deptEndTime;
	}
	public void setDeptEndTime(int deptEndTime) {
		this.deptEndTime = deptEndTime;
	}
	public int getDeptDealTime() {
		return deptDealTime;
	}
	public void setDeptDealTime(int deptDealTime) {
		this.deptDealTime = deptDealTime;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getNotXFSDeptCode() {
		return notXFSDeptCode;
	}
	public void setNotXFSDeptCode(String notXFSDeptCode) {
		this.notXFSDeptCode = notXFSDeptCode;
	}
	public int getDeptNotKnot() {
		return deptNotKnot;
	}
	public void setDeptNotKnot(int deptNotKnot) {
		this.deptNotKnot = deptNotKnot;
	}
	public int getBwNotKnot() {
		return bwNotKnot;
	}
	public void setBwNotKnot(int bwNotKnot) {
		this.bwNotKnot = bwNotKnot;
	}
	public int getMonthTransAssignNum() {
		return monthTransAssignNum;
	}
	public void setMonthTransAssignNum(int monthTransAssignNum) {
		this.monthTransAssignNum = monthTransAssignNum;
	}
	public int getYearTransAssignNum() {
		return yearTransAssignNum;
	}
	public void setYearTransAssignNum(int yearTransAssignNum) {
		this.yearTransAssignNum = yearTransAssignNum;
	}
	public int getLastYearMonthTransAssignNum() {
		return lastYearMonthTransAssignNum;
	}
	public void setLastYearMonthTransAssignNum(int lastYearMonthTransAssignNum) {
		this.lastYearMonthTransAssignNum = lastYearMonthTransAssignNum;
	}
	public int getLastYearTransAssignNum() {
		return lastYearTransAssignNum;
	}
	public void setLastYearTransAssignNum(int lastYearTransAssignNum) {
		this.lastYearTransAssignNum = lastYearTransAssignNum;
	}

	
	
}
