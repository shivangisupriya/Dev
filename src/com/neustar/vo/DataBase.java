package com.neustar.vo;

import java.io.Serializable;

import net.sf.json.JSONArray;

public class DataBase implements Serializable {
	
	private static DataBase database = null;
	
	private DataBase() {}
	
	public static synchronized DataBase getInstance() {
		if(database != null) database  = new DataBase();
		return database;
	}
	
	private static int nodeSequence = 0;
	private static JSONArray nodes = new JSONArray();
	private static JSONArray connectedNodes = new JSONArray();

	public static int getNodeSequence() {
		return nodeSequence;
	}

	public static void setNodeSequence(int nodeSequence) {
		DataBase.nodeSequence = nodeSequence;
	}

	public static JSONArray getNodes() {
		return nodes;
	}

	public static void setNodes(JSONArray nodes) {
		DataBase.nodes = nodes;
	}

	public static JSONArray getConnectedNodes() {
		return connectedNodes;
	}

	public static void setConnectedNodes(JSONArray connectedNodes) {
		DataBase.connectedNodes = connectedNodes;
	}

}
