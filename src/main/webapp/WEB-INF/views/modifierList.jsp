<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
    <title>C++ vocabulary</title>
</head>
<body>
<div align="center">
    <h2>List of Modifiers</h2>
    <table border="1" cellpadding="5" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Created</th>
            <th>Modified</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${modifierList}" var="modifier">
        <tr>
            <td><c:out value="${modifier.id}"/></td>
            <td><c:out value="${modifier.name}"/></td>
            <td><c:out value="${modifier.description}"/></td>
            <td><c:out value="${modifier.created}"/></td>
            <td><c:out value="${modifier.modified}"/></td>
            <td>
                <a style="color: forestgreen" href="/modifier/edit?id=<c:out value='${modifier.id}'/>">Edit</a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a style="color: crimson" href="/modifier/delete?id=<c:out value='${modifier.id}' />">Delete</a>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"></jsp:include>
</body>
</html>
