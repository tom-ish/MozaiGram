var username = localStorage.getItem("requestedpage");
var sessionkey = localStorage.getItem("sessionKey");


var mypagecontent = {
		initialize : function() {
			initializePage();
			loadFriendsListInfo();
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
			$('#mozaikButton').click(function(){
				goMozaik();
				return false;
			});
			localStorage.setItem("previouspage", "mypage");
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

function loadFriendsListInfo() {
	console.log("Loading friendsListInfo...");
	ServerServices.getAllFriends(sessionkey);
}

function goHome() {
	console.log("go Home called...");
	localStorage.setItem("previouspage","mypage");
	localStorage.setItem("requestedpage", username);
	window.location.href = "./myspace.html"
//	window.location.href = "./FlipLogin.jsp";
}

function goMozaik(){
	console.log("go to Mozaik called ...");
	localStorage.setItem("previouspage","mozaikpage");
	window.location.href = "./after_login_page.html"
}

window.onload = function() {
	mypagecontent.initialize();
}
