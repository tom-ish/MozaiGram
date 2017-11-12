var username = localStorage.getItem("username");
var sessionkey = localStorage.getItem("sessionKey");

var leftMenu = {
		initialize : function() {
			initializeUser();
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
}

function goHome() {
	console.log("go Home called...");
	localStorage.setItem("requestedpage", username);
	localStorage.setItem("previouspage","mypage");
	window.location.href = "./myspace.html"
//	window.location.href = "./FlipLogin.jsp";
}

function goMozaik(){
	console.log("go to Mozaik called ...");
	localStorage.setItem("previouspage","mozaikpage");
	window.location.href = "./after_login_page.html"
}

window.onload = function() {
	leftMenu.initialize();
}
