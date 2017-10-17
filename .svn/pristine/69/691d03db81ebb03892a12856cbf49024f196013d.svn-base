package com.sinosoft.fullTextSearch.manager.test;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.sinosoft.fullTextSearch.manager.FullTextSearchManager;
import com.sinosoft.fullTextSearch.util.ESUtils;
import com.sinosoft.fullTextSearch.vo.DataVO;
import com.sinosoft.xf.util.ObjectMapperWrap;
import com.sinosoftframework.core.test.SpringTxTestCase;

public class MyTest 
//extends SpringTxTestCase
{
	
//	@Test
	public void test(){
		try {
//			ESUtils.isIndexExists(INDEX_NAME);//索引是否存在
//			ESUtils.isTypeExists(INDEX_NAME, INDEX_TYPE);//索引类型是否存在
//			ESUtils.createIndex(INDEX_NAME);//创建索引
//			ESUtils.createMapping(INDEX_NAME, INDEX_TYPE);//创建mapping
//			ESUtils.deleteIndexByIndexName("petitioninfo");//删除索引
			ESUtils.createComplexIndex("petitioninfo", "petitionissue");//创建复杂索引
//			ESUtils.dataImport2ES(ESUtils.listData());//导入数据
//			ESUtils.indexStats(INDEX_NAME);
//			ESUtils.isAliasExist(INDEX_ALIAS);//别名是否存在
//			ESUtils.addAliasIndex(client, INDEX_NAME, INDEX_ALIAS);//给索引起别名
//			ESUtils.testQuery( "petitioninfo", "petitionissue", "天气污染严重");
//			ESUtils.testCompoundQuery( INDEX_NAME, INDEX_TYPE);
//			ESUtils.testAggregation( INDEX_NAME, INDEX_TYPE);
//			ESUtils.testPreQuery("petitioninfo", "petitionissue", "hengyang");
//			System.out.println("-----------------------------suggest-------------------------------");
//			List<String> list = ESUtils.getCompletionSuggest(null, "杨");
//			if(!list.isEmpty()){
//				String text = list.get(0);
//				ESUtils.testQuery( "petitioninfo", "petitionissue",text);
//			}
			
			FullTextSearchManager m = new FullTextSearchManager();
			System.out.println(m.getSuggest("周","petitioninfo","petitionissue"));
//			String html = m.multiMatchQuery("petitionTitle,issueContent", "周清水", 0, 15);
//			System.out.println(html);
			
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

//	@Test
	public void test1(){
		try {
			IndicesAdminClient indicesAdminClient = ESUtils.getClient().admin().indices();
			AnalyzeRequestBuilder request = indicesAdminClient.prepareAnalyze("刘建国反映东营冠森绝缘材料有限公司等的水污染大气污染噪声污染建设项目行政审批问题");
			request.setIndex("petitioninfo");
//			request.setTokenizer("first_py_letter");
			request.setAnalyzer("full_pinyin_letter_analyzer");
			// Analyzer（分析器）、Tokenizer（分词器）
			List<AnalyzeToken> listAnalysis = request.execute().actionGet().getTokens();
			String allPinyin = "";
			for(AnalyzeToken token : listAnalysis){
				allPinyin+=token.getTerm();
			}
			System.out.println(allPinyin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	@Test
	public void test2(){
		String s = "【匿名】反映【河北健民<淀粉>糖业<有限>公司】的【水污染|建设项目|】问题";
		System.out.println(s.replaceAll("\\【|\\】|\\[|\\]|\\<|\\>|\\|", ""));
	}

//	@Test
	public void test3(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			ESUtils.createComplexIndex("petitioninfo", "petitionissue");//创建复杂索引
			Client client = ESUtils.getClient();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
			dataSource.setUrl("jdbc:db2://localhost:50000/HZDB");
			dataSource.setUsername("db2admin");
			dataSource.setPassword("X8F3j9u8b3a1o");
			String sql = "select basic.oid oid,"
					+ "basic.petition_no petitionNo,"
					+ "basic.petition_type_name petitionTypeName,"
					+ "basic.petition_class_name petitionClassName,"
					+ "basic.petition_date petitionDate,"
					+ "basic.petition_title encryPetitionTitle,"
					+ "issue.issue_content encryIssueContent"
					+ " from petition_basic_info basic "
					+ " inner join petition_issue_info issue"
					+ " on basic.oid = issue.petition_basic_info_oid"
//					+ " where basic.oid = '00fd013bb805dfe9133b'"
//					+ " fetch first 3000 rows only "  //只拿10000条数据
					+ "";
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			stmt.setFetchSize(1000);
			rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()){
				String oid = rs.getString("oid");
				String petitionNo = rs.getString("petitionNo");
				String petitionTypeName = rs.getString("petitionTypeName");
				String petitionClassName = rs.getString("petitionClassName");
				Timestamp petitionDate = rs.getTimestamp("petitionDate");
				String encryPetitionTitle = rs.getString("encryPetitionTitle");
				String encryIssueContent = rs.getString("encryIssueContent");
				DataVO dataVo = new DataVO();
				dataVo.setOid(oid);
				dataVo.setPetitionNo(petitionNo);
				dataVo.setPetitionTypeName(petitionTypeName);
				dataVo.setPetitionDate(petitionDate);
				dataVo.setPetitionClassName(petitionClassName);
				dataVo.setEncryPetitionTitle(encryPetitionTitle);
				dataVo.setEncryIssueContent(encryIssueContent);
				ObjectMapperWrap mapper = new ObjectMapperWrap();
				String source = mapper.writeValueAsString(dataVo, "yyyy-MM-dd HH:mm:ss");
				bulkRequest.add(client.prepareIndex("petitioninfo", "petitionissue",
						dataVo.getOid()).setSource(source));
				if (i % 10000 == 0) {
					bulkRequest.execute().actionGet();
				}
				i++;
			}
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				for (BulkItemResponse item : bulkResponse.getItems()) {
					System.out.println(item.getFailureMessage());
				}
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null && !rs.isClosed()){
					rs.close();
				}
				if(stmt!=null && !stmt.isClosed()){
					stmt.close();
				}
				if(conn!=null && !conn.isClosed()){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testImport(){
		try {
			FullTextSearchManager m = new FullTextSearchManager();
			m.recreateIndexAndImportData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testIncrementImport(){
		try {
			FullTextSearchManager m = new FullTextSearchManager();
			m.incrementImportPetitionIssueData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	@Test
	public void testDelete(){
		try {
			FullTextSearchManager m = new FullTextSearchManager();
			m.deletePetitionIssueData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
