<%-- 
    Document   : classes
    Created on : May 28, 2024, 9:28:19 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="text-center m-2">Quản lý <strong>LỚP HỌC</strong></h2>

<div class="container">
    <form action="${pageContext.request.contextPath}/admins/classes" method="get" class="row g-2 mb-3 justify-content-center">
        <div class="col-md-2 col-12">
            <div class="form-floating">
                <select id="type" name="type" class="form-control">
                    <option value="faculty">Khoa</option>
                    <option value="major">Ngành</option>
                    <option value="name">Tên</option>
                </select>
                <label for="type">Bộ lọc</label>
            </div>
        </div>
        <div class="col-md-7 col-12">
            <div class="form-floating" id="facultyInput"> 
                <select id="kw" name="kw" class="form-control">
                    <c:forEach items="${faculties}" var="faculty">
                        <option value="${faculty.id}">${faculty.name}</option>
                    </c:forEach>
                </select>
                <label for="kw">Khoa đào tạo</label> 
            </div>
            <div class="form-floating" id="majorInput" style="display: none;">
                <select id="kw" name="kw" class="form-control">
                    <c:forEach items="${majors}" var="major">
                        <option value="${major.id}">${major.name}</option>
                    </c:forEach>
                </select>
                <label for="kw">Ngành đào tạo</label>
            </div>
            <div class="form-floating" id="nameInput" style="display: none;">
                <input type="text" class="form-control" id="kw" placeholder="Từ khoá" name="kw">
                <label for="kw">Từ khoá</label>
            </div>
        </div>
        <div class="col-md-3 col-12 d-flex align-items-center">
            <button type="submit" class="btn btn-dark" style="margin-left: 2px;">Tìm kiếm</button>
            <a href="<c:url value='/admins/classes' />" class="btn btn-outline-dark" style="margin-left: 10px;">Tất cả</a>
            <a href="<c:url value='/admins/classes/add' />" class="btn btn-outline-dark" style="margin-left: 10px;">Thêm</a>
        </div>
    </form>
</div>

<table class="table table-hover">
    <thead>
        <tr>
            <th>ID</th>
            <th>Lớp học</th>
            <th>Ngành</th>
            <th>Khoa</th>
            <th>Trạng thái</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${classes}" var="c">
            <tr>
                <td>${c.id}</td>
                <td>${c.name}</td>
                <td>${c.majorId.name}</td>
                <td>${c.facultyId.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${c.active==true}"><span class="badge bg-success">Đang hoạt động</span></c:when>
                        <c:otherwise><span class="badge bg-danger">Đã đình chỉ</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-outline-dark" href="<c:url value="/admins/classes/${c.id}" />">Cập nhật</a>
                    <button type="button" class="btn btn-outline-dark">Xoá</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<script>
    document.getElementById('type').addEventListener('change', function() {
        var facultyInput = document.getElementById('facultyInput');
        var majorInput = document.getElementById('majorInput'); 
        var nameInput = document.getElementById('nameInput');
        var facultySelect = document.querySelector('#facultyInput select[name="kw"]'); 
        var majorSelect = document.querySelector('#majorInput select[name="kw"]'); 
        var nameInputText = document.querySelector('#nameInput input[name="kw"]');

        if (this.value === 'faculty') {
            facultyInput.style.display = 'block';
            majorInput.style.display = 'none';
            nameInput.style.display = 'none';

            facultySelect.setAttribute('name', 'kw'); 
            majorSelect.removeAttribute('name'); 
            nameInputText.removeAttribute('name'); 
        } else if (this.value === 'major') {
            facultyInput.style.display = 'none';
            majorInput.style.display = 'block';
            nameInput.style.display = 'none';

            majorSelect.setAttribute('name', 'kw'); 
            facultySelect.removeAttribute('name'); 
            nameInputText.removeAttribute('name'); 
        } else if (this.value === 'name') {
            facultyInput.style.display = 'none';
            majorInput.style.display = 'none';
            nameInput.style.display = 'block';

            nameInputText.setAttribute('name', 'kw'); 
            facultySelect.removeAttribute('name'); 
            majorSelect.removeAttribute('name'); 
        }
    });
</script>
