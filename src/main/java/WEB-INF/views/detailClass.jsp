<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>C++ Vocabulary</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
</head>
<body>
<div align="center">
    <div class="container">
        <div class="list-group-item list-group-item-action flex-column align-items-start" style="margin-top: 20px; margin-bottom: 10px">
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1"><c:out value="${cls.name}"/></h5>
            </div>
            <p class="mb-1 text-left">${cont.text}</p>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</div>
</body>
</html>