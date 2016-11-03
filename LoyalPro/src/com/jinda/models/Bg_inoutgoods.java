package com.jinda.models;

public class Bg_inoutgoods {
	private String materialcode;
	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}
	public String getMaterialcode() {
		return materialcode;
	}
	
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	private String spec;
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getSpec() {
		return spec;
	}
	private String unit;
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnit() {
		return unit;
	}
	private String unitname;
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getUnitname() {
		return unitname;
	}
	
	private Double sateqty;
	public void setSateqty(Double sateqty) {
		this.sateqty = sateqty;
	}
	public Double getSateqty() {
		return sateqty;
	}
	
	private Double nowqty;
	public void setNowqty(Double nowqty) {
		this.nowqty = nowqty;
	}
	public Double getNowqty() {
		return nowqty;
	}
	
	private Double price;
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPrice() {
		return price;
	}
	
	private int inoutMainID;
	public void setInoutMainID(int inoutMainID) {
		this.inoutMainID = inoutMainID;
	}
	public int getInoutMainID() {
		return inoutMainID;
	}
	
	private int inoutSubID;
	public void setInoutSubID(int inoutSubID) {
		this.inoutSubID = inoutSubID;
	}
	public int getInoutSubID() {
		return inoutSubID;
	}
	
	private Double  quantity;
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getQuantity() {
		return quantity;
	}
	
	//期初数量
	private Double  oriquantity;
	public void setOriquantity(Double oriquantity) {
		this.oriquantity = oriquantity;
	}
	public Double getOriquantity() {
		return oriquantity;
	}
	
	//请购数量
	private Double applyquantity;
	public void setApplyquantity(Double applyquantity) {
		this.applyquantity = applyquantity;
	}
	public Double getApplyquantity() {
		return applyquantity;
	}
	
	//入库数量
	private Double inquantity;
	public void setInquantity(Double inquantity) {
		this.inquantity = inquantity;
	}
	public Double getInquantity() {
		return inquantity;
	}
	//退库数量
	private Double outquantity;
	public void setOutquantity(Double outquantity) {
		this.outquantity = outquantity;
	}
	public Double getOutquantity() {
		return outquantity;
	}
	
	private String totalMoney;
	public String getTotalmoney() {
		return totalMoney;
	}
	public void setTotalmoney(String totalmoney) {
		this.totalMoney = totalmoney;
	}
}
