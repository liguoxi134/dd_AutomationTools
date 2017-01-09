package com.dangdang.tools.atf.entity;

import javax.persistence.Entity;

@SuppressWarnings("rawtypes")
@Entity
public interface SoftDeleteObject extends Comparable {
	void setState(boolean value);

	boolean isState();

	String toJson();
}
