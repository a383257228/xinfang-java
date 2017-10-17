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
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.sinosoftframework.core.utils.app.AppUtils;

/**
 * Title:文件压缩/解压缩类
 * Description:
 * @author WEIQIAN
 * @date 2006-7-12
 */
public class ZipUtil {
	//服务器绝对路径
	private  static String  RealPath = AppUtils.getAppAbsolutePath();
	//每个应用都相同的根路径 应该是 "web/"
	private String rootPath = RealPath.substring(RealPath.lastIndexOf(File.separator,RealPath.lastIndexOf(File.separator)-1)+1);
	
	private static int bufferSize = 8192;
	
	private ZipOutputStream zos;
	
	private String zipFileName;
	
	public ZipUtil(String fileName){
	try {
		zipFileName = fileName;
		File zipFile = new File(zipFileName);
		if(!zipFile.exists()){
			zipFile.createNewFile();
			
		}
		zos = new ZipOutputStream(new FileOutputStream(zipFile));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	
	public boolean zipFile(String srcfilename){
		byte[] buffer = new byte[bufferSize];
	    // compress the file
	    try {
	      //存相对于"web/"的路径
	      zos.putNextEntry(new ZipEntry(srcfilename.substring(srcfilename.indexOf(rootPath)+rootPath.length())));
	    	//zos.putNextEntry(new ZipEntry(srcfilename));
	      FileInputStream in = new FileInputStream(srcfilename);
	      int length;

	      while ((length = in.read(buffer, 0, bufferSize)) != -1)
	    	  zos.write(buffer, 0, length);

	      in.close();
	      return true;
	    } catch (IOException e) {
	    	e.printStackTrace();
	        System.out.println("Couldn't compress " + srcfilename + ".");
	        return false;
	    }
	}
	
	public boolean finish(){
		try{ 
			zos.close();
			int length;
		    File zipFile =new File(zipFileName);
		    File tempFile = new File(zipFileName+"_temp");
		    byte[] buffer = new byte[bufferSize];
		    FileInputStream in = new FileInputStream( zipFile);
		    FileOutputStream os = new FileOutputStream(tempFile);  
			      while ((length = in.read(buffer, 0, bufferSize)) != -1)
			    	  os.write(buffer, 0, length);
			
			in.close();
			os.close();
			
			zipFile.delete();
			zipFile.createNewFile();
			      
			 in = new FileInputStream( tempFile);
		     os = new FileOutputStream(zipFile); 
		     int time = 0;
	      while ((length = in.read()) != -1){
	    	  if(time<5)
	    	  os.write(127);
	    	  os.write(length);
	    	  time++;
	      }
			
			in.close();
			os.close();
			
			tempFile.delete();
			return true;
		}
	    catch (IOException e) {
	    	e.printStackTrace();
	    	return false;
	    }
	}
	
	public String getZipFileName(){
		return this.zipFileName;
	}
	
	public static boolean saveFromUnzip(String zipFileName){
		ZipFile zf;
		byte[] buffer = new byte[bufferSize];
		int length;
		try {
	      
	    File zipFile =new File(zipFileName);
	    File tempFile = new File(zipFileName+"_temp");
	    FileInputStream in = new FileInputStream( zipFile);
	    FileOutputStream os = new FileOutputStream(tempFile);  
		      while ((length = in.read(buffer, 0, bufferSize)) != -1)
		    	  os.write(buffer, 0, length);
		
		in.close();
		os.close();
		zipFile.delete();
		zipFile.createNewFile();
		      
		 in = new FileInputStream( tempFile);
	     os = new FileOutputStream(zipFile);  
	     int time = 0;
		      while ((length = in.read()) != -1){
		    	  if(time>=10||time<10&&time%2!=0)
		    		  os.write(length);
		    	  time++;
		      }
		      
		in.close();
		os.close();
		tempFile.delete();
		zf = new ZipFile(new File(zipFileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (ZipException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	    // decompress the file
		Enumeration<? extends ZipEntry> en = zf.entries();
		while(en.hasMoreElements()){
			buffer = new byte[bufferSize];
			ZipEntry ze = en.nextElement();
		try {
			String filename = RealPath+ze.getName();
			//如果是linux到windows,路径为"/"分割,转换为"\"
			if(filename.indexOf("/")!=-1&&File.separator.equals("\\"))
				filename = filename.replace("/", "\\");
			//如果是windows到linux,路径为"\"分割,转换为"/"
			else if(filename.indexOf("\\")!=-1&&File.separator.equals("/"))
				filename = filename.replace("\\", "/");
			
	      File f = new File(filename);
	      File path = new File(filename.substring(0, filename.lastIndexOf(File.separator)));
	      if(f.exists()){
	    	  f.delete();
	      }else
	    	  path.mkdirs();
	      f.createNewFile();
	      FileOutputStream out = new FileOutputStream(f);
	      InputStream zis =zf.getInputStream(ze);
	      while ((length = zis.read(buffer, 0, bufferSize)) != -1)
	        out.write(buffer, 0, length);
	      try { 
	    	  out.close();
	    	  zis.close(); 
	      }catch(IOException e){
	      }
	    }
	    catch (IOException e) {
	    	try {
				zf.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    	e.printStackTrace();
	      System.out.println("Couldn't decompress " + zipFileName + ".");
	      return false;
	    }
		}
		try {
			zf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ZipUtil zip = new ZipUtil("d:/1.zip");
//		zip.zipFile("F:"+File.separator+"ZJW"+File.separator+"web"+File.separator+"Attachments"+File.separator+"Uploaded"+File.separator+"00000006042000000123"+File.separator+"20060421_174049_.doc");
//		zip.zipFile("F:"+File.separator+"ZJW"+File.separator+"web"+File.separator+"Attachments"+File.separator+"Uploaded"+File.separator+"00000006042000000123"+File.separator+"20060421_180030_.doc");
//		zip.zipFile("F:"+File.separator+"ZJW"+File.separator+"web"+File.separator+"Attachments"+File.separator+"Uploaded"+File.separator+"00000006042000000123"+File.separator+"20060421_180056_.doc");
//		zip.zipFile("F:"+File.separator+"ZJW"+File.separator+"web"+File.separator+"XmlOutput"+File.separator+"320100to320000at20060517_121811.xml");
//		zip.finish();
		saveFromUnzip("d:/140000to140100at20120304_113203.zip");
	}

}
