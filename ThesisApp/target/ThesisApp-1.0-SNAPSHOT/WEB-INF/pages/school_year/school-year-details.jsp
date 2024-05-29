<%-- 
    Document   : school-year-details
    Created on : May 27, 2024, 8:27:01 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center m-2"><c:choose>
        <c:when test="${schoolYear.id == null}">
            Thêm
            <c:url value="/admins/school-years" var="act"/>
        </c:when>
        <c:otherwise>
            Cập nhật
            <c:url value="/admins/school-years/${schoolYear.id}" var="act"/>
        </c:otherwise>
</c:choose> <strong>NIÊN KHOÁ</strong></h2>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>

<form:form method="post" action="${act}" modelAttribute="schoolYear" id="schoolYearForm">
    <form:hidden path="id"/>
    <form:hidden path="createdDate"/>
    
    <div class="form-floating mb-3"> 
        <form:input path="startYear" type="number" class="form-control" id="startYear" placeholder="Năm bắt đầu" required="required" min="1900" max="2100" /> 
        <label for="startYear">Năm bắt đầu</label>
        <form:errors path="startYear" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:input path="endYear" type="number" class="form-control" id="endYear" placeholder="Năm kết thúc" required="required" min="1900" max="2100" /> 
        <label for="endYear">Năm kết thúc</label>
        <form:errors path="endYear" cssClass="text-danger"/>
        <span id="yearError" class="text-danger"></span>
    </div>

    <c:choose>
        <c:when test="${schoolYear.id == null}">
            <form:hidden path="active"/>
            <button type="submit" class="btn btn-dark mr-2">Thêm</button>
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
            <button type="submit" class="btn btn-dark mr-2">Cập nhật</button>
        </c:otherwise>
    </c:choose> 
    <a type="button" class="btn btn-outline-dark ml-auto" href="<c:url value="/admins/school-years"/>">Quay lại</a>
</form:form>

<script>
    const schoolYearForm = document.getElementById('schoolYearForm');
    const startYearInput = document.getElementById('startYear');
    const endYearInput = document.getElementById('endYear');
    const yearErrorSpan = document.getElementById('yearError');

    const submitButton = document.querySelector('button[type="submit"]');
    const backButton = document.querySelector('a[href="/admins/school-years"]');

    schoolYearForm.addEventListener('submit', (event) => {
        const startYear = parseInt(startYearInput.value);
        const endYear = parseInt(endYearInput.value);

        if (startYear >= endYear) {
            yearErrorSpan.textContent = 'Năm kết thúc phải lớn hơn năm bắt đầu.';
            event.preventDefault();
        } else {
            yearErrorSpan.textContent = '';

            submitButton.disabled = true;
            backButton.disabled = true;
        }
    });
</script>