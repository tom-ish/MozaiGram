var username = localStorage.getItem("requestedpage");

var mypagecontent = {
		initialize : function() {
			initializePage();
				console.log("USERNAME : " + username);
			$('#logoutButton').click(function(){
				ServerServices.logout(username, sessionkey);
				window.location.href = "./index.html"
				return false;
			});
			$('#homeButton').click(function(){
				goHome();
				return false;
			});
		}
};

function initializePage() {
	console.log("USERNAME : " + username);
	var html = "Page personnelle de "+username;
	var test = document.createElement('h1');
	test.className="test";
	test.innerHTML = html;
	document.getElementById("usernameId").appendChild(test);
}


function goHome() {
	console.log("go Home called...");
	localStorage.setItem("requestedpage", username);
	window.location.href = "./myspace.html"
//	window.location.href = "./FlipLogin.jsp";
}


window.onload = function() {
	mypagecontent.initialize();
}
