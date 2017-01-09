package com.dangdang.tools.atf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(columnDefinition = "varchar(100)")
	private String optime;
	@Column(columnDefinition = "varchar(200)")
	private String thread;
	@Column(columnDefinition = "varchar(45)")
	private String infolevel;
	@Column(columnDefinition = "varchar(200)")
	private String classname;
	@Column(columnDefinition = "longtext")
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getInfolevel() {
		return infolevel;
	}

	public void setInfolevel(String infolevel) {
		this.infolevel = infolevel;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
