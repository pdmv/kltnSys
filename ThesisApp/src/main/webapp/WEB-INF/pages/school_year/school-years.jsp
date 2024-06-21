<%-- 
    Document   : school-years
    Created on : May 27, 2024, 8:22:48 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="text-center m-2">Quản lý <strong>NIÊN KHOÁ</strong></h2>

<div class="mb-3"> 
    <a href="<c:url value='/admins/school-years/add' />" class="btn btn-dark">Thêm</a>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Niên khoá</th>
            <th>Trạng thái</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${schoolYears}" var="s">
            <tr>
                <td>${s.id}</td>
                <td>${s.startYear} - ${s.endYear}</td>
                <td>
                    <c:choose>
                        <c:when test="${s.active==true}"><span class="badge bg-success">Đang hoạt động</span></c:when>
                        <c:otherwise><span class="badge bg-danger">Đã đình chỉ</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-outline-dark" href="<c:url value="/admins/school-years/${s.id}" />">Cập nhật</a>
                    <button type="button" class="btn btn-outline-dark">Xoá</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
