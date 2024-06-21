<%-- 
    Document   : major-details
    Created on : May 28, 2024, 2:54:13 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center m-2">
    <c:choose>
        <c:when test="${major.id == null}">Thêm</c:when>
        <c:otherwise>Cập nhật</c:otherwise>
    </c:choose>
    <strong>NGÀNH</strong>
</h2>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>

<c:url value="/admins/majors" var="act"/>
<form id="majorForm"> 
    <input type="hidden" name="id" value="${major.id}">
    <input type="hidden" name="createdDate" value="${major.createdDate}">

    <div class="form-floating mb-3">
        <input name="name" type="text" class="form-control" id="name" placeholder="Tên ngành" required="required" value="${major.name}">
        <label for="name">Tên ngành</label>
        <div id="nameError" class="text-danger"></div> 
    </div>
    
    <div class="form-floating mb-3">
        <select name="facultyId" class="form-control" id="facultyId" required="required">
            <option value="">Chọn khoa đào tạo</option>
            <c:forEach items="${faculties}" var="faculty">
                <option value="${faculty.id}" <c:if test="${faculty.id eq major.facultyId.id}">selected</c:if>>${faculty.name}</option>
            </c:forEach>
        </select>
        <label for="facultyId">Khoa đào tạo</label>
        <div id="facultyIdError" class="text-danger"></div> 
    </div>
    
    <c:choose>
        <c:when test="${major.id == null}">
            <input type="hidden" name="active" value="true">
            <button type="submit" class="btn btn-dark mr-2" id="submitButton">Thêm</button>
        </c:when>
        <c:otherwise>
            <div class="form-floating mb-3">
                <select name="active" class="form-control" id="active" required="required">
                    <option value="true" <c:if test="${major.active}">selected</c:if>>Hoạt động</option>
                    <option value="false" <c:if test="${!major.active}">selected</c:if>>Đình chỉ</option>
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
    const majorForm = document.getElementById('majorForm');
    const nameInput = document.getElementById('name');
    const nameErrorDiv = document.getElementById('nameError'); 
    const submitButton = document.getElementById('submitButton');
    const backButton = document.getElementById('backButton');

    majorForm.addEventListener('submit', (event) => {
        event.preventDefault();
        
        const formData = new FormData(majorForm);
        
        const url = '${act}<c:if test="${major.id != null}">/${major.id}</c:if>'
        
        fetch(url, {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Có lỗi xảy ra khi xử lý ngành.');
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


<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center m-2"><c:choose>
        <c:when test="${major.id == null}">
            Thêm
            <c:url value="/admins/majors" var="act"/>
        </c:when>
        <c:otherwise>
            Cập nhật
            <c:url value="/admins/majors/${major.id}" var="act"/>
        </c:otherwise>
</c:choose> <strong>NGÀNH</strong></h2>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>

<form:form method="post" action="${act}" modelAttribute="major" id="majorForm">
    <form:hidden path="id"/>
    <form:hidden path="createdDate"/>

    <div class="form-floating mb-3"> 
        <form:input path="name" type="text" class="form-control" id="name" placeholder="Tên ngành" required="required" /> 
        <label for="name">Tên ngành</label>
        <form:errors path="name" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:select path="facultyId" class="form-control" id="facultyId" required="required">
            <c:forEach items="${faculties}" var="faculty">
                <form:option value="${faculty.id}" label="${faculty.name}"/>
            </c:forEach>
        </form:select>
        <label for="facultyId">Khoa đào tạo</label>
        <form:errors path="facultyId" cssClass="text-danger"/>
    </div>

    <c:choose>
        <c:when test="${major.id == null}">
            <form:hidden path="active"/>
            <button type="submit" class="btn btn-primary mr-2">Thêm</button>
        </c:when>
        <c:otherwise>
            <div class="form-floating mb-3">
                <form:select path="active" class="form-control" id="active" required="required">
                    <form:option value="true" label="Hoạt động"/>
                    <form:option value="false" label="Đình chỉ"/>
                </form:select>
                <label for="active">Trạng thái</label>
                <form:errors path="active" cssClass="text-danger"/>
            </div>
            <button type="submit" class="btn btn-primary mr-2">Cập nhật</button>
        </c:otherwise>
    </c:choose>
    <a type="button" class="btn btn-secondary ml-auto" href="<c:url value="/admins/majors"/>">Quay lại</a>
</form:form>--%>

