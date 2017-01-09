package com.dangdang.tools.common.compare;

public interface ICompareSourceObject<T extends ICompareTargetObject<? extends ICompareItem>> {
	String CompareWithTarget(T target);
}
