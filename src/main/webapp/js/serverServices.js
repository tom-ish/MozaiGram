var ServerServices = {
		signup : function signup(username, pwd, pwd2, email) {
			console.log("signup called with username: " + username + ", password: " + pwd + ", password2: "+pwd2+ ", email: "+email);;
			$.ajax ({
				type: "POST",
				url: "CreateUserServlet",
				data: "username=" + username + "&password=" + pwd + "&password2=" + pwd2 + "&email=" + email,
				dataType: 'json',
				success: function(json){
					if(json.CreateUserServlet == SUCCESS_CODE){
						console.log("signUp success!");
						isLoginPage = false;
						switchToHomePage();
					}
					else{
						console.log("signUp failed with code: " + json.CreateUserServlet);
						console.log("username : " + json.username);
						console.log("password : " + json.password);
						console.log("password2 : " + json.password2);
						console.log("email : " + json.email);
					}
					
				},
				error: function(jqXHR , textStatus , errorThrown ){
					console.log(textStatus);
					console.log(jqXHR.responseText + " status: " + jqXHR.status);
//					alert("Erreur Ajax: SignUp is not working.\n" + textStatus + " " + errorThrown);
				}
			});
		},
		connect : function connect(username, password) {
			$.ajax ({
				type: "POST",
				url: "ConnectUserServlet",
				/*
				contentType: false, // obligatoire pour de l'upload
				processData: false, // obligatoire pour de l'upload
				*/
				data: "username=" + username + "&password=" + password,
				dataType: 'json',
				success: function(json){
					if(json.ConnectUserServlet == SUCCESS_CODE){
						console.log("Connexion success!");
						console.log(json);
						console.log(JSON.stringify(Array.from(json.friendRequest)));
						console.log(JSON.stringify(Array.from(json.friends)));
						// Store username in localStorage Object before switching to MozaikPage
						// Web Storage Compatibility should be checked at start
						localStorage.setItem("username", json.username);
						localStorage.setItem("sessionKey", json.sessionKey);
						localStorage.setItem("connectResultJSON", json);
						switchToMyPage();
					}
					else{
						console.log("Connexion failed!");
						console.log("returned code : " + json.ConnectUserServlet);
					}
				},
				error: function(jqXHR , textStatus , errorThrown ){
					console.log(textStatus);
					console.log(jqXHR.responseText + " status: " + jqXHR.status);
					//alert("Erreur Ajax: Connexion is not working.\n" + textStatus + " " + errorThrown);
				}
			});
		},
		logout : function logout(username, sessionkey) {
			$.ajax({
				type: "POST",
				url: "LogoutUserServlet",
				data: "username=" + username + "&sessionkey=" + sessionkey,
				dataType: 'json',
				success: function(json) {
					if(json.LogoutUserServlet == SUCCESS_CODE) {
						console.log("Deconnexion success!");
						console.log(json);
						localStorage.clear();

					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(jqXHR.responseText + " status : " + jqXHR.status);
				}
			});
		},
		/*
		uploadData : function uploadData(keyword, image) {
			console.log("ServerServices.uploadData()");
			console.log(keyword);
			console.log(image);
			
		}
		*/
		uploadData : function uploadData(form, sessionkey) {
			console.log("ServerServices.uploadData()");
			console.log(form);
			console.log("value");
			console.log(form.userKeyword.value);
			var dataform = new FormData(form);
			dataform.append("sessionkey", sessionkey);
			
			$.ajax({
				type: "POST",
				url: "UploadDataServlet",
				data: dataform,
				cache: false,
				contentType: false, // obligatoire pour de l'upload
				processData: false, // obligatoire pour de l'upload
				dataType: 'json',
				success: function(json) {
					if(json.UploadDataServlet == PROCESS_COMPLETABLE_FUTURE_TASKS_STARTED) {
						console.log(json);
						console.log("sessionKey: " + sessionkey);
						setTimeout(ServerServices.isMozaikGenerated(sessionkey), 2000);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(jqXHR.responseText + " status : " + jqXHR.status);
				}
			});
		},
		isMozaikGenerated : function isMozaikGenerated(sessionkey) {
			console.log("isMozaikGenerated called...");
			var generated = false;
			$.ajax({
				type: "POST",
				url: "IsMozaikGeneratedServlet",
				data: "sessionkey=" + sessionkey,
				dataType: 'json',
				success: function(json) {
					if(json.IsMozaikGeneratedServlet == SUCCESS_CODE) {
						console.log(json);
						generated = true;
					}
				},
				complete: function(json) {
					if(!generated) {
						// Schedule the next
	                    //setTimeout(ServerServices.isMozaikGenerated(sessionkey), 10000);
					}
					else {
						console.log("SUCCESS");
					}
					
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(jqXHR.responseText + " status : " + jqXHR.status);
				}
			});
		},
		
		
		sendFriendRequest : function sendFriendRequest(sessionkey, friendid) {
			console.log("sendFriendRequest called...");
			$.ajax({
				type: "GET",
				url: "SendFriendRequestServlet",
				data: "sessionkey=" + sessionkey + "&friendid=" + friendid,
				dataType: 'json',
				success: function(json){
					if(json.SendFriendRequestServlet == SUCCESS_CODE){
						console.log("addFriend success!");
						console.log("returned code : " + json.SendFriendRequestServlet);
					}
					else{
						console.log("addFriend failed!");
						console.log("returned code : " + json.SendFriendRequestServlet);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(jqXHR.responseText + " status : " + jqXHR.status);
				}
			});
		},
		acceptFriendRequest : function acceptFriendRequest(sessionkey, friendid) {
			console.log("acceptFriendRequest called...");
			$.ajax({
				type: "POST",
				url: "AcceptFriendRequestServlet",
				data: "sessionkey=" + sessionkey + "&userid=" + friendid
			});
		},
		
		getAllFriends : function getAllFriends(sessionkey) {
			console.log("getAllFriends called...");
			$.ajax({
				type: "GET",
				url: "GetAllFriendsServlet",
				data: "sessionkey=" + sessionkey,
				dataType: 'json',
				success: function(json){
					if(json.GetAllFriendsServlet == SUCCESS_CODE){
						console.log("getAllFriends success!");
						console.log(json);
					}
					else{
						console.log("getAllFriends failed!");
						console.log("returned code : " + json.GetAllFriendsServlet);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(jqXHR.responseText + " status : " + jqXHR.status);
				}
			});
		},
		getAllFriendRequest : function getAllFriendRequest(sessionkey) {
			console.log("getAllFriendRequest called...");
			$.ajax({
				type: "GET",
				url: "GetAllFriendRequestServlet",
				data: "sessionkey=" + sessionkey,
				datatype: 'json',
				success: function(json) {
					if(json.GetAllFriendRequestServlet == SUCCESS_CODE){
						console.log("getAllFriendRequest success!");
						console.log(json);
					}
					else {
						console.log("getAllFriendRequest failed!");
						console.log("returned code : " + json.getAllFriendRequestServlet);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(jqXHR.responseText + " status : " + jqXHR.status);
				}
			});
		},
		
		getSearchResults : function getSearchResults(sessionkey) {
			console.log("getSearchResults called...");
			var generated = false;
			$.ajax({
				type: "POST",
				url: "SearchServlet",
				data: "sessionkey=" + sessionkey,
				dataType: 'json',
				success: function(json) {
					if (json.searchEnded) {
						generated=true;
					}
					var listResearch = json.listResearch;
					localStorage.setItem("listResearch",listResearch);
					displayResults();
				},
				complete: function(json) {
					if(!generated) {
						// Schedule the next
	                    setTimeout(ServerServices.searchresult(sessionkey), 100);
					}
					else {
						console.log("Search Ended");
					}
					
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus);
					console.log(jqXHR.responseText + " status : " + jqXHR.status);
				}
			});
		}
		
}
