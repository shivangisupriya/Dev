package com.neustar.util;

import java.util.Comparator;
import java.util.Map;

public class SortByDistance implements Comparator<Map> {

	@Override
	public int compare(Map arg0, Map arg1) {
		// TODO Auto-generated method stub
		return (int) arg0.get("distance") - (int) arg1.get("distance");
	}

}
