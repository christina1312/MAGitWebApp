var REPOSITORYNAME;
//activate the timer calls after the page is loaded
$(function() {
    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    //getUsername();
    url = new URL(window.location.href);
    REPOSITORYNAME = url.searchParams.get("repositoryName");
    document.getElementById("repoName").innerHTML = REPOSITORYNAME;

    getRRName();

    getBranchesList();
  //  The branches list is refreshed automatically every second
 //   setInterval(ajaxUsersList, refreshRate);


});

function getRRName() {

    $.ajax({
        url: "collaboratin?method=getRemoteRepository&repoName=" + REPOSITORYNAME,
        success: function (RRName) {
            var prefix = document.getElementById("remoteRepoName").innerHTML;
            document.getElementById("remoteRepoName").innerHTML = prefix + " " + RRName;
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function getBranchesList() {
    $("#branchesList").empty();

    $.ajax({
        url: "collaboratin?method=getBranchesList&repoName=" + REPOSITORYNAME,
            success: function (r) {
                console.log(r);
                r.forEach(function (info) {
                    $('<li>  Branch name:  ' +info  + '<br/>' +
                        '</li>').appendTo($("#brancheslist"));
                });
            },
        error: function (error) {
            console.log(error);
        }
    });
}