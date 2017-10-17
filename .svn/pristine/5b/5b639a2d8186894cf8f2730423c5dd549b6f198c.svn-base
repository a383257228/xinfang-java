package com.sinosoft.xf.workflow.web;


import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.constants.Constants;
import com.sinosoft.xf.constants.UserConstants.ActivityNo;
import com.sinosoft.xf.petition.petitionaccept.dao.RepeatJudgeDao;
import com.sinosoft.xf.petition.petitionaccept.domain.PetitionRepeatInfo;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateTraceInfo;
import com.sinosoft.xf.workflow.manager.PetitionCirculationStateTraceInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.json.JsonUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;


/**
 * 信访状态轨迹信息Action
 * 
 * @date 2011-09-13
 * @author wangxx
 */
@Namespace("/workflow")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "petition-circulation-state-trace-info.action", type = "redirect") })
public class PetitionCirculationStateTraceInfoAction extends CrudExtActionSupport<PetitionCirculationStateTraceInfo, String> {

	private static final long serialVersionUID = 1L;
	//信访编码
	private String petitionNo;
	private String petitionBasicInfoOid;
	/**
	 * 注入信访状态轨迹信息manager
	 */
	@Autowired
	PetitionCirculationStateTraceInfoManager stateTraceInfoManager;
	//实例化信访状态轨迹信息对象
	PetitionCirculationStateTraceInfo entity = new PetitionCirculationStateTraceInfo();
	/**
	 * 重写父类方法
	 * @return 信访状态轨迹信息manager
	 */
	@Override
	protected EntityManager<PetitionCirculationStateTraceInfo, String> getEntityManager() {
		return stateTraceInfoManager;
	}

	/**
	 * 重写父类方法
	 * @throws Exception
	 * @return 信访状态轨迹信息对象
	 */
	@Override
	protected void prepareModel() throws Exception {
	}
	
	/**
	 * 获取信访状态轨迹信息对象
	 * @return 信访状态轨迹信息对象
	 */
	public PetitionCirculationStateTraceInfo getModel() {
		return entity;
	}
	
	@Autowired
	RepeatJudgeDao repeatJudgeDao;
	/**
	 * 查询轨迹信息
	 * @throws Exception
	 */
	public void pagedQuery() throws Exception {
		int size = stateTraceInfoManager.listByPetitionNoSize(filterTxt,filterValue);
		List<PetitionCirculationStateTraceInfo> list = stateTraceInfoManager.listByPetitionNo(filterTxt,filterValue,start,limit);
		if (!list.isEmpty()) {
			PetitionCirculationStateTraceInfo stateTraceInfo = list.get(0);
			//前信并处，查询前信轨迹
			if (ActivityNo.QXBC.toString().equals(stateTraceInfo.getNextActivityNo())) {
				String regionCode = (String) Struts2Utils.getSession().getAttribute("curRegionCode");
				PetitionRepeatInfo repeatInfo = repeatJudgeDao.findUnique(
						Restrictions.eq("petitionNo", stateTraceInfo.getPetitionNo()), Restrictions.eq("regionCode", regionCode),
						Restrictions.eq("sameDealFlag", "1"));
				if (repeatInfo != null) {
					List<PetitionCirculationStateTraceInfo> listMain = stateTraceInfoManager.listByPetitionNo(filterTxt,repeatInfo.getDirectPetitionNo(),start,limit);
					for(PetitionCirculationStateTraceInfo tempState : listMain) {
						tempState.setOperate("【前信轨迹】"+tempState.getOperate());
						list.add(tempState);
					}
				}
			}
		}
		ExtjsPage<PetitionCirculationStateTraceInfo> page = new ExtjsPage<PetitionCirculationStateTraceInfo>();
		page.setResult(list);
		page.setTotalCount(size);
		JsonUtils.write(page, Struts2Utils.getResponse().getWriter(), getExcludes(), Constants.DateLong);
	}

	/**
	 * 过滤返回值
	 */
	public String[] getExcludes(){
		return new String[] { "hibernateLazyInitializer",
				"handler", "petitionBasicInfo" ,"transFlag",
				"conditionCode","conditionType","petitionStateNo"};
	}

	public String getPetitionNo() {
		return petitionNo;
	}

	public void setPetitionNo(String petitionNo) {
		this.petitionNo = petitionNo;
	}

	public String getPetitionBasicInfoOid() {
		return petitionBasicInfoOid;
	}

	public void setPetitionBasicInfoOid(String petitionBasicInfoOid) {
		this.petitionBasicInfoOid = petitionBasicInfoOid;
	}
}
