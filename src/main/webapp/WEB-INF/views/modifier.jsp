<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
    <title>C++ Vocabulary</title>
</head>
<body>
<div align="center">
    <c:if test="${modifier != null}">
    <form action="update" method="post">
        </c:if>
        <c:if test="${modifier == null}">
        <form action="insert" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                    <h2>
                        <c:if test="${modifier != null}">
                            Edit Modifier
                        </c:if>
                        <c:if test="${modifier == null}">
                            Add New Modifier
                        </c:if>
                    </h2>
                <c:if test="${modifier != null}">
                    <input type="hidden" name="id" value="<c:out value='${modifier.id}' />"/>
                </c:if>
                <tr>
                    <th>Name:</th>
                    <td>
                        <input type="text" name="name" size="45" required
                               value="<c:out value='${modifier.name}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Description:</th>
                    <td>
                        <input type="text" name="description" size="45"
                               value="<c:out value='${modifier.description}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Created:</th>
                    <td>
                        <input class="date-cell" type="date" name="created" size="45" readonly="readonly"
                               value="<c:out value='${modifier.created}'/>"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Modified:</th>
                    <td>
                        <input class="date-cell" type="date" name="modified" size="45" readonly="readonly"
                               value="<c:out value='${modifier.modified}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save"/>
                    </td>
                </tr>
            </table>
        </form>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"></jsp:include>
</body>
</html>