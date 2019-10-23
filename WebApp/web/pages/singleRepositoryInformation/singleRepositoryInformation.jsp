<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Single repository information</title>
    <script type="text/javascript" src="../../common/jquery-2.0.3.min.js"></script>
    <script src="singleRepositoryInformation.js"></script>
    <link rel="stylesheet" type="text/css" href="singleRepositoryInformation.css"/>
</head>

<body>
<h1  id="repoName"  align="center" style="color:blue;">Repo name</h1>
<table border="1" style="width:100%;table-layout: fixed;">
    <tr height="10%">
        <td width="30%" style="max-height: 50px">
            <h4  id="remoteRepoName">
                 Remote Repository:
            </h4>
<%--            <h4 id="remoteUserName">--%>
<%--                Remote user name:--%>
<%--            </h4>--%>
<%--            <h4 id="myUserName">--%>
<%--                My user name:--%>
<%--            </h4>--%>
            <a href="../usersPrivateAccount/usersPrivateAccount.jsp">Back</a>
        </td>
        <td width="25%" style="max-height: 100px">
            <h5 >Please enter branch name for push:</h5>
            <input id="push" type="text" name="branchName" class=""/>
            <button class="button" onclick="onClickPushButton(event)">Push</button>
            <div>
                <h5 id="pushMessage" style="color: blue;"></h5>
            </div>
        </td>
        <td width="25%" style="max-height: 100px">
            <h5>Please enter branch name for pull:</h5>
            <input id="pull" type="text" name="branchName" class=""/>

            <button class="button" onclick="onClickPullButton(event)">Pull</button>
            <div>
                <h5 id="pullMessage" style="color: blue;"></h5>
            </div>
        </td>
        <td width="25%" style="max-height: 100px">
            <h5>Please enter target branch name: </h5>
            <input id="target" type="text" name="targetName" class=""/>
            <h5>Please enter base branch name:</h5>
            <input id="base" type="text" name="baseName" class=""/>
            <h5>Please enter message:</h5>
            <input id="message" type="text" name="PRmessage" class=""/>
            <button class="button"  onclick="onClickPullButton(event)">Create PR</button>
        </td>
    </tr>
    <tr height="20%">
        <td style="max-height: 150px">
            <div style="height:100%;max-height: 150px;overflow:auto;">
                <h5>
                    Branches list:
                </h5>
            <ul id="brancheslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
            </ul><br/>
            </div>
        </td>
        <td width="25%"  style="max-height: 150px">
            <h5>
                Head branch :
            </h5>
            <h4 id="HeadBranchName">
            </h4>
        </td>
        <td width="25%" style="max-height: 150px">
            <h5>Please enter branch name for checkout:</h5>
            <input id="checkout" type="text" name="branchName" class=""/>
            <button class="button" onclick="onClickCheckoutButton(event)"> Checkout</button>
            <div>
                <h5 id="checkoutMessage" style="color: blue;"></h5>
            </div>
        </td>
        <td width="25%" style="max-height: 150px">
            <h5>Please enter new branch name :</h5>
            <input id="createNewBranch" type="text" name="branchName" class=""/>
            <button class="button" onclick="onClickCreateNewBranchButton(event)"> Create</button>
            <div>
                <h5 id="createNewBranchMessage" style="color: blue;"></h5>
            </div>
        </td>
    </tr>
    <tr height="20%">
        <td style="max-height: 150px">
            <div style="height:100%;max-height: 150px;overflow:auto;">
                <h5>
                    Head branch commits list:
                </h5>
                <ul id="headBranchCommitsList" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
                </ul><br/>
            </div>
        </td>
        <td colspan="2" rowspan="2">
            <div style="height:100%;max-height: 350px;overflow:auto;">
                <h5>
                    This is your working copy ( All the files of the current commit) :) <button onclick="onAddFile(event)" id="addButton">+Add</button>
                </h5>
                <ul id="currentCommitFiles" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
                </ul><br/>
            </div>

        </td>
        <td width="25%" >&nbsp;

            <h5>File content :
            </h5>
            <textarea id="fileContent" rows="4" cols="40" >
            </textarea>

            <button class="button"  onclick="onEditFile(event)">edit file</button>
            <button class="button" onclick="onSave(event)" id="saveButton" >Save</button>
            <button class="button" onclick="onDeleteFile(event)" >delete file</button>
            <button class="button" onclick="onCreateFile(event)">create new file</button>

        </td>
    </tr>
    <tr height="20%" style="max-height: 150px">
        <td style="max-height: 150px">
            <div style="height:100%;max-height: 150px;overflow:auto;">
                <h5>
                     Commit files:
                </h5>
                <ul id="commitFileslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
                </ul><br/>
            </div>

            <h5>Please enter commit message :</h5>
            <input id="commit" type="text" name="commitMessage" class=""/>
            <button onclick="onCommit(event)"> commit</button>
<%--            <div>--%>
<%--                <h5 id=commitMessage" style="color: blue;"></h5>--%>
<%--            </div>--%>
        </td>
    </tr>
    <tr height="30%" style="max-height: 150px">
        <td><div width="20%" style="height:100%;max-height: 150px;overflow:auto;">
            <h5>
                Notifications:
            </h5>
            <br/>
            <ul id="notificationslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
            </ul>
        </div>
        </td>
        <td colspan="2" ><div width="20%" style="height:100%;max-height: 150px;overflow:auto;">
            <h5>
                Working copy status:
            </h5>
            <br/>
            <ul id="workingCopyStatuslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
            </ul>
        </div>
        </td>
    </tr>
    <tr>
        <br/><br/><br/><br/><br/>
    </tr>
</table>
</body>
</html>


