package com.sinosoft.xf.petition.accept.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.petition.accept.dao.PetitionDealInfoDao;
import com.sinosoft.xf.petition.deal.dao.PetitionAssignInfoDao;
import com.sinosoft.xf.petition.deal.dao.PetitionTransDealInfoDao;
import com.sinosoft.xf.petition.deal.domain.PetitionAssignInfo;
import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoft.xf.petition.deal.domain.PetitionTransDealInfo;
import com.sinosoft.xf.petition.domainutil.SurveyDealAndEndInfo;
import com.sinosoft.xf.util.StringUtils;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;

/**
 * 基本信息 manager
 * @date 2017-06-22
 * @author guoh
 */
@Service
@Transactional
public class PetitionDealInfoManager extends EntityManager<PetitionDealInfo, String> {
	@Autowired
	PetitionDealInfoDao dealInfoDao;

	@Autowired
	private PetitionTransDealInfoDao petitionTransDealInfoDao;
	@Autowired
	private PetitionAssignInfoDao petitionAssignInfoDao;
	@Override
	protected HibernateDao<PetitionDealInfo, String> getEntityDao() {
		return dealInfoDao;
	}
	/**
	 * 显示信访件办理信息
	 * @author guoh
	 * @date 2017-06-22
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SurveyDealAndEndInfo loadSurveyDealAndEndInfo(String id) throws Exception {
		SurveyDealAndEndInfo survey = new SurveyDealAndEndInfo();
		List<Map<String, Object>> list = dealInfoDao.loadSurveyDealAndEndInfo(id);
		if(null!=list){
			if(list.size()>0){
				if(null!=list.get(0).get("dealTypeName")){
					survey.setDealTypeName(list.get(0).get("dealTypeName").toString());
				}
				if(null!=list.get(0).get("dealSuggestion")){
					survey.setDealSuggestion(list.get(0).get("dealSuggestion").toString());
				}
				if(null!=list.get(0).get("surveyDept")){
					survey.setSurveyDept(list.get(0).get("surveyDept").toString());
				}
				if(null!=list.get(0).get("surveyComposing")){
					survey.setSurveyComposing(list.get(0).get("surveyComposing").toString());
				}
				if(null!=list.get(0).get("surveyContent")){
					survey.setSurveyContent(list.get(0).get("surveyContent").toString());
				}
				if(null!=list.get(0).get("realityName")){
					survey.setRealityName(list.get(0).get("realityName").toString());
				}
				if(null!=list.get(0).get("endTypeName")){
					survey.setEndTypeName(list.get(0).get("endTypeName").toString());
				}
			}
		}
		return survey;
	}
	/**
	 * 通过基本表oid查询有效的办理方案信息
	 * @author ZHZ
	 * @date 2017年9月1日
	 * @param basicOid 基本表oid
	 * @return 有效的办理信息对象
	 */
	@Transactional(readOnly = true)
	public PetitionDealInfo getValidDealInfoByBasicOid(String basicOid) throws Exception {
		PetitionDealInfo dealInfo = dealInfoDao.getValidDealInfoByBasicOid(basicOid);
		if(dealInfo==null){
			return null;
		}
		//by hjh. 如果是转交办，则到相应表获取承办单位信息
		String dealTypeCode = dealInfo.getDealTypeCode();
		if(!StringUtils.isBlank(dealTypeCode)){
			String otherDealUnit = "";
			//转办从转办表取值，交办从交办表取值。
			if("0302".equals(dealTypeCode)){//转办
				List<PetitionTransDealInfo> petitionTransDealInfos = petitionTransDealInfoDao.findBy("petitionDealInfo", dealInfo);
				if(petitionTransDealInfos.size()>0){
					PetitionTransDealInfo petitionTransDealInfo = petitionTransDealInfos.get(0);
					otherDealUnit =  petitionTransDealInfo.getRemarksInfo();
				}
			}else if("0201".equals(dealTypeCode)||"0202".equals(dealTypeCode)){//交办要结果、交办要情况
				List<PetitionAssignInfo> petitionAssignInfos = petitionAssignInfoDao.findBy("petitionDealInfo", dealInfo);
				if(petitionAssignInfos.size()>0){
					PetitionAssignInfo petitionTransDealInfo = petitionAssignInfos.get(0);
					otherDealUnit =  petitionTransDealInfo.getRemarksInfo();
				}
			}
			if(!StringUtils.isBlank(dealTypeCode))
				dealInfo.setOtherDealUnit(otherDealUnit);
		}
		return dealInfo;
	}
}