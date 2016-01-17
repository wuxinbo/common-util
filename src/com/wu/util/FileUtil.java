package com.wu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件操作相关类
 * @author wuxinbo
 *
 */
public class FileUtil {
	/**
	 * 总行数.
	 */
	private static int lines =0;
	
	/**
	 * 统计该目录下文件行数
	 * @param dirName 文件名
	 * @param suffix  需要计算的文件后缀名,例如java
	 * @return  获取最后统计的行数
	 * @throws IOException 
	 */
	public static int getLineCounts(String dirName,String suffix) throws IOException{
		File file =new File(dirName);
		File[] files =file.listFiles();
		BufferedReader reader =null;
		for (File file2 : files) {
			if (file2.isDirectory()) { //如果是目录,则进行递归
				getLineCounts(dirName+File.separator+file2.getName(), suffix);
			}else{
				if (file2.getName().endsWith(suffix)) {   //如果匹配
					reader =new BufferedReader(new FileReader(file2)) ;
					while (reader.readLine()!=null) {
						lines++;
					}
				}
			}
			
		}
		return lines;
	}
}
