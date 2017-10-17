package com.sinosoft.xf.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.sinosoft.xf.constants.Constants;
import com.sinosoftframework.core.utils.app.AppUtils;





/**
 * Title:
 * Description:
 * @author 
 * @date 2011-2-17
 */
public class TJXmlData {

	public static String exportPath = "XmlOutput"+File.separator;
	
	
	//本类中封装的Document对象
	private Document doc;
	
	private static DocumentBuilderFactory factory ;
	
	private static DocumentBuilder builder;
	
	
	public TJXmlData(){
		//初始化factory和builder
		try {
			if(factory == null)
				factory = DocumentBuilderFactory.newInstance();
			if(builder == null)
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
	}

	
	public void add(String al,String size) throws Exception{
		String st = new String();
		st ="<NewDataSet>\n" +
					"<xs:schema id=\"NewDataSet\" xmlns=\"\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:msdata=\"urn:schemas-microsoft-com:xml-msdata\">\n" +
			"    <xs:element name=\"NewDataSet\" msdata:IsDataSet=\"true\" msdata:MainDataTable=\"ds0\" msdata:UseCurrentLocale=\"true\">\n" +
			"      <xs:complexType>\n" +
			"        <xs:choice minOccurs=\"0\" maxOccurs=\"unbounded\">\n" +
			"          <xs:element name=\"ds0\">\n" +
			"            <xs:complexType>\n" +
			"              <xs:sequence>\n" ;
		for(int i = 1 ; i <= Integer.parseInt(size); i++){
			st += "                <xs:element name=\"a"+i+"\" type=\"xs:decimal\" minOccurs=\"0\" />\n";
		}
		st += 
			"                <xs:element name=\"nd\" type=\"xs:decimal\" minOccurs=\"0\" />\n" +
			"                <xs:element name=\"yf\" type=\"xs:decimal\" minOccurs=\"0\" />\n" +
			"                <xs:element name=\"a0\" type=\"xs:decimal\" minOccurs=\"0\" />\n" +
			"              </xs:sequence>\n" +
			"            </xs:complexType>\n" +
			"          </xs:element>\n" +
			"        </xs:choice>\n" +
			"		</xs:complexType>\n" +
			"	</xs:element>" +
			"</xs:schema>"+
			al+"</NewDataSet>";
			InputStream is = new ByteArrayInputStream(st.getBytes(Constants.CHARSET_UTF8));
			doc = builder.parse(is);
	}
	
	public String export(String r,String a,String b){
		StringBuffer pname = new StringBuffer(AppUtils.getAppAbsolutePath()+exportPath);
		StringBuffer fname = new StringBuffer();
		File path = new File(pname.toString());
		if(!path.exists())
			path.mkdirs();
		fname.append("x");
		fname.append(a);
		fname.append(b);
		if(StringUtils.isNotBlank(r))
			fname.append(r);
		else
			fname.append("009");
		fname.append(".xml");
		try {
			OutputStream os  = new FileOutputStream((pname.append(fname)).toString());
			XmlUtil.outputDocToOS(doc, os);
			return pname.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
