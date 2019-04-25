<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="#">C++ Vocabulary</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <c:if test="${sessionScope.get('role') ne 'admin'}">
            <ul class="navbar-nav mr-auto">
                <c:if test="${sessionScope.get('role') eq 'moderator'}">
                    <li class="nav-item">
                        <a class="nav-link ml-2" href="/server/section/new">Add New Section</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link ml-2" href="/server/section/list">List All Sections</a>
                </li>
                <c:if test="${sessionScope.get('role') eq 'moderator'}">
                    <li class="nav-item">
                        <a class="nav-link ml-2" href="/server/class/new">Add New Class</a>
                    </li>
                </c:if>
            </ul>
        </c:if>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/server/logout">SignOut</a>
            </li>
        </ul>
    </div>
</nav>
