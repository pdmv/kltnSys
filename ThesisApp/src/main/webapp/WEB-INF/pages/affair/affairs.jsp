<%-- 
    Document   : affairs
    Created on : May 29, 2024, 2:38:28 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="text-center m-2">Quản lý <strong>GIÁO VỤ</strong></h2>

<div class="container">
    <form action="${pageContext.request.contextPath}/admins/affairs" method="get" class="row g-2 mb-3 justify-content-center">
        <div class="col-md-2 col-12">
            <div class="form-floating">
                <select id="type" name="type" class="form-control">
                    <option value="id">ID</option>
                    <option value="faculty">Khoa</option>
                    <option value="name">Tên</option>
                </select>
                <label for="type">Bộ lọc</label>
            </div>
        </div>
        <div class="col-md-7 col-12">
            <div class="form-floating" id="idInput">
                <input type="text" class="form-control" id="idKw" placeholder="Từ khoá" name="kw">
                <label for="idKw">Từ khoá</label>
            </div>
            <div class="form-floating" id="facultyInput" style="display: none;">
                <select id="facultyKw" name="kw" class="form-control">
                    <c:forEach items="${faculties}" var="faculty">
                        <option value="${faculty.id}">${faculty.name}</option>
                    </c:forEach>
                </select>
                <label for="facultyKw">Khoa đào tạo</label>
            </div>
            <div class="form-floating" id="nameInput" style="display: none;">
                <input type="text" class="form-control" id="nameKw" placeholder="Từ khoá" name="kw">
                <label for="nameKw">Từ khoá</label>
            </div>
        </div>
        <div class="col-md-3 col-12 d-flex align-items-center">
            <button type="submit" class="btn btn-dark" style="margin-left: 2px;">Tìm kiếm</button>
            <a href="<c:url value='/admins/affairs' />" class="btn btn-outline-dark" style="margin-left: 10px;">Tất cả</a>
            <a href="<c:url value='/admins/affairs/add' />" class="btn btn-outline-dark" style="margin-left: 10px;">Thêm</a>
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
            <th>Khoa</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${affairs}" var="a">
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
                <td>${a.facultyId.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${a.active==true}"><span class="badge bg-success">Đang hoạt động</span></c:when>
                        <c:otherwise><span class="badge bg-danger">Đã đình chỉ</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-outline-dark" href="<c:url value="/admins/affairs/${a.id}" />">Cập nhật</a>
                    <button type="button" class="btn btn-outline-dark">Xoá</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<script>
    document.getElementById('type').addEventListener('change', function() {
        var facultyInput = document.getElementById('facultyInput');
        var idInput = document.getElementById('idInput');
        var nameInput = document.getElementById('nameInput');

        // Reset all input fields
        document.querySelectorAll('input[name="kw"]').forEach(function(input) {
            input.removeAttribute('name');
            input.value = '';
        });
        document.querySelectorAll('select[name="kw"]').forEach(function(select) {
            select.removeAttribute('name');
        });

        if (this.value === 'faculty') {
            facultyInput.style.display = 'block';
            idInput.style.display = 'none';
            nameInput.style.display = 'none';
            document.getElementById('facultyKw').setAttribute('name', 'kw');
        } else if (this.value === 'name') {
            facultyInput.style.display = 'none';
            idInput.style.display = 'none';
            nameInput.style.display = 'block';
            document.getElementById('nameKw').setAttribute('name', 'kw');
        } else if (this.value === 'id') {
            facultyInput.style.display = 'none';
            idInput.style.display = 'block';
            nameInput.style.display = 'none';
            document.getElementById('idKw').setAttribute('name', 'kw');
        }
    });
</script>
