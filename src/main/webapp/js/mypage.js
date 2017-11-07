var username = localStorage.getItem("requestedpage");

var mypagecontent = {
		initialize : function() {
			initializePage();
				console.log("USERNAME : " + username);
			$('#logoutButton').click(function(){
				ServerServices.logout(username, sessionkey);
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

function refreshDataInPage() {
	console.log("Refreshing page");
	
}

function goHome() {
	console.log("go Home called...");
	localStorage.setItem("requestedpage", username);
	window.location.href = "./mypage.html"
//	window.location.href = "./FlipLogin.jsp";
}


window.onload = function() {
	mozaikcontent.initialize();
}
