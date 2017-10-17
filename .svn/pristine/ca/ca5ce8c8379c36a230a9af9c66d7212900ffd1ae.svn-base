package com.sinosoft.fullTextSearch.util;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistResponse;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.admin.indices.stats.CommonStats;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.action.admin.indices.stats.ShardStats;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.UnmodifiableIterator;
import com.jolbox.bonecp.BoneCPDataSource;
import com.sinosoft.fullTextSearch.vo.DataVO;
import com.sinosoft.xf.util.FileUtil;
import com.sinosoft.xf.util.ObjectMapperWrap;
import com.sinosoft.xf.util.PropertiesUtils;
public class ESUtils {

	private static Logger logger = LoggerFactory.getLogger(ESUtils.class);
	
	public static String DEFAULT_INDEX_NAME;
//	private static String DEFAULT_INDEX_ALIAS = "petition";
	public static String DEFAULT_INDEX_TYPE;
	private static String DATE_FORMAT;
	private static String CLUSTER_NAME;
	private static String[] HOST_PORT_ARRAY;
	
	private static String DB_JDBC_DRIVER;
	private static String DB_JDBC_URL;
	private static String DB_JDBC_USERNAME;
	private static String DB_JDBC_PASSWORD;
	private static DataSource DB_DATA_SOURCE;
	/**
	 * es连接
	 */
	private static Client client;
	
	static{
		try {
			DEFAULT_INDEX_NAME = PropertiesUtils.getPropertiesValue("es", "DEFAULT_INDEX_NAME");
			DEFAULT_INDEX_TYPE = PropertiesUtils.getPropertiesValue("es", "DEFAULT_INDEX_TYPE");
			DATE_FORMAT = PropertiesUtils.getPropertiesValue("es", "DATE_FORMAT");
			HOST_PORT_ARRAY = PropertiesUtils.getPropertiesValue("es", "HOST_PORTS").split(",");
			CLUSTER_NAME = PropertiesUtils.getPropertiesValue("es", "CLUSTER_NAME");
			client =  createClient();
			
			DB_JDBC_DRIVER = PropertiesUtils.getPropertiesValue("es", "DB_JDBC_DRIVER");
			DB_JDBC_URL = PropertiesUtils.getPropertiesValue("es", "DB_JDBC_URL");
			DB_JDBC_USERNAME = PropertiesUtils.getPropertiesValue("es", "DB_JDBC_USERNAME");
			DB_JDBC_PASSWORD = PropertiesUtils.getPropertiesValue("es", "DB_JDBC_PASSWORD");
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 获取es连接
	 * @return
	 * @throws UnknownHostException
	 */
	public static TransportClient createClient()  throws UnknownHostException {
//		Settings settings = Settings.builder()
//				.put("cluster.name", CLUSTER_NAME)//指定集群
//				.put("client.transport.sniff", true)//开启嗅探
//				.build();
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		for(String host_port : HOST_PORT_ARRAY){
			String host = host_port.split(":")[0];
			int port = Integer.parseInt(host_port.split(":")[1]);
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
		}
		return client;
	}
	
	public static Client getClient() throws Exception{
		return client;
	}
	
	/**
	 * 获取SearchRequestBuilder
	 * @param indexName
	 * @param indexType
	 * @param size
	 * @return
	 */
	public static SearchRequestBuilder getSearchRequestBuilder(
			String indexName, String indexType,int from, int size, String sort, SortOrder order) {
		SearchRequestBuilder searchRequestBuilder = client
				.prepareSearch(indexName).setTypes(indexType)
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(from)
				.setSize(size)
				;
		if(StringUtils.isNotBlank(sort)){
			searchRequestBuilder.addSort(sort,order);
		}
		return searchRequestBuilder;
	}
	
	/**
	 * 判断索引类型是否存在
	 * @param client
	 * @param index
	 * @param type
	 * @return
	 */
	public static boolean isTypeExists(String index, String type) {
		if (!isIndexExists(index)) {
			System.out.println("--------- isTypeExists 索引 [{}] 不存在" + index);
			return false;
		}
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		TypesExistsResponse response = indicesAdminClient
				.prepareTypesExists(index).setTypes(type).get();
		return response.isExists();
	}
	
	/**
	 * 判断指定索引是否存在
	 * @param client
	 * @param index
	 * @return
	 */
	public static boolean isIndexExists(String index) {
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		IndicesExistsResponse response = indicesAdminClient
				.prepareExists(index==null?DEFAULT_INDEX_NAME:index).get();
		return response.isExists();
		/*
		 * 另一种方式 IndicesExistsRequest indicesExistsRequest = new
		 * IndicesExistsRequest(index); IndicesExistsResponse response =
		 * client.admin().indices().exists(indicesExistsRequest).actionGet();
		 */
	}
	
	/**
	 * 创建复杂索引
	 * 创建索引的时候一并创建mapping
	 * 指定分片个数为3，副本个数为2
	 * @param client
	 * @param index
	 * @return
	 */
	public static boolean createComplexIndex(String indexName, String indexType) throws Exception{
		// settings
//		Settings settings = Settings.builder().put("index.number_of_shards", 10)
//				.put("index.number_of_replicas", 1).build();
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		CreateIndexResponse response = indicesAdminClient.prepareCreate(indexName==null?DEFAULT_INDEX_NAME:indexName)
				.setSettings(getSettingJSON()).addMapping(indexType==null?DEFAULT_INDEX_TYPE:indexType, getMappingJSON()).get();
		return response.isAcknowledged();
	}
	
	/**
	 * 从文件中获取setting的json字符串配置
	 * @return
	 * @throws IOException
	 */
	private static String getSettingJSON() throws Exception{
		String path = ESUtils.class.getResource("/applicationContext.xml").getPath();
		String settingFilePath = path.substring(0, path.indexOf("WEB-INF"))+File.separator+"fullTextSearch/json/setting.json";
		File settingFile = new File(settingFilePath);
		String setting = FileUtil.file2String(settingFile);
		return setting;
	}
	
	/**
	 * 从文件中获取mapping的json字符串配置
	 * @return
	 * @throws IOException
	 */
	private static String getMappingJSON() throws Exception{
		String path = ESUtils.class.getResource("/applicationContext.xml").getPath();
		String mappingFilePath = path.substring(0, path.indexOf("WEB-INF"))+File.separator+"fullTextSearch/json/mapping.json";
		File mappingFile = new File(mappingFilePath);
		String mapping = FileUtil.file2String(mappingFile);
		return mapping;
	}
	
	/**
	 * 创建gIndexRequestBuilder
	 * @param indexName
	 * @param indexType
	 * @param id
	 * @return
	 */
	public static IndexRequestBuilder getIndexRequestBuilder(String indexName, String indexType, String id){
		return client.prepareIndex(indexName==null?DEFAULT_INDEX_NAME:indexName, indexType==null?DEFAULT_INDEX_TYPE:indexType,	id);
	}
	/**
	 * 创建gIndexRequestBuilder
	 * @param indexName
	 * @param indexType
	 * @param id
	 * @return
	 */
	public static UpdateRequestBuilder getUpdateRequestBuilder(String indexName, String indexType, String id){
		return client.prepareUpdate(indexName==null?DEFAULT_INDEX_NAME:indexName, indexType==null?DEFAULT_INDEX_TYPE:indexType, id);
	}
	
	/**
	 * 创建插入/更新的UpdateRequest
	 * 如果id对应的索引存在，就会继续更新，不存在则会插入
	 * 如果更新内容有变，索引版本号会增加
	 * @param indexName
	 * @param indexType
	 * @param id 主键
	 * @param source  数据
	 * @return
	 * @throws Exception
	 */
	public static UpdateRequest getIndexUpsertRequest(String indexName, String indexType, String id,String source) throws Exception{
		IndexRequest indexRequest = new IndexRequest(indexName==null?DEFAULT_INDEX_NAME:indexName, indexType==null?DEFAULT_INDEX_TYPE:indexType, id).source(source);
		UpdateRequest updateRequest = new UpdateRequest(indexName==null?DEFAULT_INDEX_NAME:indexName, indexType==null?DEFAULT_INDEX_TYPE:indexType, id).doc(source).upsert(indexRequest);
		return updateRequest;
	}
	
	/**
	 * 获取删除所以年数据的deleteRequestBuilder
	 * @param indexName
	 * @param indexType
	 * @param id 主键
	 * @return
	 */
	public static DeleteRequestBuilder getDeleteRequestBuilder(String indexName, String indexType, String id){
		DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(indexName==null?DEFAULT_INDEX_NAME:indexName, indexType==null?DEFAULT_INDEX_TYPE:indexType, id);
		return deleteRequestBuilder;
	}
	
	/**
	 * 创建索引时的分词器配置
	 * @param client
	 * @return
	 */
	public static XContentBuilder createSettingXContent(){
		XContentBuilder aliases = null;
		try {
			aliases = jsonBuilder()
			.startObject()
			.field("number_of_shards", 10)
			.field("number_of_replicas", 1)
				.startObject("analysis")
				.startObject("analyzer")
					.startObject("ik_pinyin_analyzer")
						.field("type", "custom")
						.field("analyzer", "ik_max_word")
						.field("tokenizer", "ik_max_word")
						.field("filter", "my_pinyin_filter")
					.endObject()
					.startObject("first_py_letter_analyzer")
						.field("tokenizer", "first_py_letter")
						.field("char_filter", "my_char_filter")
					.endObject()
					.startObject("full_pinyin_letter_analyzer")
						.field("tokenizer", "full_pinyin_letter")
						.field("char_filter", "my_char_filter")
					.endObject()
					.startObject("my_simple_analyzer")
						.field("type", "simple")
						.field("char_filter", "my_char_filter")
					.endObject()
				.endObject()
				.startObject("tokenizer")
					.startObject("first_py_letter")
						.field("type", "pinyin")
						.field("keep_first_letter", true)
						.field("keep_full_pinyin", false)
						.field("keep_original", false)
						.field("limit_first_letter_length", 16)
						.field("lowercase", true)
						.field("trim_whitespace", true)
						.field("keep_none_chinese_in_first_letter", false)
						.field("none_chinese_pinyin_tokenize", false)
						.field("keep_none_chinese", true)
						.field("keep_none_chinese_in_joined_full_pinyin", true)
					.endObject()
					.startObject("full_pinyin_letter")
						.field("type", "pinyin")
						.field("keep_first_letter", false)
						.field("keep_full_pinyin", false)
						.field("keep_joined_full_pinyin", true)
						.field("keep_separate_first_letter", false)
						.field("keep_original", false)
						.field("limit_first_letter_length", 16)
						.field("lowercase", true)
						.field("keep_none_chinese_in_first_letter", false)
						.field("none_chinese_pinyin_tokenize", false)
						.field("keep_none_chinese", true)
						.field("keep_none_chinese_in_joined_full_pinyin", true)
					.endObject()
				.endObject()
				.startObject("filter")
					.startObject("my_pinyin_filter")
						.field("type", "pinyin")
//						.field("keep_original", true)
						.field("keep_joined_full_pinyin", true)
//						.field("first_letter", "none")
//						.field("padding_char", "")
					.endObject()
				.endObject()
				.startObject("char_filter")
					.startObject("my_char_filter")
					.field("type", "pattern_replace")
					.field("pattern", "\\，|\\（|\\）|\\(|\\)|\\【|\\】|\\[|\\]|\\<|\\>|\\|")
					.field("replacement", "")
					.endObject()
				.endObject()
			.endObject()
			.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(aliases.prettyPrint().string());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aliases;
	}
	
	/**
	 * 获取用于设置映射的builder
	 * @return
	 */
	public static XContentBuilder getMapping(){
		XContentBuilder mapping = null;
		try {
			mapping = jsonBuilder()
			.startObject()
			//文档过期时间
			.startObject("_ttl")
				.field("enabled",false)
				.endObject()
					.startObject("properties")
					.startObject("oid")
						/*
						 * type表示类型，支持一下几种
						 *  文本: string
							数字: byte, short, integer, long
							浮点数: float, double
							布尔值: boolean
							Date: date
						 */
						.field("type","string")
						.field("index","no")//Don’t index this field at all. This field will not be searchable.
					.endObject()
					.startObject("petitionTypeName")
						.field("type","string")
						.field("index","not_analyzed")
//						.field("index","analyzed")//default:analyzed. First analyze the string, then index it. In other words, index this field as full text.
					.endObject()
					.startObject("petitionClassName")
						.field("type","string")
						.field("index","not_analyzed")
					.endObject()
					.startObject("petitionTitle")
						.field("type","string")
						.field("analyzer", "ik_max_word")
						.field("search_analyzer", "ik_smart")
						.field("null_value", "无")//空置使用默认字符串
						.field("boost", "9999")
					.endObject()
					.startObject("issueContent")
						.field("type","string")
						.field("analyzer", "ik_max_word")
						.field("search_analyzer", "ik_smart")
						.field("null_value", "无")//空置使用默认字符串
						.field("boost", "9999")
						.field("term_vector", "with_positions_offsets_payloads")//
					.endObject()
					.startObject("petitionNo")
						.field("type","string")
						.field("index","not_analyzed") //Index this field, so it is searchable, but index the value exactly as specified. Do not analyze it.
					.endObject()
					.startObject("petitionDate")
						.field("type","date")
						.field("format",DATE_FORMAT)
						.field("index","not_analyzed")
					.endObject()
					.startObject("suggest")
						.field("type","string")
						.field("index","not_analyzed")
						.startObject("fields")
							.startObject("suggest_simple")
								.field("type","string")
								.field("analyzer", "my_simple_analyzer")
							.endObject()
							.startObject("suggest_full_pinyin")
								.field("type","string")
								.field("analyzer", "full_pinyin_letter_analyzer")
								.field("search_analyzer", "simple")
							.endObject()
							.startObject("suggest_first_py")
								.field("type","string")
								.field("analyzer", "first_py_letter_analyzer")
							.endObject()
						.endObject()
					.endObject()
//					.startObject("suggestFullPinyin")
//						.field("type","string")
////						.field("index","not_analyzed")
//						.field("analyzer", "simple")
//					.endObject()
				.endObject()
			.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapping;
	}
	
	/**
	 * 创建索引
	 * @param client
	 * @param indexName
	 */
	public static void createIndex(String indexName){
		CreateIndexRequest request = new CreateIndexRequest(DEFAULT_INDEX_NAME);
		client.admin().indices().create(request);
		//或者
//		IndicesAdminClient indicesAdminClient = client.admin().indices();
//		CreateIndexResponse response = indicesAdminClient.prepareCreate(indexName).get();
//		System.out.println(response.isAcknowledged());
	}
	
	/**
     * 为索引创建别名
     * @param client
     * @param index
     * @param alias
     * @return
     */
    public static boolean addAliasIndex(Client client, String index , String alias){
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        IndicesAliasesResponse response = indicesAdminClient.prepareAliases().addAlias(index, alias).get();
        return response.isAcknowledged();
    }
    
    /**
     * 判断别名是否存在
     * @param client
     * @param aliases
     * @return
     */
    public static boolean isAliasExist(String... aliases){
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        AliasesExistResponse response = indicesAdminClient.prepareAliasesExist(aliases).get();
        return response.isExists();
    }
    
    /**
     * 获取别名
     * @param client
     * @param aliases
     */
    public static void getAliasIndex(Client client, String... aliases){
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        GetAliasesResponse response = indicesAdminClient.prepareGetAliases(aliases).get();
        ImmutableOpenMap<String, List<AliasMetaData>> aliasesMap = response.getAliases();
        UnmodifiableIterator<String> iterator = (UnmodifiableIterator<String>) aliasesMap.keysIt();
        while(iterator.hasNext()){
            String key = iterator.next();
            List<AliasMetaData> aliasMetaDataList = aliasesMap.get(key);
            for(AliasMetaData aliasMetaData : aliasMetaDataList){
            	System.out.println("--------- getAliasIndex {}"+ aliasMetaData.getAlias());
            }
        }
    }
    
    /**
     * 删除别名
     * 删除别名API允许我们删除指定索引的别名，如果索引没有该别名，则会报错
     * @param client
     * @param index
     * @param aliases
     * @return
     */
    public static boolean deleteAliasIndex(Client client, String index, String... aliases){
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        IndicesAliasesResponse response = indicesAdminClient.prepareAliases().removeAlias(index, aliases).get();
        return response.isAcknowledged();
    }
	
	/**
	 * 通过名称删除指定索引
	 * @param indexName
	 */
	public static void deleteIndexByIndexName(String indexName){
		try {
	        IndicesAdminClient indicesAdminClient = client.admin().indices();
	        DeleteIndexResponse response = indicesAdminClient.prepareDelete(indexName==null?DEFAULT_INDEX_NAME:indexName).execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试查询数据
	 * @param indexName
	 * @param indexType
	 */
	public static void testQuery(String indexName, String indexType, String txt) {
		SearchResponse searchResponse = client
			.prepareSearch(indexName)
			.setTypes(indexType)
//			.setQuery(QueryBuilders.matchAllQuery())  // 查询所有
//			.setQuery(QueryBuilders.matchQuery("issueContent", txt)) //执行全文查询标准查询，包括模糊匹配
			.setQuery(QueryBuilders.multiMatchQuery(txt, "issueContent", "petitionTitle")) //matchQuery的多字段版本
//			.setQuery(QueryBuilders.termQuery("issueContent", "贪污腐败"))  //使用精确的术语在指定字段中查找文档。
//			.setQuery(QueryBuilders.termsQuery("issueContent", "贪污腐败","大气污染"))  //在termQuery基础上指定多个查询
//			.setQuery(QueryBuilders.commonTermsQuery("issueContent", "检举贪污腐败")) //更为专业的查询，更倾向于偏僻词。
//			.setQuery(QueryBuilders.rangeQuery("petitionDate")   //在指定范围内查询（日期、数字或字符串）
////				.from("2016-01-01 00:00:00")                            //范围从
////				.to("2017-01-01 00:00:00")                             //范围到
////				.includeLower(true)                 //是否包含from的值 （true相当于>=，false相当于>）
////				.includeUpper(false)              //是否包含to的值 （true相当于<=，false相当于<）\
//				//或者
//				.gte("2016-01-01")
//				.lt("2017-01-01")
//				.format("yyyy-MM-dd")    //指定了输入的日期格式，否则必须和字段定义的格式一致
//					) 
//			.setQuery(QueryBuilders.existsQuery("issueContent")) // 查找指定字段中是否包含任何非空值
//			.setQuery(QueryBuilders.prefixQuery("issueContent","反映"))  //查找包含指定前缀的术语的字段的文档。
//			.setQuery(QueryBuilders.wildcardQuery("issueContent", "反映?贪污腐败*"))  //找到文件所在的字段中指定包含指定的条件匹配模式，在模式支持单个字符的通配符（？）多字符通配符（*）
//			.setQuery(QueryBuilders.regexpQuery("issueContent", "正则表达式"))  //查找指定字段中包含与指定正则表达式相匹配的项的文档。
//			.setQuery(QueryBuilders.fuzzyQuery("issueContent", "检举贪污腐败"))  //没搞懂
//			.setQuery(QueryBuilders.typeQuery("索引类型名称"))  //通过索引类型查找
//			.setQuery(QueryBuilders.idsQuery("索引类型1", "索引类型2").addIds("id1", "id2", "id3"))  //查询指定的索引类型中的指定的id们
//			.setQuery(QueryBuilders.idsQuery().addIds("id1", "id2", "id3"))  //查询指定的id们，前面已经指定索引类型了
			
//			.setQuery(QueryBuilders.simpleQueryStringQuery("+kimchy -elasticsearch")) //支持简明的Lucene的查询语法，在一个查询语句中允许指定AND|OR|NOT条件和多字段搜索。只适用于专家用户。
			
//			.setQuery(QueryBuilders.matchQuery("name","tom").operator(Operator.AND)) //根据tom分词查询name,默认or
//			.setQuery(QueryBuilders.queryStringQuery("贪污")) //指定查询的字段
//			.setQuery(QueryBuilders.queryString("name:to* AND age:[0 TO 19]")) //根据条件查询,支持通配符大于等于0小于等于19
//			.addHighlightedField("issueContent") //设置高亮字段
			
			//5以后不使用一下方式
//			.addHighlightedField("issueContent") //设置高亮字段
//			.addHighlightedField("petitionTitle") //设置高亮字段
//            .setHighlighterPreTags("<font color='red'>")  //高亮字段前缀
//            .setHighlighterPostTags("</font>")  //高亮字段后缀
            /*
             * 设置查询类型
             * QUERY_THEN_FETCH   向索引的所有分片（shard）都发出查询请求，各分片返回的时候把元素文档（document）和计算后的排名信息一起返回。这种搜索方式是最快的。因为相比下面的几种搜索方式，这种查询方法只需要去shard查询一次。但是各个shard返回的结果的数量之和可能是用户要求的size的n倍。
             * QUERY_AND_FETCH(默认方式) 大概分两个步骤，第一步，先向所有的shard发出请求，各分片只返回排序和排名相关的信息（注意，不包括文档document)，然后按照各分片返回的分数进行重新排序和排名，取前size个文档。然后进行第二步，去相关的shard取document。这种方式返回的document与用户要求的size是相等的。   
             * DFS_QUERY_AND_FETCH  这种方式比QUERY_THEN_FETCH多了一个初始化散发(initial scatter)步骤，有这一步，据说可以更精确控制搜索打分和排名。
             * DFS_QUERY_THEN_FETCH 比QUERY_AND_FETCH多了一个初始化散发(initial scatter)步骤。
             */
			.setSearchType(SearchType.QUERY_THEN_FETCH)
			.setFrom(0)
			.setSize(10)// 分页
//				.addSort("petitionTypeName", SortOrder.DESC)// 排序
//				.get();
			.execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();//匹配到的数据总数
		System.out.println(total);
		SearchHit[] searchHits = hits.hits(); //获取匹配到的数据的集合
		int i = 1;
		for (SearchHit s : searchHits) {
//			System.out.println(s.getSourceAsString()); //一整行数据的string
			// 获取定义好的的高亮字段map集
			Map<String, HighlightField> highlightFieldMap = s.highlightFields();
			// 取得定义的高亮标签
			// 从高亮字段map集中取得指定高亮字段
			HighlightField highlightField2 = highlightFieldMap.get("petitionTitle");
			String hightLigntValue = i+"----";
			if(highlightField2!=null){
				Text[] highlighterPTagsFields = highlightField2.fragments();
				// 为title串值增加自定义的高亮标签
				String title = "";
				for (Text text : highlighterPTagsFields) {
					title += text;
				}
				hightLigntValue += title;
			}
			// 从高亮字段map集中取得指定高亮字段
			HighlightField highlightField1 = highlightFieldMap.get("issueContent");
			if(highlightField1!=null){
				Text[] highlighterPTagsFields = highlightField1.fragments();
				// 为title串值增加自定义的高亮标签
				String title = "";
				for (Text text : highlighterPTagsFields) {
					title += text;
				}
				hightLigntValue += "--------------"+title;
			}
			System.out.println(hightLigntValue);
			i++;
		}
	}  
	
	
	
	/**
	 * 测试查询数据
	 * @param indexName
	 * @param indexType
	 */
	public static void testPreQuery(String indexName, String indexType, String txt) {
		SearchResponse searchResponse = client
				.prepareSearch(indexName)
				.setTypes(indexType)
				.setQuery(QueryBuilders.prefixQuery("suggest",txt))  //查找包含指定前缀的术语的字段的文档。
//				.addHighlightedField("suggest") //设置高亮字段
//				.setHighlighterPreTags("<font color='red'>")  //高亮字段前缀
//				.setHighlighterPostTags("</font>")  //高亮字段后缀
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.setFrom(0)
				.setSize(10)// 分页
//				.addSort("petitionTypeName", SortOrder.DESC)// 排序
//				.get();
				.execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();//匹配到的数据总数
		System.out.println(total);
		SearchHit[] searchHits = hits.hits(); //获取匹配到的数据的集合
		for (SearchHit s : searchHits) {
//			System.out.println(s.getSourceAsString()); //一整行数据的string
			// 获取定义好的的高亮字段map集
			Map<String, HighlightField> highlightFieldMap = s.highlightFields();
			// 从高亮字段map集中取得指定高亮字段
			HighlightField highlightField = highlightFieldMap.get("suggest");
			if(highlightField!=null){
				Text[] highlighterPTagsFields = highlightField.fragments();
				// 为title串值增加自定义的高亮标签
				String title = "";
				for (Text text : highlighterPTagsFields) {
					title += text;
				}
				System.out.println(/*s.getSource().get("petitionTitle")+*/"-----"+title);
			}
			// 取得定义的高亮标签
		}
	}  
	
	/**
	 * 测试复合查询
	 * @param indexName
	 * @param indexType
	 */
	public static void testCompoundQuery(String indexName, String indexType){
		SearchResponse searchResponse = client
				.prepareSearch(indexName)
				.setTypes(indexType)
//				.setQuery(QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("issueContent", "贪污腐败")).boost(2.0f))
				.setQuery(                        //多条件复合查询
					QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("issueContent", "检举贪污腐败"))
					.must(QueryBuilders.rangeQuery("petitionDate")
						.gte("2016-01-01 00:00:00")
				  		.lt("2017-01-01 00:00:00")
					)
				)
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(0)
				.setSize(20)// 分页
				// .addSort("petitionTypeName", SortOrder.DESC)// 排序
				.execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();//匹配到的数据总数
		System.out.println(total);
		SearchHit[] searchHits = hits.hits(); //获取匹配到的数据的集合
		for (SearchHit s : searchHits) {
			System.out.println(s.getSourceAsString()); //一整行数据的string
		}
	}
	
	/**
	 * 测试聚合的使用
	 * @param indexName
	 * @param indexType
	 */
	public static void testAggregation(String indexName, String indexType){
		SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(indexType);
		
//		testDateHistogramAggregation(requestBuilder);
//		testSignificantTermsAggregation(requestBuilder);
		testTermsAggregation(requestBuilder);
//		testMultiTermsAggregation(requestBuilder);
		
	}
	
	/**
	 * 单个字段分组统计
	 * @param requestBuilder
	 */
	private static void testTermsAggregation(SearchRequestBuilder requestBuilder){
		SearchResponse sr = requestBuilder
			.addAggregation(
				AggregationBuilders.terms("petitionTypeNames")// 命名
						.field("issueContent")
						.order(Terms.Order.count(false)) // 排序 按数量排序 true为正序
//					 	.order(Terms.Order.term(true)) //排序 按数量排序 true为正序
				)
				.get();
		
		Terms genders = sr.getAggregations().get("petitionTypeNames");
		// For each entry
		for (Terms.Bucket entry : genders.getBuckets()) {
			System.out.println("key:" + entry.getKeyAsString() + "-----" + "count:" + entry.getDocCount());
		}
	}
	
	/**
	 * 多个字段分组统计
	 */
	private static void testMultiTermsAggregation(SearchRequestBuilder requestBuilder){
		SearchResponse sr = requestBuilder.addAggregation(
				AggregationBuilders.terms("petitionTypeNames")// 命名
						.field("petitionTypeName")
						.order(Terms.Order.count(false)) // 排序 按数量排序 true为正序
//					 	.order(Terms.Order.term(true)) //排序 按数量排序 true为正序
						.subAggregation(        //子分组聚合
								AggregationBuilders.terms("petitionClassNames")// 命名
									.field("petitionClassName"))
				).get();
		Terms petitionTypeNames = sr.getAggregations().get("petitionTypeNames");
		// For each entry
		for (Terms.Bucket entry : petitionTypeNames.getBuckets()) {
			System.out.println("信访方式:" + entry.getKeyAsString() + "-----" + "文档数量:" + entry.getDocCount());
			Terms petitionClassNames = entry.getAggregations().get("petitionClassNames");
			for (Terms.Bucket entry1 : petitionClassNames.getBuckets()) {
				System.out.println("信访方式:" + entry.getKeyAsString() + ",信访类别："+entry1.getKeyAsString()+"-----" + "文档数量:" + entry1.getDocCount());
			}
			System.out.println("---------------------------------------------");
		}
	}
	
	private static void testSignificantTermsAggregation(SearchRequestBuilder requestBuilder){
		SearchResponse sr = requestBuilder
				.setQuery(QueryBuilders.termQuery("petitionTypeName", "来信"))
				.addAggregation(
						AggregationBuilders.significantTerms(
								"significant_countries").field("issueContent"))
				.get();
		SignificantTerms agg = sr.getAggregations().get("significant_countries");
		System.out.println(sr.getHits().getTotalHits());
		// For each entry
		for (SignificantTerms.Bucket entry : agg.getBuckets()) {
		    entry.getKey();      // Term
		    entry.getDocCount(); // Doc count
		    System.out.println(entry.getKey() + "----------------" + entry.getDocCount());
		}
	}
	
	
	/**
	 * 日期柱状图聚合
	 * 可以通过日期间隔，统计每一时间段的文档数量
	 * @param requestBuilder
	 */
	private static void testDateHistogramAggregation(SearchRequestBuilder requestBuilder){
		SearchResponse sr = requestBuilder
//				.setQuery(QueryBuilders.rangeQuery("petitionDate").gte("2010-01-01").format("yyyy-MM-dd"))
				.addAggregation(AggregationBuilders
		                .dateHistogram("agg")  //命名
		                .field("petitionDate")  //字段
		                .dateHistogramInterval(DateHistogramInterval.YEAR) //按年间隔
	                )
			.execute().actionGet();
			Histogram agg = sr.getAggregations().get("agg");
			for (Histogram.Bucket entry : agg.getBuckets()) {
			    DateTime key = (DateTime) entry.getKey();    // Key
			    String keyAsString = entry.getKeyAsString(); // Key as String
			    long docCount = entry.getDocCount();         // Doc count
			    System.out.println("key ["+keyAsString+"], date ["+key.getYear()+"], doc_count ["+docCount+"]");
//			    logger.info("key [{}], date [{}], doc_count [{}]", keyAsString, key.getYear(), docCount);
			}
	}
	
	/**
	 * 关闭索引
	 * 关闭索引API允许我们关闭不使用的索引，进而释放节点和集群的资源，如cpu时钟周期和内存。
	 * @param client
	 * @param index
	 * @return
	 */
	public static boolean closeIndex(Client client, String index) {
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		CloseIndexResponse response = indicesAdminClient.prepareClose(index)
				.get();
		return response.isAcknowledged();
	}
	
	/**
	 * 打开索引
	 * 打开索引API允许我们打开我们之前使用关闭索引API
	 * @param client
	 * @param index
	 * @return
	 */
	public static boolean openIndex(Client client, String index) {
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		OpenIndexResponse response = indicesAdminClient.prepareOpen(index)
				.get();
		return response.isAcknowledged();
	}
	
	/**
	 * 
	 * @param client
	 * @param size
	 */
	public static void deleteByTerm(Client client , int size) {
		SearchResponse response = client.prepareSearch(DEFAULT_INDEX_NAME)
				.setTypes(DEFAULT_INDEX_TYPE)
//				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchAllQuery())
//				.setQuery(QueryBuilders.())
//				.setQuery("贪污")
				.setFrom(0).setSize(size)
				.setExplain(true).execute().actionGet();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (SearchHit hit : response.getHits()) {
			String id = hit.getId();
			bulkRequest.add(client.prepareDelete(DEFAULT_INDEX_NAME, DEFAULT_INDEX_TYPE, id)
					.request());
		}
		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
			for (BulkItemResponse item : bulkResponse.getItems()) {
				System.out.println(item.getFailureMessage());
			}
		} else {
			System.out.println("delete ok");
		}
	}
	
	
	/**
	 * 导入数据到es
	 * @param client
	 * @param list
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static void dataImport2ES(List<DataVO> list) throws JsonGenerationException, JsonMappingException, IOException{
		BulkRequestBuilder bulkRequest = client.prepareBulk();
//		int i = 1;
		for (int i = 0; i < list.size(); i++) {
			DataVO data = list.get(i);
			// 使用map保存
			// 使用json字符串保存
			ObjectMapperWrap mapper = new ObjectMapperWrap();
			String source = mapper.writeValueAsString(data, DATE_FORMAT);
			bulkRequest.add(client.prepareIndex(DEFAULT_INDEX_NAME, DEFAULT_INDEX_TYPE,
					data.getOid()).setSource(source));
			if (i % 10000 == 0) {
				bulkRequest.execute().actionGet();
			}
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			for (BulkItemResponse item : bulkResponse.getItems()) {
				System.out.println(item.getFailureMessage());
			}
		}
	}
	
	/**
	 * 删除指定id的文档
	 * @param client
	 * @param indexName
	 * @param indexType
	 * @param id
	 */
	public void deleteDocById(TransportClient client, String indexName,
			String indexType, String id) {
		// 删除指定id的文档
		DeleteResponse deleteResponse = client.prepareDelete(indexName,
				indexType, id).get();
		System.out.println(deleteResponse.getVersion());
	}
	
	/**
	 * 设置索引映射
	 * @param client
	 */
	public static void createMapping(String indexName, String indexType) {
		PutMappingRequest mapping = Requests.putMappingRequest(indexName)
				.type(indexType).source(getMapping());
		client.admin().indices().putMapping(mapping).actionGet();
		//或者
//		IndicesAdminClient indicesAdminClient = client.admin().indices();
//        PutMappingResponse response = indicesAdminClient.preparePutMapping(indexName).setType(indexType).setSource(getMapping(client)).get();
	}
	
	/** 
	* 搜索建议，自动补全搜索结结果 
	* @param indices 索引库名称 
	* @param prefix 搜索前缀词 
	* @return 建议列表 
	*/ 
	public static List<String> getCompletionSuggest(String indices, String prefix) {
		if(indices==null) indices = DEFAULT_INDEX_NAME;
//		CompletionSuggestionBuilder suggestionsBuilder = new CompletionSuggestionBuilder(
//				"complete");
//		suggestionsBuilder.text(prefix);
//		suggestionsBuilder.field("suggest");
////		suggestionsBuilder.analyzer("pinyin");
//		suggestionsBuilder.size(10);
		
		CompletionSuggestionBuilder suggestionsBuilder = SuggestBuilders
				.completionSuggestion("suggest").text(prefix);
		
		SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion(
				"complete", suggestionsBuilder);
		SearchResponse response = client.prepareSearch(indices).suggest(suggestBuilder).get();
		
		
		List<? extends Entry<? extends Option>> list = response.getSuggest().getSuggestion("complete").getEntries();
		if (list == null) {
			return null;
		} else {
			List<String> suggests = new ArrayList<String>();
			for (Entry<? extends Option> e : list) {
				for (Option option : e) {
					suggests.add(option.getText().toString());
					System.out.println(option.getText().toString());
				}
			}
			return suggests;
		}
	}
	
	/**
     * 更新设置
     * @param client
     * @param index
     * @param settings    比如：更改副本数为2（修改分片个数会报错）: Settings.builder().put("index.number_of_replicas", 2).build()
     * @return
     */
    public static boolean updateSettingsIndex(TransportClient client, String index, Settings settings){
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        UpdateSettingsResponse response = indicesAdminClient.prepareUpdateSettings(index).setSettings(settings).get();
        return response.isAcknowledged();
    }
    
    /**
     * 索引统计状态
     * 
     * @param client
     * @param index
     */
    public static void indexStats(String index) {
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        IndicesStatsResponse response = indicesAdminClient.prepareStats(index).all().get();
        ShardStats[] shardStatsArray = response.getShards();
        //碎皮状态
        for(ShardStats shardStats : shardStatsArray){
        	System.out.println("shardStats {}  "+shardStats.toString());
        }
        //索引状态
        Map<String, IndexStats> indexStatsMap = response.getIndices();
        for(String key : indexStatsMap.keySet()){
            System.out.println("indexStats {}  "+ indexStatsMap.get(key));
        }
        //公共状态
        CommonStats commonStats = response.getTotal();
        System.out.println("total commonStats {}  "+commonStats.toString());
        commonStats = response.getPrimaries();
        System.out.println("primaries commonStats {}  "+ commonStats.toString());
    }
    
    /**
     * 转换为全拼
     * @param text
     * @return
     */
	public static String toFullPinyin(String text){
		try {
			IndicesAdminClient indicesAdminClient = ESUtils.getClient().admin().indices();
			AnalyzeRequestBuilder request = indicesAdminClient.prepareAnalyze(text);
			request.setIndex("petitioninfo");
//			request.setTokenizer("first_py_letter");
			request.setAnalyzer("full_pinyin_letter_analyzer");
			// Analyzer（分析器）、Tokenizer（分词器）
			List<AnalyzeToken> listAnalysis = request.execute().actionGet().getTokens();
			String allPinyin = "";
			for(AnalyzeToken token : listAnalysis){
				allPinyin+=token.getTerm();
			}
			return allPinyin;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取数据库数据源
	 * @return
	 */
	public static synchronized DataSource getDataSource(){
		if(DB_DATA_SOURCE==null){
//			DB_DATA_SOURCE = new DriverManagerDataSource();
//			DB_DATA_SOURCE.setDriverClassName(DB_JDBC_DRIVER);
//			DB_DATA_SOURCE.setUrl(DB_JDBC_URL);
//			DB_DATA_SOURCE.setUsername(DB_JDBC_USERNAME);
//			DB_DATA_SOURCE.setPassword(DB_JDBC_PASSWORD);
			DB_DATA_SOURCE = new com.jolbox.bonecp.BoneCPDataSource();
			((BoneCPDataSource)DB_DATA_SOURCE).setDriverClass(DB_JDBC_DRIVER);
			((BoneCPDataSource)DB_DATA_SOURCE).setJdbcUrl(DB_JDBC_URL);
			((BoneCPDataSource)DB_DATA_SOURCE).setUsername(DB_JDBC_USERNAME);
			((BoneCPDataSource)DB_DATA_SOURCE).setPassword(DB_JDBC_PASSWORD);
		}
	    return DB_DATA_SOURCE;
	}
	
	
}
