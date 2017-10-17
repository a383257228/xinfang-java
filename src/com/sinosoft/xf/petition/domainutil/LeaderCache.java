package com.sinosoft.xf.petition.domainutil;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用于缓存领导和秘书的文字平台数据
 * @author hjh
 * 2015年9月23日下午5:09:29
 */
public class LeaderCache {

	/**
	 * 全市、本委受理数据存储对象
	 */
	private LeaderInfo li;
	
	/**
	 * 本委办理数据存储对象
	 */
	private List<LeaderInfo> lilist;
	
	/**
	 * 数据的更新时间
	 */
	private Timestamp dataTime;

	public LeaderInfo getLi() {
		return li;
	}

	public void setLi(LeaderInfo li) {
		this.li = li;
	}

	public List<LeaderInfo> getLilist() {
		return lilist;
	}

	public void setLilist(List<LeaderInfo> lilist) {
		this.lilist = lilist;
	}

	public Timestamp getDataTime() {
		return dataTime;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}
	
	
}
