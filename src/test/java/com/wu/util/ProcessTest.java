package com.wu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(ExeCmd("CMD /c nslookup www.baidu.com"));
	}
	
	/**
	 * ִ��Cmd���
	 * @param cmd Ҫִ�е�cmd���
	 * @return ����ִ�к�Ľ��
	 */
	public static String ExeCmd(String cmd){
		StringBuffer str=new StringBuffer("");
		String line =null;
		try {
			Process p=Runtime.getRuntime().exec(cmd);
			BufferedReader br =new BufferedReader(new InputStreamReader(p.getInputStream()));
			while((line=br.readLine())!=null){
				str.append(line+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
		
	}

}
