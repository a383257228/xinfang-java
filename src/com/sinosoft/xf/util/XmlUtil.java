/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.xf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sinosoft.xf.constants.Constants;



/**
 * Title:
 * Description:
 * @author WEIQIAN
 * @date 2006-3-15
 */

public class XmlUtil {
	
	private static DocumentBuilderFactory factory ;
	
	private static DocumentBuilder builder;
	
	private static Document doc;
	
	private static XPathFactory xpfactory ;
	
	private static XPath path ;
	
	private static Transformer transformer  ;
	
	private static TransformerFactory tfactory;
	/**
	 * 初始化builder,得到解析后的Document对象
	 * @param File  传入的xml文件
	*/
	private static void initDocument(){
		try {
			if(factory == null){
				factory = DocumentBuilderFactory.newInstance();
			}
			if(builder == null)
				builder = factory.newDocumentBuilder();
			doc = builder.newDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化builder,得到解析后的Document对象
	 * @param File  传入的xml文件
	*/
	private static void initDocument(File f){
		try {
//			if(factory == null){
				factory = DocumentBuilderFactory.newInstance();
//			}if(builder == null)
				builder = factory.newDocumentBuilder();
			doc = builder.parse(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化builder,得到解析后的Document对象
	 * @param File  传入的xml文件
	*/
	private static void initDocument(InputStream is){
		try {
//			if(factory == null){
				factory = DocumentBuilderFactory.newInstance();
//			}if(builder == null)
				builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
			System.out.println("factory"+factory.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化XPath
	*/
	private static void initXPath(){
//		if(xpfactory == null)
			xpfactory = XPathFactory.newInstance();
//		if(path == null)
			path = xpfactory.newXPath();
	}
	
	/**
	 * 初始化Transformer
	 */
	private static void initTransformer(){
//		if(tfactory == null)
			tfactory = TransformerFactory.newInstance();
//		if(transformer == null)
			try {
				transformer = tfactory.newTransformer();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	/**
	 * @param String XPath字符串
	 * @return String 获得String类型的值
	*/
	public static String getString(String XPathStr,InputStream f){
		
		initDocument(f);
		initXPath();
		
		try {
			return path.evaluate(XPathStr, doc);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param String XPath字符串
	 * @return Node 获得Node类型的值
	*/
	public static Node getNode(String XPathStr,InputStream f){
		
		initDocument(f);
		initXPath();
	
		try {
		return (Node) path.evaluate(XPathStr, doc ,XPathConstants.NODE);
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		}
	
	}
	
	/**
	 * @param String XPath字符串
	 * @return NodeList 获得NodeList类型的值
	*/
	public static NodeList getNodeList(String XPathStr,InputStream f){
		
		initDocument(f);
		initXPath();
	
		try {
		return (NodeList) path.evaluate(XPathStr, doc ,XPathConstants.NODESET);
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		}
	
	}
	
	/**
	 * @param String XPath字符串
	 * @return int 获得int类型的值
	*/
	public static int getInt(String XPathStr,InputStream f){
		
		initDocument(f);
		initXPath();
	
		try {
		return ((Number) path.evaluate(XPathStr, doc, XPathConstants.NUMBER)).intValue();
		} catch (Exception e) {
		e.printStackTrace();
		return -1;
		}
	
	}
	
	/**
	 * @param String XPath字符串
	 * @param Object 开始搜索的元素,可以是Node或者NodeList等
	 * @param QName 返回类型,可以是 XPathConstants 中的一些类型
	 * @return Object 跟返回类型对应的对象
	*/
	public static Object getObject(String XPathStr,Object o,QName qname){
		
		initXPath();
		
		try {
			return path.evaluate(XPathStr, o, qname);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param Document 输出的Document
	 * @param File 输出到的文件
	 * @return boolean 成功标志
	*/	
	public static boolean outputDocToFile(Document d,File f){
		
		initTransformer();
		try {
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			transformer.transform(new DOMSource(d),new StreamResult(f));
		} catch (Exception e) {
			e.printStackTrace();
			f.delete();
			return false;
		}
		return true;
	}
	
	/**
	 * @param Document 输出的Document
	 * @param OutputStream 输出到输出流
	 * @return boolean 成功标志
	*/	
	public static boolean outputDocToOS(Document d,OutputStream out){
		
		initTransformer();
		
		try {
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			transformer.transform(new DOMSource(d),new StreamResult(out));
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	/**
	 * xml字符串保存为指定路径的xml文件
	 * @author lijun 2015-4-30
	 * @param xmlStr 要保存的xml字符串
	 * @param filePath 要保存文件的文件夹路径
	 * @param fileName 要保存的文件名
	 */
	public static void xmlStr2File(String xmlStr, String filePath, String fileName){
		try {
			StringReader sr = new StringReader(xmlStr.toString());
			InputSource inputsource = new InputSource(sr);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputsource);
			File file = new File(filePath);
			if(!file.exists()){
				file.mkdirs();
			}
			OutputStream os  = new FileOutputStream(filePath + File.separator + fileName);
			outputDocToOS(doc, os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node n;
		try {
			n = XmlUtil.getNode("/XF_JUBAO/Jubao", new FileInputStream("c:"+File.separator+"xfjubao.xml"));
			System.out.println(XmlUtil.getObject("/XF_JUBAO/Jubao/XF_ID", n, XPathConstants.STRING));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
