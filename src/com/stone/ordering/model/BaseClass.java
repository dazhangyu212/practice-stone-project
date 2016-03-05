package com.stone.ordering.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * 类名:BaseClass
 * 描述:
 * 公司:北京海鑫科技高科技股份有限公司
 * 作者:zhangyu
 * 创建时间:2016年3月5日
 */
public class BaseClass {
	
	public BaseClass() {
		// TODO Auto-generated constructor stub
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * 主键生成规则：数据来源于???待定，那么将所有信息进行MD5编码后作为主键；如果来源于对比结果，那么将身份证号和人员编号进行MD5编码后作为主键
	 */
	@DatabaseField(columnName = "ID", id = true)
	private String ID;
}
