package com.sinosoft.xf.petition.scan.manager;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.xf.petition.accept.dao.PetitionBasicInfoDao;
import com.sinosoft.xf.petition.accept.domain.PetitionBasicInfo;
import com.sinosoft.xf.petition.deal.domain.PetitionDealInfo;
import com.sinosoft.xf.petition.scan.dao.PetitionScanFileDao;
import com.sinosoft.xf.petition.scan.domain.PetitionScanFile;
import com.sinosoft.xf.util.Encrypt;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

/**
 * 扫描文件manager
 */
@Service
@Transactional
public class PetitionScanFileManager extends EntityManager<PetitionScanFile, String> {
	@Autowired
	PetitionScanFileDao petitionScanFileDao;
	@Autowired
	PetitionBasicInfoDao petitionBasicInfoDao;
	@Override
	protected HibernateDao<PetitionScanFile, String> getEntityDao() {
		return petitionScanFileDao;
	}
	/**
	 * 查询列表
	 */
	@Transactional(readOnly=true)
	public ExtjsPage<PetitionScanFile> listScanFile(Map<String, Object> map,String filterTxt, String filterValue, String sort, String dir, int start, int limit) throws Exception {
		ExtjsPage<PetitionScanFile> page = new ExtjsPage<PetitionScanFile>();
		String currPetitionNo=null;
		if (filterTxt != null && !"".equals(filterTxt.trim())) {
			String[] filterTxts = filterTxt.split(";");
			String[] filterValues = filterValue.split(";");
			for(int i = 0; i < filterValues.length; i++) {
				String stringTxt = filterTxts[i];
				String stringValue = filterValues[i];
				if (stringValue != null && !"".equals(stringValue)) {
					if("currPetitionNo".equals(stringTxt)){
						currPetitionNo=stringValue;
					}
				}
			}
		}
		//2016-03-23liub如果当前信件是上级转办，那么查看上级的信件是否是集体研判下来的，如果是则不能查询多媒体和扫描件
		boolean dontSelect=false;
		if(currPetitionNo!=null){
			List<PetitionBasicInfo> basicList=petitionBasicInfoDao.findBy("currPetitionNo", currPetitionNo);
			for (PetitionBasicInfo petitionBasicInfo : basicList) {
				if(petitionBasicInfo.getRegionCode().equals(Struts2Utils.getSessionAttribute("curRegionCode"))
						&&petitionBasicInfo.getPetitionSourceCode().equals("0201")){
					for (PetitionBasicInfo petitionBasicInfo2 : basicList) {
						if(petitionBasicInfo2.getIsSecretaryFlag().equals("3")){
							Set<PetitionDealInfo>dealSet=petitionBasicInfo2.getDealInfoSet();
							for (PetitionDealInfo petitionDealInfo : dealSet) {
								if(petitionDealInfo.getValidFlag().equals("0")
										&&petitionDealInfo.getTransDealInfo().getToRegionCode().equals(Struts2Utils.getSessionAttribute("curRegionCode"))){
									dontSelect=true;
								}
							}
						}
					}
				}
			}
		}
		if(!dontSelect){
			int size = petitionScanFileDao.listScanFileCount(map,filterTxt,filterValue);
			List<PetitionScanFile> list = petitionScanFileDao.listScanFile(map,filterTxt,  filterValue,  sort,dir,  start, limit);
			Iterator<PetitionScanFile> it = list.iterator();
			int deleteSize = 0;
			while(it.hasNext()){
				PetitionScanFile scan = it.next();
				String id = Encrypt.encrypt(scan.getId());
				scan.setId(id);
				if((scan.getPetitionNo()==null||scan.getPetitionNo().equals(""))
						&&(scan.getPetitionPrtNo()==null||scan.getPetitionPrtNo().equals(""))
						&&(scan.getCurrPetitionNo()==null||scan.getCurrPetitionNo().equals(""))){
					it.remove();
					deleteSize++;
				}
			}
			page.setTotalCount(size-deleteSize);
			page.setResult(list);
		}
		return page;
	}
	
}