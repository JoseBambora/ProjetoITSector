<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.frontend.demo.model.User" %>
<%@ page import="java.util.List" %>
<% User user = (User) session.getAttribute("user");%>

<!DOCTYPE html>
<html>
<head>
    <title>Delete User</title>
    <script src='/javascript/jquery-3.6.4.min.js'></script>
    <script src='/javascript/jquery.modal.min.js'></script>
    <script src='/javascript/logout.js'></script>
    <script src='/javascript/generic.js'></script>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>
    <jsp:include page="../templates/header.jsp" />
    <div class="container">
        <div class="card">
            <h3 class="myheader">Are you sure you want to delete user <%= user.getId() %>?</h3>
            <form method="POST">
                <input class="mybutton" type="submit" value="Yes"/>
                <button class="mybutton" id="goback">No</button>
            </form>
            <br/>
        </div>
    </div>
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>
