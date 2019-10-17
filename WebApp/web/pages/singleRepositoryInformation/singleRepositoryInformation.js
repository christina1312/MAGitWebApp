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
    //The users list is refreshed automatically every second
 //   setInterval(ajaxUsersList, refreshRate);

    //The repositories list is refreshed automatically every second
  //  setInterval(ajaxRepositoriesList, refreshRate);

    //The Notification list is refreshed automatically every second
 //   setInterval(ajaxNotificationsList, refreshRate);
    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    //triggerAjaxChatContent();
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