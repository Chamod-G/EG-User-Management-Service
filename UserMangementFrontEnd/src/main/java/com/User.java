package com;
import java.sql.*;

public class User {

	public Connection connect()
	{
		Connection con = null;

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogriddb","root", "");
			//For testing
			System.out.print("Successfully Connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return con;
	}
	
	public String insertUser(String nic, String name, String address, String type, String sector){
		String output = "";
		
		try {
			
		Connection con = connect();
		
		if (con == null) {
			return "Error while connecting to the database";
		}
		
		// create a prepared statement
		 String query = " insert into usermanagement (`userID`,`userNIC`,`userName`,`userAddress`,`userType`,`userSector`)"
		 + " values (?, ?, ?, ?, ?, ?)"; 
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		//binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, nic);
		preparedStmt.setString(3, name);
		preparedStmt.setString(4, address);
		preparedStmt.setString(5, type);
		preparedStmt.setString(6, sector);
		
		//execute statement
		preparedStmt.execute();
		con.close();
		
		String newUsers = readUsers();
		output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}"; 
		}
		catch(Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the user.\"}";
			System.err.println(e.getMessage()); 
		}
		
		return output;
		
	}
	
	public String readUsers() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for reading";
			}
		
			//prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr><th>Item Code</th><th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th><th>Update</th><th>Remove</th></tr>";
		
			String query = "select * from items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//iterate through the rows in rs
			while(rs.next()) {
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				
				//Add a row into the html table
				output +="<tr><td>" + itemCode + "</td>";
				output +="<td>"+itemName+"</td>";
				output +="<td>"+itemPrice+"</td>";
				output +="<td>"+itemDesc+"</td>";
				
				//buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-itemid='" + itemID + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + itemID + "'></td></tr>";
			}
			
			con.close();
			
			//complete the html table
			output += "</table>";
		}
		catch(Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
		public String updateItem(String ID, String code, String name, String price, String desc) {
		
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
			
			//create a prepared statement
			String query = "UPDATE items SET itemCode=?, itemName=?, itemPrice=?, itemDesc=? WHERE itemID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding the values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, Integer.parseInt(ID));

			//execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
		}
		catch(Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage()); 
		}
		
		return output;
		
	}
	
	public String deleteItem(String itemID)
	{
		String output = "";
		try
		 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for deleting.";
			 }
			 // create a prepared statement
			 String query = "delete from items where itemID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(itemID));
		
			 // execute the statement
			 preparedStmt.execute();
			 con.close();

			 String newItems = readItems();
			 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 
		 }
		catch (Exception e)
		 {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
			System.err.println(e.getMessage()); 
		 }
		return output;
	}
}
