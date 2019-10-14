var repositoryNameSaver;
var chatVersion = 0;
var refreshRate = 2000; //mili seconds
//var USER_LIST_URL = buildUrlWithContextPath("userslist");
//var REPOSITORY_LIST_URL= buildUrlWithContextPath("repositorieslist");
var  CHAT_LIST_URL= "";

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

function showUserRepositories(ev) {
    var userName = ev.target.id;
    $("#repositoriesUserlist").empty();
    $.ajax({
        url: "repositorieslist?method=getRepositoriesList&userName=" + userName,
        success: function (r) {
            console.log(r);
            $('<li>' + r + '</li>').appendTo($("#repositoriesUserlist"));
        }
    })
}

function writeRepositoryToboard(index, repository) {
    var tr = $(document.createElement("tr"));
    var RepositoryName = $(document.createElement("td"));
    var ActiveBranchName = $(document.createElement("td"));
    var NumberOfBranches = $(document.createElement("td"));
    var LastCommitTime = $(document.createElement("td"));
    var LastCommitMessage = $(document.createElement("td"));

    $(RepositoryName).html(repository.name);
    $(ActiveBranchName).html(repository.ActiveBranchName);
    $(NumberOfBranches).html(repository.NumberOfBranches);
    $(LastCommitTime).html(repository.LastCommitTime);
    $(LastCommitMessage).html(repository.LastCommitTime);

    tr.append(RepositoryName);
    tr.append(ActiveBranchName);
    tr.append(NumberOfBranches);
    tr.append(LastCommitTime);
    tr.append(LastCommitMessage);

}
function refreshRepositoriesList(Repositories) {
    //clear all current users
    $("#repositorieslist").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(Repositories || [], function(key, upFile) {
        console.log("Adding repository #" + key + ": " + upFile);

        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $('<option>' + key + '</option>').appendTo($("#repositorieslist"));
    });

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

    // $.ajax({
    //     url: "repositorieslist?method=getRepositoriesList", //REPOSITORY_LIST_URL,
    //     success: function (Repositories) {
    //         console.log("before callback repository list");
    //         refreshRepositoriesList(Repositories);
    //     },
    //     error: function(error) {
    //         console.log(error);
    //     }
    // });
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

//activate the timer calls after the page is loaded
$(function() {
    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);

    //The repositories list is refreshed automatically every second
    //setInterval(ajaxRepositoriesList, refreshRate);

    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxChatContent();
});



function changeName(sel) {
    repositoryNameSaver = (sel.options[sel.selectedIndex].text);
    document.getElementById("RepositoryName").textContent = "Repository name:" + repositoryNameSaver;
    //getPlayerNameUplaod();
    //getGameSize();
    //getGameType();
    //getPlayerInsideRepository();

}

function logout() {
    $.ajax({
        url: "repositorieslist",
        data:{
            button:"logout",
        },
        success: function (test) {
            console.log(test);
            window.location.replace("../index.jsp");
        }
    });
}

function DeleteRepository() {
    $.ajax({
        url: "repositorylist",
        data:{
            repositoryname:repositoryNameSaver,
            button:"DeleteRepository",
        },
        success: function (test) {
            confirm(test);
        }
    });
}

