package com.sinosoft.xf.dataPredict.domainutil;

import java.io.Serializable;
import java.sql.Timestamp;

public class OptionInfo implements  Serializable{
	private static final long serialVersionUID = 1L;
	private String oid;
	private String newWord;
	private String wordFrequency;//词频
	private String wordPart;//词性
	private String editWord ;//编辑后的新词
	private String dictionaryFlag ;//是否加入词典标识  0 未加入  1 要加入
	private String ignoreFlag ;//是否忽略该词  0未忽略  1要忽略
	private Timestamp createDate ;//创建时间
	
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getNewWord() {
		return newWord;
	}
	public void setNewWord(String newWord) {
		this.newWord = newWord;
	}
	public String getWordFrequency() {
		return wordFrequency;
	}
	public void setWordFrequency(String wordFrequency) {
		this.wordFrequency = wordFrequency;
	}
	public String getWordPart() {
		return wordPart;
	}
	public void setWordPart(String wordPart) {
		this.wordPart = wordPart;
	}
	public String getEditWord() {
		return editWord;
	}
	public void setEditWord(String editWord) {
		this.editWord = editWord;
	}
	public String getDictionaryFlag() {
		return dictionaryFlag;
	}
	public void setDictionaryFlag(String dictionaryFlag) {
		this.dictionaryFlag = dictionaryFlag;
	}
	public String getIgnoreFlag() {
		return ignoreFlag;
	}
	public void setIgnoreFlag(String ignoreFlag) {
		this.ignoreFlag = ignoreFlag;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}
