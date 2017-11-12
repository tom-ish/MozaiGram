var username = localStorage.getItem("username");
var sessionkey = localStorage.getItem("sessionKey");

var leftMenu = {
	initialize : function() {
		$('#logoutButton').click(function(){
			console.log("Logout Clicked!");
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
		$('#friendRequestButton').click(function(){
			showFriendRequest();
			return false;
		});
	}
};

function goHome() {
	console.log("go Home called...");
	localStorage.setItem("requestedpage", username);
	window.location.href = "./myspace.html";
}

function goMozaik(){
	console.log("go to Mozaik called ...");
	window.location.href = "./after_login_page.html";
}

function showFriendRequest(){
	console.log("showing friends request...");
	openFriendRequestSideNav();
}

function openFriendRequestSideNav() {
	document.getElementById("friendRequestSideNav").style.width="300px";
	document.getElementById("visibleContent").style.marginLeft="300px";
}

function closeFriendRequestSideNav() {
	document.getElementById("friendRequestSideNav").style.width="0";
	document.getElementById("visibleContent").style.marginLeft="0";
}

window.onload = function() {
	leftMenu.initialize();
}
