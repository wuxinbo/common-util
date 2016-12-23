package com.wu.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	private static Logger log=Logger.getLogger(FileUtil.class);
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
	public static void io(InputStream in, OutputStream out, int bufferSize) throws IOException {
		if (bufferSize == -1) {
			bufferSize = DEFAULT_BUFFER_SIZE;
		}

		byte[] buffer = new byte[bufferSize];
		int amount;

		while ((amount = in.read(buffer)) >= 0) {
			out.write(buffer, 0, amount);
		}
	}

	/**
	 * 目录是否存在
	 * @param path 路径
	 * @return 如果存在返回true，否则发挥false。
     */
	public static boolean fileIsExist(String path){
		if (new File(path).exists()){
			return true;
		}
		return false;
	}
//	public static
	/**
	 * 文件下载
	 * @param url
	 * @param outputPath
	 */
	public static void DownloadFile(String url,String outputPath) throws IOException {
		HttpURLConnection conn= (HttpURLConnection)new URL(url).openConnection();
		File downloadFile=new File(outputPath);
		if (!downloadFile.exists()){ //目录不存在就新建目录
			downloadFile.mkdirs();
		}
		String FileName= url.substring(url.lastIndexOf("/")+1,url.length());
		log.info("需要下载的文件名："+FileName);
		if (outputPath.endsWith(File.separator)){
			outputPath+=FileName;
		}
		log.info("输出的文件路径为："+outputPath);
		io(conn.getInputStream(),new FileOutputStream(outputPath),2048);
	}
}
