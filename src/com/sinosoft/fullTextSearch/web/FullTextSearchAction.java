package com.sinosoft.fullTextSearch.web;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.sinosoft.fullTextSearch.manager.FullTextSearchManager;
import com.sinosoftframework.core.struts2.CrudActionSupport;
import com.sinosoftframework.core.utils.struts2.Struts2Utils;

@Namespace("/fullTextSearch")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD,location = "full-text-search.action", type = "redirect") })
public class FullTextSearchAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	FullTextSearchManager fullTextSearchManager;
	
	String keyword;
	String searchText;
	String searchField;
	String indexName;
	String indexType;
	
	int from;
	int size;

	/**
	 * 获取下拉框自动补全选项
	 * @throws Exception
	 */
	public void suggest() throws Exception{
		String data = fullTextSearchManager.getSuggest(keyword,indexName,indexType);
		Struts2Utils.getResponse().getWriter().write(data);
	}
	
	/**
	 * 多字段匹配检索
	 * @throws Exception
	 */
	public void search() throws Exception{
		String html = fullTextSearchManager.multiMatchQuery(searchField, searchText, from, size, indexName, indexType);
		Struts2Utils.getResponse().getWriter().write(html);
	}
	
	public void recreateIndexAndImportData() throws Exception{
		fullTextSearchManager.recreateIndexAndImportData();
		Struts2Utils.getResponse().getWriter().write("true");
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}
	
}
