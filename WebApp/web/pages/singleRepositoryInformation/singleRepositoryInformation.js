var REPOSITORYNAME;
var RRName;
var refreshRate = 2000; //mili seconds
var nameFromPrompt;
var pathFromPrompt;
//activate the timer calls after the page is loaded
$(function() {
    //prevent IE from caching ajax calls
    $.ajaxSetup({cache: false});

    //getUsername();
    url = new URL(window.location.href);
    REPOSITORYNAME = url.searchParams.get("repositoryName");
    document.getElementById("repoName").innerHTML = REPOSITORYNAME;

    getRRName();
    showWorkingCopyStatus();
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

    var content = document.getElementById("fileContent").value;
    document.getElementById("fileContent").value = "";
    $.ajax({
        url: "collaboration?method=checkLocationPath&pathFromPrompt=" + pathFromPrompt +
            "&repoName=" + REPOSITORYNAME,
        success: function (message) {
            if (!message.localeCompare("OK")) {
                $.ajax({
                    url: "collaboration?method=createFile&pathFromPrompt=" + pathFromPrompt +
                        "&repoName=" + REPOSITORYNAME + "&content=" + content +
                        "&nameFromPrompt=" + nameFromPrompt,
                    success: function (r) {
                        console.log(r);
                        showWorkingCopyStatus();
                        alert(r);
                    },
                    error: function (message) {
                        console.log(message);
                    }
                })
            } else {
                alert(message);
            }
        }
    })

}

function onEditFile() {
    document.getElementById("fileContent").readOnly =false;
    alert("Edit the file in \'content area\' and press save");
    console.log(document.getElementById("fileContent").name);

}
function onAddFile(event){
    document.getElementById("fileContent").value="";
    document.getElementById("fileContent").readOnly =false;

    nameFromPrompt = prompt("Please enter new file name", '');
    pathFromPrompt = prompt("Please enter relative path for the new file, the default will be the root ! ", '');
    alert('File name : ' + nameFromPrompt + '\nFile path : ' + pathFromPrompt
    + '\nPlease enter new file content in the \'File content\' area and press \'create new file\'');

}
function onDeleteFile() {
    var path= document.getElementById("fileContent").name;
    path = path.replace(/\\/g, "%5C");
    $.ajax({
        url: "collaboration?method=deleteFile&repoName=" + REPOSITORYNAME+
        "&path=" +path,
        success: function (message) {
            document.getElementById("fileContent").value=" ";
            showWorkingCopyStatus();
            alert(message);
        },
        error: function (message) {
            console.log(message);
            document.getElementById("fileContent").value=" ";
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
       // var fileName = split[split.length-1];
        $('<li onclick="onClickFile(event)" id="'+ path +'">' + path + '</li>').appendTo($("#currentCommitFiles"));

    });
}
function onCommit(event){
    var commitMessage=document.getElementById("commit").value;
    $.ajax({

        url: "collaboration?method=commit&repoName=" + REPOSITORYNAME
            + "&commitMessage=" + commitMessage,
        success: function (message) {
            //document.getElementById("commit").innerText = message;
            alert(message);
            document.getElementById("commit").value="";
            $("#workingCopyStatuslist").empty();
        },
        error: function (message) {
            console.log(message);
            alert(message);
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

function onSave(){
    var content =  document.getElementById("fileContent").value ;
    var path = document.getElementById("fileContent").name;
    path = path.replace(/\\/g, "%5C");

    document.getElementById("fileContent").value =" ";
    document.getElementById("fileContent").readOnly =true;
    $.ajax({
        url: "collaboration?method=editFile&path="+ path+
            "&repoName=" + REPOSITORYNAME + "&content=" + content,
        success: function (message) {
            showWorkingCopyStatus();
            alert(message);
        },
        error: function (message) {
            console.log(message);
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
                $('<li>  File name:  ' + info + '<br/>' +
                    '</li>').appendTo($("#commitFileslist"));
            });
        }
    })
}

function showWorkingCopyStatus() {
      $("#workingCopyStatuslist").empty();
    $.ajax({
        url: "collaboration?method=getWorkingCopyChanges"+
            "&repoName=" + REPOSITORYNAME,
        success: function (r) {
            console.log(r);

            if(r.newFile.length>0 ||r.editFile.length>0 || r.deleteFile.length>0) {
                var editFile = r.editFile;
                var deleteFile = r.deleteFile;
                var newFile = r.newFile;

                $.each(editFile || [], function (index, editItem) {
                    $('<li> ' + editItem + '<br/>' +
                        '</li>').appendTo($("#workingCopyStatuslist"));
                });

                $.each(deleteFile || [], function (index, deleteItem) {
                    $('<li> ' + deleteItem + '<br/>' +
                        '</li>').appendTo($("#workingCopyStatuslist"));
                });

                $.each(newFile || [], function (index, newItem) {
                    $('<li>' + newItem + '<br/>' +
                        '</li>').appendTo($("#workingCopyStatuslist"));
                });
            }
            else {
                $('<li> There is no changes since last commit! <br/>' +
                    '</li>').appendTo($("#workingCopyStatuslist"));
            }
        }
    })
}