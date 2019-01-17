package com.neustar.bo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface ShortestPathBo {
	
	public JSONArray getAllNodes();
	public JSONArray getAll(int count, String userId);
	public JSONArray get(JSONObject input);
	public JSONObject createNode(String input);
	public JSONObject createConnectedNode(JSONObject input);
	public JSONObject calculateShortestPath(JSONObject input);

}
