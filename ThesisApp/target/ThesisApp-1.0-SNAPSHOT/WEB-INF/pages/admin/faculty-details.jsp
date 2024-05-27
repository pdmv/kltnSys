<%-- 
    Document   : add-faculty
    Created on : May 28, 2024, 12:14:46 AM
    Author     : phamdominhvuong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center m-2">
    <c:choose>
        <c:when test="${faculty.id == null}">Thêm</c:when>
        <c:otherwise>Cập nhật</c:otherwise>
    </c:choose>
    <strong>KHOA ĐÀO TẠO</strong>
</h2>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>

<c:url value="/admins/faculties" var="act"/>
<form id="facultyForm"> 
    <input type="hidden" name="id" value="${faculty.id}">
    <input type="hidden" name="createdDate" value="${faculty.createdDate}">
    <input type="hidden" name="active" value="${faculty.active}">

    <div class="form-floating mb-3">
        <input name="name" type="text" class="form-control" id="name" placeholder="Tên khoa" required="required" value="${faculty.name}">
        <label for="name">Tên khoa</label>
        <div id="nameError" class="text-danger"></div> 
    </div>
    
    <button type="submit" class="btn btn-primary mr-2" id="submitButton">
        <c:choose>
            <c:when test="${faculty.id == null}">Thêm</c:when>
            <c:otherwise>Cập nhật</c:otherwise>
        </c:choose>
    </button>
    <a type="button" class="btn btn-secondary ml-auto" href="${act}" id="backButton">Quay lại</a>
</form>

<script>
    const facultyForm = document.getElementById('facultyForm');
    const nameInput = document.getElementById('name');
    const nameErrorDiv = document.getElementById('nameError'); 
    const submitButton = document.getElementById('submitButton');
    const backButton = document.getElementById('backButton');

    facultyForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const formData = new FormData(facultyForm);

        fetch('${act}', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Có lỗi xảy ra khi xử lý khoa.');
            }
            return response.text(); 
        })
        .then(data => {
            window.location.href = '${act}'; 
        })
        .catch(error => {
            nameErrorDiv.textContent = error.message;
        });

        // Disable nút khi submit
        submitButton.disabled = true;
        backButton.disabled = true;
    });
</script>