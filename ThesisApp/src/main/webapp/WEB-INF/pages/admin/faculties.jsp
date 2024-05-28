<%-- 
    Document   : faculties
    Created on : May 27, 2024, 10:01:05 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="text-center m-2">Quản lý <strong>KHOA ĐÀO TẠO</strong></h2>

<div class="container">
    <form action="${pageContext.request.contextPath}/admins/faculties" method="get" class="row g-2 mb-3 justify-content-center">
        <div class="col-md-7 col-12">
            <div class="form-floating">
                <input type="text" class="form-control" id="kw" placeholder="Từ khoá" name="kw">
                <label for="kw">Từ khoá</label>
            </div>
        </div>
        <div class="col-md-3 col-12 d-flex align-items-center">
            <button type="submit" class="btn btn-dark" style="margin-left: 2px;">Tìm kiếm</button>
            <a href="<c:url value='/admins/faculties' />" class="btn btn-outline-dark" style="margin-left: 10px;">Tất cả</a>
            <a href="<c:url value='/admins/faculties/add' />" class="btn btn-outline-dark" style="margin-left: 10px;">Thêm</a>
        </div>
    </form>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Tên khoa</th>
            <th>Trạng thái</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${faculties}" var="f">
            <tr>
                <td>${f.id}</td>
                <td>${f.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${f.active==true}"><span class="badge bg-success">Đang hoạt động</span></c:when>
                        <c:otherwise><span class="badge bg-danger">Đã đình chỉ</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-outline-dark" href="<c:url value="/admins/faculties/${f.id}" />">Cập nhật</a>
                    <button type="button" class="btn btn-outline-dark">Xoá</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
