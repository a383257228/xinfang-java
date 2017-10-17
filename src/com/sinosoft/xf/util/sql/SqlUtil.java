package com.sinosoft.xf.util.sql;

import java.util.Map;

import com.sinosoft.organization.domain.PersonInfo;
import com.sinosoft.xf.petition.accept.manager.PetitionBasicInfoManager;

public class SqlUtil {
	//本市、本委拼接sql
	public static String municipalAndcommittee(Map<String, Object> map,PersonInfo person,PetitionBasicInfoManager petitionBasicInfoManager){
		StringBuffer sb = new StringBuffer();
		String swFlag = map.get("swFlag").toString();
		if(swFlag.equals("bw")){//包含上级转交办和不包含上级转交办
			String bwRadio = map.get("bwRadio").toString(); 
			if("0".equals(bwRadio)){//选中含上级
				
			}else if("1".equals(bwRadio)){//选中不含上级
				sb.append(" and basic.petition_source_code not like '02%' ");
			}
			//20170925 byg 查询包含被合并的问题属地
			String unionOldSql = petitionBasicInfoManager.unionOldRegionCodeAndNewSync(person.getRegionCode());
			sb.append(" and basic.region_code ").append(unionOldSql);
		}
		if(swFlag.equals("qs")){//只查自收
			sb.append(" and basic.petition_source_code like '01%' ");
		}
		sb.append(" and basic.petition_source_code not in (select code from System_Code_Node where parent_Id =(select oid from System_Code_Node where name = '问题线索移送') ) ");
		return sb.toString();
	}
}