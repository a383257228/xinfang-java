package com.sinosoft.xf.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 上传文件操作，包括：
 * 1、分析上传的图片名称，并返回后缀名和原名
 * 2、将上传的文件转换成byte[]类型
 * 3、拷贝文件到指定文件夹内
 * @author  HJM
 * @createDate 2011-9-7
 */
public class FileUtil {
	/**
	 * 默认的文件后缀名称
	 */
	private static String defaultFileExt = "doc";
	
	/**
	 * 文件转换成base64编码的String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFile(File file) throws IOException {
		BASE64Encoder encoder = new BASE64Encoder();
		String result = encoder.encode(file2Byte(file));
		return result;
	}   
	/**
	 * 分析上传的文件名称uploadFileName，并返回后缀名和原名
	 * @param uploadFileName 上传文件的文件名
	 */
	public static Map<String, String> analyzeUploadFileName(String uploadFileName) {
		StringTokenizer st = new StringTokenizer(uploadFileName, ".");
		String[] items = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			items[i++] = st.nextToken();
		}
		String fileExt = items[items.length - 1]; // 后缀名
		String origName;// 文件原名
		//如果没有后缀名,后缀名既是文件原名
		if (items.length == 1) {
			origName = fileExt;
		}else{
			origName = uploadFileName.substring(0, uploadFileName.length() - fileExt.length() - 1); 
		}
		//如果没有后缀名或后缀名太长
		if (items.length == 1 || fileExt.length()>10) {
			fileExt = defaultFileExt;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("fileExt", fileExt);
		map.put("origName", origName);
		return map;
	}
	/**
	 * base64编码的String转换成文件,保存在path位置
	 * @param fileContent
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static File saveFile(String fileContent, String path)throws IOException {
		File file = new File(path);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		FileOutputStream out = new FileOutputStream(file);
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = decoder.decodeBuffer(fileContent);
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e) {
			out.flush();
			out.close();
		}
		return file;
	}
	 /**
	  * 写XML文件到指定目录
	 * @param document  dom4j Document
	 * @param outFile   需要写的文件路径
	 * @param encoding  XML文件编码
	 * @throws Exception
	 */
	public static void writeDocument(Document document, String outFile,String encoding) throws Exception {
           // 读取文件,并设置编码
			FileOutputStream os = new FileOutputStream(outFile);
//           FileWriter fileWriter = new FileWriter(outFile);//linux下可能会乱码
           OutputFormat format = OutputFormat.createPrettyPrint();
           format.setEncoding(encoding);
           // 创建写文件方法
           XMLWriter xmlWriter = new XMLWriter(os, format);
           // 写入文件
           xmlWriter.write(document);
           xmlWriter.flush();
           xmlWriter.close();
	    }
	/**
	 * 文件转化为字节数组
	 * 
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static byte[] file2Byte(File file) throws IOException {
		byte[] ret = null;
		if (file == null) {
			return null;
		}
		FileInputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
		try {
			byte[] b = new byte[4096];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			ret = out.toByteArray();
			out.flush();
			out.close();
		} catch (IOException e) {
			in.close();
			out.flush();
			out.close();
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String file2String(File file) throws Exception {
		FileInputStream fis = null;
		InputStreamReader isr=null;
		BufferedReader br= null;
		StringBuffer sb = new StringBuffer();
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader( fis,"UTF-8");
			br = new BufferedReader(isr);  
			String line = null;  
			while ((line = br.readLine()) != null) {  
				sb.append(line);  
			}  
			fis.close();
			isr.close();
			br.close();  
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		} finally{
			try {
				if(fis!=null){
					fis.close();
				}
				if(isr!=null){
					isr.close();
				}
				if(br!=null){
					br.close(); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 字节转换为文件
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static void byte2File(byte[] buf, String filePath, String fileName) throws IOException {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	   public static void main(String args[]){
		   File file = new File("D:\\1");
		   System.out.println(file.isDirectory());
		   System.out.println(file.isFile());
//		   delAllFile("D:\\1");
	       System.out.println("deleted");
	}


	//删除指定文件夹下所有文件
	public static boolean delAllFile(String delpath) {
		try {
			File file = new File(delpath);
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						System.out.println(delfile.getAbsolutePath() + "删除文件成功");
					} else if (delfile.isDirectory()) {
						delAllFile(delpath + "\\" + filelist[i]);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("deletefile() Exception:" + e.getMessage());
		}
		return true;}
}
