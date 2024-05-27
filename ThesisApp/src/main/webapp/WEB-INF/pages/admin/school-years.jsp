<%-- 
    Document   : school-years
    Created on : May 27, 2024, 8:22:48 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="text-center m-2">Danh sách <strong>NIÊN KHOÁ</strong></h2>

<div class="mb-3"> 
    <a href="<c:url value='/admins/school-years/add' />" class="btn btn-success">Thêm</a>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Niên khoá</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${schoolYears}" var="s">
            <tr>
                <td>${s.id}</td>
                <td>${s.startYear} - ${s.endYear}</td>
                <td>
                    <a class="btn btn-primary" href="<c:url value="/admins/school-years/${s.id}" />">Cập nhật</a>
                    <button type="button" class="btn btn-danger">Xoá</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
