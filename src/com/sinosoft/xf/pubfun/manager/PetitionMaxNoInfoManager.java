package com.sinosoft.xf.pubfun.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.pubfun.dao.PetitionMaxNoInfoDao;
import com.sinosoft.xf.pubfun.domain.PetitionMaxNoInfo;
import com.sinosoft.xf.util.StringUtils;
import com.sinosoft.xf.util.TimeUtils;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
/**
 *最大号信息manager
 * @date 2011-09-15
 * @author wangxx 
 */
@Service
@Transactional
public class PetitionMaxNoInfoManager extends EntityManager<PetitionMaxNoInfo,String> {
	/**
	 * 注入最大号信息dao
	 */
	@Autowired
	PetitionMaxNoInfoDao maxNoInfoDao;
	/**
	 * 获取当前最大显示信访编号
	 * @date 2013-06-14
	 * @author Dustin
	 * 调用了哪些方法：
	 * 1、maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode 通过号码类型和号码限制条件查询对象
	 * 2、maxNoInfoDao.updateMaxNoInfo 根据id修改最大号，使最大号加1
	 * 3、StringUtils.toLengthString 将字符串补数,将sourString的前面用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * 被哪些方法调用：
	 * 1、PetitionWorkflowEngineInfoManager.saveAll 状态、状态轨迹保存的数据库操作
	 * @param regionCode 区域编码
	 * @return 当前信访编号
	 */
	public synchronized String getCurrPetitionNo(String regionCode) throws Exception{
		int length = 26;
		//返回的当前最大号
		StringBuffer noReturn = new StringBuffer(regionCode+TimeUtils.getYyMmDd());
		String noType = "CURRXFNO"+TimeUtils.getYear();
		//最大号码
		int maxNo = 1;
		PetitionMaxNoInfo maxCurrPetitionNoInfo = maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(
					noType,regionCode);
		if(null==maxCurrPetitionNoInfo){
			//不存在，创建一个该类型的号码，将maxNo赋值为1
			maxCurrPetitionNoInfo = new PetitionMaxNoInfo();
			maxCurrPetitionNoInfo.setMaxNo(maxNo);
			maxCurrPetitionNoInfo.setNoType(noType);
			maxCurrPetitionNoInfo.setRegionCode(regionCode);
			maxNoInfoDao.save(maxCurrPetitionNoInfo);
		}else{
			//存在原号码+1，并且将号码赋给maxNo。
			maxNo = maxCurrPetitionNoInfo.getMaxNo()+1;
				maxCurrPetitionNoInfo.setMaxNo(maxNo);
			maxNoInfoDao.updateMaxNoInfo(maxNo,maxCurrPetitionNoInfo.getId());
		}
		//定义号码头信息 共18位
		int petitionNoLength = length-noReturn.length();
		//获取String类型的最大号码
		String strMaxNo = StringUtils.toLengthString(String.valueOf(maxNo), "0", petitionNoLength);
		noReturn.append(strMaxNo);
		return noReturn.toString();
	}/**
	 * 获取当前最大显示问题线索编号
	 * @date 2014-05-21
	 * @author liub
	 * 调用了哪些方法：
	 * 1、maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode 通过号码类型和号码限制条件查询对象
	 * 2、maxNoInfoDao.updateMaxNoInfo 根据id修改最大号，使最大号加1
	 * 3、StringUtils.toLengthString 将字符串补数,将sourString的前面用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * 被哪些方法调用：
	 * 1、PetitionWorkflowEngineInfoManager.saveAll 状态、状态轨迹保存的数据库操作
	 * @param regionCode 区域编码
	 * @return 当前信访编号
	 */
	public synchronized String getQueryKeyNo(String regionCode){
		int length = 7;
		//返回的当前最大号
		StringBuffer noReturn = new StringBuffer(TimeUtils.getYear());
		String noType = "QUERYKEYNO";
		//最大号码 
		int maxNo = 0;
		PetitionMaxNoInfo maxQueryKeyNoInfo = maxNoInfoDao.getMaxNoInfoByNoTypeAndNoLimitAndRegionCode(
					noType,regionCode);
		if(null==maxQueryKeyNoInfo){
			//不存在，创建一个该类型的号码，将maxNo赋值为1
			maxQueryKeyNoInfo = new PetitionMaxNoInfo();
			maxNo = 1;
			maxQueryKeyNoInfo.setId(null);
			maxQueryKeyNoInfo.setMaxNo(maxNo);
			maxQueryKeyNoInfo.setNoType(noType);
			maxQueryKeyNoInfo.setRegionCode(regionCode);
			maxNoInfoDao.save(maxQueryKeyNoInfo);
		}else{
			//存在原号码+1，并且将号码赋给maxNo。
			maxNo = maxQueryKeyNoInfo.getMaxNo()+1;
				maxQueryKeyNoInfo.setMaxNo(maxNo);
//				maxNoInfoDao.save(maxQueryKeyNoInfo);
			maxNoInfoDao.updateMaxNoInfo(maxNo,maxQueryKeyNoInfo.getId());
		}
		//定义号码头信息 共18位
		int petitionNoLength = length-noReturn.length();
		//获取String类型的最大号码
		String strMaxNo = StringUtils.toLengthString(String.valueOf(maxNo), "0", petitionNoLength);
		noReturn.append(strMaxNo);
		return noReturn.toString();
	}
	/**
	 * 重写父类方法
	 * @return 最大号信息dao
	 */
	@Override
	protected HibernateDao<PetitionMaxNoInfo,String> getEntityDao() {
		return maxNoInfoDao;
	}
}
