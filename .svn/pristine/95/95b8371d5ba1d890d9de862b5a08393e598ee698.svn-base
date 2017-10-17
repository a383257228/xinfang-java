package com.sinosoft.xf.dataPredict.manager;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.dataPredict.dao.DataPredictFpmInfoDao;
import com.sinosoft.xf.dataPredict.domain.DataPredictFpmInfo;
import com.sinosoft.xf.dataPredict.domainutil.AlarmVO;
import com.sinosoft.xf.dataPredict.domainutil.PetitionInfo;
import com.sinosoft.xf.util.encrypt.Encrypt;
import com.sinosoft.xf.util.inmap2map.InMap2Map;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 关联分析  manager
 * @date 2017-08-12
 * @author liyang
 */
@Service
@Transactional
public class DataPredictFpmInfoManager extends EntityManager<DataPredictFpmInfo, String> {
	@Autowired
	DataPredictFpmInfoDao predictFpmInfoDao;

	@Override
	protected HibernateDao<DataPredictFpmInfo, String> getEntityDao() {
		return predictFpmInfoDao;
	}
	
	/**
	 * 查询区自办，转办数量
	 * @author lzd
	 * @return ExtjsPage<Map<String, Object>>
	 * @throws Exception 
	 */
	public ExtjsPage<PetitionInfo> dataPredictFPMQW(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception{
		int size = predictFpmInfoDao.getquInfoSize(map,person);
		List<Map<String, Object>> list = predictFpmInfoDao.dataPredictFPMQW(map,start, limit,person);
		return getPetitionInfoOther(size,list);
	}
	/**
	 * 查询派驻自办，转办数量
	 * @author lzd
	 * @return ExtjsPage<Map<String, Object>>
	 * @throws Exception 
	 */
	public ExtjsPage<PetitionInfo> dataPredictFPMS(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception{
		int size = predictFpmInfoDao.getpaizhuInfoSize(map,person);
		List<Map<String, Object>> list = predictFpmInfoDao.dataPredictFPMS(map,start, limit,person);
		return getPetitionInfoOther(size,list);
	}
	
	
	
	/***
	 * 信件了结返回该信件信息
	 * @author lzd
	 * @throws Exception
	 */
	public void dataPredictInsertAlarm(AlarmVO vo){
		
		String dealTypeName = vo.getDealTypeName();//获取处理方式
		//ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		HttpServletRequest request = Struts2Utils.getRequest();
		Map<String, Object> map = InMap2Map.getMapFormInMap(request.getParameterMap());
		map.put("vo", vo);
		//通过问题类别code查找支持度最高的问题（后续条件得加上区）
		List<Map<String, Object>> list = predictFpmInfoDao.dataPredictAlarm(map);
		//page.setResult(list);
		String fpResult = "";
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> mapResult = list.get(i);
			fpResult = (String) mapResult.get("DEAL_TYPE_NAME");//取fp算法处理方式结果
		}
		//比较处理方式是否正确
		if(dealTypeName.equals(fpResult)){
			//1代表处理方式相同。0代表处理方式不同，然后存入数据库
			vo.setDealWay(1);
			map.put("vo", vo);
			//处理完成的信件插入到数据库
			predictFpmInfoDao.dataPredictInsertAlarm(map);
		} else { //信访处理方式相同则页面不显示这条信访
			vo.setDealWay(0);
			map.put("vo", vo);
			//处理完成的信件插入到数据库
			predictFpmInfoDao.dataPredictInsertAlarm(map);
		}
	}
	
	
	public ExtjsPage<PetitionInfo> getPetitionInfoOther(int size,List<Map<String, Object>> lists)throws Exception{
		ExtjsPage<PetitionInfo> page = new ExtjsPage<PetitionInfo>();
		PetitionInfo petitionInfo = new PetitionInfo();
		petitionInfo.setResult(lists);
		List<PetitionInfo> lis = new ArrayList<PetitionInfo>();
		lis.add(petitionInfo);
 		page.setResult(lis);
		page.setTotalCount(size);
		return page;
	}
	
	/**
	 * 信访件列表查询公共调用方法
	 * @date 2017-06-16
	 * @author guoh
	 * @param size
	 * @param list
	 * @return
	 */
	public ExtjsPage<PetitionInfo> getPetitionInfo(int size,List<Map<String, Object>> lists)throws Exception{
		ExtjsPage<PetitionInfo> page = new ExtjsPage<PetitionInfo>();
		PetitionInfo petitionInfo = new PetitionInfo();
		for (int i = 0; i < lists.size(); i++) {
			String accuseds = lists.get(i).get("ACCUSEDS").toString(); //获取被反映人
			accuseds = Encrypt.decrypt(accuseds);//被反映人解密
			//被反映人重新赋值
			if(lists.get(i).containsKey("ACCUSEDS")){
				lists.get(i).put("ACCUSEDS", accuseds);
			}
		}
		petitionInfo.setResult(lists);
		List<PetitionInfo> lis = new ArrayList<PetitionInfo>();
		lis.add(petitionInfo);
 		page.setResult(lis);
		page.setTotalCount(size);
		return page;
	}
	
	/***
	 * 查询（告警页面数据）上海市
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public ExtjsPage<PetitionInfo> dataPredictFindAlarm(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception{
		int size = predictFpmInfoDao.getCityInfoSize(map,person);
		List<Map<String, Object>> list = predictFpmInfoDao.dataPredictFindAlarm(map,start,limit,person);
		return getPetitionInfo(size,list);
	}
	
	
	
	/***
	 * 查询（告警页面数据）区，委办
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public ExtjsPage<PetitionInfo> dataPredictFindAlarmOther(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception{
		int size = predictFpmInfoDao.getBasicInfoSize(map,person);
		List<Map<String, Object>> list = predictFpmInfoDao.dataPredictFindAlarmOther(map,start,limit,person);
		return getPetitionInfo(size,list);
	}
	/***
	 * 查找问题类别与行政级别关联关系数据
	 * @param map
	 * @return
	 */
	public ExtjsPage<Map<String, Object>> findDataQL(Map<String, Object> map){
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = predictFpmInfoDao.findDataQL(map);
		page.setResult(list);
		return page;
	}
	
	/**
	 * 动态获取regName
	 * @param map
	 * @return
	 */
	public ExtjsPage<Map<String, Object>> getNameQuAndPaizhu(Map<String, Object> map){
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = predictFpmInfoDao.getNameQuAndPaizhu(map);
		page.setResult(list);
		return page;
	}
	
	
	/***
	 * 查看行政级别个数
	 * @param map
	 * @return
	 */
	public ExtjsPage<Map<String, Object>> findOBCount(Map<String, Object> map){
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = predictFpmInfoDao.findOBCount(map);
		page.setResult(list);
		return page;
	}
	/***
	 * 查看TOP5
	 * @param map
	 * @return
	 */
	public ExtjsPage<Map<String, Object>> findDataTOP(Map<String, Object> map){
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = predictFpmInfoDao.findDataTOP(map);
		page.setResult(list);
		return page;
	}
	/***
	 * 根据问题1，问题2 去补全的数据表中查找对应的support
	 * @param map
	 * @return
	 */
	public ExtjsPage<Map<String, Object>> completion(Map<String, Object> map){
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = predictFpmInfoDao.completion(map);
		page.setResult(list);
		return page;
	}
	
	
	/***
	 * 根据oid查找信访件的详细信息
	 * @param map
	 * @return
	 */
	public ExtjsPage<Map<String, Object>> fpmFindInfo(Map<String, Object> map){
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = predictFpmInfoDao.fpmFindInfo(map);
		page.setResult(list);
		return page;
	}
	/**
	 * 更新未操作字段
	 * @author lzd 
	 * @date 2017-08-30
	 * @throws Exception
	 */
	public void updateUnconfirmed(String oids) throws Exception{
		predictFpmInfoDao.updateUnconfirmed(oids);
	}
}