$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	 {
	 	$("#alertSuccess").hide();
	 }
	 $("#alertError").hide();
});

//SAVE --------------------------------------------
$(document).on("click", "#btnSave", function(event)
{
 	// Clear alerts---------------------
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide();
	 
	 // Form validation-------------------
	var status = validateItemForm();
	if (status != true)
	 {
		 $("#alertError").text(status);
		 $("#alertError").show();
		 return;
	 }
	 
	 // If valid------------------------
 	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
 	
 	$.ajax(
	 {
		 url : "UsersAPI",
		 type : type,
		 data : $("#formItem").serialize(),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 	onItemSaveComplete(response.responseText, status);
	 	 }
	 }); 

});

//UPDATE---------------------------------------------
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidItemIDSave").val($(this).data("userID")); 	
	$("#userNIC").val($(this).closest("tr").find('td:eq(0)').text());
	$("#userName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#userAddress").val($(this).closest("tr").find('td:eq(2)').text());
	$("#userType").val($(this).closest("tr").find('td:eq(3)').text());
	$("#userSector").val($(this).closest("tr").find('td:eq(3)').text());  
});

//DELETE----------------------------------------------
$(document).on("click", ".btnRemove", function(event)
{
	 $.ajax(
	 {
		 url : "UsersAPI",
		 type : "DELETE",
		 data : "userID=" + $(this).data("userID"),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 	onItemDeleteComplete(response.responseText, status);
		 }
	 });
});


// CLIENT-MODEL================================================================
function validateItemForm()
{
	// CODE
	if ($("#userNIC").val().trim() == "")
	 {
	 	return "Insert User NIC.";
	 }
	// NAME
	if ($("#userName").val().trim() == "")
	 {
	 	return "Insert User Name.";
	 }
	// ADDRESS-------------------------------
	if ($("#userAddress").val().trim() == "")
	 {
	 	return "Insert User Address.";
	 }
	// TYPE
		if ($("#userType").val().trim() == "")
	 {
	 	return "Insert User Type.";
	 }
	 	// SECTOR
		if ($("#userSector").val().trim() == "")
	 {
	 	return "Insert User Sector.";
	 }
	return true;
}

function onItemSaveComplete(response, status)
{	
	if (status == "success") 
	{
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")
		{
			 $("#alertSuccess").text("Successfully saved.");
			 $("#alertSuccess").show();
			 
			 $("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		}
	} else if (status == "error")
	{
		 $("#alertError").text("Error while saving.");
		 $("#alertError").show();
	} else
	{
		 $("#alertError").text("Unknown error while saving..");
		 $("#alertError").show();
	}
	
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}

function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
		 var resultSet = JSON.parse(response);
		 if (resultSet.status.trim() == "success")
		 {
			 $("#alertSuccess").text("Successfully deleted.");
			 $("#alertSuccess").show();
			 
			 $("#divItemsGrid").html(resultSet.data);
		 } else if (resultSet.status.trim() == "error")
		 {
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		 }
	 } else if (status == "error")
	 {
		 $("#alertError").text("Error while deleting.");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while deleting..");
		 $("#alertError").show();
 	 }
 }



