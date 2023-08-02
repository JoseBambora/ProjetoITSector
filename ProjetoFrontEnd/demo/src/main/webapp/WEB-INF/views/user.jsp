<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.frontend.demo.model.User" %>
<%@ page import="java.util.List" %>
<% User user = (User) session.getAttribute("user");%>

<!DOCTYPE html>
<html>
<head>
    <title>User <%= user.getId()%> </title>
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
            <h2 class="myheader">User <%= user.getId()%> Info</h2>
            <div class="container">
                <table class="mytable">
                    <tbody>
                        <tr class="mytr">
                            <td class="mytd w25 bold">Id</td>
                            <td class="mytd"><%= user.getId() %></td>
                        </tr>
                        <tr class="mytr">
                            <td class="mytd w25 bold">Username</td>
                            <td class="mytd"><%= user.getUsername() %></td>
                        </tr>
                    </tbody>
                </table>
                <br/>
            </div>
        </div>
    </div>
    <br/>
    <div class="container">
        <a class="mybutton" href="/users/update/<%= user.getId() %>">Update User</a>
        <a class="mybutton" href="/users/delete/<%= user.getId() %>">Delete User</a>
        <button class="mybutton" id="goback">Go Back</button>
    </div>
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>
