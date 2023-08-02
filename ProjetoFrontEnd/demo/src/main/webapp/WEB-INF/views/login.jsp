<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.frontend.demo.model.User" %>
<% User user = (User) session.getAttribute("user");%>
<!DOC TYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <script src='/javascript/jquery-3.6.4.min.js'></script>
    <script src='/javascript/jquery.modal.min.js'></script>
    <script src='/javascript/logout.js'></script>
    <script src='/javascript/generic.js'></script>
</head>
<body>
    <jsp:include page="../templates/header.jsp" />
    <form method="POST">
        <div class="container">
            <div class="card">
                <h2 class="myheader">Login</h2>
                <div class="container">
                    <label class="mylabel">Username</label>
                    <input class="myinput" name="username" value="<%= user.getUsername() %>" type="text"/>
                    <label class="mylabel">Password</label>
                    <input class="myinput" name="password" value="<%= user.getPassword() %>" type="password"/>
                </div>
                <br/>
            </div>
            <br/>
            <input class="mybutton" type="submit" value="Submit"/>
        </div>
    </form>
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>
