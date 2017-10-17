package com.sinosoft.xf.dataPredict.manager;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.dataPredict.constants.DataPredictOpinionConstants;
import com.sinosoft.xf.dataPredict.dao.DataPredictOpinionInfoDao;
import com.sinosoft.xf.dataPredict.domain.DataPredictOpinionInfo;
import com.sinosoft.xf.dataPredict.domainutil.QuestionTypeBean;
import com.sinosoft.xf.dataPredict.domainutil.TreeNodeBean;
import com.sinosoft.xf.petition.accept.dao.PetitionBasicInfoDao;
import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;
import com.sinosoft.xf.petition.domainutil.PetitionInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.manager.EntityManager;
import com.sinosoftframework.core.utils.page.ExtjsPage;

/**
 * 民意分析   manager
 * @date 2017-08-12
 * @author liyang
 */
@Service
@Transactional
public class DataPredictOpinionInfoManager extends EntityManager<DataPredictOpinionInfo, String> {
	@Autowired
	DataPredictOpinionInfoDao opinionInfoDao;
	@Autowired
	PetitionBasicInfoDao issueInfoDao;
	@Autowired
	PetitionBasicInfoManager basicInfoManager;
	@Override
	protected HibernateDao<DataPredictOpinionInfo, String> getEntityDao() {
		return opinionInfoDao;
	}
	/**
	 * 获取新词列表
	 * @author liyang
	 * @param limit 
	 * @param start 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public ExtjsPage<Map<String, Object>> getNewWord(int start, int limit) throws Exception{
		ExtjsPage<Map<String, Object>> page = new ExtjsPage<Map<String, Object>>();
		List<Map<String, Object>> list = opinionInfoDao.getNewWord(start,limit);
		page.setResult(list);
		return page;
	}
	
	/**
	 * 更新忽略信息 、加入词典
	 * @author liyang 
	 * @param person 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void updateIgnore(String oids,String otype, PersonInfo person) throws Exception {
		opinionInfoDao.updateIgnore(oids,otype,person);
	}
	
	/**
	 * 保存新词
	 * @author liyang 
	 * @param person 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void saveNewWord(String oids, String newVal, PersonInfo person) throws Exception {
		opinionInfoDao.saveNewWord(oids,newVal,person);
		
	}
	/**
	 * 获取词典路径及分类
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAllDict() throws Exception {
	    return opinionInfoDao.getAllDict();	
	}
	
	/**
	 * 添加分类
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void addClass(Map<String, Object> map,PersonInfo person) throws Exception {
		String pid = (String)map.get("parent_sec_Id");
//		String className = (String)map.get("className");
//		String oid = (String)map.get("oid");
		//标识父节点是那个（检举控告和违纪违法特殊）
//		int type = 0;
//		if("00fd0131fb3f2c700016".equals(pid) || "00fd0131fb3f2c700017".endsWith(pid) || "00fd0131fb3f2c700018".endsWith(pid)) {
//			type = 1;
//		}
		//获取要新增的分类编码
		
		List<Map<String, Object>> listCode = opinionInfoDao.getClassCode(pid);
		//SELECT  RIGHT(CAST(A.CODE as int),2) CODES,code  FROM DATAPREDICT_OPINION_DICT A where A.parent_id = '00fd0131fb3f2c700017' or  A.parent_id = '00fd0131fb3f2c700018' order by CODES desc

		//获取词典其他信息
		List<Map<String, Object>> list = opinionInfoDao.getDictBase();
		if(null != list && list.size() > 0) {
			//停用词路径
			map.put("STOPWORDDICTURL",list.get(0).get("STOPWORDDICTURL"));
			//暂存词路径
			map.put("TEMPORARYWORDDICTURL",list.get(0).get("TEMPORARYWORDDICTURL"));
			//词典路径
			int len = ((String)list.get(0).get("CLASSWORDDICTURL")).length() - ((String)list.get(0).get("CODE")).length() - 4;
			String nn = "";
			int newNum = Integer.parseInt((String)listCode.get(0).get("CODES")) + 1;
			if(newNum <= 9) {
				nn = "0" + String.valueOf(newNum);
			} else {
				nn = String.valueOf(newNum);
			}
			
			String newName = ((String)listCode.get(0).get("CODE")).substring(0, ((String)listCode.get(0).get("CODE")).length() - ((String)listCode.get(0).get("CODES")).length()) + nn;
			String dictUrl = ((String)list.get(0).get("CLASSWORDDICTURL")).substring(0, len) + newName + ".txt";
		    // System.out.println(dictUrl);
		    map.put("CLASSWORDDICTURL",dictUrl);
		    map.put("CODE",newName);
		    opinionInfoDao.addClass(map, person);
		
		}

	}
	
	/**
	 * 新词分类
	 * @author liyang 
	 * @param person 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void updateNewWordClass(Map<String, Object> map, PersonInfo person) throws Exception {
		opinionInfoDao.updateNewWordClass(map,person);
		
	}
	/**
	 * 新词合并
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public void mergeNewWord(Map<String, Object> map,PersonInfo person) {
		
		opinionInfoDao.mergeNewWord(map, person);
		opinionInfoDao.disableNewWord(map, person);
	}
	public List<QuestionTypeBean> getQuesTypeBean() throws Exception {
		List<QuestionTypeBean> last = new ArrayList<QuestionTypeBean>();
		QuestionTypeBean qtb = new QuestionTypeBean();
		qtb.setName("检举控告分类");
		List<QuestionTypeBean> list = new ArrayList<QuestionTypeBean>();
		//获取第一层节点
		List<Map<String,Object>> listOne = opinionInfoDao.getQuesTypeBean("00fd0131fb3f2c700016");
		for(int i = 0; i < listOne.size(); i++) {
			QuestionTypeBean qtbOne =  new QuestionTypeBean();
			qtbOne.setPid("00fd0131fb3f2c700016");
			qtbOne.setName((String)listOne.get(i).get("NAME"));
			qtbOne.setDictType((String)listOne.get(i).get("ID"));
			
			if((Integer)listOne.get(i).get("HASCHILDFLAG") == 1) {
				//获取第二层节点
				List<QuestionTypeBean> listTwos = new ArrayList<QuestionTypeBean>();
				List<Map<String,Object>> listTwo = opinionInfoDao.getQuesTypeBean((String)listOne.get(i).get("OID"));
				for(int j = 0; j < listTwo.size(); j++) {
					QuestionTypeBean qtbTwo =  new QuestionTypeBean();
					qtbTwo.setPid((String)listTwo.get(j).get("PID"));
					qtbTwo.setName((String)listTwo.get(j).get("NAME"));
					qtbTwo.setDictType((String)listTwo.get(j).get("ID"));
					
					
					if((Integer)listTwo.get(j).get("HASCHILDFLAG") == 1) {
						qtbTwo.setCollapsed(QuestionTypeBean.COLLAPSED);
						//获取第三层节点
						List<QuestionTypeBean> listThrees = new ArrayList<QuestionTypeBean>();
						List<Map<String,Object>> listThree = opinionInfoDao.getQuesTypeBean((String)listTwo.get(j).get("OID"));
						for(int k = 0; k < listThree.size(); k++) {
							QuestionTypeBean qtbThree =  new QuestionTypeBean();
							qtbThree.setPid((String)listThree.get(k).get("PID"));
							qtbThree.setName((String)listThree.get(k).get("NAME"));
							qtbThree.setDictType((String)listThree.get(k).get("ID"));
							qtbThree = getWordDis(qtbThree);
							if(qtbTwo.getColorFlag() <= qtbThree.getColorFlag()) {
								qtbTwo.setColor(qtbThree.getColor());
								qtbTwo.setColorFlag(qtbThree.getColorFlag());
							}
							listThrees.add(qtbThree);
					    }
						qtbTwo.setChildren(listThrees);
				    } else {
				    	qtbTwo = getWordDis(qtbTwo);
				    }
					
					if(qtbOne.getColorFlag() <= qtbTwo.getColorFlag()) {
						qtbOne.setColor(qtbTwo.getColor());
						qtbOne.setColorFlag(qtbTwo.getColorFlag());
					}
					listTwos.add(qtbTwo);
			   }
				qtbOne.setChildren(listTwos);
		} else {
			qtbOne = getWordDis(qtbOne);
		}
			
		
		if(qtb.getColorFlag() <= qtbOne.getColorFlag()) {
			qtb.setColor(qtbOne.getColor());
			qtb.setColorFlag(qtbOne.getColorFlag());
		}
		list.add(qtbOne);
		qtb.setChildren(list);
	}
		
		last.add(qtb);
	return last;
		
 }
	
	/**
	 * 获取某一分类下的词频
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public QuestionTypeBean getWordDis(QuestionTypeBean queTypeId) throws Exception {
		String color = DataPredictOpinionConstants.COLOR_ZE;
		int colorFlag = DataPredictOpinionConstants.COLOR_ZE_KEY;
		List<Map<String,Object>> listOne = opinionInfoDao.getWordDis(queTypeId.getDictType());
		if(null != listOne && listOne.size() > 0) {
			if(listOne.size() < DataPredictOpinionConstants.WORDCOUNT_CLASS_ONE ) {
				for(int i = 0; i < listOne.size(); i++) {
					if((Integer)listOne.get(i).get("WORDFREQUENCY") == 0) {
						if(colorFlag < DataPredictOpinionConstants.COLOR_ONE_KEY) {
							color = DataPredictOpinionConstants.COLOR_ONE;
							colorFlag = DataPredictOpinionConstants.COLOR_ONE_KEY;
						}
						
					} else if( 0 < (Integer)listOne.get(i).get("WORDFREQUENCY") && (Integer)listOne.get(i).get("WORDFREQUENCY") < DataPredictOpinionConstants.WORDDIS_CLASS_ONE) {
						if(colorFlag < DataPredictOpinionConstants.COLOR_ONE_KEY) {
							color = DataPredictOpinionConstants.COLOR_ONE;
							colorFlag = DataPredictOpinionConstants.COLOR_ONE_KEY;
						}
						
					} else if((Integer)listOne.get(i).get("WORDFREQUENCY") >=  DataPredictOpinionConstants.WORDDIS_CLASS_ONE 
						   && (Integer)listOne.get(i).get("WORDFREQUENCY") <  DataPredictOpinionConstants.WORDDIS_CLASS_TWO) {
						if(colorFlag < DataPredictOpinionConstants.COLOR_TWO_KEY) {
							color = DataPredictOpinionConstants.COLOR_TWO;
							colorFlag = DataPredictOpinionConstants.COLOR_TWO_KEY;
						}
					} else {
						if(colorFlag < DataPredictOpinionConstants.COLOR_THREE_KEY) {
							color = DataPredictOpinionConstants.COLOR_THREE;
							colorFlag = DataPredictOpinionConstants.COLOR_THREE_KEY;
						}
					}
				}
				
			} else if(listOne.size() < DataPredictOpinionConstants.WORDCOUNT_CLASS_TWO){
				for(int i = 0; i < listOne.size(); i++) {
				  if((Integer)listOne.get(i).get("WORDFREQUENCY") >=  DataPredictOpinionConstants.WORDDIS_CLASS_TWO ) {
					  if(colorFlag < DataPredictOpinionConstants.COLOR_THREE_KEY) {
							color = DataPredictOpinionConstants.COLOR_THREE;
							colorFlag = DataPredictOpinionConstants.COLOR_THREE_KEY;
						}
					} else {
						if(colorFlag < DataPredictOpinionConstants.COLOR_TWO_KEY) {
							color = DataPredictOpinionConstants.COLOR_TWO;
							colorFlag = DataPredictOpinionConstants.COLOR_TWO_KEY;
						}
					}
				}
			} else {
				if(colorFlag < DataPredictOpinionConstants.COLOR_THREE_KEY) {
					color = DataPredictOpinionConstants.COLOR_THREE;
					colorFlag = DataPredictOpinionConstants.COLOR_THREE_KEY;
				}
			}
		} else {
			color = DataPredictOpinionConstants.COLOR_ZE;
			colorFlag = DataPredictOpinionConstants.COLOR_ZE_KEY;
		}
		
		queTypeId.setColor(color);
		queTypeId.setColorFlag(colorFlag);
		return queTypeId;
	}
	
	
	/**
	 * 获取词云数据
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, String>> getWordCloud(String queTypeId) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Map<String,Object>> listOne = opinionInfoDao.getWordCloud(queTypeId);
		if(null != listOne && listOne.size() > 0) {
			for(int i = 0; i < listOne.size(); i++) {
		        if(null != listOne.get(i).get("TEXT") && !("".equals(listOne.get(i).get("TEXT"))) && 
		        		null != listOne.get(i).get("SIZE") && !("".equals(listOne.get(i).get("SIZE")))) {
		        	Map<String, String> map = new HashMap<String, String>();
		        	map.put("text", (String)listOne.get(i).get("TEXT"));
		        	map.put("size", String.valueOf((Integer)listOne.get(i).get("SIZE")));
		        	list.add(map);
		        }
			}
		}
		return list;
	}
	/**
	 * 获取问题类别bean
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<TreeNodeBean> getTreeNodeBean() throws Exception {
		List<TreeNodeBean> last = new ArrayList<TreeNodeBean>();
		TreeNodeBean qtb = new TreeNodeBean();
		qtb.setText("检举控告分类");
		qtb.setLeaf(false);
		qtb.setState("closed");
		qtb.setCodeType("WTXZ");
		qtb.setAllowChildren(true);
		List<TreeNodeBean> list = new ArrayList<TreeNodeBean>();
		//获取第一层节点
		List<Map<String,Object>> listOne = opinionInfoDao.getTreeNodeBean("00fd0131fb3f2c700016");
		for(int i = 0; i < listOne.size(); i++) {
			TreeNodeBean qtbOne =  new TreeNodeBean();
			qtbOne.setOid((String)listOne.get(i).get("OID"));
			qtbOne.setPid("00fd0131fb3f2c700016");
			qtbOne.setText((String)listOne.get(i).get("NAME"));
			qtbOne.setCodeType((String)listOne.get(i).get("CODETYPE"));
			qtbOne.setId((String)listOne.get(i).get("ID"));//id
			list.add(qtbOne);
			if((Integer)listOne.get(i).get("HASCHILDFLAG") == 1) {
				qtbOne.setState("closed");
				qtbOne.setLeaf(false);
				qtbOne.setAllowChildren(true);
				//获取第二层节点
				List<TreeNodeBean> listTwos = new ArrayList<TreeNodeBean>();
				List<Map<String,Object>> listTwo = opinionInfoDao.getTreeNodeBean((String)listOne.get(i).get("OID"));
				for(int j = 0; j < listTwo.size(); j++) {
					TreeNodeBean qtbTwo =  new TreeNodeBean();
					qtbTwo.setOid((String)listTwo.get(j).get("OID"));
					qtbTwo.setPid((String)listTwo.get(j).get("PID"));
					qtbTwo.setCodeType((String)listTwo.get(j).get("CODETYPE"));
					qtbTwo.setText((String)listTwo.get(j).get("NAME"));
					qtbTwo.setId((String)listTwo.get(j).get("ID"));
					
					listTwos.add(qtbTwo);
					if((Integer)listTwo.get(j).get("HASCHILDFLAG") == 1) {
						qtbTwo.setState("closed");
						qtbTwo.setLeaf(false);
						qtbTwo.setAllowChildren(true);
						//获取第三层节点
						List<TreeNodeBean> listThrees = new ArrayList<TreeNodeBean>();
						List<Map<String,Object>> listThree = opinionInfoDao.getTreeNodeBean((String)listTwo.get(j).get("OID"));
						for(int k = 0; k < listThree.size(); k++) {
							TreeNodeBean qtbThree =  new TreeNodeBean();
							qtbThree.setOid((String)listThree.get(k).get("OID"));
							qtbThree.setPid((String)listThree.get(k).get("PID"));
							qtbThree.setText((String)listThree.get(k).get("NAME"));
							qtbThree.setId((String)listThree.get(k).get("ID"));
							qtbThree.setCodeType((String)listThree.get(k).get("CODETYPE"));	
							qtbThree.setLeaf(true);
							qtbThree.setAllowChildren(false);
							listThrees.add(qtbThree);
					    }
						qtbTwo.setChildren(listThrees);
				    } else {
				    	qtbTwo.setLeaf(true);
						qtbTwo.setAllowChildren(false);
				    }
					
			   }
				qtbOne.setChildren(listTwos);
		} else {
			qtbOne.setLeaf(true);
			qtbOne.setAllowChildren(false);
		}
		qtb.setChildren(list);
	}
		last.add(qtb);
	return last;
	}
	
	/**
	 * 添加分类页面   获取问题类别bean
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<TreeNodeBean> getClassTreeBean() throws Exception{
		List<TreeNodeBean> last = new ArrayList<TreeNodeBean>();
		TreeNodeBean qtb = new TreeNodeBean();
		qtb.setText("检举控告分类");
		qtb.setLeaf(false);
		qtb.setState("closed");
		qtb.setAllowChildren(true);
		qtb.setOid("00fd0131fb3f2c700016");
		qtb.setId("01");
		List<TreeNodeBean> list = new ArrayList<TreeNodeBean>();
		//获取第一层节点
		List<Map<String,Object>> listOne = opinionInfoDao.getTreeNodeBean("00fd0131fb3f2c700016");
		for(int i = 0; i < listOne.size(); i++) {
			TreeNodeBean qtbOne =  new TreeNodeBean();
			qtbOne.setOid((String)listOne.get(i).get("OID"));
			qtbOne.setPid("00fd0131fb3f2c700016");
			qtbOne.setText((String)listOne.get(i).get("NAME"));
			qtbOne.setId((String)listOne.get(i).get("ID"));//id
			//qtbOne.setLeaf(true);
			//qtbOne.setAllowChildren(false);
			if((Integer)listOne.get(i).get("HASCHILDFLAG") == 1) {
				list.add(qtbOne);
				//qtbOne.setState("closed");
				qtbOne.setLeaf(false);
				qtbOne.setAllowChildren(true);
				//获取第二层节点
				List<TreeNodeBean> listTwos = new ArrayList<TreeNodeBean>();
				List<Map<String,Object>> listTwo = opinionInfoDao.getTreeNodeBean((String)listOne.get(i).get("OID"));
				for(int j = 0; j < listTwo.size(); j++) {
					TreeNodeBean qtbTwo =  new TreeNodeBean();
					qtbTwo.setOid((String)listTwo.get(j).get("OID"));
					qtbTwo.setPid((String)listTwo.get(j).get("PID"));
					qtbTwo.setText((String)listTwo.get(j).get("NAME"));
					qtbTwo.setId((String)listTwo.get(j).get("ID"));
					qtbTwo.setLeaf(true);
					qtbTwo.setAllowChildren(false);
					if((Integer)listTwo.get(j).get("HASCHILDFLAG") == 1) {
						listTwos.add(qtbTwo);
				    }
			   }
				qtbOne.setChildren(listTwos);
		
			}	else {
				qtbOne.setLeaf(true);
				qtbOne.setAllowChildren(false);
			}
		   qtb.setChildren(list);
	}
		last.add(qtb);
	return last;
	}
	
	/**
	 * 获取词典类型   违纪违法 
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDictType() throws Exception {
		List<Map<String,Object>> listOne = opinionInfoDao.getDictType();
		return listOne;
	}
	
	/**
	 * 获取区信息
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAreaMess(String newWord) throws Exception {
		List<Map<String,Object>> listOne = opinionInfoDao.getAreaMess(newWord);
		return listOne;
	}
	/**
	 * 获取派驻信息
	 * @author liyang 
	 * @date 2017-08-21
	 * @throws Exception
	 */
	public List<Map<String, Object>> getCommitteeMess(String newWord) {
		List<Map<String,Object>> listOne = opinionInfoDao.getCommitteeMess(newWord);
		return listOne;
	}
	/**
	 * 信访件分布情况本委查询
	 * @date 2017-08-25
	 * @author guoh
	 * @param filterValue
	 * @param start
	 * @param limit
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ExtjsPage<PetitionInfo> getLetterInfo(Map<String, Object> map,int start,int limit,PersonInfo person) throws Exception {
		int size = opinionInfoDao.getLetterInfoSize(map,person);
		List<Map<String, Object>> lists = opinionInfoDao.getLetterInfoList(map,start,limit,person);
		return basicInfoManager.getPetitionInfo(size,lists);
	}
	
	/**
	 * 获取某一区的信访编号
	 * @date 2017-08-25
	 * @author liyang
	 * @throws IOException
	 * @throws Exception
	 */
	public List<Map<String, Object>> getALLPitId(Map<String, Object> map) {
		List<Map<String,Object>> listOne = opinionInfoDao.getALLPitId(map);
		return listOne;
	}		
}