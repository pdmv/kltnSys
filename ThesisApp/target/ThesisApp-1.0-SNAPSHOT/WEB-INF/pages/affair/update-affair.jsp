<%-- 
    Document   : update-affair
    Created on : May 29, 2024, 2:58:03 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center m-2">Cập nhật <strong>GIÁO VỤ</strong></h2>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>

<c:url value="/admins/affairs/${id}" var="act"/>
<form:form method="post" action="${act}" modelAttribute="affair" enctype="multipart/form-data" onsubmit="return validatePassword()">
    <form:hidden path="id"/>
    <form:hidden path="createdDate"/>
    <form:hidden path="accountId.id"/>
    <form:hidden path="accountId.avatar"/>
    <form:hidden path="accountId.createdDate"/>
    
    <c:if test="${!empty affair.accountId.avatar}">
    <div class="d-flex justify-content-center mb-3">
        <img src="${affair.accountId.avatar}" class="rounded-circle" alt="Ảnh đại diện" height="150" width="150">
    </div>
    </c:if>
    
    <div class="form-floating mb-3">
        <form:input path="lastName" type="text" class="form-control" id="lastName" placeholder="Họ" required="required"/>
        <label for="lastName">Họ</label>
        <form:errors path="lastName" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:input path="firstName" type="text" class="form-control" id="firstName" placeholder="Tên" required="required"/>
        <label for="firstName">Tên</label>
        <form:errors path="firstName" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:select path="gender" class="form-control" id="gender" required="required">
            <form:option value="male" label="Nam"/>
            <form:option value="female" label="Nữ"/>
            <form:option value="other" label="Khác"/>
        </form:select>
        <label for="gender">Giới tính</label>
        <form:errors path="gender" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:input path="email" type="email" class="form-control" id="email" placeholder="Email" required="required"/>
        <label for="email">Email</label>
        <form:errors path="email" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:input path="dob" type="date" class="form-control" id="dob" placeholder="Ngày sinh" required="required"/>
        <label for="dob">Ngày sinh</label>
        <form:errors path="dob" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:input path="address" type="text" class="form-control" id="address" placeholder="Địa chỉ" required="required"/>
        <label for="address">Địa chỉ</label>
        <form:errors path="address" cssClass="text-danger"/>
    </div>
    
    <div class="form-floating mb-3" id="facultyInput">
        <form:select path="facultyId" class="form-control">
            <c:forEach items="${faculties}" var="faculty">
                <option value="${faculty.id}" <c:if test="${faculty.id eq affair.facultyId.id}">selected</c:if>>${faculty.name}</option>
            </c:forEach>
        </form:select>
        <label for="kw">Khoa quản lý</label>
        <form:errors path="facultyId" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:input path="accountId.username" type="text" class="form-control" id="username" placeholder="Tên người dùng" required="required"/>
        <label for="username">Tên người dùng</label>
        <form:errors path="accountId.username" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:password path="accountId.password" class="form-control" id="password" placeholder="Mật khẩu"/>
        <label for="password">Mật khẩu</label>
        <form:errors path="accountId.password" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <input type="password" class="form-control" id="confirmPassword" placeholder="Xác nhận mật khẩu"/>
        <label for="confirmPassword">Xác nhận mật khẩu</label>
        <span id="confirmPasswordError" class="text-danger"></span>
    </div>

    <div class="form-floating mb-3">
        <form:input path="accountId.file" type="file" class="form-control" id="file" placeholder="Sửa ảnh đại diện" accept="image/*"/>
        <label for="file">Sửa ảnh đại diện</label>
        <form:errors path="accountId.file" cssClass="text-danger"/>
    </div>
    
    <div class="form-floating mb-3">
        <form:select path="active" class="form-control" id="active" required="required">
            <form:option value="true" label="Hoạt động"/>
            <form:option value="false" label="Đình chỉ"/>
        </form:select>
        <label for="active">Trạng thái</label>
        <form:errors path="active" cssClass="text-danger"/>
    </div>

    <button type="submit" class="btn btn-dark">Cập nhật</button>
    <a type="button" class="btn btn-outline-dark" href="<c:url value="/admins/affairs"/>">Quay lại</a>
</form:form>

<script type="text/javascript">
    function validatePassword() {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirmPassword").value;
        var errorSpan = document.getElementById("confirmPasswordError");

        if (password !== confirmPassword) {
            errorSpan.innerText = "Mật khẩu không khớp";
            return false;
        }

        errorSpan.innerText = "";
        
        disableButtons();

        return true;
    }

    function disableButtons() {
        var buttons = document.querySelectorAll('button');
        buttons.forEach(function(button) {
            button.disabled = true;
        });
    }
</script>
