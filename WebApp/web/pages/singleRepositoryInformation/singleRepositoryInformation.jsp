<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Single repository information</title>
    <script type="text/javascript" src="../../common/jquery-2.0.3.min.js"></script>
    <script src="singleRepositoryInformation.js"></script>
</head>

<body>
<h1  id="repoName"  align="center" style="color:blue;">User Private Account</h1><br/>
<table border="1" style="width:100%;table-layout: fixed;">
    <tr height="20%">
        <td width="30%">
            <h4 id="remoteRepoName">
                Remote Repository:
            </h4>
        </td>
    </tr>
    <tr height="20%">
        <td>
            <div  style="height:100%;max-height: 300px;overflow:auto;">
            Branches list:
            <ul id="brancheslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
            </ul><br/>
            </div>
        </td>
        <td width="25%">Single branch info:</td>
        <td width="25%">
            <p>&nbsp;&nbsp;</p>
            <p>checkout branch button&nbsp;</p>
            <p>&nbsp;</p>
        </td>
        <td width="25%">
            <p>Create new branch button&nbsp;</p>
        </td>
    </tr>
    <tr height="20%">
        <td  width="25%">
            <h3 style="color:blue;">Collaboration</h3><br/>
            <p>Pull button</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
        </td>
        <td  width="25%">
            <p>&nbsp;fork button</p>
        </td>
        <td  width="25%">&nbsp;
            <p>Push new branch button</p>
            <p>&nbsp;</p>
        </td>
        <td  width="25%">push button</td>
    </tr>
    <tr height="20%">
        <td width="25%">head branch commits list:</td>
        <td colspan="2" rowspan="2">
            <p style="text-align: center;">WC</p>
        </td>
        <td width="25%">&nbsp;
            <p>modify file button</p>
            <p>delete file button</p>
            <p>create new file button</p>
        </td>
    </tr>
    <tr height="20%">
        <td width="25%">Notifiation</td>
        <td width="25%">&nbsp;</td>
    </tr>
</table>
</body>
</html>


