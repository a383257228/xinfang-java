package com.sinosoft.xf.util.encrypt;



import java.io.File;
import java.util.UUID;

public class ZipCipherUtil {
	/**
	 * 对目录srcFile下的�?��文件目录进行先压缩后加密,然后保存为destfile
	 * 
	 * @param srcFile
	 *            要操作的文件或文件夹
	 * @param destfile
	 *            压缩加密后存放的文件
	 * @param keyfile
	 *            密钥
	 */
	public void encryptZip(String srcFile, String destfile, String keyStr)
			throws Exception {
		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		// 先压缩文�?
		new ZipUtil().zip(srcFile, temp.getAbsolutePath());
		// 对文件加�?
		new CipherUtil().encrypt(temp.getAbsolutePath(), destfile, keyStr);
		temp.delete();
	}

	/**
	 * 对文件srcfile进行先解密后解压�?然后解压缩到目录destfile�?
	 * 
	 * @param srcfile
	 *            要解密和解压缩的文件�?
	 * @param destfile
	 *            解压缩后的目�?
	 * @param publicKey
	 *            密钥
	 */
	public void decryptUnzip(String srcfile, String destfile, String keyStr)
			throws Exception {
		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		// 先对文件解密
		new CipherUtil().decrypt(srcfile, temp.getAbsolutePath(), keyStr);
		// 解压�?
		new ZipUtil().unZip(temp.getAbsolutePath(), destfile);
		temp.delete();
	}

	public static void main(String[] args) throws Exception {
		long l1 = System.currentTimeMillis();	
		//解密	
//		new ZipCipherUtil().decryptUnzip("d:\\1.zip", "D:\\2", "");
		new ZipCipherUtil().encryptZip("D:\\wldr", "d:\\wldr.zip", "Mos@1#520");
		long l2 = System.currentTimeMillis();
		System.out.println((l2 - l1) + "毫秒.");
	}
}
