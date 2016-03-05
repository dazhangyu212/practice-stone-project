package com.stone.ordering.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 类名:DinnerOrder
 * 描述:
 * 公司:北京海鑫科技高科技股份有限公司
 * 作者:zhangyu
 * 创建时间:2016年3月5日
 */
@DatabaseTable(tableName = "DinnerOrder")
public class DinnerOrder extends BaseClass {

	public DinnerOrder() {
		// TODO Auto-generated constructor stub
	}
	
	public static class charge{
		private charge() {
			// TODO Auto-generated constructor stub
		}
		public static final int UNPAID = 0; 
		public static final int CHARGED = 1;
		
	}
	@DatabaseField(columnName = "Count")
	private int count;
	@DatabaseField(columnName = "DiningTableID")
	private String DiningTableID;
	@DatabaseField(columnName = "Total")
	private String total;
	@DatabaseField(columnName = "Ording")
	private String orderingTime;
	@DatabaseField(columnName = "CustomerID")
	private String customerID;
	@DatabaseField(columnName = "Dishs")
	private String dishs;
	@DatabaseField(columnName = "Charge")
	private int charge;
	@DatabaseField(columnName = "Remarks")
	private String remarks;
	@DatabaseField(columnName = "Reserved1")
	private String reserved1;
	@DatabaseField(columnName = "Reserved2")
	private String reserved2;
	@DatabaseField(columnName = "Reserved3")
	private String reserved3;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDiningTableID() {
		return DiningTableID;
	}

	public void setDiningTableID(String diningTableID) {
		DiningTableID = diningTableID;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getOrderingTime() {
		return orderingTime;
	}

	public void setOrderingTime(String orderingTime) {
		this.orderingTime = orderingTime;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public String getDishs() {
		return dishs;
	}

	public void setDishs(String dishs) {
		this.dishs = dishs;
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
