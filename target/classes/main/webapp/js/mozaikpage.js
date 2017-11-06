var username = localStorage.getItem("username");
var sessionkey = localStorage.getItem("sessionKey");

var mozaikcontent = {
		initialize : function() {
			initializeUser();
			console.log("USERNAME : " + username + ", sessionKey : " + sessionkey);
			$('#logoutButton').click(function(){
				ServerServices.logout(username, sessionkey);
				return false;
			});
		}
};

function initializeUser() {
	console.log("USERNAME : " + username + ", sessionKey : " + sessionkey);

	var html = "Welcome "+username+", sessionKey : " + sessionkey +"!";
	var test = document.createElement('h1');
	test.className="test";
	test.innerHTML = html;
	document.getElementById("usernameId").appendChild(test);
}

function goHome() {
	console.log("go Home called...");
	window.location.href = "./index.html"
//	window.location.href = "./FlipLogin.jsp";
}


window.onload = function() {
	mozaikcontent.initialize();
}