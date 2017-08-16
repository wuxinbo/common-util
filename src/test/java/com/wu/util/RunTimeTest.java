package com.wu.util;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

/**
 * Created by wuxinbo on 12/22/16.
 *
 * @author wuxinbo
 * @version 1.0
 */
public class RunTimeTest {
    private ThreadLocal<String> test =new ThreadLocal<String>();
    @Test
    public void RuntimeTest() throws IOException, InterruptedException {
        System.out.println(test);
//        Process runtime =Runtime.getRuntime().exec(new String[]{"/bin/zsh","ls"});
        ProcessBuilder processBuilder =new ProcessBuilder();
        ProcessBuilder ls = processBuilder.command("gedit","/home/wuxinbo/test.txt");
//        ls.redirectOutput(new File("/home/wuxinbo/test.txt"));
        ls.redirectErrorStream(true);
        Process start = ls.start();
//        System.out.println("maxmemory is "+start./1000/1000);;
        String line =null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(start.getInputStream()));
        while ((line=reader.readLine())!=null){
            System.out.println(line);

        }
        start.waitFor();
//        Thread.sleep(7000);
        System.out.println("exit value is "+start.exitValue());

//        System.out.println("classpath is "+ManagementFactory.getRuntimeMXBean().getClassPath());
        System.out.println("pid is "+ManagementFactory.getRuntimeMXBean().getName());
//        System.out.println("name is "+ManagementFactory.get);

    }

}
