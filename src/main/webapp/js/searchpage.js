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
			if(res[i].charAt(0)=='[' || res[i].charAt(0)==' '){
				res[i]=res[i].substr(1,res[i].length);
			}
			if (res[i].charAt(res[i].length-1)==']' || res[i].charAt(res[i].length-1)==' '){
				res[i]=res[i].substr(0,res[i].length-1);
			}
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
	button.style.height="200px";
	button.style.width="800px";
	button.onclick=accessClick;
	$(document.getElementById("AccessArea")).append(button);
	return;
}

function flushAccessArea() {
	$(document.getElementById("AccessArea")).innerHTML="";
}

function accessClick() {
//	localStorage.setItem("requestedpage", field.value);
	window.location.href = "./myspace.html";
}

window.onload = function() {
	searchpagecontent.initialize();
}
