package com.sinosoft.xf.constants;

import java.util.HashMap;
import java.util.Map;


/**
 * @author  HJM
 * @createDate 2011-9-7
 */
public class Constants {
	public static final String NEW_QNAME = "http://services.datatransfer.xf.sinosoft.com";
	public static final String OLD_QNAME = "http://servicesImpl.webservice.sinosoft.com";
	public static final int TIME_OUT_IN_MILLISECONDS = 48000000;
	public static final String XFS_CODE = "310000000001";
	public static final String LOCALHOST = "http://156.10.1.122/jubao/";
	public static final String ADMIN = "admin";
	public static final String SECADMIN = "secadmin";
	public static final String AQADMIN = "aqadmin";
	/**
	 * 短日期格式
	 */
	public static final String DateShort = "yyyyMMdd";

	/**
	 * 短日期格式
	 */
	public static final String Date_Short = "yyyy-MM-dd";

	/**
	 * 长日期格式
	 */
	public static final String Long_Time = "yyyyMMddHHmmss";
	/**
	 * 长日期格式
	 */
	public static final String Long_Time_SSS = "yyMMddHHmmssSSS";
	public static final String Year = "yyyy";
	public static final String Year_Month = "yyyy-MM";
	/**
	 * 长时间格式带下划线
	 */
	public static final String Long_Time_ = "yyMMdd_HHmmss";
	/**
	 * 长日期格式
	 */
	public static final String DateLong = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 中文短日期格式
	 */
	public static final String Date_CN_Short = "yyyy年MM月dd日";

	/**
	 * 中文长日期格式
	 */
	public static final String Date_CN_Long = "yyyy年MM月dd日 HH:mm:ss";
	/**
	 * 当年开始日期
	 */
	public static final String YEAT_START_SHORT = "yyyy-01-01";
	/**
	 * 当年开始日期
	 */
	public static final String YEAT_END_SHORT = "yyyy-12-31";
	/**
	 * 当年开始日期
	 */
	public static final String MONTH_START_SHORT = "yyyy-MM-01";

	/**
	 * SHA加密常量
	 */
	public static final String SHA_Arithmetic = "SHA";
	/**
	 * MD5加密常量
	 */
	public static final String MD5_Arithmetic = "MD5";
	
	
	/**
	 * 编码格式
	 */
	public static final String CHARSET_GBK = "GBK";
	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String CHARSET_ISO8859 = "ISO8859-1";

	
	/**
	 * 程序中用到的一些常量
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String ZERO = "0";
	public static final String TWO = "2";
	public static final String THREE = "3";
	public static final String SubBack = "6";
	public static final String FIFTEEN = "15";
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final String ACCEPT = "0000000100";
	public static final String ACCEPT_NAME = "信访受理";
	public static final String SH_REGION_CDOE = "310000000000";
	public static final String HP_REGION_CDOE = "310100000000";//黄浦区的regionCode
	/**
	 * EXT常用返回值
	 */
	public static final String SUCCESS_TRUE = "{success:true}";
	public static final String SUCCESS_FALSE = "{success:false}";
	
	public static final String SINGLE_QUOTATION = "'";
	public static final String DOUBLE_QUOTATION = "\"";
	/**
	 * 区分派驻和区县
	 * @author ZHZ
	 * @date 2017年8月28日
	 */
	/**
	 * 区县code
	 * @author ZHZ
	 * @date 2017年8月28日
	 */
	public static final String [] countyRegionCode = {"310100000000","310400000000","310500000000","310600000000","310700000000","310900000000",
		"311000000000","311200000000","311300000000","311400000000","311500000000","311600000000","311700000000","311800000000","312000000000","313000000000"};
	/**
	 * 区县name
	 * @author ZHZ
	 * @date 2017年8月28日
	 */
	public static final String [] countyRegionName = {"黄浦区","徐汇区","长宁区","静安区","普陀区","虹口区","杨浦区","闵行区","宝山区","嘉定区",
		"浦东新区","金山区","松江区","青浦区","奉贤区","崇明区"};
	/**
	 * 派驻code
	 * @author ZHZ
	 * @date 2017年8月28日
	 */
	public static final String [] dispatchRegionCode = {"31AA00000000","31AB00000000","31AD00000000","31AE00000000","31AF00000000","31AH00000000",
		"31AI00000000","31AK00000000","31AL00000000","31AN00000000","31AM00000000","31AP00000000","31AW00000000","31AX00000000","31BA00000000",
		"31BB00000000","31BC00000000","31BD00000000","31BE00000000","31BG00000000","31BK00000000","31BL00000000","31BR00000000","31BU00000000",
		"31DG00000000","31DE00000000","31DF00000000","31DI00000000","31DP00000000","31DM00000000","31DN00000000","31DT00000000"};
	/**
	 * 派驻name
	 * @author ZHZ
	 * @date 2017年8月28日
	 */
	public static final String [] dispatchRegionName = {"市国资委党委纪检组","市建设交通工作党委纪检组","市委宣传部纪检组","市级机关纪工委","市委政法委机关纪检组","市经济信息化工作党委纪检组","市合作交流工作党委纪检组",
		"市科技工作党委纪检组","市财政局纪检组","市国家安全局纪检组","市公安局纪检组","市检察院纪检组","市高级人民法院纪检组","市教育卫生党委纪检组","市委农办纪检组","市发展改革委纪检组","市商务委纪检组",
		"市环保局纪检组","市规划国土资源局纪检组","市地税局纪检组","市民政局纪检组","市人力资源社会保障局纪检组","市政府办公厅纪检组","市卫生计生委纪检组","市交通委纪检组","市人大机关纪检组","市政协机关纪检组",
		"市文广影视局纪检组","市委统战部纪检组","市委办公厅纪检组","市委组织部纪检组","市总工会机关纪检组"};
	/**
	 * 区县派驻机构code与名字的键值对(为了 合并机构的code转换为并入机构code后，name未更新)
	 * @author BYG
	 * @date 2017年8月28日
	 */
	public static final Map<String, String> countyRegionCode_name = new HashMap<String, String>();
	static{
		countyRegionCode_name.put("310100000000","黄浦区");
		countyRegionCode_name.put("310400000000","徐汇区");
		countyRegionCode_name.put("310500000000","长宁区");
		countyRegionCode_name.put("310600000000","静安区");
		countyRegionCode_name.put("310700000000","普陀区");
		countyRegionCode_name.put("310900000000","虹口区");
		countyRegionCode_name.put("311000000000","杨浦区");
		countyRegionCode_name.put("311200000000","闵行区");
		countyRegionCode_name.put("311300000000","宝山区");
		countyRegionCode_name.put("311400000000","嘉定区");
		countyRegionCode_name.put("311500000000","浦东新区");
		countyRegionCode_name.put("311600000000","金山区");
		countyRegionCode_name.put("311700000000","松江区");
		countyRegionCode_name.put("311800000000","青浦区");
		countyRegionCode_name.put("312000000000","奉贤区");
		countyRegionCode_name.put("313000000000","崇明区");
		countyRegionCode_name.put("31AA00000000","市国资委党委纪检组");
		countyRegionCode_name.put("31AB00000000","市建设交通工作党委纪检组");
		countyRegionCode_name.put("31AD00000000","市委宣传部纪检组");
		countyRegionCode_name.put("31AE00000000","市级机关纪工委");
		countyRegionCode_name.put("31AF00000000","市委政法委机关纪检组");
		countyRegionCode_name.put("31AH00000000","市经济信息化工作党委纪检组");
		countyRegionCode_name.put("31AI00000000","市合作交流工作党委纪检组");
		countyRegionCode_name.put("31AK00000000","市科技工作党委纪检组");
		countyRegionCode_name.put("31AL00000000","市财政局纪检组");
		countyRegionCode_name.put("31AN00000000","市国家安全局纪检组");
		countyRegionCode_name.put("31AM00000000","市公安局纪检组");
		countyRegionCode_name.put("31AP00000000","市检察院纪检组");
		countyRegionCode_name.put("31AW00000000","市高级人民法院纪检组");
		countyRegionCode_name.put("31AX00000000","市教育卫生党委纪检组");
		countyRegionCode_name.put("31BA00000000","市委农办纪检组");
		countyRegionCode_name.put("31BB00000000","市发展改革委纪检组");
		countyRegionCode_name.put("31BC00000000","市商务委纪检组");
		countyRegionCode_name.put("31BD00000000","市环保局纪检组");
		countyRegionCode_name.put("31BE00000000","市规划国土资源局纪检组");
		countyRegionCode_name.put("31BG00000000","市地税局纪检组");
		countyRegionCode_name.put("31BK00000000","市民政局纪检组");
		countyRegionCode_name.put("31BL00000000","市人力资源社会保障局纪检组");
		countyRegionCode_name.put("31BR00000000","市政府办公厅纪检组");
		countyRegionCode_name.put("31BU00000000","市卫生计生委纪检组");
		countyRegionCode_name.put("31DG00000000","市交通委纪检组");
		countyRegionCode_name.put("31DE00000000","市人大机关纪检组");
		countyRegionCode_name.put("31DF00000000","市政协机关纪检组");
		countyRegionCode_name.put("31DI00000000","市文广影视局纪检组");
		countyRegionCode_name.put("31DP00000000","市委统战部纪检组");
		countyRegionCode_name.put("31DM00000000","市委办公厅纪检组");
		countyRegionCode_name.put("31DN00000000","市委组织部纪检组");
		countyRegionCode_name.put("31DT00000000","市总工会机关纪检组");
	}
}
