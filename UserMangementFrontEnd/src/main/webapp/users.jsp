<%@page import="com.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Page Lab10</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/items.js"></script>
</head>
<body>

<div class="container">
	<div class="row">
		<div class="col-6"> 

			<h1>Items Management</h1>
					<form id="formItem" name="formItem">
						User NIC: 
						<input id="userNIC" name="userNIC" type="text" class="form-control form-control-sm">
						<br>
						User name: 
						<input id="userName" name="userName" type="text" class="form-control form-control-sm">
						<br>
						User Address: 
						<input id="userAddress" name="userAddress" type="text" class="form-control form-control-sm">
						<br>
						User Type: 
						<input id="userType" name="userType" type="text" class="form-control form-control-sm">
						<br>
						User Sector: 
						<input id="userSector" name="userSector" type="text" class="form-control form-control-sm">
						<br>
						
						<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
						
						<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
					</form>
					
					<br>
					
					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
					
					<br>
					
					<div id="divItemsGrid">
						<%
							User userObj = new User();
							out.print(userObj.readUsers());
						%>
					</div>

		</div> 
	</div> 
</div> 		
</body>
</html>