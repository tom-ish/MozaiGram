function launchSearch () {
		var word=document.getElementById("search").value;
	   		console.log("Search page called...");
			//goSearch();
	   		console.log("Searching for "+word);
			ServerServices.getSearchResults(word);
}



function goSearch() {
	window.location.href="./search.html"
}
