<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="../../common/jquery-2.0.3.min.js"></script>
    <script src="usersPrivateAccount.js"></script>
    <link rel="stylesheet" type="text/css" href="usersPrivateAccount.css"/>

</head>
<body>
<h1 align="center" style="color:blue;">User Private Account</h1><br/>

<div class="tableDiv" >

<table border="1" style="width:100%;table-layout: fixed;">
 <tr>
     <td width="20%" style="height: 300px;">
         <h3>Select a File:</h3>
         <form id="uploadForm" action="uploadfile" enctype="multipart/form-data" method="POST">
             <input type="file" name="file1" id="file1" /><br>
             <input type="Submit" value="Upload File" /><br>
         </form>
         <a href="../../logout">Logout</a>
         <br/><br/><br/>
         <% Object Message = request.getAttribute("Message");%>
         <% if (Message != null) {%>
         <span class="bg-danger" style="color:red;"><%=Message%></span>
         <% } %>
     </td>
     </td>
     <td width="50%" rowspan="2" >
         <div style="height:100%;max-height: 600px;overflow:auto;">
             <h5>
              Repositories list:
             </h5>
             <ul id="repositorieslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
             </ul>
         </div>

     </td>
     <td><div style="height:100%;max-height: 300px;overflow:auto;">
         <h5>
            Users list:
         </h5>
         <br/>
         <ul id="userslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
         </ul>
     </div>
     </td>
 </tr>
 <tr>
     <td><div width="20%" style="height:100%;max-height: 300px;overflow:auto;">
         <h5>
            Notifications:
         </h5>
<%--         <button onclick="addNote(event)"> Add note</button>--%>
         <br/>
         <ul id="notificationslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
         </ul>
     </div>
     </td>
     <td><div  style="height:100%;max-height: 300px;overflow:auto;">
         <h5>
          Users Repositories List:
         </h5>
         <ul id="repositoriesUserlist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
         </ul>
         <br/>
     </div>
     </td>
 </tr>
</table>
</div>
</body>
</html>
