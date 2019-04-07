<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
    <title>C++ Vocabulary</title>
</head>
<body>
<div align="center">
    <div class="container" style="width: 700px;">
        <c:if test="${cls != null}">
        <form action="update" method="post">
            </c:if>
            <c:if test="${cls == null}">
            <form action="insert" method="post">
                </c:if>
                <h2>
                    <c:if test="${cls != null}">
                        Edit Topic
                    </c:if>
                    <c:if test="${cls == null}">
                        New Topic
                    </c:if>
                </h2>
                <c:if test="${cls != null}">
                    <input type="hidden" name="id" value="<c:out value='${cls.id}' />"/>
                </c:if>
                <div class="form-group" style="text-align: left;">
                    <label for="nameField">Name</label>
                    <input type="text" class="form-control" required="45" id="nameField" name="name"
                           value="<c:out value='${cls.name}' />"/>
                </div>
                <div class="form-group" style="text-align: left;">
                    <label for="descTextArea">Description</label>
                    <textarea class="form-control" id="descTextArea" name="description" value="" rows="3">
                        <c:out value='${cls.description}'/>
                    </textarea>
                </div>
                <c:if test="${cls != null}">
                    <input type="hidden" name="created" value="<c:out value='${cls.created}' />"/>
                </c:if>
                <div class="form-group" style="text-align: left;">
                    <label for="content">Content</label>
                    <input type="hidden" name="contId" value="<c:out value='${cont.id}' />"/>
                    <textarea class="form-control" id="content" name="content" value="" rows="9">
                        <c:out value='${cont.text}'/>
                    </textarea>
                </div>
                <div class="form-group" style="text-align: left;">
                    <label for="section">Section</label>
                    <select class="form-control" id="section" name="section">
                        <c:forEach var="section" items="${sections}">
                            <option value="${section.id}" ${section.id == cls.modifierId ? 'selected="selected"' : ''}>${section.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <input type="submit" class="btn btn-primary" value="Save"/>
            </form>
    </div>
    <jsp:include page="/WEB-INF/views/footer.jsp"></jsp:include>
</div>
</body>
</html>
