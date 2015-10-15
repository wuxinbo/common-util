package com.wu.util;

import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.log4j.Logger;
/**
 * 反射工具类，提供相关方法。
 * @author wuxinbo
 * @version 1.0
 * @since 1.0
 */
public class ReflectUtil {
	public static final String userClassName ="com.test.model.User";
	private static Logger log4j = LogUtil.createlog4j(ReflectUtil.class);
	/**
	 * 获取某个属性的类型。
	 * @param Fields 域集合
	 * @param fieldName 成员名字。
	 * @return 获取成员变量的类型，例如（Integer,String）
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
	 * @return 属性为空的个数
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
	 * @param methodName 方法名
	 * @param Methods 方法集合
	 * @param obj 需要赋值的对象。
	 * @param value 数据
	 * @return 封装好的对象。
	 */
	public static Object setMethodValue(String methodName,Method[] Methods,Object obj,Object value){
		for (Method method : Methods) {
			if (methodName.equalsIgnoreCase(method.getName())) {
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
			return obj.getDeclaredFields(); //获取已生成的成员变量集合。
		} catch (ClassNotFoundException e) {
			log4j.error("类名没找到！");
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
	 * 获取一个对象的所有方法。
	 * @param obj
	 * @return 所有的方法集合
	 */
	public static Method[] getMethodFromObject(Object obj){
		return obj.getClass().getDeclaredMethods();
		
	}
	/**
	 * 获取一个对象的所有方法。
	 * @param obj
	 * @param pattern 匹配规则，例如（Set**）,暂时支持匹配开头。
	 * @return 符合条件的所有的方法集合
	 */
	public static List<Method> getMethodFromObject(Object obj,String pattern){
		Method[] methods =getMethodFromObject(obj);
		List<Method> list =new ArrayList<Method>();
		if (pattern!=null&&!pattern.equals("")) { //非空情况下
			for (Method method : methods) {
				if (method.getName().startsWith(pattern)) { //匹配开头。
					list.add(method);
				}
			}
		}
		return list;
		
	}
	/**
	 * 判断成员方法中是否包含给定的方法名（不区分大小写）。
	 * @param name 需要判断的方法名。
	 * @param Methods 成员方法集合。
	 * @return 如果有则返回true，否则返回false。
	 */
	public static boolean isEqualMethodName(String name,Method[] Methods){
		for (Method method : Methods) {
			if (name.equalsIgnoreCase(method.getName())) {
				return true;
			}
			continue;
		}
		return false;
	}
	
	/**
	 * 对象赋值，将一个对象中的相同字段赋值给另一个对象（当两个字段类型相同）
	 * @param ClassName 类全名，
	 * @param object 绑定有数据的对象
	 * @return 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object setObjectValueFromObject(String ClassName,Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object obj =createInstance(ClassName);
		List<Method> getMethods = getMethodFromObject(object,"get"); //获取所有的get方法
		List<Method> setMethods = getMethodFromObject(obj,"set"); //获取所有的set方法。
		Method[] methods =setMethods.toArray(new Method[0]);
		String methodName =""; //暂时存储
		for (Method method : getMethods) {
			if (method.invoke(object, null)!=null) { //获取方法的返回值。
				methodName ="set"+method.getName().substring(3,method.getName().length());
				if (isEqualMethodName(methodName,methods)) {//方法名比较。没有匹配的返回null
					setMethodValue(methodName, methods, obj, method.invoke(object, null)); //方法赋值。
				}
			}
		}
		return obj;
	}
}
