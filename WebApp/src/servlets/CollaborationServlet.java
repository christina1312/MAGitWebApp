package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import utils.ServletUtils;
import System.GitManager;
import utils.SessionUtils;
import System.BasicMAGitManager;
import System.NotificationManager;
import System.Commit;
import System.Branch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;


@WebServlet(name = "CollaborationServlet", urlPatterns = {"/pages/singleRepositoryInformation/collaboration"})
public class CollaborationServlet extends HttpServlet {

   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws Exception {
       String username = SessionUtils.getUsername(request);
       response.setContentType("application/json");
       String g = request.getParameter("method");

       GitManager currGitManager = ServletUtils.getGitManager(getServletContext());       Gson gson = new Gson();
       String repoName = request.getParameter("repoName");
       String RRName = request.getParameter("RRName");
       BasicMAGitManager rrRepo = currGitManager.getRepositoryByUserName("INCOGNITO", RRName); // todo get RR user name
       String LRPath="C:\\magit-ex3\\"+username;
       String json=null;
       BasicMAGitManager repo=null;

       if(!g.equalsIgnoreCase("fork")) {
           repo = currGitManager.getRepositoryByUserName(username, repoName);
       }

       switch (g) {
           case "getRemoteRepository": {
               String rrName = repo.getRRName();
               json = gson.toJson(rrName);
               break;
           }
           case "getBranchesList": {
               Set<String> res = repo.getbranchesNameList();
               json = gson.toJson(res);
               break;
           }
           case "getHeadBranch": {
               String headBranch = repo.getHeadBranch();
               json = gson.toJson(headBranch);
               break;
           }
           case "checkout": {
               try {
                   String name = request.getParameter("newBranch");
                   repo.CheckoutHeadBranch(name);
                   json = gson.toJson(name + " checked out successfully!");
               } catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "createNewBranch": {
               try {
                   String name = request.getParameter("newBranch");
                   repo.CreateNewBranch(name, "master");
                   json = gson.toJson(name + " created successfully!");
               } catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "getHeadBranchCommitsList": {
               List<Commit> commitList = repo.getCommitList();
               List<Map<String, String>> resList = new ArrayList<>();

               for (Commit commit : commitList) {
                   Map<String, String> tempMap = new HashMap<>();

                   tempMap.put("sha1", commit.getSha1());
                   tempMap.put("Message", commit.getMessage());
                   tempMap.put("Date", commit.getDateCreated());
                   tempMap.put("CreatedBy", commit.getCreatedBy());
                   resList.add(tempMap);
               }
               Type resultType = new TypeToken<List<Map<String, String>>>() {
               }.getType();
               json = gson.toJson(resList, resultType);
               break;
           }
           case "getCommitsFileslist": {
               try {
                   String commitName = request.getParameter("commitName");
                   List<String> res = repo.getCommitsFileslist(commitName, false);
                   json = gson.toJson(res);
               } catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "pull": {
             try {
                 String branchName = request.getParameter("branchName"); // todo verify the branch name
                 if(repo.checkBeforePull(branchName)) {
                     Branch RB = repo.searchBranchByName(RRName + "\\" + branchName);
                     ArrayList<Commit> delta = rrRepo.pull(RB, branchName);
                     repo.updateBranchAfterPull(branchName, delta);
                     json = gson.toJson("Pulled successfully!");
                 }
                 // else throws execption todo check it !!
             }
             catch(Exception ex){
                 json = gson.toJson(ex.getMessage());
             }
               break;
           }
           case "push": {
              try {
                  String branchName = request.getParameter("branchName");
                  Branch newBranch=repo.searchBranchByName(branchName); // in LR side
                  rrRepo.createRRBranchesFiles(newBranch);
                  json = gson.toJson("Pushed successfully!");
              }
              catch(Exception ex){
                  json = gson.toJson(ex.getMessage());
              }
              break;
             }
           case "fork": {
               try {
                   String repoUserName = request.getParameter("repoUserName");
                   if(!username.equalsIgnoreCase(repoUserName)) {


                       repo = new BasicMAGitManager(username);
                       String repositoryPath = request.getParameter("repositoryPath");
                       repo.cloneRepository(repositoryPath, LRPath + "\\" + repoName, repoName);
                       currGitManager.addRepository(repo, username);

                       String message = repoName + " forked by " + username;
                       currGitManager.addUserNotification(repoUserName, message);
                       json = gson.toJson("Forked successfully!");
                   }
                   else
                   {
                       json = gson.toJson("You cannot fork your own repository!");
                   }
               }
               catch(Exception ex){
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "getcurrentCommitFiles": {
               try {
                   List<String> res = repo.getCurrentWorkingCopyFileslist();
                   json = gson.toJson(res);
               }
            catch (Exception ex) {
               json = gson.toJson(ex.getMessage());
           }
               break;
           }
           case "getWorkingCopyChanges": {
               try {
                   Map<String,List<String >> res = repo.ShowWorkingCopyStatus();

                   Type resultType = new TypeToken<Map<String, String>>() {
                   }.getType();
                   json = gson.toJson(res, resultType);
               }
               catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "commit": {
               try {
                   repo.DoCommit("newCommit");
                   json = gson.toJson("All changes committed successfully!");
               }catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "getFileContent": {
               try {
                   String path = request.getParameter("path");
                   String res = repo.getFileContent(path);
                   Map<String, String> tempMap = new HashMap<>();
                   tempMap.put("path",path);
                   tempMap.put("content", res);
                   Type resultType = new TypeToken<Map<String, String>>() {
                   }.getType();
                   json = gson.toJson(tempMap, resultType);

               }catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "createFile": {
               try {
                   String path = request.getParameter("pathFromPrompt");
                   String name = request.getParameter("nameFromPrompt");
                   if(name!=null && name!="") {
                       String newContent = request.getParameter("content");
                       String pathToCheck="";
                       if (path.equalsIgnoreCase(""))
                           pathToCheck = LRPath + "\\" + repoName + "\\" + name+".txt";
                       else {
                               pathToCheck = LRPath + "\\" + repoName + "\\" + path + "\\" + name+".txt";
                       }
                       if (!repo.checkIfFileExistsInLocation(pathToCheck)) {
                           repo.createNewFile(pathToCheck, newContent);
                           json = gson.toJson("'"+name+".txt"+"'" + " has been created successfully!");
                       } else {
                           json = gson.toJson(name + " is already exists in the given path!");
                       }
                   }
                   else {
                       json = gson.toJson("File name cannot be empty!");
                   }
               } catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "checkLocationPath": {
               String path = request.getParameter("pathFromPrompt");
               if(path.equalsIgnoreCase("")) {
                   json = gson.toJson("OK");
                   break;
               }
               if (!repo.checkIfFileExistsInLocation(LRPath + "\\" + repoName + "\\" + path))
                   json = gson.toJson("The path " + "'" + path + "'" + " doesnt exists!");
               else
                   json = gson.toJson("OK");
               break;
           }
           case "deleteFile": {
               try {
                   String path = request.getParameter("path");
                   repo.deleteFile(path);
                   json = gson.toJson("The file was deleted !");

               } catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "editFile": {
               try {
                   String path = request.getParameter("path");
                   String newContent = request.getParameter("content");
                   repo.editFile(path, newContent);
                   json = gson.toJson("The file was edited !");
               } catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
       }
       try (PrintWriter out = response.getWriter()) {
           out.println(json);
           out.flush();
       }
   }

       @Override
       protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           try {
               processRequest(request, response);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }