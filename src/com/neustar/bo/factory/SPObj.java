package com.neustar.bo.factory;

import com.neustar.bo.ShortestPathBo;
import com.neustar.bo.impl.ShortestPathBoImpl;

public class SPObj {
	
	public static ShortestPathBo getInstance(String type) {
		if(type.equalsIgnoreCase("sp")) return new ShortestPathBoImpl();
		return null;
	}

}
