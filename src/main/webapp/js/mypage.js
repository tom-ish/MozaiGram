var username = localStorage.getItem("requestedpage");
var sessionkey = localStorage.getItem("sessionKey");
var connectResultJSON = localStorage.getItem("connectResultJSON");


var mypagecontent = {
		initialize : function() {
			initializePage();
			loadFriendsListInfo();
			console.log("myPageContent loaded");
			console.log("USERNAME : " + username);
			console.log(connectResultJSON);
			console.log(connectResultJSON.friendRequest);
			console.log(connectResultJSON.friends);
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

window.onload = function() {
	mypagecontent.initialize();
}
