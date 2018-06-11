package com.zltel.broadcast.um.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.pagehelper.util.StringUtil;

/**
 * file工具类
 * @author 张毅
 *
 */
public class FileUtil {
	private FileUtil() { }
	
	/**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFile(String filePath) {
        return StringUtil.isEmpty(filePath) ? null : new File(filePath);
    }
    
    /**
     * 得到文件名后缀
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
    	return StringUtil.isEmpty(fileName) ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    
    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFile(filePath));
    }
    
    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }
	
    /**
     * 目录是否存在
     * @param path
     * @return
     */
	public static boolean idDir (String path) {
		return isDir(getFile(path));
	}
	
	/**
	 * 目录是否存在
	 * @param file
	 * @return
	 */
	public static boolean isDir (File file) {
		return isFileExists(file) && file.isDirectory();
	}
	
	/**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFile(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
    
    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFile(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(String filePath) {
        return createFileByDeleteOldFile(getFile(filePath));
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) return false;
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        // 创建目录失败返回false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 保存文件到本地
     * @param is 要保存的文件流
     * @param path 文件所在的目录
     * @param fileName 文件名
     */
    public static boolean writeFile (InputStream is, String path, String fileName) throws Exception {
    	if(createOrExistsDir(path)) {	//验证目录
    		File saveFile = new File(path + File.separator + fileName);
    		if (createOrExistsFile(saveFile)) {
    	        FileOutputStream os = new FileOutputStream(saveFile);	//写入到此
    	        
    	        byte[] buff = new byte[1024];
    	        int i = is.read(buff);
    			while (i != -1) {
    				os.write(buff, 0, i);
    				os.flush();
    				i = is.read(buff);
    			}
    			closeIO(is, os);
    		}
    		return false;
    	}
    	return false;
    }
    
    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
