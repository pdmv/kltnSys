<%-- 
    Document   : index
    Created on : May 26, 2024, 2:29:23 AM
    Author     : phamdominhvuong
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container mt-5">
    <h1 class="text-center mb-4">Chào mừng đến với <strong>TRANG QUẢN TRỊ</strong></h1>

    <div class="row mt-5">
        <!-- Niên khóa -->
        <div class="col-md-3 mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <i class="fas fa-calendar-alt fa-3x mb-3"></i>
                    <h5 class="card-title">Niên khóa</h5>
                    <p class="card-text">Quản lý niên khóa học.</p>
                    <a href="<c:url value='/admins/school-years' />" class="btn btn-outline-dark">Truy cập</a>
                </div>
            </div>
        </div>
        <!-- Khoa -->
        <div class="col-md-3 mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <i class="fas fa-university fa-3x mb-3"></i>
                    <h5 class="card-title">Khoa</h5>
                    <p class="card-text">Quản lý các khoa đào tạo.</p>
                    <a href="<c:url value='/admins/faculties' />" class="btn btn-outline-dark">Truy cập</a>
                </div>
            </div>
        </div>
        <!-- Ngành -->
        <div class="col-md-3 mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <i class="fas fa-graduation-cap fa-3x mb-3"></i>
                    <h5 class="card-title">Ngành</h5>
                    <p class="card-text">Quản lý các ngành học.</p>
                    <a href="<c:url value='/admins/majors' />" class="btn btn-outline-dark">Truy cập</a>
                </div>
            </div>
        </div>
        <!-- Lớp -->
        <div class="col-md-3 mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <i class="fas fa-chalkboard fa-3x mb-3"></i>
                    <h5 class="card-title">Lớp</h5>
                    <p class="card-text">Quản lý các lớp học.</p>
                    <a href="<c:url value='/admins/classes' />" class="btn btn-outline-dark">Truy cập</a>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <!-- Giảng viên -->
        <div class="col-md-3 mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <i class="fas fa-user-tie fa-3x mb-3"></i>
                    <h5 class="card-title">Giảng viên</h5>
                    <p class="card-text">Quản lý các giảng viên.</p>
                    <a href="<c:url value='/admins/lecturers' />" class="btn btn-outline-dark">Truy cập</a>
                </div>
            </div>
        </div>
        <!-- Nhân viên -->
        <div class="col-md-3 mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <i class="fas fa-users fa-3x mb-3"></i>
                    <h5 class="card-title">Giáo vụ</h5>
                    <p class="card-text">Quản lý các giáo vụ.</p>
                    <a href="<c:url value='/admins/staff' />" class="btn btn-outline-dark">Truy cập</a>
                </div>
            </div>
        </div>
        <!-- Sinh viên -->
        <div class="col-md-3 mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <i class="fas fa-user-graduate fa-3x mb-3"></i>
                    <h5 class="card-title">Sinh viên</h5>
                    <p class="card-text">Quản lý các sinh viên.</p>
                    <a href="<c:url value='/admins/students' />" class="btn btn-outline-dark">Truy cập</a>
                </div>
            </div>
        </div>
        <!-- Quản trị viên -->
        <div class="col-md-3 mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <i class="fas fa-user-shield fa-3x mb-3"></i>
                    <h5 class="card-title">Quản trị viên</h5>
                    <p class="card-text">Quản lý các quản trị viên.</p>
                    <a href="<c:url value='/admins' />" class="btn btn-outline-dark">Truy cập</a>
                </div>
            </div>
        </div>
    </div>
</div>