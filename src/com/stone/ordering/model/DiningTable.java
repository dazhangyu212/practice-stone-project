package com.stone.ordering.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 类名:DiningTable
 * 描述:
 * 公司:北京海鑫科技高科技股份有限公司
 * 作者:zhangyu
 * 创建时间:2016年3月5日
 */
@DatabaseTable(tableName = "DiningTable")
public class DiningTable extends BaseClass {

	public DiningTable() {
		// TODO Auto-generated constructor stub
	}

	public static class Status{
		private Status(){};
		public static final int AVAILIABLE = 0;
		public static final int ENJOYING = 1;
		public static final int Cleaning = 2;
	};
	@DatabaseField(columnName = "TableNum")
	private String tableNum;
	@DatabaseField(columnName = "Status")
	private int status;
	@DatabaseField(columnName = "Remarks")
	private String remarks;
	@DatabaseField(columnName = "Reserved1")
	private String reserved1;
	@DatabaseField(columnName = "Reserved2")
	private String reserved2;
	@DatabaseField(columnName = "Reserved3")
	private String reserved3;
	
	public String getTableNum() {
		return tableNum;
	}

	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	
}
