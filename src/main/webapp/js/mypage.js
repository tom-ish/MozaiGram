	var username = localStorage.getItem("requestedpage");
	var sessionkey = localStorage.getItem("sessionKey");
//	var friends = localStorage.getItem("friends");
//	var friendRequests = localStorage.getItem("friendRequests");

var mypagecontent = {
		initialize : function() {
			initializePage();
//			loadFriendsListInfo();
//			loadFriendRequestsInfo();
		}
		
};

function initializePage() {
	console.log("Page personnelle de "+username);
	var html = "Page personnelle de "+username;
	var test = document.createElement('h1');
	test.className="test";
	test.innerHTML = html;
	document.getElementById("usernameId").appendChild(test);
	return;
}

function loadFriendsListInfo() {
	
	var friendsArray = friends.split(STRINGIFY_SEPARATOR);
	for (var i = 0; i < friendsArray.length; i++) {
		var friendInfoArray = friendInfo.split(STRINGIFY_ATTRIBUTE_SEPARATOR);
	}
	
	return;
}

function loadFriendRequestsInfo() {
	var friendRequestsSideNav = $(document.getElementById("friendRequestSideNav"));
	
	var requestsArray = friendRequests.split(STRINGIFY_SEPARATOR);
	localStorage.setItem("requestsArray", requestsArray);
	for (var i = 0; i < requestsArray.length; i++) {
		var div = document.createElement("div");
		var requestInfoArray = requestArray.split(STRINGIFY_ATTRIBUTE_SEPARATOR);
		div.innerHTML = requestInfoArray[1];
		friendRequestsSideNav.append(div);
	}
	return;
}

window.onload = function() {
	mypagecontent.initialize();
	leftMenu.initialize();
}
