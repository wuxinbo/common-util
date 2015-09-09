package com.wu;

import com.wu.model.User;
import com.wu.util.ReflectUtil;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 用户添加主文件，将用户添加到数据库，可以执行此文件批量添加。
 * @author Administrator
 *
 */
public class JdbcTest {
	private static final String URL="jdbc:oracle:thin:@10.16.62.15:1521:dmis";
//	private static final String URL="jdbc:oracle:thin:@10.1.2.37:1521:dmis";
	private static final String USERNAME ="finance";
	private static final String PASSWORD ="dmis";
	private static Connection conn=null;
	private static ResultSet set=null;
	private static PreparedStatement sta =null;
	private static final String userClassName ="com.test.model.User";
	private static final String INSERT_FINANCE="insert into t_user (c_user_cde,c_user_nme,c_dept_cde,c_sub_dept_cde,c_state,c_password,T_CRT_TM)values(?,?,?,?,?,?,?)";
	/**
	 * insert sql
	 */
	private static final String INSERT_DMISORACLE ="insert into t_user(c_user_cde,c_user_nme,c_dept_cde,c_password) values(?,?,?,?)";
	private static final String INSERT_TEST="insert into t_user(c_user_cde,c_user_nme,c_dept_cde,c_password) values(#c_user_cde#,#c_user_nme#,#c_dept_cde#,#c_password#)";
	/**
	 * 查询用户表
	 */
	public static final String QUERY_USER ="SELECT c_user_cde as userCde,c_user_nme as userName ,c_dept_cde as deptCde ,c_state as state ,c_password as password ,c_crt_cde as createUser,T_CRT_TM as createDate FROM t_user";
	private static Logger log4j =Logger.getLogger(JdbcTest.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				insertToUser();
//		List<String> indexlist =getSqlArgs(INSERT_TEST,"#");
//		for (String integer : indexlist) {
//			System.out.println(integer);
//		}
	}
	/**
	 * ����ݲ��뵽t_user�?
	 */
	public static void insertToUser() {
		if(isFinance()){//根据用户名选择要执行的sql语句。
			insert(INSERT_FINANCE);
		}else{
			insert(INSERT_DMISORACLE);
		}
	}
	public void logTest(){
		Logger log4j =Logger.getLogger(getClass());
		log4j.info("log hello world");
	} 
	/**
	 * ͳ��sql�в���ĸ���
	 * @param sql sql���
	 * @param args ����ռλ��������
	 * @return ���ز���ĸ���
	 */
	public static int countSqlArgs(String sql,String args){
		int position =0;
		int count=1;
		position=sql.indexOf(args);
		int lastposition =sql.lastIndexOf(args);
		if(position!=0){
			while(position!=lastposition){
				position=sql.indexOf(args, position+1);
				count++;
			}
		}
		return count;
	}
	/**
	 * 统计#出现的位置。
	 * @param sql 查找的语句。
	 * @return 如果有多个返回一个List.
	 */
	public static List<Integer> getArgsIndexList(String sql,String args){
		List<Integer> indexList = new ArrayList<Integer>(); //args位置集合。
		int firstPosition =sql.indexOf(args); //出现第一次的位置。
		int lastPosition =sql.lastIndexOf(args);//最后出现的位置。
		int position =0;
		if (firstPosition!=lastPosition){ //找出args出现的位置。
			while(sql.indexOf(args, position+1)!=lastPosition){
				position =sql.indexOf(args, position+1);
				indexList.add(position);
			}
		}else{
			
		}
		indexList.add(lastPosition);//把最后出现的位置添加进去。
		return indexList;
	}
	/**
	 * 获取sql中的参数。
	 * @param sql sql语句
	 * @param args 标识符
	 * @return 参数列表
	 */
	public static List<String> getSqlArgs(String sql,String args){
		List<String> argsList =new ArrayList<String>();
		List<Integer> indexList =getArgsIndexList(sql, args);
		int size =indexList.size();
		if (size%2==0) { //判断args出现的次数是否为偶数。
			int odd = 0; //奇数
			int evenNumber =0; //偶数。
			for (int i = 0; i < size; i++) {
				if (i%2==0) { //偶数。
					evenNumber =i;
					continue;
				}else{//奇数
					odd =i;
				}
				if (odd==i) {
					argsList.add(sql.substring(indexList.get(evenNumber)+1, indexList.get(odd)));
				}
			}
		}
		
		return argsList;
	}
	
	/**
	 * ��ȡConnection����
	 * @return
	 */
	public static Connection getConnection(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);//获得连接对象。
			conn.setAutoCommit(false);//�ֶ�commit
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	/**
	 * ��ȡ��statement��
	 * @return
	 */
	public static Statement getStatement(String sql){
		conn=getConnection();
		try {
			if (sql==null) {
				return conn.createStatement();
			}else{
				return conn.prepareStatement(sql);
			}
			
		} catch (SQLException e) {
			System.out.println("��ȡstatementʧ�ܣ�");
			e.printStackTrace();
		}
		return null;
	}
	public static ResultSet getSet(String sql){
		sta =(PreparedStatement)getStatement(sql);
		try {
			set =sta.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return set;
	}
	/**
	 * ��ӡ��ѯ���
	 * @param set
	 */
	public static void printSet(ResultSet set ){
		try {
			ResultSetMetaData metadata	= set.getMetaData();
			while(set.next()){
				for (int i = 1; i < metadata.getColumnCount(); i++) {
					System.out.print(metadata.getColumnName(i));
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeJdbc(conn,sta,set);
	}
	/**
	 * 从ResultSet中获取数据并封装到List中。
	 * @param set 数据源
	 * @return 封装好的集合。
	 */
	public static List getListFromSet(ResultSet set){
		try {
			ResultSetMetaData metadata	= set.getMetaData();
			/**
			 * 列名
			 */
			String ColumeName ="";
			Method[] Methods =ReflectUtil.getMethodFromObject(userClassName);
			Field[] fields =ReflectUtil.getFieldFromObject(userClassName);
			List users =new ArrayList();
			int ColumnCount=metadata.getColumnCount()+1;
			while(set.next()){
				Object user = ReflectUtil.createInstance(userClassName);
				for (int i = 1; i < ColumnCount; i++) {
					ColumeName=metadata.getColumnName(i);//获取表头。
					if (ReflectUtil.isEqualMethodName("set"+ColumeName, Methods)) {
						user=(User)ReflectUtil.setValue(ReflectUtil.getMethodName("set"+ColumeName, Methods),
																	getValueFromColumeType(ColumeName,fields,set), 
																	Methods,user);
					}
				}
				users.add(user);
			}
			return users;
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据返回的列类型，得到返回值。
	 * @param columeName
	 * @param Fields
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	public static Object  getValueFromColumeType(String columeName,Field[] Fields,ResultSet set) throws SQLException{
		String type =ReflectUtil.getTypeOfField(Fields, columeName);
		Object obj =null;
		if (type.endsWith("String")) {
			obj =set.getString(columeName);
		}else if(type.endsWith("int")){
			obj =set.getInt(columeName);
		}else if(type.endsWith("Date")){
			obj =set.getDate(columeName);
		}else if(type.endsWith("double")){
			obj =set.getDouble(columeName);
		}
		
		return obj;
	}
	/**
	 * �жϵ�ǰ����Ƿ�Ϊfinance
	 * @return ����Ƿ���true,���򷵻�false
	 */
	public static boolean isFinance(){
		if (USERNAME.equals("finance")) {
			return true;
		}else{
			return false;
		}
	}
	public static void insert(String sql){
		 String password =getTriplDESStr("123456");
		 sta =(PreparedStatement)getStatement(sql);
		 if(isFinance()){
			 try{
				 
				//从Excel文件中读取需要的数据
				 List<User> list =PoiUtil.getListFromExcel("d:/用户测试.xls","com.wu.model.User",Constant.userMap); 
				 Time now =new Time(new Date().getTime());
				 int size =list.size();
				 for (int i=0;i<size;i++) {
						sta.setString(1, list.get(i).getUserCde());
						sta.setString(2, list.get(i).getUserName());
						sta.setString(3, list.get(i).getDeptCde());
						sta.setString(4, list.get(i).getDeptCde());
						sta.setString(5, "1");
						sta.setString(6, password);
						sta.setTime(7,now);
						sta.addBatch();
					} 
				 	sta.executeBatch();
					conn.commit();
			 }catch (SQLException e) {
					System.out.println("insert failed!");
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		closeJdbc(conn,sta,set);
	}
	
	public static String getDESStr(String usernamePwd){
		return DesTest.encodeCipher(usernamePwd);
	}
	/**
	 * �����������
	 * @return
	 */
	public static String getTriplDESStr(String pwd){
		return TriplDES.getEncryptStr(pwd);
	}
	/**
	 * ���ιر�jdbc���ӡ�
	 * @param conn
	 */
	public static void closeJdbc(Connection conn,PreparedStatement sta,ResultSet set){
		
		try {
			if(set!=null){
				set.close();
			}
			if(sta!=null){
				sta.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
}
