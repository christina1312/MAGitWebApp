var REPOSITORYNAME;
var RRName;
var refreshRate = 2000; //mili seconds
//activate the timer calls after the page is loaded
$(function() {
    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    //getUsername();
    url = new URL(window.location.href);
    REPOSITORYNAME = url.searchParams.get("repositoryName");
    document.getElementById("repoName").innerHTML = REPOSITORYNAME;

    getRRName();
    getHeadBranchName();
    getBranchesList();
    setInterval(ajaxHeadBranchCommitsList, refreshRate);
    setInterval(ajaxCurrentCommitFiles, refreshRate);

});

function onClickCheckoutButton() {
    var newBranchName=document.getElementById("checkout").value;

    $.ajax({
        url: "collaboration?method=checkout&repoName=" + REPOSITORYNAME +
            "&newBranch=" + newBranchName,
        success: function (message) {
            document.getElementById("checkoutMessage").innerText = message;
            document.getElementById("checkout").value="";
            getHeadBranchName();
        },
        error: function (message) {
            console.log(message);
            document.getElementById("checkout").value="";
        }
    });
}

function onClickPushButton() {
    var BranchName=document.getElementById("push").value;
    $.ajax({

        url: "collaboration?method=push&repoName=" + REPOSITORYNAME +
            "&branchName=" + BranchName,
        success: function (message) {
            document.getElementById("pushMessage").innerText = message;
            document.getElementById("push").value="";
            getHeadBranchName();
        },
        error: function (message) {
            console.log(message);
            document.getElementById("push").value="";
        }
    });
}

function onClickPullButton() {
    var BranchName=document.getElementById("pull").value;
    $.ajax({
        url: "collaboration?method=pull&repoName=" + REPOSITORYNAME+
            "&branchName=" + BranchName + "&RRName=" + RRName,
        success: function (message) {
            document.getElementById("pullMessage").innerText = message;
            document.getElementById("pull").value="";
            getHeadBranchName();
        },
        error: function (message) {
            console.log(message);
            document.getElementById("pull").value="";
        }
    });
}

function onCreateFile() {

    $("#fileContent").empty();
    // document.getElementById("fileContent").readOnly =false;
    var content =  document.getElementById("fileContent").value ;
    var path = document.getElementById("fileContent").name;
    $.ajax({
        url: "collaboration?method=createFile&path="+ path+
            "&repoName=" + REPOSITORYNAME + "&content=" + content,
        success: function (r) {
            console.log(r);
            document.getElementById("fileContent").innerHTML = r.content;
            document.getElementById("fileContent").name = r.path;
            document.getElementById("fileContent").readOnly =true;
        }
    })
}

function onEditFile() {
   // document.getElementById("saveButton").style.visibility = 'visible';
    document.getElementById("fileContent").readOnly =false;
    console.log(document.getElementById("fileContent").name);

}
function onAddFile(){
    var prompt1 = prompt("Please enter new file name");
    if (prompt1 != null) {
        alert("Please enter new file content in the 'File content' area and press 'create new file'!");
    }
}
function onDeleteFile() {
    var path= document.getElementById("fileContent").name;
    $.ajax({
        url: "collaboration?method=deleteFile&repoName=" + REPOSITORYNAME+
        "&path=" +path,
        success: function (message) {
            document.getElementById("deleteFileMessage").innerText = message;
            document.getElementById("deleteFile").value="";
            getHeadBranchName();
        },
        error: function (message) {
            console.log(message);
            document.getElementById("deleteFile").value="";
        }
    });
}

function onClickCreateNewBranchButton() {
    var newBranchName=document.getElementById("createNewBranch").value;

    $.ajax({
        url: "collaboration?method=createNewBranch&repoName=" + REPOSITORYNAME +
            "&newBranch=" + newBranchName,
        success: function (message) {
            document.getElementById("createNewBranchMessage").innerText = message;
            document.getElementById("createNewBranch").value="";
            getBranchesList();
        },
        error: function (message) {
            console.log(message);
            document.getElementById("createNewBranch").value="";
        }
    });
}

function getRRName() {

    $.ajax({
        url: "collaboration?method=getRemoteRepository&repoName=" + REPOSITORYNAME,
        success: function (rrName) {
            if(rrName== null) {
                rrName="There is no remote repository :(";
            }
            var prefix = document.getElementById("remoteRepoName").innerHTML;
            document.getElementById("remoteRepoName").innerHTML = prefix + " " + rrName;
            RRName=rrName;
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function getRRName() {

    $.ajax({
        url: "collaboration?method=getRemoteRepository&repoName=" + REPOSITORYNAME,
        success: function (rrName) {
            if(rrName== null) {
                rrName="There is no remote repository :(";
            }
            var prefix = document.getElementById("remoteRepoName").innerHTML;
            document.getElementById("remoteRepoName").innerHTML = prefix + " " + rrName;
            RRName=rrName;
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function getHeadBranchName() {
    $.ajax({
        url: "collaboration?method=getHeadBranch&repoName=" + REPOSITORYNAME,
        success: function (headBranchName) {
            document.getElementById("HeadBranchName").innerHTML = headBranchName;
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function getBranchesList() {
    $("#branchesList").empty();

    $.ajax({
        url: "collaboration?method=getBranchesList&repoName=" + REPOSITORYNAME,
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

function ajaxHeadBranchCommitsList() {

    $.ajax({
        url: "collaboration?method=getHeadBranchCommitsList&repoName=" + REPOSITORYNAME ,
        success: function(commits) {
            console.log("before callback");
            refreshHeadBranchCommitsList(commits);
        },
        error: function(error){
            console.log(error);
        }
    });
}

function ajaxCurrentCommitFiles() {

    $.ajax({
        url: "collaboration?method=getcurrentCommitFiles&repoName=" + REPOSITORYNAME ,
        success: function(commits) {
            console.log("before callback");
            refreshCurrentCommitFile(commits);
        },
        error: function(error){
            console.log(error);
        }
    });
}
function refreshCurrentCommitFile(r) {
    //clear all current users
    $("#currentCommitFiles").empty();

    r.forEach(function (path) {
        var split = path.split(/\\/);
        var fileName = split[split.length-1];
        $('<li onclick="onClickFile(event)" id="'+ path +'">' + fileName + '</li>').appendTo($("#currentCommitFiles"));

    });
}
function onCommit(event){
    var commitMessage=document.getElementById("commitMessage").value;
    $.ajax({

        url: "collaboration?method=commit&repoName=" + REPOSITORYNAME
            + "&commitMessage=" + commitMessage,
        success: function (message) {
            document.getElementById("commitMessage").innerText = message;
            document.getElementById("commit").value="";
        },
        error: function (message) {
            console.log(message);
            document.getElementById("commit").value="";
        }
    });
}

function onClickFile(event){
    $("#fileContent").empty();

    var path = event.target.id.replace(/\\/g, "%5C");
    $.ajax({
        url: "collaboration?method=getFileContent&path="+ path+
            "&repoName=" + REPOSITORYNAME,
        success: function (r) {
            console.log(r);
            document.getElementById("fileContent").value = r.content;
            document.getElementById("fileContent").name = r.path;
            document.getElementById("fileContent").readOnly =true;
        }
    })
}

function onSave(event){
    $("#fileContent").empty();
   // document.getElementById("fileContent").readOnly =false;

    var content =  document.getElementById("fileContent").value ;
    var path = document.getElementById("fileContent").name;
    $.ajax({
        url: "collaboration?method=editFile&path="+ path+
            "&repoName=" + REPOSITORYNAME + "&content=" + content,
        success: function (r) {
            console.log(r);
            document.getElementById("fileContent").innerHTML = r.content;
            document.getElementById("fileContent").name = r.path;
            document.getElementById("fileContent").readOnly =true;
        }
    })
}
function refreshHeadBranchCommitsList(commits) {
    //clear all current users
    $("#headBranchCommitsList").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    commits.forEach(function (info) {
        $('<li onclick="showSingleCommitInfo(event)" id='+info.sha1+'>' +
            'The commit sha1 is: '+info.sha1 + '<br/>' +
            'The message of the commit: '+info.Message + '<br/>' +
            'Date of creation: '+info.Date + '<br/>' +
            'Created by: '+info.CreatedBy + '<br/>' +
            '</li>').appendTo($("#headBranchCommitsList"));
    });
}

function showSingleCommitInfo(ev) {
    var commitName=ev.target.id;

    $("#commitFileslist").empty();
    $.ajax({
        url: "collaboration?method=getCommitsFileslist&commitName="+ commitName+
            "&repoName=" + REPOSITORYNAME,
        success: function (r) {
            console.log(r);
            r.forEach(function (info) {
                var split = info.split(/\\/);
                var fileName = split[split.length-1];
                $('<li>  File name:  ' + fileName + '<br/>' +
                    '</li>').appendTo($("#commitFileslist"));
            });
        }
    })
}