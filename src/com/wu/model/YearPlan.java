package com.wu.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 年计划Dto.
 * @author xbwuc
 *
 */
public class YearPlan {
	/**
	 * 计划年
	 */
	private String year;
	/**
	 * 部门计划目标。
	 */
	private BigDecimal planGoal;
	/**
	 * 机构代码。
	 */
	private String deptCde;
	/**
	 * 数据插入时间。
	 */
	private Date insertDate;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public BigDecimal getPlanGoal() {
		return planGoal;
	}
	public void setPlanGoal(String planGoal) {
		this.planGoal = new BigDecimal(planGoal);
	}
	public String getDeptCde() {
		return deptCde;
	}
	public void setDeptCde(String deptCde) {
		this.deptCde = deptCde;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
}
