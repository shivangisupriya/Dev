package com.neustar.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.neustar.bo.ShortestPathBo;
import com.neustar.bo.factory.SPObj;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
@Path("/sp")
public class ShortestPathService {
	
	ShortestPathBo bo;
	
	public ShortestPathService() {
		// TODO Auto-generated constructor stub
		bo = SPObj.getInstance("sp");
	}
	
	@POST
	@Path("/add/city")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createUser(@Context HttpServletRequest request, 
			@Context HttpServletResponse response) {
		JSONObject res = new JSONObject();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(request.getInputStream()));
			String line = null; StringBuffer strBuff = new StringBuffer();
			while((line = br.readLine()) != null) {
				strBuff.append(line);
			}
			br.close();
			JSONObject input = JSONObject.fromObject(strBuff.toString());
			if(!"".equals(input.getString("city"))) {
				res = bo.createNode(input.getString("city"));
			}else {
				res.put("status", "FAILURE");
				res.put("message", "Invalid input");
			}			
		}catch(Exception exp) {
			exp.printStackTrace();
			res.put("status", "FAILURE");
			res.put("message", "Technical issue");
		}finally {
			try {
				response.setContentType("application/json");
				PrintWriter writer = response.getWriter();
				writer.print(res);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@POST
	@Path("/add/connected/city")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createConnectedCity(@Context HttpServletRequest request, 
			@Context HttpServletResponse response) {
		JSONObject res = new JSONObject();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(request.getInputStream()));
			String line = null; StringBuffer strBuff = new StringBuffer();
			while((line = br.readLine()) != null) {
				strBuff.append(line);
			}
			br.close();
			JSONObject input = JSONObject.fromObject(strBuff.toString());
			if(!"".equals(input.getString("city"))
					&& !"".equals(input.getString("connectedCity"))
					&& !"".equals(input.getString("distance"))) {
				res = bo.createConnectedNode(input);
			}else {
				res.put("status", "FAILURE");
				res.put("message", "Invalid input");
			}			
		}catch(Exception exp) {
			exp.printStackTrace();
			res.put("status", "FAILURE");
			res.put("message", "Technical issue");
		}finally {
			try {
				response.setContentType("application/json");
				PrintWriter writer = response.getWriter();
				writer.print(res);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@POST
	@Path("/calculate/path")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void calculateShortPath(@Context HttpServletRequest request, 
			@Context HttpServletResponse response) {
		JSONObject res = new JSONObject();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(request.getInputStream()));
			String line = null; StringBuffer strBuff = new StringBuffer();
			while((line = br.readLine()) != null) {
				strBuff.append(line);
			}
			br.close();
			JSONObject input = JSONObject.fromObject(strBuff.toString());
			if(!"".equals(input.getString("source"))
					&& !"".equals(input.getString("dest"))) {
				res = bo.calculateShortestPath(input);
			}else {
				res.put("status", "FAILURE");
				res.put("message", "Invalid input");
			}			
		}catch(Exception exp) {
			exp.printStackTrace();
			res.put("status", "FAILURE");
			res.put("message", "Technical issue");
		}finally {
			try {
				response.setContentType("application/json");
				PrintWriter writer = response.getWriter();
				writer.print(res);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/cities")
	@Produces(MediaType.APPLICATION_JSON)
	public void getAllCities(@Context HttpServletResponse response) {
		JSONArray cities = new JSONArray();
		try {
			cities = bo.getAllNodes();
		}catch(Exception exp) {
			exp.printStackTrace();
		}finally {
			try {
				response.setContentType("application/json");
				PrintWriter writer = response.getWriter();
				writer.print(cities);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
