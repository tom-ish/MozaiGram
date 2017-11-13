$(document).ready(function(){
	$("#launchSearchButton").click(function() {
		console.log("button clicked");
		console.log(SearchForm);
		console.log("---------------------");
		console.log(sessionkey);
//					ServerServices.uploadData(dragNdropForm.userKeyword.value, fileList[0]);
		ServerServices.getSearchResults(document.getElementById("searchWord").value);
					return false;
	});

});


var searchpagecontent = {

		
};



function displayResults(searchkey){
	console.log("Search results for "+searchkey)
	var html = "Resultat de recherche pour "+searchkey;
	var test = document.createElement('h1');
	test.className="test";
	test.innerHTML = html;
	document.getElementById("usernameId").value = test;
	flushAccessArea();
	var List=localStorage.getItem("listResearch");
	console.log("Search's results are: "+List);
	var res=List.split(",");

		for (var i = 0; i < res.length; i++) {
    		//alert(List[i]);
    		displayAccessButton(res[i]);
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
	searchpagecontent.initialize();
}
