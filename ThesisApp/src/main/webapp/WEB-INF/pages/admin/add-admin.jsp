<%-- 
    Document   : admin-details
    Created on : May 26, 2024, 10:42:33 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center m-2">Thêm <strong>QUẢN TRỊ VIÊN</strong></h2>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>

<c:url value="/admins/add" var="act"/>
<form:form method="post" action="${act}" modelAttribute="admin" enctype="multipart/form-data" onsubmit="return validatePassword()">
    <form:hidden path="id"/>
    <form:hidden path="createdDate"/>
    
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

    <div class="form-floating mb-3">
        <form:input path="accountId.username" type="text" class="form-control" id="username" placeholder="Tên người dùng" required="required"/>
        <label for="username">Tên người dùng</label>
        <form:errors path="accountId.username" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <form:password path="accountId.password" class="form-control" id="password" placeholder="Mật khẩu" required="required"/>
        <label for="password">Mật khẩu</label>
        <form:errors path="accountId.password" cssClass="text-danger"/>
    </div>

    <div class="form-floating mb-3">
        <input type="password" class="form-control" id="confirmPassword" placeholder="Xác nhận mật khẩu" required="required"/>
        <label for="confirmPassword">Xác nhận mật khẩu</label>
        <span id="confirmPasswordError" class="text-danger"></span>
    </div>

    <div class="form-floating mb-3">
        <form:input path="accountId.file" type="file" class="form-control" id="file" placeholder="Ảnh đại diện" accept="image/*"/>
        <label for="file">Ảnh đại diện</label>
        <form:errors path="accountId.file" cssClass="text-danger"/>
    </div>

    <button type="submit" class="btn btn-dark">Thêm</button>
    <a type="button" class="btn btn-outline-dark" href="<c:url value="/admins"/>">Quay lại</a>
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
        return true;
    }
</script>
