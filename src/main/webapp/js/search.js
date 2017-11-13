function launchSearch () {
		var word=document.getElementById("search").value;

			goSearch();
	   		console.log("Searching for "+word);
			ServerServices.getSearchResults(word);
}



function goSearch() {
	window.location.href="./search.html"
}
