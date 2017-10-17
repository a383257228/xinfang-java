package com.sinosoft.fullTextSearch.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sinosoft.fullTextSearch.sql.Sql;
import com.sinosoft.fullTextSearch.sql.from.impl.FromBasicSql;
import com.sinosoft.fullTextSearch.sql.from.impl.FromDeleteRecordSql;
import com.sinosoft.fullTextSearch.sql.join.impl.JoinIssueSql;
import com.sinosoft.fullTextSearch.sql.join.impl.JoinTraceSql;
import com.sinosoft.fullTextSearch.sql.select.impl.SelectDeleteBasicOidSql;
import com.sinosoft.fullTextSearch.sql.select.impl.SelectDistinctPetitionIssueSql;
import com.sinosoft.fullTextSearch.sql.where.impl.WhereDeleteRecordTimestampSql;
import com.sinosoft.fullTextSearch.sql.where.impl.WhereTraceTimestampSql;
import com.sinosoft.fullTextSearch.util.ESUtils;
import com.sinosoft.fullTextSearch.vo.DataVO;
import com.sinosoft.xf.util.ObjectMapperWrap;
import com.sinosoftframework.core.domain.UIDGenerator;

@Service
public class FullTextSearchManager {
	
	Logger logger = LoggerFactory.getLogger(FullTextSearchManager.class);

	/**
	 * 获取下拉框自动补全选项
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public String getSuggest(String keyWord, String indexName, String indexType) throws Exception{
		
		BoolQueryBuilder subBoolQueryBuilder = QueryBuilders.boolQuery();
		subBoolQueryBuilder.should(QueryBuilders.prefixQuery("suggest.suggest_full_pinyin",keyWord));
		subBoolQueryBuilder.should(QueryBuilders.prefixQuery("suggest.suggest_first_py",keyWord));
		subBoolQueryBuilder.should(QueryBuilders.prefixQuery("suggest.suggest_simple",keyWord));
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(subBoolQueryBuilder);
		
		TermsAggregationBuilder aggregation = AggregationBuilders
				.terms("suggestAgg")// 命名
				.field("suggest")
			 	.order(Terms.Order.compound(Terms.Order.term(true),Terms.Order.count(false))); //排序 按数量排序 true为正序
		
//		AggregationBuilder aggregation =
//			    AggregationBuilders
//			        .terms("suggestAgg").field("suggest")
//			        .subAggregation(
//			            AggregationBuilders.topHits("top")
////			                .explain(true)
//			                .size(1)
//			                .from(10)
//			        );
		
		SearchRequestBuilder request = ESUtils.getSearchRequestBuilder(indexName, indexType, 0, 10,null,null);
		SearchResponse searchResponse = request
				.setQuery(boolQueryBuilder)
				.addAggregation(aggregation)
				.execute().actionGet();
		Terms genders = searchResponse.getAggregations().get("suggestAgg");
		StringBuffer sb = new StringBuffer();
		StringBuffer data = new StringBuffer();
		sb.append("{\"data\":[");
		for (Terms.Bucket entry : genders.getBuckets()) {
			String suggest = entry.getKeyAsString();
			long count = entry.getDocCount();
			data.append("{\"title\":\""+suggest+"\",\"result\":{\"suggest\":\""+suggest+"\",\"count\":\""+count+"\"}},");
		}
		sb.append(StringUtils.substringBeforeLast(data.toString(), ","));
		sb.append("]}");
		return sb.toString();
	}

	/**
	 * 多字段匹配检索
	 * @param searchField
	 * @param searchText
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public String multiMatchQuery(String searchField, String searchText, int from, int size, String indexName, String indexType) throws Exception{
		if(StringUtils.isNotBlank(searchField) && StringUtils.isNotBlank(searchText)){
			SearchRequestBuilder request = ESUtils.getSearchRequestBuilder(indexName, indexType, from, size,null,null);
			MultiMatchQueryBuilder mmqb = QueryBuilders.multiMatchQuery(searchText, searchField.split(","));
			SearchResponse searchResponse = request.setQuery(mmqb)
					.highlighter(new HighlightBuilder().field("issueContent").field("petitionTitle").preTags("<em>").postTags("</em>"))
					.highlighter(new HighlightBuilder().field("petitionTitle"))
//					.addHighlightedField("issueContent") //设置高亮字段
//					.addHighlightedField("petitionTitle") //设置高亮字段
//		            .setHighlighterPreTags("<em>")  //高亮字段前缀
//		            .setHighlighterPostTags("</em>")  //高亮字段后缀
		            .execute().actionGet();
			
			SearchHits hits = searchResponse.getHits();
			SearchHit[] searchHits = hits.getHits(); //获取匹配到的数据的集合
			StringBuffer result = new StringBuffer("{\"result\":[");
			//是否为第一行数据
			boolean isFirstRow = true;
			for (SearchHit s : searchHits) {
				Map<String, HighlightField> highlightFieldMap = s.getHighlightFields();
				// 从高亮字段map集中取得指定高亮字段
				HighlightField highlightField2 = highlightFieldMap.get("petitionTitle");
				String petitionTitle = "";
				if(highlightField2!=null){
					Text[] highlighterPTagsFields = highlightField2.fragments();
					// 为title串值增加自定义的高亮标签
					for (Text text : highlighterPTagsFields) {
						petitionTitle += text;
					}
				}
				// 从高亮字段map集中取得指定高亮字段
				HighlightField highlightField1 = highlightFieldMap.get("issueContent");
				String issueContent = "";
				if(highlightField1!=null){
					Text[] highlighterPTagsFields = highlightField1.fragments();
					// 为title串值增加自定义的高亮标签
					for (Text text : highlighterPTagsFields) {
						issueContent += text;
					}
				}
				if(!isFirstRow){
					result.append(",");
				}
				result.append("{");
				result.append("\"id\":\""+(String)s.getId()+"\",");
				result.append("\"petitionNo\":\""+(String)s.getSource().get("petitionNo")+"\",");
				result.append("\"regionCode\":\""+(String)s.getSource().get("regionCode")+"\",");
				if(StringUtils.isNotBlank(petitionTitle)){
					result.append("\"petitionTitle\":\""+petitionTitle+"\",");
				}else{
					result.append("\"petitionTitle\":\""+(String)s.getSource().get("petitionTitle")+"\",");
				}
				result.append("\"petitionTitleTitle\":\""+(String)s.getSource().get("petitionTitle")+"\",");
				if(StringUtils.isNotBlank(issueContent)){
					result.append("\"issueContent\":\""+issueContent.replace("\r\n", "<br/>").replace("\n", "<br/>").replace("\r", "<br/>")+"\",");
					String issueContentTitle = (String)s.getSource().get("issueContent");
					if(issueContentTitle.length()>400){
						issueContentTitle = issueContentTitle.substring(0, 400)+"...";
					}
					result.append("\"issueContentTitle\":\""+issueContentTitle.replace("\r\n", "<br/>").replace("\n", "<br/>").replace("\r", "<br/>")+"\"");
				}else{
					result.append("\"issueContent\":\"无\",");
					result.append("\"issueContentTitle\":\"无\"");
				}
				result.append("}");
				isFirstRow = false;
			}
			result.append("]}");
			return result.toString();
		}
		return null;
	}
	
	/**
	 * 增量删除
	 */
	public void deletePetitionIssueData(){
		deletePetitionIssueData(ESUtils.DEFAULT_INDEX_NAME, ESUtils.DEFAULT_INDEX_TYPE);
	}
	
	/**
	 * 增量删除
	 * @param indexName
	 * @param indexType
	 */
	public void deletePetitionIssueData(String indexName,String indexType){
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Sql sql = new Sql();
			sql.setSelectSql(new SelectDeleteBasicOidSql());
			sql.setFromSql(new FromDeleteRecordSql());
			sql.setWhereSql(new WhereDeleteRecordTimestampSql());
			String sqlStr = sql.toString();
			conn = ESUtils.getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStr);
			Client client = ESUtils.getClient();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			//保存需要抽取的数据中的最大入库时间
			Timestamp maxInsetTime = null;
			int i = 0;
			while(rs.next()){
				i++;
				bulkRequest.add(ESUtils.getDeleteRequestBuilder(indexName, indexType, rs.getString("oid")));
				//删除记录的插入时间
				Timestamp insertTimestamp = rs.getTimestamp("insertTimestamp");
				//如果最大时间记录为空，并且当前这条删除记录的入库时间不为空，就将最大时间记录为这条删除记录的入库时间
				if(maxInsetTime==null && insertTimestamp!=null){
					maxInsetTime = rs.getTimestamp("insertTimestamp");
				}
				//如果当前删除记录的入库时间不为空，并且入库时间大于最大时间记录，将最大时间记录置为当前删除记录的入库时间
				else if(insertTimestamp!=null && insertTimestamp.getTime()>maxInsetTime.getTime()){
					maxInsetTime = insertTimestamp;
				}
				//es最多一次提交10000条
				if (i == 10000) {
					bulkRequest.execute().actionGet();
					i=0;
				}
			}
			//如果有剩余未提交的，在此一并提交
			if(i>0){
				BulkResponse bulkResponse = bulkRequest.execute().actionGet();
				if (bulkResponse.hasFailures()) {
					for (BulkItemResponse item : bulkResponse.getItems()) {
						System.out.println(item.getFailureMessage());
					}
				}
			}
			UIDGenerator u = new UIDGenerator();
			//如果最大入库时间不为空，时间戳记录表中中就保存这个时间，否则使用字段默认时间current timestamp
			if(maxInsetTime!=null){
				pstmt = conn.prepareStatement("insert into es_timestamp (oid,index_name,index_type,extract_type,extract_time) values(?,?,?,?,?)");
			}else{
				pstmt = conn.prepareStatement("insert into es_timestamp (oid,index_name,index_type,extract_type) values(?,?,?,?)");
			}
			String oid = (String) u.generate(null, null);
			pstmt.setString(1, oid);
			pstmt.setString(2, ESUtils.DEFAULT_INDEX_NAME);
			pstmt.setString(3, ESUtils.DEFAULT_INDEX_TYPE);
			pstmt.setInt(4, 1);//抽取类型为删除
			if(maxInsetTime!=null){
				pstmt.setTimestamp(5, maxInsetTime);
			}
			pstmt.execute();
			rs.close();
			stmt.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			logger.error(""+e.getMessage(), e);
			throw new RuntimeException(e.getMessage(),e);
		} finally{
			try {
				if(rs!=null && !rs.isClosed()){
					rs.close();
				}
				if(stmt!=null && !stmt.isClosed()){
					stmt.close();
				}
				if(pstmt!=null && !pstmt.isClosed()){
					pstmt.close();
				}
				if(conn!=null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				logger.error("关闭数据库资源异常："+e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 增量导入信访问题索引数据
	 */
	public void incrementImportPetitionIssueData(){
		incrementImportData(ESUtils.DEFAULT_INDEX_NAME, ESUtils.DEFAULT_INDEX_TYPE);
	}
	
	/**
	 * 增量导入信访问题索引数据
	 * @param indexName  索引名
	 * @param indexType  索引类型
	 */
	public void incrementImportData(String indexName,String indexType){
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//组装sql
		Sql sql = new Sql();
		sql.setSelectSql(new SelectDistinctPetitionIssueSql());
		sql.setFromSql(new FromBasicSql());
		sql.addJoinTableSql(new JoinIssueSql());
		sql.addJoinTableSql(new JoinTraceSql(),Sql.JOIN_TYPE_LEFT);
		sql.setWhereSql(new WhereTraceTimestampSql());
		String sqlStr = sql.toString();
		try {
			conn = ESUtils.getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.setFetchSize(10000);
			rs = stmt.executeQuery(sqlStr);
			Client client = ESUtils.getClient();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			//保存需要抽取的数据中的最大入库时间
			Timestamp maxInsetTime = null;
			int i = 0;
			while(rs.next()){
				i++;
				//创建索引数据视图对象，并赋值
				DataVO dataVo = new DataVO();
				dataVo.setOid(rs.getString("oid"));
				dataVo.setPetitionNo(rs.getString("petitionNo"));
				dataVo.setPetitionTypeName(rs.getString("petitionTypeName"));
				dataVo.setPetitionDate(rs.getTimestamp("petitionDate"));
				dataVo.setPetitionClassName(rs.getString("petitionClassName"));
				dataVo.setRegionCode(rs.getString("regionCode"));
				dataVo.setEncryPetitionTitle(rs.getString("encryPetitionTitle"));
				dataVo.setEncryIssueContent(rs.getString("encryIssueContent"));
				//当前记录的入库时间
				Timestamp insertTime = rs.getTimestamp("insertTime");
				//如果最大时间记录为空，并且当前这条数据的入库时间不为空，就将最大时间记录为这条数据的入库时间
				if(maxInsetTime==null && insertTime!=null){
					maxInsetTime = insertTime;
				}
				//如果当前记录的入库时间不为空，并且入库时间大于最大时间记录，将最大时间记录置为当前数据的入库时间
				else if(insertTime!=null && insertTime.getTime()>maxInsetTime.getTime()){
					maxInsetTime = insertTime;
				}
				//将数据转换为json格式
				ObjectMapperWrap mapper = new ObjectMapperWrap();
				//指定日期格式
				String source = mapper.writeValueAsString(dataVo, "yyyy-MM-dd HH:mm:ss");
				bulkRequest.add(ESUtils.getIndexUpsertRequest(indexName, indexType, rs.getString("oid"), source));
				//es最多一次提交10000条
				if (i == 10000) {
					bulkRequest.execute().actionGet();
					i=0;
				}
			}
			//如果有剩余未提交的，在此一并提交
			if(i>0){
				BulkResponse bulkResponse = bulkRequest.execute().actionGet();
				if (bulkResponse.hasFailures()) {
					for (BulkItemResponse item : bulkResponse.getItems()) {
						System.out.println(item.getFailureMessage());
					}
				}
			}
			UIDGenerator u = new UIDGenerator();
			//如果最大时间记录不空，就把时间戳表中的交换时间置为最大时间
			if(maxInsetTime!=null){
				pstmt = conn.prepareStatement("insert into es_timestamp (oid,index_name,index_type,extract_type,extract_time) values(?,?,?,?,?)");
			}else{
				pstmt = conn.prepareStatement("insert into es_timestamp (oid,index_name,index_type,extract_type) values(?,?,?,?)");
			}
			String oid = (String) u.generate(null, null);
			pstmt.setString(1, oid);
			pstmt.setString(2, ESUtils.DEFAULT_INDEX_NAME);
			pstmt.setString(3, ESUtils.DEFAULT_INDEX_TYPE);
			pstmt.setInt(4, 0);
			if(maxInsetTime!=null){
				pstmt.setTimestamp(5, maxInsetTime);
			}
			pstmt.execute();
			rs.close();
			stmt.close();
			pstmt.close();
			conn.close();
		}  catch (Exception e) {
			logger.error(""+e.getMessage(), e);
			throw new RuntimeException(e.getMessage(),e);
		} finally{
			try {
				if(rs!=null && !rs.isClosed()){
					rs.close();
				}
				if(stmt!=null && !stmt.isClosed()){
					stmt.close();
				}
				if(pstmt!=null && !pstmt.isClosed()){
					pstmt.close();
				}
				if(conn!=null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				logger.error("关闭数据库资源异常："+e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 创建索引并导入数据
	 */
	public void recreateIndexAndImportData(){
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//如果索引存在，删除后重建
			if(ESUtils.isIndexExists(null)){
				//删除索引
				ESUtils.deleteIndexByIndexName(null);
			}
			//创建复杂索引
			ESUtils.createComplexIndex(null, null);
			Sql sql = new Sql();
			sql.setSelectSql(new SelectDistinctPetitionIssueSql());
			sql.setFromSql(new FromBasicSql());
			sql.addJoinTableSql(new JoinIssueSql());
			sql.addJoinTableSql(new JoinTraceSql(),Sql.JOIN_TYPE_LEFT);
			//查询要导入索引的数据
			String sqlStr = sql.toString();
			logger.info(sqlStr);
			conn = ESUtils.getDataSource().getConnection();
			stmt = conn.createStatement();
			//设置每次fetch的数据量
			stmt.setFetchSize(10000);
			rs = stmt.executeQuery(sqlStr);
			Client client = ESUtils.getClient();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			Timestamp maxInsetTime = null;
			int i = 0;
			while(rs.next()){
				i++;
				//创建索引数据视图对象，并赋值
				DataVO dataVo = new DataVO();
				dataVo.setOid(rs.getString("oid"));
				dataVo.setPetitionNo(rs.getString("petitionNo"));
				dataVo.setPetitionTypeName(rs.getString("petitionTypeName"));
				dataVo.setPetitionDate(rs.getTimestamp("petitionDate"));
				dataVo.setPetitionClassName(rs.getString("petitionClassName"));
				dataVo.setRegionCode(rs.getString("regionCode"));
				dataVo.setEncryPetitionTitle(rs.getString("encryPetitionTitle"));
				dataVo.setEncryIssueContent(rs.getString("encryIssueContent"));
				//当前记录的入库时间
				Timestamp insertTime = rs.getTimestamp("insertTime");
				//如果最大时间记录为空，并且当前这条数据的入库时间不为空，就将最大时间记录为这条数据的入库时间
				if(maxInsetTime==null && insertTime!=null){
					maxInsetTime = insertTime;
				}
				//如果当前记录的入库时间不为空，并且入库时间大于最大时间记录，将最大时间记录置为当前数据的入库时间
				else if(insertTime!=null && insertTime.getTime()>maxInsetTime.getTime()){
					maxInsetTime = insertTime;
				}
				//将数据转换为json格式
				ObjectMapperWrap mapper = new ObjectMapperWrap();
				//指定日期格式
				String source = mapper.writeValueAsString(dataVo, "yyyy-MM-dd HH:mm:ss");
				bulkRequest.add(ESUtils.getIndexRequestBuilder(null, null, dataVo.getOid()).setSource(source));
				
				if (i % 10000 == 0) {
					bulkRequest.execute().actionGet();
					i=0;
				}
			}
			if(i>0){
				BulkResponse bulkResponse = bulkRequest.execute().actionGet();
				if (bulkResponse.hasFailures()) {
					for (BulkItemResponse item : bulkResponse.getItems()) {
						System.out.println(item.getFailureMessage());
					}
				}
			}
			UIDGenerator u = new UIDGenerator();
			//如果最大时间记录不空，就把时间戳表中的交换时间置为最大时间
			if(maxInsetTime!=null){
				pstmt = conn.prepareStatement("insert into es_timestamp (oid,index_name,index_type,extract_type,extract_time) values(?,?,?,?,?)");
			}else{
				pstmt = conn.prepareStatement("insert into es_timestamp (oid,index_name,index_type,extract_type) values(?,?,?,?)");
			}
			String oid = (String) u.generate(null, null);
			pstmt.setString(1, oid);
			pstmt.setString(2, ESUtils.DEFAULT_INDEX_NAME);
			pstmt.setString(3, ESUtils.DEFAULT_INDEX_TYPE);
			pstmt.setInt(4, 0);
			if(maxInsetTime!=null){
				pstmt.setTimestamp(5, maxInsetTime);
			}
			pstmt.addBatch();
			oid = (String) u.generate(null, null);
			pstmt.setString(1, oid);
			pstmt.setString(2, ESUtils.DEFAULT_INDEX_NAME);
			pstmt.setString(3, ESUtils.DEFAULT_INDEX_TYPE);
			pstmt.setInt(4, 1);
			if(maxInsetTime!=null){
				pstmt.setTimestamp(5, maxInsetTime);
			}
			pstmt.addBatch();
			pstmt.executeBatch();
			rs.close();
			stmt.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			logger.error(""+e.getMessage(), e);
			throw new RuntimeException(e.getMessage(),e);
		} finally{
			try {
				if(rs!=null && !rs.isClosed()){
					rs.close();
				}
				if(stmt!=null && !stmt.isClosed()){
					stmt.close();
				}
				if(pstmt!=null && !pstmt.isClosed()){
					pstmt.close();
				}
				if(conn!=null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				logger.error("关闭数据库资源异常："+e.getMessage(), e);
			}
		}
	}
}
