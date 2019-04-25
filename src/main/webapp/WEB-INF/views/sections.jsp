<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
    <title>C++ vocabulary</title>
</head>
<body>
<div align="center">
    <div class="container">
        <div class="list-group" style="margin-top: 20px; margin-bottom: 10px">
            <c:forEach var="section" items="${sections}">
                <div class="list-group-item list-group-item-action flex-column align-items-start">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1"><a href="/server/class/list?id=<c:out value='${section.id}'/>"><c:out
                                value="${section.name}"/></a></h5>
                        <c:if test="${sessionScope.get('role') eq 'moderator'}">
                            <small class="text-muted">
                                <input style="color: red" value="Delete" type="button"
                                       onclick="location.href='/server/section/delete?id=<c:out
                                               value='${section.id}'/>'"/>
                                <input style="color: forestgreen" value="Edit" type="button"
                                       onclick="location.href='/server/section/edit?id=<c:out
                                               value='${section.id}'/>'"/>
                            </small>
                        </c:if>
                    </div>
                    <p class="mb-1 text-left"><c:out value="${section.description}"/></p>
                </div>
            </c:forEach>
        </div>

        <nav aria-label="Navigation for sections">
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
    </div>
    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
