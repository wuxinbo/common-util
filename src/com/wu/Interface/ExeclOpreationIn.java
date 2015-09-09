package com.wu.Interface;

import java.lang.reflect.Field;
import java.util.List;

/**
 *   对execl操作的接口。
 * @author wuxinbo
 *
 */
public interface ExeclOpreationIn {
	/**
	 * 将数据写入到磁盘上。
	 */
	public void WriteDataToXls(String path);
	/**
	 * 设置Excel表头。
	 * @param fields 成员变量集合
	 * @param list 数据集合。
	 */
	public void setExcelHeader(Field [] fields,List list);
}
