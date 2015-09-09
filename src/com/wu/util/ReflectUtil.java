package com.wu.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
/**
 * 反射工具类，提供相关方法。
 * @author Administrator
 *
 */
public class ReflectUtil {
	public static final String userClassName ="com.test.model.User";
	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
			Field[] Fields =getFieldFromObject(userClassName);
			Method[] Methods =getMethodFromObject(userClassName);
//			User user =new User();
			System.out.println(getTypeOfField(Fields,"usercde"));
			
//			System.out.println(((User)setValue("setUsercde", "1234", Methods)).getUserCde());;
/*
 * 方法测试。
 */
			//			try {
//			Object obj=createInstance(userClassName);
//			Class <?>	userClass=Class.forName(userClassName);
//			Method method =userClass.getMethod("setUserCde", String.class);
//			method.invoke(obj, "123456");
//			System.out.println(((User)obj).getUserCde());
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
			
			
	}
	/**
	 * 获取某个属性的类型。
	 * @param Fields 域集合
	 * @param fieldName 成员名字。
	 * @return
	 */
	public static String getTypeOfField(Field[] Fields,String fieldName){
		Type type =null;
		for (int i = 0; i < Fields.length; i++) {
			if (fieldName.equalsIgnoreCase(Fields[i].getName())) {
				type =Fields[i].getGenericType();
				break;
			}
		}
		return type.toString();
	}
	/**
	 *  比对给定的成员变量名字，
	 * @param fields 成员集合
	 * @param FieldName 成员变量名
	 * @return 如果找到对应的成员变量就返回，否则返回null
	 */
	public  static Field getFieldName(Field[] fields,String FieldName){
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equalsIgnoreCase(FieldName)) {
					return (fields[i]);
				}
			}
		return null;
	}
	/**
	 * 统计属性不为null的个数
	 * @param obj
	 * @return
	 */
	public static int getCountOfField(Object obj){
		int i=0;
		Field[] fields = getFieldFromObject(userClassName);
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				System.out.println(field.get(obj));
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			i++;
		}
		
		return i;
	}
	/**
	 * 利用反射给成员变量赋值。
	 * @param MethodName方法名
	 * @param value 值
	 */
	public static Object setValue(String MethodName,Object value,Method[] Methods,Object obj){
		//获取方法名。
		String methodName =getMethodName(MethodName,Methods);
		return obj =setMethodValue(methodName,Methods,obj,value);
	}
	/**
	 * 给方法赋值。
	 * @param name
	 * @param Methods
	 * @param obj
	 * @param value
	 * @return
	 */
	public static Object setMethodValue(String name,Method[] Methods,Object obj,Object value){
		for (Method method : Methods) {
			if (name.equalsIgnoreCase(method.getName())) {
				try {
					method.invoke(obj, value);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}			
			}
		}
		return obj;
	}
	/**
	 * 获取指定的方法名
	 * @param name 给定的方法名
	 * @param Methods 方法集合
	 * @return 如果给定的方法名和给定的方法集合中的方法名匹配（不区分大小写），返回匹配的方法名
	 * 			否则返回null
	 */
	public static String getMethodName(String name,Method[] Methods){
		for (Method method : Methods) {
			if (name.equalsIgnoreCase(method.getName())) {
				return method.getName();
			}
			continue;
		}
		return null;
	}
	/**
	 * 利用反射生成一个对象。
	 * @param className 类的全名。
	 * @return object对象。
	 */
	public static Object createInstance(String className){
		try {
			Class <?> obj =Class.forName(className);
			return obj.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取对象中的成员变量
	 * @param className 类名
	 * @return 成员变量数组。
	 */
	public static Field[] getFieldFromObject(String className){
		Class<?> obj;
		try {
			obj = Class.forName(className);
			return obj.getDeclaredFields();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 获取对象中的成员方法
	 * @param className 类名
	 * @return 成员方法数组。
	 */
	public static Method[] getMethodFromObject(String className){
		Class<?> obj;
		try {
			obj = Class.forName(className);
			return obj.getDeclaredMethods();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 判断成员方法中是否包含给定的方法名（不区分大小写）。
	 * @param name 需要判断的方法名。
	 * @param Methods 成员方法集合。
	 * @return 如果有则返回true，否则返回false。
	 */
	public static boolean isEqualMethodName(String name,Method[] Methods){
		for (Method method : Methods) {
			if ("getuserCde".equalsIgnoreCase(method.getName())) {
				return true;
			}
			continue;
		}
		return false;
	}
}
