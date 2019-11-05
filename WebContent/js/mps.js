

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
						var deptId = responseObject.department;
						sessionStorage.setItem("userId", responseObject.id);
				
					window.location = "./index.html"
				} else {
					alert("User User Authorization Failed. please verify details");
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