package com.caseindex.bean;

/**
 * CaseList entity. @author MyEclipse Persistence Tools
 */

public class CaseList implements java.io.Serializable {

	// Fields

	private Integer id;
	private String keyword;
	private String filename;
	private String des;
	private Integer time;
	private String formTime;
	
	// Constructors

	/** default constructor */
	public CaseList() {
	}

	/** full constructor */
	public CaseList(String keyword, String filename, String des,
			Integer time) {
		this.keyword = keyword;
		this.filename = filename;
		this.des = des;
		this.time = time;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Integer getTime() {
		return this.time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getFormTime() {
		return formTime;
	}

	public void setFormTime(String formTime) {
		this.formTime = formTime;
	}
	
	//�������
	

}