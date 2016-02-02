package com.wu;

import com.wu.Interface.ExeclOpreationIn;
import com.wu.constant.Constant;
import com.wu.exception.IoExcelException;
import com.wu.model.User;
import com.wu.util.PropertiesUtil;
import com.wu.util.ReflectUtil;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 读取excel类工具类，缺少的功能可自行补充。
 * @author Administrator
 *
 */
public class PoiUtil {
	public static final String MESSAGE_ERROR_PATH = "/message-error.properties";

	private static Logger log =Logger.getLogger(PoiUtil.class);
	
//	static Map<String,String>  userMap = new HashMap<String, String>();
	private static Map<String,String> dtoMap =null;
	/**
	 * 测试文件路径。
	 */
//	private static String path ="D:/wuxinbo/execl/权限整理2015-07-15.xlsx";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		userMap.put("机构代码", "deptCde");
//		userMap.put("用户名", "userName");
//		userMap.put("用户代码", "userCde");
		Constant.initMap();
//		writeExecl("d:/用户表.xls",new ExcelOpreationImpl());
		try {
//			List list =getListFromExcel("d:/年计划模板.xls","com.wu.model.YearPlan",Constant.planOfYearMap);
			List list =getListFromExcel("d:/用户测试.xls","com.wu.model.User",Constant.userMap);
			System.out.println(list.size());
			if (list !=null) {
				for (Object object : list) {
//					System.out.println(((YearPlan)object).getYear()+" "+((YearPlan)object).getPlanGoal());
					log.debug(((User)object).getUserCde());
				}
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到POIFileSystem对象。
	 * @param path 文件路径。
	 * @return POIFileSystem对象
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static POIFSFileSystem getPOIFIle(String path) throws FileNotFoundException, IOException{
		return  new POIFSFileSystem(new FileInputStream(path));
	}
	/**
	 * 从给定的文件生成Workbook
	 * @param path 文件路径。
	 * @return Workbook Object
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static Workbook getWorkbook(String path) throws InvalidFormatException, IOException{
		return  WorkbookFactory.create(new File(path));
	}
	/**
	 * 每次读取xls表格一行数据。
	 * @param 行数。
	 * @return
	 * @throws IoExcelException 
	 */
	public static String readExeclForRow(String path,int rowNumber,List FieldList) throws IoExcelException{
		StringBuffer buffer =new StringBuffer();
		try {
			Sheet sheet= getWorkbook(path).getSheetAt(0); //获取第一个sheet。
			int CellCount =sheet.getRow(rowNumber).getLastCellNum();//获取每行单元格数。
			int size =FieldList.size();
			for (int i = 0; i < size; i++) {
				Map<String,String > map =(Map)FieldList.get(i);
				for (int j = 0; j < CellCount; j++) {
					if (Integer.valueOf(map.get("position"))==j) {
						buffer.append( sheet.getRow(rowNumber).getCell(j).getStringCellValue()+" ");
					}
				}
			}
			return buffer.toString();//获取第一行所有单元格的值。
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(IllegalStateException e){
			throw new IoExcelException(PropertiesUtil.getValueFromKey("notString",MESSAGE_ERROR_PATH)); //输出错误信息。
		}
		return null;
	}
	/**
	 * 从Excel中读取数据并封装成java模型。当第一行数据没有时将返回null
	 * @param path 文件路径
	 * @param ClassName Dto类全名
	 * @return 返回封装好的集合。
	 * @throws IOException  
	 * @throws InvalidFormatException 
	 */
	public static List getListFromExcel(String path,String ClassName,Map<String,String> map) throws InvalidFormatException, IOException{
		Constant.initMap();
		dtoMap =map;
		Sheet sheet= getWorkbook(path).getSheetAt(0);
		List list = new ArrayList();
		List<Map<String,String>> Fieldlist = new ArrayList<Map<String,String>>(); //成员集合。
		Row firstRow =sheet.getRow(0);
		int CellNum =firstRow.getLastCellNum(); //每行有多少个Cell。
		int RowCount =sheet.getLastRowNum(); //总行数。
		Fieldlist=getExcelHeader(Fieldlist, CellNum, firstRow);
		try {
			if (firstRow==null) {
				throw new IoExcelException(PropertiesUtil.getValueFromKey("firstRowNodata",MESSAGE_ERROR_PATH));
			}
		//循环读取每行数据。
		for (int i = 1; i <= RowCount; i++) {
			log.debug(i);
			String datastr =readExeclForRow(path,i,Fieldlist);
			String [] str =datastr.split(" ");
			Object obj =ReflectUtil.createInstance(ClassName);
			list.add(packageDataToDto(str,obj,Fieldlist,ClassName));
		}
		} catch (IoExcelException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	/**
	 * 读取excel第一行作为需要读取的列。当第一行没有数据时，程序将不会读取任何数据，
	 * 并且表头的值要和模型的成员变量相同（不区分大小写。）
	 * @param Fieldlist 需要加载的列。
	 * @param CellNum 第一行的有值的单元格数。
	 * @param firstRow excel的第一行。
	 * @throws IoExcelException 
	 */
	public static List<Map<String,String>> getExcelHeader(List<Map<String,String>> Fieldlist, int CellNum,
			Row firstRow)  {
		for (int i = 0; i < CellNum; i++) { //获取表头。
			if (firstRow.getCell(i)!=null&&!firstRow.getCell(i).getStringCellValue().equals("")) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("position", String.valueOf(i)); //保存位置。
				map.put("FieldName", dtoMap.get(firstRow.getCell(i).getStringCellValue())); //保存值
				Fieldlist.add(map);
			}
		}
		return Fieldlist;
	}
	/**
	 * 封装数据到Dto里面。
	 * @param datas 数据数组
	 * @param dto 模型类型
	 * @return 封装好的模型。
	 */
	private static Object packageDataToDto(Object[] datas,Object dto,List<Map<String,String>> Fieldlist,String ClassName){
		Method[] methods =ReflectUtil.getMethodFromObject(ClassName); //得到方法集合。
		for (int i = 0; i < datas.length; i++) {
			Map<String,String> map =Fieldlist.get(i);
			String name =map.get("FieldName");
			String methodName =ReflectUtil.getMethodName("set"+name, methods);
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					try {
						method.invoke(dto, datas[i]);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return dto;
	}
	
	
	/**
	 * 将内存中的数据写到硬盘上用作xls格式
	 * @param path 文件路径
	 * @return 如果写出成功返回true，否则返回false。
	 */
	public static boolean writeExecl(String path,ExeclOpreationIn excelOpreation){
		excelOpreation.WriteDataToXls(path);
		return true;
	}
	
	public static void setCellValue(Object value,Cell cell,Field field){
		Type type =field.getGenericType();
		if (type.toString().endsWith("String")) {
			cell.setCellValue((String)value);
		}else if(type.toString().endsWith("Date")){
			cell.setCellValue((Date)value);
		}
		
	}
	
}
