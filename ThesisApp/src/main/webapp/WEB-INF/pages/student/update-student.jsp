<%-- 
    Document   : update-student
    Created on : May 29, 2024, 5:03:45 PM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center m-2">Cập nhật <strong>SINH VIÊN</strong></h2>

<c:if test="${errorMessage != null}">
    <div class="alert alert-danger" role="alert">${errorMessage}</div>
</c:if>

<c:url value="/admins/students/${id}" var="act"/>
<form:form method="post" action="${act}" modelAttribute="student" enctype="multipart/form-data" onsubmit="return validatePassword()">
    <form:hidden path="id"/>
    <form:hidden path="createdDate"/>
    <form:hidden path="accountId.id"/>
    <form:hidden path="accountId.avatar"/>
    <form:hidden path="accountId.createdDate"/>
    
    <c:if test="${!empty student.accountId.avatar}">
    <div class="d-flex justify-content-center mb-3">
        <img src="${student.accountId.avatar}" class="rounded-circle" alt="Ảnh đại diện" height="150" width="150">
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
        <form:select path="facultyId" class="form-control" onchange="updateMajors()" required="required">
            <c:forEach items="${faculties}" var="faculty">
                <option value="${faculty.id}" <c:if test="${faculty.id eq student.facultyId.id}">selected</c:if>>${faculty.name}</option>
            </c:forEach>
        </form:select>
        <label for="kw">Khoa quản lý</label>
        <form:errors path="facultyId" cssClass="text-danger"/>
    </div>
    
    <div class="form-floating mb-3">
        <select name="majorId" class="form-control" id="majorId" required="required" onchange="updateClasses()"> 
            <option value="">Chọn ngành đào tạo</option>
            <c:forEach items="${majors}" var="major">
                <option value="${major.id}" <c:if test="${major.id eq student.majorId.id}">selected</c:if>>${major.name}</option> 
            </c:forEach>
        </select>
        <label for="majorId">Ngành đào tạo</label> 
        <div id="majorIdError" class="text-danger"></div> 
    </div>
    
    <div class="form-floating mb-3">
        <select name="classId" class="form-control" id="classId" required="required"> 
            <option value="">Chọn lớp học</option>
            <c:forEach items="${classes}" var="myClass">
                <option value="${myClass.id}" <c:if test="${myClass.id eq student.classId.id}">selected</c:if>>${myClass.name}</option> 
            </c:forEach>
        </select>
        <label for="classId">Lớp học</label> 
        <div id="classIdError" class="text-danger"></div> 
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
    <a type="button" class="btn btn-outline-dark" href="<c:url value="/admins/students"/>">Quay lại</a>
</form:form>

<script type="text/javascript">
    const facultySelect = document.getElementById('facultyId');
    const majorSelect = document.getElementById('majorId');
    const classSelect = document.getElementById('classId');
    
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
    
    function updateMajors() {
        const id = facultySelect.value;
        const url = '<c:url value="/admins/major-by-facultyId"/>?type=faculty&kw=' + id;
        if (id) {
            fetch(url)
                .then(response => response.json())
                .then(data => {
                    majorSelect.innerHTML = '<option value="">Chọn ngành đào tạo</option>';
                    data.forEach(major => {
                        const option = document.createElement('option');
                        option.value = major.id;
                        option.textContent = major.name;
                        if (major.id == ${student.majorId.id}) {
                            option.selected = true
                        }
                        majorSelect.appendChild(option);
                    });
                    updateClasses();
                })
                .catch(error => {
                    console.error('Error fetching majors:', error);
                });
        } else {
            majorSelect.innerHTML = '<option value="">Chọn ngành đào tạo</option>';
        }
    }
    
    function updateClasses() {
        const id = majorSelect.value;
        const url = '<c:url value="/admins/class-by-majorId"/>?type=major&kw=' + id;
        if (id) {
            fetch(url)
                .then(response => response.json())
                .then(data => {
                    classSelect.innerHTML = '<option value="">Chọn lớp học</option>';
                    data.forEach(myClass => {
                        const option = document.createElement('option');
                        option.value = myClass.id;
                        option.textContent = myClass.name;
                        if (myClass.id == ${student.classId.id}) {
                            option.selected = true
                        }
                        classSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Error fetching classes:', error);
                });
        } else {
            classSelect.innerHTML = '<option value="">Chọn lớp học</option>';
        }
    }
    
    window.onload = function() {
        facultyId = ${student.facultyId.id};
        if (facultyId != null) {
            updateMajors();
            updateClasses()
        }
    };
</script>
