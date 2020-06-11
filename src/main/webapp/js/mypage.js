	var username = localStorage.getItem("username");
	var sessionkey = localStorage.getItem("sessionKey");
	var friends = localStorage.getItem("friends");
	var friendRequests = localStorage.getItem("friendRequests");

var mypagecontent = {
		initialize : function() {
			initializePage();
	//		loadFriendsListInfo();
//			loadFriendRequestsInfo();
		},
		initializeLeftMenu : function() {
			leftMenu.initialize();			
		},
		initializeThumbnails : function() {
			loadThumbnails();
		}
};

function initializePage() {
	if(localStorage.getItem("searchMode") == "true") {
		username=localStorage.getItem("requestedpage");
		localStorage.setItem("searchMode","false");
	}
	else
		username=localStorage.getItem("username");
	console.log("Page personnelle de "+username);
	var html = "Page personnelle de "+username;
	var test = document.createElement('h1');
	test.className="test";
	test.innerHTML = html;
	document.getElementById("usernameId").appendChild(test);
	ServerServices.getImgUser(username);
	var listImg=localStorage.getItem("listImg");
	console.log("Images correspondant à "+username+": "+listImg);
	document.getElementById("libsmall").title="Mosaïques de "+username;

	return;
}

function loadFriendsListInfo() {
	var friends = localStorage.getItem("friends");
	var friendsArray = friends.split(STRINGIFY_SEPARATOR);
	for (var i = 0; i < friendsArray.length; i++) {
		var friendInfoArray = friendsArray.split(STRINGIFY_ATTRIBUTE_SEPARATOR);
		//displayFriend();
	}
	
	return;
}

function displayImages(list){
	var l=list.split(",");
	

		for (var i = 0; i < l.length; i++) {
			if(l[i].charAt(0)=='[' || l[i].charAt(0)==' '){
				l[i]=l[i].substr(1,l[i].length);
			}
			if (l[i].charAt(l[i].length-1)==']' || l[i].charAt(l[i].length-1)==' '){
				l[i]=l[i].substr(0,l[i].length-1);
			}
    		displayImage(l[i],i);
		}
		return;
}

function displayImage(url,i){
		console.log("Displaying "+url);
		var colmd3 = document.createElement('div');
        colmd3.setAttribute('class','col-md-3 browse-grid');
        colmd3.id = 'divimg'+i;        
        libsmall.appendChild(colmd3);

        var objcolmd3 = document.getElementById(colmd3.id);

        objcolmd3.innerHTML = 
        '<a data-toggle="modal" data-target="#myModal'+i+'"> <img'+
            ' src='+url+
            ' class="img-fluid">'+
        '</a>';

        var divmodalfade = document.createElement('div');
        divmodalfade.id = 'myModal' + i; 
        divmodalfade.setAttribute('class','modal fade');
        divmodalfade.setAttribute('myModal','-1');
        divmodalfade.setAttribute('role','dialog');
        divmodalfade.setAttribute('aria-labelledby','myModalLabel');
        divmodalfade.setAttribute('aria-hidden','true');
        document.getElementById(colmd3.id).appendChild(divmodalfade);

        var divmodalimg = document.createElement('div');
        divmodalimg.id = 'divmodalimg'+i;
        divmodalimg.setAttribute('class','modal img-modal');
        document.getElementById(divmodalfade.id).appendChild(divmodalimg);

        var divmodaldialog = document.createElement('div');
        divmodaldialog.id = divmodaldialog+i;
        divmodaldialog.setAttribute('class','modal-dialog modal-lg');
        document.getElementById(divmodalimg.id).appendChild(divmodaldialog);

        var divmodalcontent = document.createElement('div');
        divmodalcontent.id='divmodalcontent'+i;
        divmodalcontent.setAttribute('class','modal-content');
        document.getElementById(divmodaldialog.id).appendChild(divmodalcontent);

        var divmodalbody = document.createElement('div');
        divmodalbody.id='divmodalbody'+i;
        divmodalbody.setAttribute('class','modal-body');
        document.getElementById(divmodalcontent.id).appendChild(divmodalbody);

        var divmodalrow = document.createElement('div');
        divmodalrow.id = 'divmodalrow'+i;
        divmodalrow.setAttribute('class','row');
        document.getElementById(divmodalbody.id).appendChild(divmodalrow);

        var objdivmodalrow = document.getElementById(divmodalrow.id);

        objdivmodalrow.innerHTML = 
        '<div class="col-md-8 modal-image">'+
            '<img class="img-responsive "'+
            'src='+url+'>'+
        '</div>';

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



/*
 * THUMBNAILS
 */
function loadThumbnails() {
	ServerServices.myMozaikThumbnails(sessionkey);
	return;
}

function addComment(buttonid, sessionkey, commentid, imgid){   
    console.log("Comment button clicked");
    console.log("---------------------");
    console.log(sessionkey);
    ServerServices.addComment(sessionkey, document.getElementById(commentid).value, imgid);
    return;
}

function listImages(json){
	console.log("returned json in thumbnails");
	console.log(json);
	
	var imagesjson = json.myMozaikRslt;
	console.log(imagesjson);
	/*
    var imagesjson =[
                        {
                            "id":1,
                            "link":"https://www.tuxboard.com/custom/blog/images/art-cinema/monalisa.jpg",
                            "username":"Dat",
                            "avatar":"https://vignette.wikia.nocookie.net/justdance/images/1/1e/Pump_It_avatar.png/revision/latest?cb=20151217015341",
                            "comments":[
                                            {
                                                "author":"Azerty",
                                                "avatar":"http://lorempixel.com/50/50/people/6",
                                                "comment":"What a nice picture !",
                                            },
                                            {
                                                "author":"John Snow",
                                                "avatar":"http://lorempixel.com/50/50/people/7",
                                                "comment":"Winter is comming !",
                                            },
                                            {
                                                "author":"Ygrid",
                                                "avatar":"http://lorempixel.com/50/50/people/9",
                                                "comment":"You know nothing !",
                                            }
                                        ]
                        },
                        {
                            "id":2,
                            "username":"Dat",
                            "avatar":"https://vignette.wikia.nocookie.net/justdance/images/1/1e/Pump_It_avatar.png/revision/latest?cb=20151217015341",
                            "link":"https://www.tuxboard.com/custom/blog/images/art-cinema/elizabeth_taylor.jpg",
                            "comments":[]
                        },
                        {
                            "id":3,
                            "username":"Dat",
                            "avatar":"https://vignette.wikia.nocookie.net/justdance/images/1/1e/Pump_It_avatar.png/revision/latest?cb=20151217015341",
                            "link":"https://media.paperblog.fr/i/137/1370731/sandhi-schimmel-art-mozaique-recup-L-4.jpeg",
                            "comments":[]
                        }
                    ];
                    */
    var libsmall = document.getElementById("libsmall");
    for(var i = 0; i < imagesjson.length; i++) {
        var colmd3 = document.createElement('div');
        colmd3.setAttribute('class','col-md-3 browse-grid');
        colmd3.id = 'divimg'+i;        
        libsmall.appendChild(colmd3);

        var objcolmd3 = document.getElementById(colmd3.id);

        objcolmd3.innerHTML = 
        '<a data-toggle="modal" data-target="#myModal'+i+'"> <img'+
            ' src='+imagesjson[i].link+
            ' class="img-fluid">'+
        '</a>';

        var divmodalfade = document.createElement('div');
        divmodalfade.id = 'myModal' + i; 
        divmodalfade.setAttribute('class','modal fade');
        divmodalfade.setAttribute('myModal','-1');
        divmodalfade.setAttribute('role','dialog');
        divmodalfade.setAttribute('aria-labelledby','myModalLabel');
        divmodalfade.setAttribute('aria-hidden','true');
        document.getElementById(colmd3.id).appendChild(divmodalfade);

        var divmodalimg = document.createElement('div');
        divmodalimg.id = 'divmodalimg'+i;
        divmodalimg.setAttribute('class','modal img-modal');
        document.getElementById(divmodalfade.id).appendChild(divmodalimg);

        var divmodaldialog = document.createElement('div');
        divmodaldialog.id = divmodaldialog+i;
        divmodaldialog.setAttribute('class','modal-dialog modal-lg');
        document.getElementById(divmodalimg.id).appendChild(divmodaldialog);

        var divmodalcontent = document.createElement('div');
        divmodalcontent.id='divmodalcontent'+i;
        divmodalcontent.setAttribute('class','modal-content');
        document.getElementById(divmodaldialog.id).appendChild(divmodalcontent);

        var divmodalbody = document.createElement('div');
        divmodalbody.id='divmodalbody'+i;
        divmodalbody.setAttribute('class','modal-body');
        document.getElementById(divmodalcontent.id).appendChild(divmodalbody);

        var divmodalrow = document.createElement('div');
        divmodalrow.id = 'divmodalrow'+i;
        divmodalrow.setAttribute('class','row');
        document.getElementById(divmodalbody.id).appendChild(divmodalrow);

        var objdivmodalrow = document.getElementById(divmodalrow.id);

        objdivmodalrow.innerHTML = 
        '<div class="col-md-8 modal-image">'+
            '<img class="img-responsive "'+
            'src='+imagesjson[i].link+'>'+
        '</div>';

        var colmd4 = document.createElement('div');
        colmd4.setAttribute('class','col-md-4 modal-meta');
        colmd4.id = 'colmd4'+i;        
        objdivmodalrow.appendChild(colmd4);

        var objcolmd4 = document.getElementById(colmd4.id);

        var modalmetatop = document.createElement('div');
        modalmetatop.id='modalmetatop'+i;
        modalmetatop.setAttribute('class','modal-meta-top');
        objcolmd4.appendChild(modalmetatop);

        var objmodalmetatop = document.getElementById(modalmetatop.id);

        objmodalmetatop.innerHTML = 
        '<button type="button" class="close" data-dismiss="modal">'+
            '<span aria-hidden="true">×</span>'+
        '</button>'+
        '<div class="img-poster clearfix">'+
            '<a href=""><img class="img-circle"'+
            'src="images/avatar.png"'+ /*imagesjson[i].avatar+*/'/></a>'+
            '<strong><a href="">'+imagesjson[i].username+'</a></strong>'+
        '</div>';

        var ulcommentlist = document.createElement('ul');
        ulcommentlist.setAttribute('class','img-comment-list');
        ulcommentlist.id = 'ulcommentlist'+i;        
        objmodalmetatop.appendChild(ulcommentlist);

        var objulcommentlist = document.getElementById(ulcommentlist.id);

        for (var j = 0; j < imagesjson[i].comments.length; j++) {
            objulcommentlist.innerHTML +=      
            '<li>'+
                '<div class="comment-img">'+
                    '<img src="images/avatar.png"'+ /*imagesjson[i].comments[j].avatar+*/'/>'+
                '</div>'+
                '<div class="comment-text">'+
                    '<strong><a href="">'+ imagesjson[i].comments[j].author +'</a></strong>'+
                    '<p>' + imagesjson[i].comments[j].comment +'</p>'+                                                   
                '</div>'+
            '</li>';   
        }
              
        var modalmetabottom = document.createElement('div');
        modalmetabottom.id='modalmetabottom'+i;
        modalmetabottom.setAttribute('class','modal-meta-bottom');
        document.getElementById(colmd4.id).appendChild(modalmetabottom);

        var inputgroup = document.createElement('div');
        inputgroup.id='inputgroup'+i;
        inputgroup.setAttribute('class','input-group input-group-sm chatMessageControls');
        document.getElementById(modalmetabottom.id).appendChild(inputgroup);

        var inputcomment = document.createElement('input');
        inputcomment.id='inputcomment'+i;
        inputcomment.setAttribute('class','form-control');
        inputcomment.setAttribute('type','text');
        inputcomment.setAttribute('placeholder','Leave a commment...');
        inputcomment.setAttribute('aria-describedby','sizing-addon3');
        document.getElementById(inputgroup.id).appendChild(inputcomment);

        var inputgroupbtn = document.createElement('span');
        inputgroupbtn.id='inputgroupbtn'+i;
        inputgroupbtn.setAttribute('class','input-group-btn');
        document.getElementById(inputgroup.id).appendChild(inputgroupbtn);

        var btnsendcomment = document.createElement('button');
        btnsendcomment.id='btnsendcomment'+i;
        btnsendcomment.setAttribute('class','btn btn-primary');
        btnsendcomment.setAttribute('type','button');
        btnsendcomment.addEventListener('click', function(event){
            addComment(sessionkey,inputcomment.id,imagesjson[i].id);
            event.preventDefault();
        });
        //btnsendcomment.setAttribute('onclick',addComment(sessionkey,inputcomment.id,imagesjson[i].id));
        document.getElementById(inputgroupbtn.id).appendChild(btnsendcomment);

        btnsendcomment.innerHTML = '<i class="fa fa-send"></i>Send';
    }
    return;
}


/*
 * THUMBNAILS
 */

window.onload = function() {
	mypagecontent.initialize();
	mypagecontent.initializeLeftMenu();
	mypagecontent.initializeThumbnails();
}
