<%-- 
    Document   : admins
    Created on : May 26, 2024, 2:19:37 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="text-center m-2">Quản lý <strong>QUẢN TRỊ VIÊN</strong></h2>

<div class="container">
    <form action="${pageContext.request.contextPath}/admins" method="get" class="row g-2 mb-3 justify-content-center">
        <div class="col-md-2 col-12">
            <div class="form-floating">
                <select id="type" name="type" class="form-control">
                    <option value="id">ID</option>
                    <option value="name">Tên</option>
                </select>
                <label for="type">Bộ lọc</label>
            </div>
        </div>
        <div class="col-md-7  col-12">
            <div class="form-floating">
                <input type="text" class="form-control" id="kw" placeholder="Từ khoá" name="kw">
                <label for="kw">Từ khoá</label>
            </div>
        </div>
        <div class="col-md-3 col-12 d-flex align-items-center">
            <button type="submit" class="btn btn-dark" style="margin-left: 2px;">Tìm kiếm</button>
            <a href="<c:url value='/admins' />" class="btn btn-outline-dark" style="margin-left: 10px;">Tất cả</a>
            <a href="<c:url value='/admins/add' />" class="btn btn-outline-dark" style="margin-left: 10px;">Thêm</a>
        </div>
    </form>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Họ</th>
            <th>Tên</th>
            <th>Giới tính</th>
            <th>Ngày sinh</th>
            <th>Địa chỉ</th>
            <th>Email</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${adminList}" var="a">
            <tr>
                <td>${a.id}</td>
                <td>${a.lastName}</td>
                <td>${a.firstName}</td>
                <td>
                    <c:choose>
                        <c:when test="${a.gender == 'male'}">Nam</c:when>
                        <c:when test="${a.gender == 'female'}">Nữ</c:when>
                        <c:otherwise>Khác</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <fmt:formatDate value="${a.dob}" pattern="dd/MM/yyyy" />
                </td>
                <td>${a.address}</td>
                <td>${a.email}</td>
                <td>
                    <c:choose>
                        <c:when test="${a.active==true}"><span class="badge bg-success">Đang hoạt động</span></c:when>
                        <c:otherwise><span class="badge bg-danger">Đã đình chỉ</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-outline-dark" href="<c:url value="/admins/${a.id}" />">Cập nhật</a>
                    <button type="button" class="btn btn-outline-dark">Xoá</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
