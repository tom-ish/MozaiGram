function search(field) {
   console.log("Searching for "+field.value);
  document.getElementById("error").innerText = "";
    if (field.value === "") {
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
		serverServices.getSearchResults(field.value);
	}
}

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
