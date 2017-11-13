jQuery('#searchButton').on('input', function () {
		var word=document.getElementById("search").value;
	   console.log("Searching for "+word);
			goSearch();
			document.getElementById("search").value=word;
			ServerServices.getSearchResults(word);
		

});



function goSearch() {
	window.location.href="./search.html"
}
