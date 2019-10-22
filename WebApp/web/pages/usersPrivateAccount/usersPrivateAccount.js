var repositoryNameSaver;
var chatVersion = 0;
var refreshRate = 2000; //mili seconds
//var USER_LIST_URL = buildUrlWithContextPath("userslist");
//var REPOSITORY_LIST_URL= buildUrlWithContextPath("repositorieslist");
var  CHAT_LIST_URL= "";
var username;

function refreshUsersList(users) {
    //clear all current users
    $("#userslist").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(users || [], function (index, username) {
        console.log("Adding user #" + index + ": " + username);
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $('<li onclick="showUserRepositories(event)" id="'+ username +'">' + username + '</li>').appendTo($("#userslist"));
    });
}

function addNote(){
    var note= "bla bla blaaa"
    $.ajax({
        type: "POST",
        url: "notificationslist",
        data: "note=" + note,
        dataType: 'json',
        success: function (r) {
            console.log("note added");
        }

    })
}
function showUserRepositories(ev) {
    var userName = ev.target.id;
    $("#repositoriesUserlist").empty();
    $.ajax({
        url: "repositorieslist?method=getRepositoriesList&userName=" + userName,
        success: function (r) {
            console.log(r);
            r.forEach(function (info) {

                $('<li onclick="onCloneRepository(event)" username="'+userName+'" id="'+info.Path+'" name="' +info.RepositoryName+'">  Repository name:  ' + info.RepositoryName + '<br/>' +
                        'Active branch name: '+info.ActiveBranchName + '<br/>' +
                        'Branch amount: '+info.BranchAmount + '<br/>' +
                        'Last commit time: '+info.LastCommitTime + '<br/>' +
                        'Last commit message: '+info.LastCommitMessage + '<br/>' +
                        '</li>').appendTo($("#repositoriesUserlist"));
            });
        }
    })
}

function onCloneRepository(event) {
    var repositoryPath = event.target.id;
    var repoName=event.target.attributes.name.value;
    var repoUserName=event.target.attributes.username.value;
 //   repositoryPath = "C:%5Cmagit-ex3%5Cbb%5Crepo 2";
    repositoryPath = repositoryPath.replace(/\\/g, "%5C");
    var txt;
    if (confirm("Do you want to fork this repository?")) {
        $.ajax({
            url: "/Web_war/pages/singleRepositoryInformation/collaboration?method=fork&repositoryPath=" + repositoryPath+
            "&repoName="+ repoName + "&repoUserName="+ repoUserName,
            success: function (message) {
            txt=message;
            alert(txt);
            },
            error: function(message) {
                txt=message;
                alert(txt);
            }
        });
    } else {
        txt="You canceled the operation!";
        alert(txt);
    }
}

function showSingleRepository(event) {
    document.location.href = "../singleRepositoryInformation/singleRepositoryInformation.jsp?repositoryName=" + event.target.id
}

function refreshRepositoriesList(r) {
    //clear all current users
    $("#repositorieslist").empty();

        r.forEach(function (info) {
            $('<li onclick="showSingleRepository(event)" id="'+ info.RepositoryName +'">  Repository name:  ' + info.RepositoryName + '<br/>' +
                'Active branch name: '+info.ActiveBranchName + '<br/>' +
                'Branch amount: '+info.BranchAmount + '<br/>' +
                'Last commit time: '+info.LastCommitTime + '<br/>' +
                'Last commit message: '+info.LastCommitMessage + '<br/>' +
                '</li>').appendTo($("#repositorieslist"));
        });
}

function refreshNotificationsList(r) {
    //clear all current users
    $("#notificationslist").empty();

    if (r.length > 0) {
        r.forEach(function (info) {
            $('<li>' + info.message + '<br/>' + info.time + '<br/>' +
                '</li>').appendTo($("#notificationslist"));
        });
    } else {
        $('<li>There is no notification !' +
            '</li>').appendTo($("#notificationslist"));
    }
}

//entries = the added chat strings represented as a single string
function appendToChatArea(entries) {
    $.each(entries || [], appendChatEntry);

    // handle the scroller to auto scroll to the end of the chat area
    var scroller = $("#chatarea");
    var height = scroller[0].scrollHeight - $(scroller).height();
    $(scroller).stop().animate({ scrollTop: height }, "slow");
}

function appendChatEntry(index, entry){
    var entryElement = createChatEntry(entry);
    $("#chatarea").append(entryElement).append("<br>");
}

function createChatEntry (entry){
    entry.chatString = entry.chatString.replace (":)", "<span class='smiley'></span>");
    return $("<span class=\"success\">").append(entry.username + "> " + entry.chatString);
}

function ajaxUsersList() {
    $.ajax({
        url: "userslist?method=getUsersList" , //USER_LIST_URL,
        success: function(users) {
            console.log("before callback");
            refreshUsersList(users);
        },
        error: function(error){
            console.log(error);
        }
    });
}

function ajaxRepositoriesList() {

    $.ajax({
        url: "repositorieslist?method=getRepositoriesList&userName=" + username, //REPOSITORY_LIST_URL,
        success: function (Repositories) {
            console.log("before callback repository list");
            refreshRepositoriesList(Repositories);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function ajaxNotificationsList() {

    $.ajax({
        url: "notificationslist?username=" + username, //REPOSITORY_LIST_URL,
        success: function (NotificationsList) {
            console.log(NotificationsList);
            refreshNotificationsList(NotificationsList);
        },
        error: function (error) {
            console.log(error);
        }
    });
}
//call the server and get the chat version
//we also send it the current chat version so in case there was a change
//in the chat content, we will get the new string as well
function ajaxChatContent() {
    $.ajax({
        url: CHAT_LIST_URL,
        data: "chatversion=" + chatVersion,
        dataType: 'json',
        success: function(data) {
            console.log("Server chat version: " + data.version + ", Current chat version: " + chatVersion);
            if (data.version !== chatVersion) {
                chatVersion = data.version;
                appendToChatArea(data.entries);
            }
            triggerAjaxChatContent();
        },
        error: function(error) {
            triggerAjaxChatContent();
        }
    });
}

function triggerAjaxChatContent() {
    setTimeout(ajaxChatContent, refreshRate);
}

function getUsername() {

    $.ajax({
        url: "userslist?method=getCurrentUserName", //REPOSITORY_LIST_URL,
        success: function (userName) {
            username = userName;
        },
        error: function(error) {
            console.log(error);
        }
    });
}

//activate the timer calls after the page is loaded
$(function() {
    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    getUsername();

    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);

    //The repositories list is refreshed automatically every second
    setInterval(ajaxRepositoriesList, refreshRate);

    //The Notification list is refreshed automatically every second
    setInterval(ajaxNotificationsList, refreshRate);
    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxChatContent();
});