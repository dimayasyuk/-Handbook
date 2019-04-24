<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>JeyRubyHandbook</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div align="center">
    <jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
    <div class="container">
        <h2>Users</h2>
        <table class="table">
            <thead>
            <tr>
                <th>Login</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><c:out value="${user.login}"/></td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.surname}"/></td>
                    <select class="form-control" id="role" name="role">
                        <c:forEach var="role" items="${roles}">
                            <option value="${user.roleId}" ${user.roleId == role.id ? 'selected="selected"' : ''}>${role.name}</option>
                        </c:forEach>
                    </select>
                    <%--<td style="width: 170px">--%>
                        <%--<button class="btn btn-primary" style="background-color: aliceblue;">--%>
                            <%--<a href="edit?id=<c:out value='${role.id}' />">Save</a>--%>
                        <%--</button>--%>
                        <%--&nbsp;&nbsp;--%>
                        <%--<button class="btn btn-primary" style="background-color: aliceblue;">--%>
                            <%--<a href="delete?id=<c:out value='${category.id}' />">Delete</a>--%>
                        <%--</button>--%>
                    <%--</td>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>