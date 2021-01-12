package org.kechuang.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Rambo
 * @date 2020/2/4
 * @description
 */
public class FileUploadUtil {

	/**
	 *
	 * @Description: 存入文件到指定地址
	 * @param multipartFile 传入的文件
	 * @param filePath 存储的地址
	 * @return
	 */
	public static boolean saveFileToPath(MultipartFile multipartFile,String filePath){
		BufferedOutputStream bos=null;
		try
		{
			bos = new BufferedOutputStream(new FileOutputStream(filePath));
			byte[] bytes = multipartFile.getBytes();
			bos.write(bytes);
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally
		{
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
		}
		return true;
	}
}
