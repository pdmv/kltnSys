<%-- 
    Document   : class-details
    Created on : May 28, 2024, 9:34:00 PM
    Author     : phamdominhvuong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center m-2">
    <c:choose>
        <c:when test="${aClass.id == null}">Thêm</c:when>
        <c:otherwise>Cập nhật</c:otherwise>
    </c:choose>
    <strong>LỚP HỌC</strong> 
</h2>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>

<c:url value="/admins/classes" var="act"/> 
<form id="classForm">
    <input type="hidden" name="id" value="${aClass.id}"> 
    <input type="hidden" name="createdDate" value="${aClass.createdDate}">

    <div class="form-floating mb-3">
        <input name="name" type="text" class="form-control" id="name" placeholder="Tên lớp học" required="required" value="${aClass.name}"> 
        <label for="name">Tên lớp học</label> 
        <div id="nameError" class="text-danger"></div>
    </div>

    <div class="form-floating mb-3">
        <select name="facultyId" class="form-control" id="facultyId" required="required" onchange="updateMajors()">
            <option value="">Chọn khoa đào tạo</option>
            <c:forEach items="${faculties}" var="faculty">
                <option value="${faculty.id}" <c:if test="${faculty.id eq aClass.facultyId.id}">selected</c:if>>${faculty.name}</option> 
            </c:forEach>
        </select>
        <label for="facultyId">Khoa đào tạo</label>
        <div id="facultyIdError" class="text-danger"></div>
    </div>

    <div class="form-floating mb-3">
        <select name="majorId" class="form-control" id="majorId" required="required"> 
            <option value="">Chọn ngành đào tạo</option>
            <c:forEach items="${majors}" var="major">
                <option value="${major.id}" <c:if test="${major.id eq aClass.majorId.id}">selected</c:if>>${major.name}</option> 
            </c:forEach>
        </select>
        <label for="majorId">Ngành đào tạo</label> 
        <div id="majorIdError" class="text-danger"></div> 
    </div>

    <c:choose>
        <c:when test="${aClass.id == null}">
            <input type="hidden" name="active" value="true">
            <button type="submit" class="btn btn-dark mr-2" id="submitButton">Thêm</button>
        </c:when>
        <c:otherwise>
            <div class="form-floating mb-3">
                <select name="active" class="form-control" id="active" required="required">
                    <option value="true" <c:if test="${aClass.active}">selected</c:if>>Hoạt động</option> 
                    <option value="false" <c:if test="${!aClass.active}">selected</c:if>>Đình chỉ</option>
                </select>
                <label for="active">Trạng thái</label>
                <div id="activeError" class="text-danger"></div>
            </div>
            <button type="submit" class="btn btn-dark mr-2" id="submitButton">Cập nhật</button>
        </c:otherwise>
    </c:choose>
    <a type="button" class="btn btn-outline-dark ml-auto" href="${act}" id="backButton">Quay lại</a>
</form>

<script>
    const classForm = document.getElementById('classForm');
    const nameInput = document.getElementById('name');
    const nameErrorDiv = document.getElementById('nameError');
    const submitButton = document.getElementById('submitButton');
    const backButton = document.getElementById('backButton');
    const facultySelect = document.getElementById('facultyId');
    const majorSelect = document.getElementById('majorId');

    classForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const formData = new FormData(classForm);

        const url = '${act}<c:if test="${aClass.id != null}">/${aClass.id}</c:if>'; 

        fetch(url, {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Có lỗi xảy ra khi xử lý lớp học.'); 
            }
            return response.text();
        })
        .then(data => {
            window.location.href = '${act}'; 
        })
        .catch(error => {
            nameErrorDiv.textContent = error.message; 
        });

        submitButton.disabled = true;
        backButton.disabled = true;
    });

    function updateMajors() {
        const id = facultySelect.value;
        const url = '<c:url value="/admins/major-by-facultyId"/>?type=faculty&kw=' + id;
        const majorId = '${aClass.majorId.id}';
        if (id) {
            fetch(url)
                .then(response => response.json())
                .then(data => {
                    majorSelect.innerHTML = '<option value="">Chọn ngành đào tạo</option>';
                    data.forEach(major => {
                        const option = document.createElement('option');
                        option.value = major.id;
                        option.textContent = major.name;
                        if (majorId != "" && major.id == majorId) {
                            option.selected = true;
                        }
                        majorSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching majors:', error);
                });
        } else {
            majorSelect.innerHTML = '<option value="">Chọn ngành đào tạo</option>';
        }
    }
</script>
<c:if test="${aClass.facultyId != null}">
    <script>
        window.onload = function() {
            updateMajors();
        }
    </script>
</c:if>

