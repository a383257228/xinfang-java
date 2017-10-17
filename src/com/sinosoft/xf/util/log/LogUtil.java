package com.sinosoft.xf.util.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sinosoft.log.domain.OperationDataLog;
import com.sinosoft.log.domain.OperationLog;
import com.sinosoft.xf.constants.UserConstants.PetitionTypeCode;
import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

public class LogUtil {
	/**
	 * 设置日志参数
	 * @return OperationLog
	 */
	/**
	 * map可以传null
	 * 如果有特殊说明需要提前放入map
	 *  如：map.put("operationDesc", map.get("operationBtnName")+" 信访编号为："+entity.getCurrPetitionNo());
	 */
	public static  OperationLog logSave(Map<String,Object> map,PetitionBasicInfo entity,String saveBeforeId) {
		try {
			if (map == null) {
				map = InMap2Map.getMapFormInMap(Struts2Utils.getRequest().getParameterMap());
			}
			OperationLog log = new OperationLog();
			if(saveBeforeId==null||saveBeforeId.equals("")){
				log.setOperationTypeCode("add");
				log.setOperationTypeName("新增");
				log.setCountNum(1);
			}else{
				log.setOperationTypeCode("modify");
				log.setOperationTypeName("修改");
			}
			if(map.get("operationFuncName")==null){
				map.put("operationFuncCode", "LetterAccept");
				map.put("operationFuncName", "来信受理");
				if(PetitionTypeCode.LetterAccept.toString().equals(entity.getPetitionTypeCode())){
					map.put("operationFuncCode", "LetterAccept");
					map.put("operationFuncName", "来信受理");
				}else if(PetitionTypeCode.VisitAccept.toString().equals(entity.getPetitionTypeCode())){
					map.put("operationFuncCode", "VisitAccept");
					map.put("operationFuncName", "来访受理");
				}else if(PetitionTypeCode.TelAccept.toString().equals(entity.getPetitionTypeCode())){
					map.put("operationFuncCode", "TelAccept");
					map.put("operationFuncName", "电话受理");
				}else if(PetitionTypeCode.NetAccept.toString().equals(entity.getPetitionTypeCode())){
					map.put("operationFuncCode", "NetAccept");
					map.put("operationFuncName", "网络受理");
				}
			}
			String dataValue = "信访编号："+entity.getCurrPetitionNo();
			if(entity.getPetitionPrtNo()!=null&&!"".equals(entity.getPetitionPrtNo())){
				dataValue +=";条形码："+entity.getPetitionPrtNo();
			}
			String operationDesc = map.get("operationFuncName")+","+map.get("operationBtnName")+dataValue;
			log.setOperationFuncCode(map.get("operationFuncCode").toString());
			log.setOperationFuncName(map.get("operationFuncName").toString());
			log.setOperationBtnName(map.get("operationBtnName").toString());
			log.setOperationDesc(operationDesc);
			log.setOperationLevelCode("NORMAL");
			log.setOperationLevelName("一般");
			log.setOperationResultCode("1");
			log.setOperationResultName("成功");
			return log;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 设置日志数据参数
	 * @param dataDesc
	 * @param dataValue
	 * @return
	 */
	public static List<OperationDataLog> setDataLogs(String dataDesc, String dataValue) {
		List<OperationDataLog> dataLogs = new ArrayList<OperationDataLog>();
		dataLogs.add(new OperationDataLog(dataDesc,dataValue));
		return dataLogs;
	}
}