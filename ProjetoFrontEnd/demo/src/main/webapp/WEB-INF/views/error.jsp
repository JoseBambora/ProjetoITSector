<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.frontend.demo.model.User" %>
<%@ page import="java.util.List" %>
<% String message = (String) session.getAttribute("message");%>

<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <script src='/javascript/jquery-3.6.4.min.js'></script>
    <script src='/javascript/jquery.modal.min.js'></script>
    <script src='/javascript/logout.js'></script>
    <script src='/javascript/generic.js'></script>
</head>
<body>
    <jsp:include page="../templates/header.jsp" />
    <div class="container">
        <div class="card">
            <h2 class="myheader">Something went wrong</h2>
            <p><%= message %></p>
        </div>
        <button class="mybutton" id="goback">Go Back</button>
    </div>
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>
