package com.wu.impl;

import com.wu.Interface.ExeclOpreationIn;
import com.wu.JdbcTest;
import com.wu.model.User;
import com.wu.util.ReflectUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

public class ExcelOpreationImpl implements ExeclOpreationIn {

	public void WriteDataToXls(String path) {
		Workbook book =new XSSFWorkbook();
		Sheet sheet=book.createSheet("用户表");
		Row row =null;
		/**
		 * 从数据库中查出用户记录。
		 */
		ResultSet set =JdbcTest.getSet(JdbcTest.QUERY_USER);
		List<User> list =JdbcTest.getListFromSet(set);
		Field[] fields=ReflectUtil.getFieldFromObject(ReflectUtil.userClassName);
		row =sheet.createRow(0);
		for (int i = 0; i < fields.length; i++) {
			if (fields[i]!=null) {
				row.createCell(i).setCellValue(fields[i].getName());
			}
		}
		for (int i = 1; i < list.size(); i++) {
			row =sheet.createRow(i);
			row.setHeightInPoints(20); //行高20.
			try {
				new ItratorFieldImpl().ItratorObject(list.get(i-1), fields, row);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(path);
			book.write(fos);
			book.close();
		} catch (IOException e) {
			e.printStackTrace();
//			log.error("写入文件失败！");
		}
		
	}
	
	public void setExcelHeader(Field[] fields, List list) {
		for (Object object : list) {
//			USer
		}
	}


}
