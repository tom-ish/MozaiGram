var searchkey = localStorage.getItem("searchkey");

var mozaikcontent = {
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
			localStorage.setItem("previouspage", "mozaikpage");
		}
		
};

function initializeUser() {
	console.log("Resultat de recherche pour "+searchkey);

	var html = "Resultat de recherche pour "+searchkey;
	var test = document.createElement('h1');
	test.className="test";
	test.innerHTML = html;
	document.getElementById("usernameId").value = test;

	
}

function goHome() {
	console.log("go Home called...");
	localStorage.setItem("requestedpage", username);
	window.location.href = "./myspace.html"
//	window.location.href = "./FlipLogin.jsp";
}

function goMozaik(){
	console.log("go to Mozaik called ...");
	window.location.href = "./after_login_page.html"
}

function displayResults(){
	var html = "Resultat de recherche pour "+searchkey;
	var test = document.createElement('h1');
	test.className="test";
	test.innerHTML = html;
	document.getElementById("usernameId").value = test;
	flushAccessArea();
	var List=localStorage.getItem("listResearch");
		var arrayLength = List.length;

		for (var i = 0; i < arrayLength; i++) {
    		alert(List[i]);
    		displayAccessButton(List[i]);
		}
}

function displayAccessButton(name) {
	console.log("displaying access button for "+name+"'s page");

	var button = document.createElement("button");
	button.id = name+"Button";
	button.form = "AccessForm";
	button.value = name;
	button.type = "submit";
	$(document.getElementById("AccessArea")).append(button);
	return;
}

function flushAccessArea() {
	$(document.getElementById("AccessArea")).innerHTML="";
}

window.onload = function() {
	mozaikcontent.initialize();
}
