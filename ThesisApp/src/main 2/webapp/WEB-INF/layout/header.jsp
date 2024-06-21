<%-- 
    Document   : header
    Created on : May 26, 2024, 2:25:51 AM
    Author     : phamdominhvuong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/?page=1" />">
            Thesis Management System
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="<c:url value="/" />">Trang chủ</a>
                </li>
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name == null}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value="/login" />">Đăng nhập</a></li>
                </c:when>
                <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Đào tạo
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/school-years" />">Niên khoá</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/faculties" />">Khoa đào tạo</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/majors" />">Ngành đào tạo</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/classes" />">Lớp học</a>
                                </li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Người dùng
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/affairs" />">Giáo vụ</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/lecturers" />">Giảng viên</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/students" />">Sinh viên</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins" />">Quản trị viên</a>
                                </li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Thống kê
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/stats/thesis-scores" />">Điểm khoá luận</a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="<c:url value="/admins/stats/thesis-count-by-major" />">Tần suất tham gia</a>
                                </li>
                            </ul>
                        </li>
                        <li class="nav-item"><a class="nav-link" href="<c:url value="/logout" />">Đăng xuất</a></li>
                    </ul>
                    <span class="navbar-text"><a class="nav-link" href="<c:url value="/" />">Chào <strong>${pageContext.request.userPrincipal.name}</strong>!</a></span>
                    <a class="navbar-brand" href="#">
                        <img src="${userAvatar}" alt="user-avatar" style="width:40px;" class="rounded-pill"> 
                    </a>
                </c:when>
            </c:choose>
        </div>
    </div>
</nav>
