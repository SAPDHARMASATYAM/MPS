function loadMenu(menuName) {
	console.log(menuName + " Is Loading ........");
	$("#contact").hide();
	$("#blog").hide();
	$("#" + menuName).show();
	if(menuName == "blog"){
		allMessages();
	}
}

function accountRegistration() {
	console.log("Account registration got called .....");
	try {
		var account = new Object();
		account.email = $('#registrationEmailId').val();
		account.fName = $('#registrationFirstName').val();
		account.lName = $('#registrationLastName').val();
		account.mobile = $('#registrationMobile').val();
		account.address = $('#registrationAddress').val();
		account.password = $('#registrationPassword').val();
		console.log("Account registration object : " + account);
		$.ajax({
			url: "./accountRegistration",
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(account),
			contentType: 'application/json',
			mimeType: 'application/json',

			success: function (data) {
				var respJSONString = JSON.stringify(data);
				console.log(respJSONString);
				var jsonObj = JSON.parse(respJSONString);
				console.log(jsonObj.responseStatus + " : " + jsonObj.responseMessage);
				if (jsonObj.responseStatus === 'Success') {
					alert("Account Registration Success.");
						var responseObject = JSON.parse(JSON.stringify(data.responseBody));
						sessionStorage.setItem("userId", responseObject.email);
				
					window.location = "./index.html"
				} else {
					alert("User User Registration Failed. please verify details");
				}
			},

			error: function (data, status, er) {
				alert("error: " + JSON.stringify(data) + " status: " + status + " er:" + er);
				window.location = "./index.html"
			}
		});
	} catch (e) {
		console.log("Exception in calling Registration " + e);
	}
}

function accountLogin(){
	console.log("Account registration got called .....");
	try {
		var account = new Object();
		account.email = $('#loginEmailId').val();
		account.password = $('#loginPassword').val();
		console.log("Account Login object : " + account);
		$.ajax({
			url: "./accountLogin",
			type: 'POST',
			dataType: 'json',
			data: JSON.stringify(account),
			contentType: 'application/json',
			mimeType: 'application/json',

			success: function (data) {
				var respJSONString = JSON.stringify(data);
				console.log(respJSONString);
				var jsonObj = JSON.parse(respJSONString);
				console.log(jsonObj.responseStatus + " : " + jsonObj.responseMessage);
				if (jsonObj.responseStatus === 'Success') {
					alert("Account Login Success.");
						var responseObject = JSON.parse(JSON.stringify(data.responseBody));
						sessionStorage.setItem("userId", responseObject.email);
				        window.location= "./message.html";
				} else {
					alert("User User Login Failed. please verify details");
				}
			},

			error: function (data, status, er) {
				alert("error: " + JSON.stringify(data) + " status: " + status + " er:" + er);
			}
		});
	} catch (e) {
		console.log("Exception in calling Registration " + e);
	}
}

function updateToList(){
	try {
		var account = new Object();
		account.email = sessionStorage.getItem("userId");
		$.ajax({
			url : "./getAllUsers",
			type : 'POST',
			dataType : 'json',
			data : JSON.stringify(account),
			contentType : 'application/json',
			mimeType : 'application/json',

			success : function(data) {

				var respJSONString = JSON.stringify(data);
				console.log(respJSONString);
				var jsonObj = JSON.parse(respJSONString);
				console.log(jsonObj.responseStatus + " : " + jsonObj.responseMessage);
				if(jsonObj.responseStatus === 'Success'){
					$('#to').empty();
					$.each(data.responseBody, function (i, item) {
						//var option = new Option(item, item); 
						$('#to').append('<option value=\''+item+'\'>' + item + '</option>');

					});
				}else{
					alert("No Accounts found for sending");
				}
			
			},

			error : function(data, status, er) {
				alert("error: " + data + " status: " + status + " er:" + er);
			}
		});
	} catch (ex) {
		alert(ex);
	}
}

function messageTypeChange() {

	var isPublic = $("#messageType").val();
	if (isPublic != "true") {
		$("#to").show();
		updateToList();
	} else {
		$("#to").hide(100);
		
	}
}

function sendMessage(){
	try {
        console.log("Sending new message");
		var message = new Object();
		message.from = sessionStorage.getItem("userId");
		message.from = "sapthagiri.koduri@adtran.com"
		message.type = $("#messageType").val();
		message.to = $("#to").val();
		message.subject = $("#subject").val();
		message.body = $("#message").val();
		
		console.log("Sending message deatils :: " + message);
		$.ajax({
			url : "./sendMessage",
			type : 'POST',
			dataType : 'json',
			data : JSON.stringify(message),
			contentType : 'application/json',
			mimeType : 'application/json',

			success : function(data) {
				var respJSONString = JSON.stringify(data);
				console.log(respJSONString);
				var jsonObj = JSON.parse(respJSONString);
				console.log(jsonObj.responseStatus + " : " + jsonObj.responseMessage);
				if(jsonObj.responseStatus === 'Success'){
					alert("Message  sent.");
					
				}else{
					alert("Message sending failed");
				}
			},

			error : function(data, status, er) {
				alert("error: " + JSON.stringify(data) + " status: " + status + " er:" + er);
				
			}
        });
        console.log("Message sending call Completed...............");
	} catch (ex) {
		alert(ex);
		
	}
}

function allMessages(){
	
	try {
		var message = new Object();
		message.from = sessionStorage.getItem("userId");

		message.type = 'true';
		$.ajax({
			url : "./allMessages",
			type : 'POST',
			dataType : 'json',
			data : JSON.stringify(message),
			contentType : 'application/json',
			mimeType : 'application/json',

			success : function(data) {
				console.log("allMessages : " + JSON.parse(JSON.stringify(data.responseBody)));
				$('#messagesBody').empty();
				$.each(data.responseBody, function(idx, obj) {
					var eachrow = "<tr>"
						+ "<td>" + obj.subject + "</td>"
						+ "<td>" + obj.body + "</td>"
						+ "<td>" + obj.date + "</td>";
					
						if(obj.mailType === true){
							eachrow += "<td> Public </td>"
						}else{
							eachrow += "<td> Private </td>"
						}
						 eachrow +="<td><a href='javascript:deleteMessage("+obj.id+")'> Delete</a></td>";
						eachrow += "</tr>";
					$('#messagesBody').append(eachrow);
				});
				
			},

			error : function(data, status, er) {
				alert("error: " + JSON.stringify(data) + " status: " + status + " er:" + er);
			}
		});
	} catch (ex) {
		alert(ex);
	}
}

function deleteMessage(id){
	try {

		var message = new Object();
		message.id = id;
		console.log("deleteMessage Id is  : " + id);
		$.ajax({
			url : "./deleteMessage",
			type : 'POST',
			dataType : 'json',
			data : JSON.stringify(message),
			contentType : 'application/json',
			mimeType : 'application/json',

			success : function(data) {
				var respJSONString = JSON.stringify(data);
				console.log(respJSONString);
				var jsonObj = JSON.parse(respJSONString);
				console.log(jsonObj.responseStatus + " : " + jsonObj.responseMessage);
				if(jsonObj.responseStatus == "Success"){
					alert("Message deleted successfully.");
				}else{
					alert("Message Not deleted.");
				}
			},
			error : function(data, status, er) {
				alert("error: " + JSON.stringify(data) + " status: " + status + " er:" + er);
			}
		});
		allMessages();
	} catch (ex) {
		alert(ex);
	}
}

function findMessages() {
	try {
		var message = new Object();
		message.from = sessionStorage.getItem("userId");
		message.body = $("#searchBody").val();
		message.subject = $("#searchSubject").val();
		console.log("findMessages object : " + message);
		$.ajax({
			url : "./findMessages",
			type : 'POST',
			dataType : 'json',
			data : JSON.stringify(message),
			contentType : 'application/json',
			mimeType : 'application/json',

			success : function(data) {
				console.log("find result : " + JSON.parse(JSON.stringify(data.responseBody)));
				$('#messagesBody').empty();
				$.each(data.responseBody, function(idx, obj) {
					var eachrow = "<tr>"
						+ "<td>" + obj.subject + "</td>"
						+ "<td>" + obj.body + "</td>"
						+ "<td>" + obj.date + "</td>";
					
						if(obj.type === true){
							eachrow += "<td> Public </td>"
						}else{
							eachrow += "<td> Private </td>"
						}
						 eachrow +="<td><a href='javascript:deleteMessage("+obj.id+")'> Delete</a></td>";
						eachrow += "</tr>";
					$('#messagesBody').append(eachrow);
				});
			},

			error : function(data, status, er) {
				alert("error: " + JSON.stringify(data) + " status: " + status + " er:" + er);
			}
		});
	} catch (ex) {
		alert(ex);
	}
	
}