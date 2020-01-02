package com.tk.sys.util;

import java.io.*;
import java.util.Properties;

/**
 * 配置文件工具类
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class FileUtils {

	/**
	 * 获取密钥
	 */
	public static String getSecretKey(String filepath, String key) {
		InputStream inputStream = FileUtils.class.getClassLoader()
				.getResourceAsStream(filepath);
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}

    /**
     * 将文件转化为字节数组
     * @param file
     * @return
     */
    public static byte[] getBytes(File file){
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

}