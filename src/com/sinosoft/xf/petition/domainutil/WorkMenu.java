package com.sinosoft.xf.petition.domainutil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WorkMenu implements Comparable<WorkMenu>,  Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private int workNum;
	private String menuName;
	private Integer menuOrder;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWorkNum() {
		return workNum;
	}
	public void setWorkNum(int workNum) {
		this.workNum = workNum;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	private WorkMenu(){}
	static Map<String,String> map = new HashMap<String,String>();
	public static WorkMenu createWorkMenu(String title) throws Exception{
		WorkMenu menu = new WorkMenu();
		menu.setTitle(title);
		menu.setWorkNum(0);
		if(map.isEmpty()){
			Document document = new SAXReader().read(WorkMenu.class.getResource("menu.xml"));
			Element root = document.getRootElement();
			List<Element> list = root.selectNodes("child");
			for(Element element : list) {
				map.put(element.attributeValue("ename"), element.attributeValue("cname"));
			}
		}
		menu.setMenuName(map.get(title));
		return menu;
	}
	public static void main(String[] args){
		int size = 5;
		int listSize = size%2;
		System.out.println(listSize);
	}
	@Override
	public int compareTo(WorkMenu o) {
		int temp=this.getMenuOrder()-o.getMenuOrder();
		return temp;
	}
}
