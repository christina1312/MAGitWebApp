<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Single repository information</title>
    <script type="text/javascript" src="../../common/jquery-2.0.3.min.js"></script>
    <script src="singleRepositoryInformation.js"></script>
    <script src="singleRepositoryInformation.css"></script>
</head>

<body>
<h1  id="repoName"  align="center" style="color:blue;">Repo name</h1>
<table border="1" style="width:100%;table-layout: fixed;">
    <tr height="10%">
        <td width="30%" style="max-height: 50px">
            <h4  id="remoteRepoName">
                 Remote Repository:
            </h4>
            <h4 id="remoteUserName">
                Remote user name:
            </h4>
        </td>
        <td width="25%" style="max-height: 100px">
            <h5 >Please enter branch name for push:</h5>
            <input id="push" type="text" name="branchName" class=""/>
            <button onclick="onClickPushButton(event)">Push</button>
            <div>
                <h5 id="pushMessage" style="color: blue;"></h5>
            </div>
        </td>
        <td width="25%" style="max-height: 100px">
            <h5>Please enter branch name for pull:</h5>
            <input id="pull" type="text" name="branchName" class=""/>

            <button onclick="onClickPullButton(event)">Pull</button>
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
            <button onclick="onClickPullButton(event)">Create PR</button>
        </td>
    </tr>
    <tr height="20%">
        <td style="max-height: 150px">
            <div style="height:100%;max-height: 150px;overflow:auto;">
            Branches list:
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
            <button onclick="onClickCheckoutButton(event)"> Checkout</button>
            <div>
                <h5 id="checkoutMessage" style="color: blue;"></h5>
            </div>
        </td>
        <td width="25%" style="max-height: 150px">
            <h5>Please enter new branch name :</h5>
            <input id="createNewBranch" type="text" name="branchName" class=""/>
            <button onclick="onClickCreateNewBranchButton(event)"> Create</button>
            <div>
                <h5 id="createNewBranchMessage" style="color: blue;"></h5>
            </div>
        </td>
    </tr>
    <tr height="20%">
        <td style="max-height: 150px">
            <div style="height:100%;max-height: 150px;overflow:auto;">
                head branch commits list:
                <ul id="headBranchCommitsList" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
                </ul><br/>
            </div>
        </td>
        <td colspan="2" rowspan="2">
            <p style="text-align: center;">WC</p>
        </td>
        <td width="25%" >&nbsp;
            <button onclick="onModifyFile(event)">modify file</button>
            <button onclick="onDeleteFile(event)" >delete file</button>
            <button onclick="onCreateFile(event)">create new file</button>
        </td>
    </tr>
    <tr height="20%" style="max-height: 150px">
        <td style="max-height: 150px">
            <div style="height:100%;max-height: 150px;overflow:auto;">
                Commit files:
                <ul id="commitFileslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
                </ul><br/>
            </div>
        </td>
    </tr>
    <tr height="20%" style="max-height: 150px">
        <td><div width="20%" style="height:100%;max-height: 150px;overflow:auto;">
            Notifications:
            <button onclick="addNote(event)"> Add note</button>
            <br/>
            <ul id="notificationslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
            </ul>
        </div>
        </td>
    </tr>
</table>
</body>
</html>


