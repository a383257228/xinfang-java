//package com.sinosoft.xf.query.common.impl.blbj;
//
//import java.sql.Timestamp;
//import java.util.Date;
//
//import com.sinosoft.xf.constants.BusinessConstants;
//import com.sinosoft.xf.query.common.iface.SqlBody;
//import com.sinosoft.xf.util.TimeUtils;
//
///**
// * 信访室办结表信息查询
// * @author hjh
// *
// * 2015-2-9上午11:56:54
// */
//public class XFBJqlHelper extends BJSqlHelper{
////	static Logger log = Logger.getLogger(XFBJqlHelper.class);
//	
//	public static void main(String[] args) {
//		System.out.println(new Date());
//		System.out.println(new Timestamp(System.currentTimeMillis()));
//		String sql = new XFBJqlHelper().acceptCountSql();
//		System.out.println(sql);
//	}
//
//	@Override
//	public String acceptCountSql() {
//		String select = "select count(0) as num ,basic.curr_Dept_Code currDeptCode ";
//		StringBuffer sql = new StringBuffer(select+getSqlBody());
//		
//		sql.append(" and basic.petition_date >= '" + timeTange[0]+"'");
//		sql.append(" and basic.petition_date <= '" + timeTange[1]+"'");
//		sql.append(" and ( basic.deal_type_code in ( '0202','0201','0101','01021','01022','01023','01024','01025') or state.activity_No in ('0000000100','0000000101','0000000102','0000000104'))");
//		sql.append("and basic.curr_dept_code = '310000000001' ");
//		sql.append("group by basic.curr_dept_code ");
//		return sql.toString();
//	}
//	
//	
//
//	@Override
//	public String dealingCountSql() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String endCountSql() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String legacyCountSql() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//}
