package com.dangdang.tools.common.compare;

import java.util.List;

public interface ICompareTargetObject<T extends ICompareItem> {
	List<T> match(T sourceItem);
}
