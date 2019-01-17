package com.neustar.util;

import java.util.List;
import java.util.Map;

public class Node {
	
	private int nodeId;
	private List<Map> adjacentNodes;
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public List<Map> getAdjacentNodes() {
		return adjacentNodes;
	}
	public void setAdjacentNodes(List<Map> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}	
}
