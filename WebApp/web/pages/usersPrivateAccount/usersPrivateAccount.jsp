<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="../../common/jquery-2.0.3.min.js"></script>
    <script src="usersPrivateAccount.js"></script>

</head>
<body>
<h1 align="center" style="color:blue;">User Private Account</h1><br/>
<table border="1" style="width:100%;table-layout: fixed;">
 <tr>
     <td width="20%" style="height: 300px;">
         <h3>Select a File:</h3>
         <form id="uploadForm" action="uploadfile" enctype="multipart/form-data" method="POST">
             <input type="file" name="file1" id="file1" /><br>
             <input type="Submit" value="Upload File" /><br>
         </form>
         <a href="index.html">Home</a>
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
             Repositories list
             <ul id="repositorieslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
             </ul>
         </div>

     </td>
     <td><div style="height:100%;max-height: 300px;overflow:auto;">
         Users list:
         <br/>
         <ul id="userslist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
         </ul>
     </div>
     </td>
 </tr>
 <tr>
     <td><div width="20%" style="height:100%;max-height: 300px;overflow:auto;">
         Notifications:
         <br/>
         <ul id="notificationlist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
         </ul>
     </div>
     </td>
     <td><div  style="height:100%;max-height: 300px;overflow:auto;">
         Users Repositories List:
         <ul id="repositoriesUserlist" style="color: black; font-family: 'Droid serif', serif; font-size: 20px; font-weight: bold; font-style: inherit; line-height: 44px; margin: 0 0 12px; text-align: left;">
         </ul>
         <br/>
     </div>
     </td>
 </tr>
</table>
</body>
</html>
