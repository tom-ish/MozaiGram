jQuery('#search').on('input', function () {
		var word=document.getElementById("search").value;
	   console.log("Searching for "+word);
		if (word === "") {
		 var page = localStorage.getItem("previouspage");
			if (page == "mozaikpage"){
				goMozaik();
			}
			else if (page=="mypage"){
				goPage();
			}
		}
		else {
			goSearch();
			document.getElementById("sb-search").value=word;
			ServerServices.getSearchResults(word);
		}

});

function goMozaik(){
	console.log("go to Mozaik called ...");
	window.location.href = "./after_login_page.html"
}

function goPage() {
	var req= localStorage.getItem("requestedpage");
	console.log(req+"'s page called...");
	window.location.href = "./myspace.html"
//	window.location.href = "./FlipLogin.jsp";
}

function goSearch() {
	window.location.href="./search.html"
}
