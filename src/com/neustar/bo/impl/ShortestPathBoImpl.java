package com.neustar.bo.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import com.neustar.bo.ShortestPathBo;
import com.neustar.util.Node;
import com.neustar.util.SortByTime;
import com.neustar.vo.DataBase;
import com.neustar.vo.Store;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShortestPathBoImpl implements ShortestPathBo {

	@Override
	public JSONArray getAllNodes() {
		// TODO Auto-generated method stub
		JSONArray nodes = new JSONArray();
		try {
			DataBase database = DataBase.getInstance();
			if(database.getNodes() != null && database.getNodes().size()>0)
				nodes = database.getNodes();
		}catch(Exception exp) {
			exp.printStackTrace();
		}		
		return nodes;
	}

	@Override
	public JSONArray getAll(int count, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray get(JSONObject input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject createNode(String input) {
		// TODO Auto-generated method stub
		JSONArray nodes = new JSONArray();
		JSONObject res = new JSONObject();
		try {
			DataBase database = DataBase.getInstance();
			if(database.getNodes() != null && database.getNodes().size()>0)
				nodes = database.getNodes();
			JSONObject node = new JSONObject();
			node.put("nodeId", database.getNodeSequence()+1);
			node.put("nodeName", input);
			nodes.add(node);
			database.setNodes(nodes);
			database.setNodeSequence(database.getNodeSequence()+1);
			res.put("status", "SUCCESS");
			res.put("message", "Node has been created");
		}catch(Exception exp) {
			exp.printStackTrace();
		}
		return res;
	}

	@Override
	public JSONObject createConnectedNode(JSONObject input) {
		// TODO Auto-generated method stub
		JSONArray connectedNodes = new JSONArray();
		JSONObject res = new JSONObject();
		try {
				DataBase database = DataBase.getInstance();
				if(database.getConnectedNodes() != null && database.getConnectedNodes().size()>0)
					connectedNodes = database.getConnectedNodes();
				JSONObject connectedNode = new JSONObject();
				connectedNode.put("nodeId", input.get("city"));
				connectedNode.put("connectedNodeId", input.get("connectedCity"));
				connectedNode.put("distance", input.get("distance"));
				connectedNodes.add(connectedNode);
				java.util.List<JSONObject> jsList = new ArrayList<JSONObject>();
				for(int itr=0; itr< connectedNodes.size();itr++) {
					jsList.add(connectedNodes.getJSONObject(itr));
				}
				Collections.sort(jsList);
				connectedNodes = new JSONArray();
				for(int itr=0; itr< jsList.size();itr++) {
					connectedNodes.add(jsList.get(itr));
				}
				database.setConnectedNodes(connectedNodes);
				res.put("status", "SUCCESS");
				res.put("message", "Node connection has been created");			
		}catch(Exception exp) {
			exp.printStackTrace();
			res.put("status", "FAILURE");
			res.put("message", "Technical issue");
		}
		return res;
	}

	@Override
	public JSONObject calculateShortestPath(JSONObject input) {
		// TODO Auto-generated method stub
		JSONArray nods = new JSONArray();
		JSONObject res = new JSONObject();
		try {
			int source = Integer.parseInt(input.getString("source"));
			Map nVisit = new HashMap<Integer, Boolean>();
			Map nDistance = new HashMap<Integer, Integer>();
			Map nodes = new HashMap<Integer, Node>();
			List<Node> nl = getAllNodeDetails();
			Node sourceNode = null;
			for(Node n: nl) {
				System.out.println("Node Id : " + n.getNodeId());
				System.out.println("Adjacent Nodes : " + n.getAdjacentNodes());
				if(n.getNodeId() == source) {
					nDistance.put(n.getNodeId(), 0);
					sourceNode = n;
				} else {
					nDistance.put(n.getNodeId(), Integer.MAX_VALUE);
					
				}
				nVisit.put(n.getNodeId(), false);
				nodes.put(n.getNodeId(), n);
			}
			System.out.println("nVisit : " + nVisit);
			System.out.println("nDistance : " + nDistance);
			System.out.println("nodes : " + nodes);
			Set<Integer> nKeys = nodes.keySet();
			System.out.println("nKeys : " + nKeys);
			while(sourceNode != null) {
				boolean flag = false;
				Node temp = null;
				for(int n: nKeys) {
					if(sourceNode.getNodeId() == n) {
						nVisit.put(sourceNode.getNodeId(), true);
						List adjn = sourceNode.getAdjacentNodes();
						for(int itr2 = 0; itr2<adjn.size(); itr2++) {
							Map aj = (Map) adjn.get(itr2);
							if(!(boolean) nVisit.get((int) aj.get("node")) && !flag) {
								temp = (Node) nodes.get((int) aj.get("node"));
								flag = true;
							}
							System.out.println("Node : " + (int) aj.get("node"));
							System.out.println("Prev : " + (int) nDistance.get((int) aj.get("node")));
							int dis = ((int) nDistance.get(sourceNode.getNodeId())) == Integer.MAX_VALUE? 
									(int) aj.get("distance") : ((int) nDistance.get(sourceNode.getNodeId())) + (int) aj.get("distance");
							System.out.println("Curr : " + dis);
							if(dis < (int) nDistance.get((int) aj.get("node"))) nDistance.put((int) aj.get("node"), dis);
						}
						break;
					}
				}
				if(temp != null) sourceNode = temp;
				else sourceNode = null;
			}
			System.out.println("Distance : " + nDistance);
			res.put("status", "SUCCESS");
			if((int)nDistance.get(Integer.parseInt(input.getString("dest"))) < Integer.MAX_VALUE)
				res.put("message", "Shortest path is " + (int)nDistance.get(Integer.parseInt(input.getString("dest"))));
			else 
				res.put("message", "Source and dest are not connected.");
		}catch(Exception exp) {
			exp.printStackTrace();
		}
		return res;

	}
	
	public List<Node> getAllNodeDetails(){
		List<Node> nodes = new ArrayList<>();
		try {
			DataBase database = DataBase.getInstance();
			JSONArray nds = database.getNodes();
			if(nds != null && nds.size() > 0) {
				for(int itr=0;itr<nds.size();itr++) {
					Node nd = new Node();
					nd.setNodeId(nds.getJSONObject(itr).getInt("nodeId"));
					JSONArray adjs = database.getConnectedNodes();
					List<Map> ads = new ArrayList<>();
					for(int itr2=0;itr2<adjs.size();itr2++) {
						if(adjs.getJSONObject(itr2).getInt("nodeId") == nds.getJSONObject(itr).getInt("nodeId")) {
							Map ad = new HashMap<>();
							ad.put("node", adjs.getJSONObject(itr2).getInt("connectedNodeId"));
							ad.put("distance", adjs.getJSONObject(itr2).getInt("distance"));
							ads.add(ad);
						}
					}
					nd.setAdjacentNodes(ads);
					nodes.add(nd);
				}
				
			}
		}catch(Exception exp) {
			exp.printStackTrace();
		}
		return nodes;

	}

}
