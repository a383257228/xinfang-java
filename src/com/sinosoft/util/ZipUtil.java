package com.sinosoft.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

  
public class ZipUtil {  
	/** 
	 * zip压缩文件
	 * @param srcDir 源文件、文件夹路径
	 * @param zippath 压缩后文件路径+文件名
	 */  
	public static void zip(String srcDir, String zippath){
		File file = new File(srcDir);
		if(file.isDirectory()){
			List<File> paths = getFiles(file);
			compressFilesZip(paths, zippath, srcDir);
		}else if(file.isFile()){
			compressFileZip(file, zippath, srcDir);
		}
	}
	
    /** 
     * 递归取到当前目录所有文件
     * @param dir 
     * @return 
     */  
	private static List<File> getFiles(File file){  
		List<File> lstFiles = new ArrayList<File>();
		File [] files = file.listFiles();
		for(File f : files){
			if(f.isDirectory()){
				lstFiles.add(f);  
				lstFiles.addAll(getFiles(f));  
			}else{   
				lstFiles.add(f);  
			}  
		}  
		return lstFiles;  
	}  
	
	/**
	 * 获取文件名（该文件名包含部分路径名）
	 * @param dir
	 * @param file
	 * @return
	 */
	private static String getFilePathName(String dir,File file){  
		String p = file.getAbsolutePath().replace(dir+File.separator, "");  
		p = p.replace("\\", File.separator);
		return p;  
	}  
    
	/**
	 * 创建ZipArchiveOutputStream
	 * @param zipFilePath
	 * @return
	 * @throws IOException
	 */
	private static ZipArchiveOutputStream createZipArchiveOutputStream(String zipFilePath) throws IOException{
		File zipFile = new File(zipFilePath);
		ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(zipFile); 
		zaos.setUseZip64(Zip64Mode.AsNeeded);
		return zaos;
	}
	
    /** 
     * 压缩一批file对象 
     * @param files       需要压缩的文件对象集合
     * @param zipFilePath 压缩后的zip文件路径   ,如"D:/test/aa.zip"; 
     */  
    private static void compressFilesZip(List<File> files,String zipFilePath,String dir) {  
        if(files.isEmpty()) {  
            return ;  
        }  
        ZipArchiveOutputStream zaos = null;  
        try {
        	zaos = createZipArchiveOutputStream(zipFilePath);
            for(File file : files) {
            	//获取文件名，因为压缩的是文件夹，所以其子文件或文件夹要保持原来的路径接口，所以这个方法获取的是部分的文件路径+文件/文件夹名
                String name = getFilePathName(dir,file); 
                //开始创建压缩文件
                compress(zaos, name, file);
            }  
            zaos.finish();  
        }catch(Exception e){  
            throw new RuntimeException(e);  
        }finally {  
            try {  
                if(zaos != null) {  
                    zaos.close();  
                }  
            } catch (IOException e) {  
                throw new RuntimeException(e);  
            }  
        }  
           
    }  
    
    /** 
     * 把文件压缩成zip格式 
     * @param files         需要压缩的文件 
     * @param zipFilePath 压缩后的zip文件路径   ,如"D:/test/aa.zip"; 
     */  
    private static void compressFileZip(File file,String zipFilePath,String srcDir) {  
    	ZipArchiveOutputStream zaos = null;  
    	try {
    		zaos = createZipArchiveOutputStream(zipFilePath);
    		 //开始创建压缩文件
			compress(zaos, file.getName(), file);
    		zaos.finish();  
    	}catch(Exception e){  
    		throw new RuntimeException(e);  
    	}finally {  
    		try {  
    			if(zaos != null) {  
    				zaos.close();  
    			}  
    		} catch (IOException e) {  
    			throw new RuntimeException(e);  
    		}  
    	}  
    }  
    
    /**
     * 压缩文件或文件夹
     * @param zaos
     * @param name
     * @param file
     * @throws Exception
     */
    private static void compress(ZipArchiveOutputStream zaos, String name, File file) throws Exception{
    	 //将每个文件用ZipArchiveEntry封装  
        //再用ZipArchiveOutputStream写到压缩文件中  
    	ZipArchiveEntry zipArchiveEntry  = new ZipArchiveEntry(file,name);  
    	zaos.putArchiveEntry(zipArchiveEntry);
    	 if(file.isDirectory()){
         	if(file.listFiles().length==0){
         		zaos.closeArchiveEntry();
         	}
         }else{
         	InputStream is = null;  
         	try {  
         		is = new BufferedInputStream(new FileInputStream(file));
         		byte[] buffer = new byte[1024];  
         		int len = -1;
         		while((len = is.read(buffer)) != -1) {
         			//把缓冲区的字节写入到ZipArchiveEntry  
         			zaos.write(buffer, 0, len);
         		}
         		zaos.closeArchiveEntry();
         	}catch(Exception e) {
         		throw new RuntimeException(e);  
         	}finally {
         		if(is != null)  
         			is.close();  
         	}  
         }
    }
      
     
    /** 
    * 把zip文件解压到指定的文件夹 
    * @param zipFilePath zip文件路径, 如 "D:/test/aa.zip" 
    * @param saveFileDir 解压后的文件存放路径, 如"D:/test/" () 
    */  
    public static void unzip(String zipFilePath, String saveFileDir) {
        if(!saveFileDir.endsWith("\\") && !saveFileDir.endsWith("/") ){  
            saveFileDir += File.separator;  
        }
        File dir = new File(saveFileDir);  
        if(!dir.exists()){  
            dir.mkdirs();  
        }  
        File file = new File(zipFilePath);  
        if (file.exists()) {  
            InputStream is = null;   
            ZipArchiveInputStream zais = null;  
            try {  
                is = new FileInputStream(file);  
                zais = new ZipArchiveInputStream(is);  
                ArchiveEntry archiveEntry = null;  
                while ((archiveEntry = zais.getNextEntry()) != null) {   
                    // 获取文件名  
                    String entryFileName = archiveEntry.getName();
                    String entryFilePath = saveFileDir + entryFileName; 
                    if (File.separator.equals("/")) {
                    	if(entryFilePath.indexOf("\\") > 0){
                    		entryFilePath = entryFilePath.substring(0,entryFilePath.indexOf("\\"))+"/"+entryFilePath.substring(entryFilePath.indexOf("\\")+1, entryFilePath.length());
                    		entryFilePath = entryFilePath.replace("\\", "/");
                    	}
                    }
                    // 构造解压出来的文件存放路径  
                    OutputStream os = null;  
                    try {  
                        // 把解压出来的文件写到指定路径  
                        File entryFile = new File(entryFilePath);  
                        if(entryFileName.endsWith("/")){  
                            entryFile.mkdirs();  
                        }else{  
                            os = new BufferedOutputStream(new FileOutputStream(  
                                    entryFile));                              
                            byte[] buffer = new byte[1024 ];   
                            int len = -1;   
                            while((len = zais.read(buffer)) != -1) {  
                                os.write(buffer, 0, len);   
                            }  
                        }  
                    } catch (IOException e) {  
                        throw new IOException(e);  
                    } finally {  
                        if (os != null) {  
                            os.flush();  
                            os.close();  
                        }  
                    }  
  
                }   
            } catch (Exception e) {  
                throw new RuntimeException(e);  
            } finally {  
                try {  
                    if (zais != null) {  
                        zais.close();  
                    }  
                    if (is != null) {  
                        is.close();  
                    }  
                } catch (IOException e) {  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
    }  
}  