<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Administration</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div align="center">
    <jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
    <div class="container">
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
            <c:forEach var="item" items="${users}">
                <form action="/server/user/edit" method="get">
                    <input type="hidden" name="id" value="<c:out value='${item.id}' />"/>
                    <tr style="height: 40px">
                        <td><c:out value="${item.login}"/></td>
                        <td><c:out value="${item.name}"/></td>
                        <td><c:out value="${item.surname}"/></td>
                        <td style="width: 150px; height: 30px">
                            <label hidden for="rol">Role</label>
                            <select class="form-control" id="rol" name="rol">
                                <c:forEach var="rol" items="${roles}">
                                    <option value="${rol.id}" ${item.roleId == rol.id ? 'selected="selected"' : ''}>${rol.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input style="color: forestgreen" type="submit" class="btn" value="Save"/>
                            <a class="btn" style="color: red" href="/server/user/delete?id=<c:out value='${item.id}' />">Delete</a>

                        </td>
                    </tr>
                </form>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${number > recordsPerPage}">
            <nav aria-label="Navigation for users">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="list?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">Previous</a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li class="page-item active"><a class="page-link">
                                        ${i} <span class="sr-only">(current)</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="list?recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="list?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
    </div>
</div>
</body>
</html>