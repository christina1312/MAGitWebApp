<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Single repository information</title>
    <script type="text/javascript" src="../../common/jquery-2.0.3.min.js"></script>
    <script src="singleRepositoryInformation.js"></script>
</head>

<body>
<h1  id="repoName"  align="center" style="color:blue;">Repo name</h1>
<table border="1" style="width:100%;table-layout: fixed;">
    <tr height="20%">
        <td width="30%">
            <h4 id="remoteRepoName">
                 Remote Repository:
            </h4>
            <h4 id="remoteUserName">
                Remote user name:
            </h4>
        </td>
    </tr>
    <tr height="20%">
        <td max-height: 200px>
            <div  style="height:100%;max-height: 200px;overflow:auto;">
            Branches list:
            <ul id="brancheslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
            </ul><br/>
            </div>
        </td>
        <td width="25%">Head branch :</td>
        <td width="25%">
            <button> checkout branch </button>
        </td>
        <td width="25%">
            <button>Create new branch</button>
        </td>
    </tr>
    <tr height="10%">
        //colaboration buttons
    </tr>
    <tr height="20%">
        <td width="25%">head branch commits list:</td>
        <td colspan="2" rowspan="2">
            <p style="text-align: center;">WC</p>
        </td>
        <td width="25%">&nbsp;
            <button>modify file</button>
            <button>delete file</button>
            <button>create new file</button>
        </td>
    </tr>
    <tr height="20%">
        //<td width="25%">Notifiation</td>
        <td><div width="20%" style="height:100%;max-height: 300px;overflow:auto;">
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


