<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.frontend.demo.model.User" %>
<%@ page import="java.util.List" %>
<% List<User> users = (List<User>)session.getAttribute("users");%>

<!DOCTYPE html>
<html>
<head>
    <title>Users Page</title>
    <script src='/javascript/jquery-3.6.4.min.js'></script>
    <script src='/javascript/jquery.modal.min.js'></script>
    <script src='/javascript/listusers.js'></script>
    <script src='/javascript/logout.js'></script>
    <script src='/javascript/generic.js'></script>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>
    <jsp:include page="../templates/header.jsp" />
    <div class="container">
        <div class="card">
            <h2 class="myheader">Welcome to users application management!</h2>
            <div class="container">
                <p>Here are the available users:</p>
                <table class="mytable">
                    <thead>
                        <tr class="mytr">
                            <th class="myth"> ID </th>
                            <th class="myth"> Username </th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for(User user : users){%>
                        <tr class="user mytr" id="<%= user.getId() %>">
                            <td class="mytd"><%= user.getId()       %></td>
                            <td class="mytd"><%= user.getUsername() %></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
        <br/>
        <div class="container">
            <a href="/users/create/" class="mybutton">Create User</a>
        </div>
    </div>
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>
