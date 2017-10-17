package com.sinosoft.xf.workflow.web;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateInfo;
import com.sinosoft.xf.workflow.domain.PetitionCirculationStateTraceInfo;
import com.sinosoft.xf.workflow.manager.PetitionCirculationStateInfoManager;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.struts2.CrudExtActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;



/**
 * 信访流转状态信息Action
 * @date 2011-09-13
 * @author wangxx
 */
@Namespace("/workflow")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "petition-circulation-state-info.action", type = "redirect") })
public class PetitionCirculationStateInfoAction extends CrudExtActionSupport<PetitionCirculationStateInfo, String>  {

	private static final long serialVersionUID = 1L;
	/**
	 * 注入信访流转状态信息manager
	 */
	@Autowired
	PetitionCirculationStateInfoManager petitionCirculationStateInfoManager;
	/**
	 * 注入信访基本信息manager
	 */
	@Autowired
	PetitionBasicInfoManager basicInfoManager;
	/**
	 * 注入信访流转状态轨迹信息manager
	 */
	//实例化信访流转状态信息对象
	private transient PetitionCirculationStateInfo entity = new PetitionCirculationStateInfo();

	/**
	 * 重写父类方法
	 * @return 信访流转状态信息manager
	 */
	@Override
	protected EntityManager<PetitionCirculationStateInfo, String> getEntityManager() {
		return petitionCirculationStateInfoManager;
	}

	/**
	 * 重写父类方法
	 * @throws Exception
	 * @return 信访流转状态信息对象
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (id != null && !"".equals(id)) {
			entity = petitionCirculationStateInfoManager.get(id);
		} else {
			entity = new PetitionCirculationStateInfo();
		}
	}
	
	/**
	 * 获取信访流转状态信息对象
	 * @return 信访流转状态信息对象
	 */
	public PetitionCirculationStateInfo getModel() {
		return entity;
	}

	@SuppressWarnings("unused")
	private String petitionCirculationStateInfo;
	@SuppressWarnings("unused")
	private String petitionLatterPriorState;
		
	public void setPetitionCirculationStateInfo(String petitionCirculationStateInfo) {
		this.petitionCirculationStateInfo = petitionCirculationStateInfo;
	}

	public void setPetitionLatterPriorState(String petitionLatterPriorState) {
		this.petitionLatterPriorState = petitionLatterPriorState;
	}


	/**
	 * 信访件已办理过的轨迹编号
	 */
	private String priorState;
	
	/**
	 * 信访件当期状态的编号
	 */
	private String activityNo;
	
	/**
	 * 当前信访件的oid
	 */
	private String badicId;
	
	/**
	 * 查询信访件当前所处的状态
	 * @author xuy
	 * @throws IOException,Exception
	 * @createDate 2012-06-07 
	 */	
	public void getPetitionLatterState() throws IOException,Exception{
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpSession session = request.getSession();
		PetitionBasicInfo PetitionBasicInfo = basicInfoManager.get(badicId);
		if(PetitionBasicInfo!=null){
			PetitionCirculationStateInfo petitionCirculationStateInfo = PetitionBasicInfo.getCirculationStateInfo();
			Set<PetitionCirculationStateTraceInfo> petitionLatterPriorState= PetitionBasicInfo.getCirculationStateTraceInfoSet();
			activityNo = petitionCirculationStateInfo.getActivityNo();
			StringBuilder str=new StringBuilder();
			PetitionCirculationStateTraceInfo info=new PetitionCirculationStateTraceInfo();
			Iterator<PetitionCirculationStateTraceInfo> it=petitionLatterPriorState.iterator();       
			while(it.hasNext()){
				info=(PetitionCirculationStateTraceInfo)it.next();
				str.append(info.getActivityNo());
				str.append(",");
				str.append(info.getNextActivityNo());
				str.append(",");
			}
			priorState=str.toString().substring(0, str.length()-1);
			session.setAttribute("activityNo", activityNo);
			session.setAttribute("priorState", priorState);
		}
	}

	public String getBadicId() {
		return badicId;
	}

	public void setBadicId(String badicId) {
		this.badicId = badicId;
	}	
}
