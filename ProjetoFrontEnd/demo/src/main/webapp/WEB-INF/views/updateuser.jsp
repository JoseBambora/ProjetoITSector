<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.frontend.demo.model.User" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    String action = (String) session.getAttribute("action");
    String header = (String) session.getAttribute("header");
    boolean update = (boolean) session.getAttribute("update");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>
    <script src='/javascript/jquery-3.6.4.min.js'></script>
    <script src='/javascript/jquery.modal.min.js'></script>
    <script src='/javascript/logout.js'></script>
    <script src='/javascript/generic.js'></script>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>
    <jsp:include page="../templates/header.jsp" />
    <form action="<%= action %>" method="POST">
        <div class="container">
            <div class="card">
                <h2 class="myheader"><%= header%></h2>
                <div class="container">
                    <label class="mylabel">Id</label>
                    <input class="myinput" type="text" name="id" value="<%= user.getId() %>" <% if(update) {%> readonly <% } %>/>
                    <br/>
                    <label class="mylabel">Username</label>
                    <input class="myinput" type="text" name="username" value="<%= user.getUsername() %>"/>
                    <br/>
                    <label class="mylabel">Password</label>
                    <input class="myinput" type="password" name="password" value="<%= user.getPassword() %>"/>
                </div>
                <br />
            </div>
            <br/>
            <div>
                <input class="mybutton" type="submit" value="Submit"/>
                <button class="mybutton" id="goback">Go Back</button>
            </div>
        </div>
    </form>
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>
