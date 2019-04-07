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
    <div class="container" style="width: 700px;">
        <c:if test="${section != null}">
        <form action="update" method="post">
            </c:if>
            <c:if test="${section == null}">
            <form action="insert" method="post">
                </c:if>
                <h2>
                    <c:if test="${section != null}">
                        Edit Section
                    </c:if>
                    <c:if test="${section == null}">
                        New Section
                    </c:if>
                </h2>
                <c:if test="${section != null}">
                    <input type="hidden" name="id" value="<c:out value='${section.id}' />"/>
                </c:if>
                <div class="form-group" style="text-align: left;">
                    <label for="nameField">Name</label>
                    <input type="text" class="form-control" required="45" id="nameField" name="name"
                           value="<c:out value='${section.name}' />"/>
                </div>
                <div class="form-group" style="text-align: left;">
                    <label for="descTextArea">Description</label>
                    <textarea class="form-control" id="descTextArea" name="description" value="" rows="10"><c:out value='${section.description}'/></textarea>
                </div>
                <input type="submit" class="btn btn-primary" value="Save"/>
            </form>
    </div>
    <jsp:include page="/WEB-INF/views/footer.jsp"></jsp:include>
</div>
</body>
</html>