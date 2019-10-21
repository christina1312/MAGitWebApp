package servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.ServletUtils;
import System.GitManager;
import utils.SessionUtils;
import System.BasicMAGitManager;
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
       BasicMAGitManager rrRepo = currGitManager.getRepositoryByUserName(username, RRName); // todo get RR user name
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
                   List<String> res = repo.getCommitsFileslist(commitName);
                   json = gson.toJson(res);
               } catch (Exception ex) {
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "pull": {
             try {
                 String branchName = request.getParameter("branchName"); // todo verify the branch name
                 ArrayList<Commit> delta = rrRepo.pull();
                 repo.updateBranchAfterPull(branchName,delta);
                 json = gson.toJson("Pulled successfully!");
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
                   repo = new BasicMAGitManager(username);
                   String newRepoName=request.getParameter("newRepoName");
                   String repositoryPath = request.getParameter("repositoryPath");
                   repo.cloneRepository(repositoryPath, LRPath+"\\"+newRepoName, newRepoName);
                   currGitManager.addRepository(repo,username);
                   json = gson.toJson("Forked successfully!");
               }
               catch(Exception ex){
                   json = gson.toJson(ex.getMessage());
               }
               break;
           }
           case "createFile": {
               break;
           }
           case "deleteFile": {
               break;
           }
           case "modifyFile": {
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