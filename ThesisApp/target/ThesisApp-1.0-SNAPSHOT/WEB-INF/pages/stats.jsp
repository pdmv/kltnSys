<%-- 
    Document   : stats
    Created on : Jun 15, 2024, 2:50:19 PM
    Author     : tid83
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thống kê</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>
        <div class="container">
            <h1 class="text-center">Thống kê điểm trung bình và tần suất tham gia làm khoá luận</h1>

            <div class="row">
                <div class="col-md-6">
                    <h2>Biểu đồ điểm trung bình khoá luận theo niên khoá</h2>
                    <canvas id="avgScoreChart"></canvas>
                </div>
                <div class="col-md-6">
                    <h2>Biểu đồ tần suất tham gia làm khoá luận theo ngành</h2>
                    <canvas id="thesisParticipationChart"></canvas>
                </div>
            </div>
        </div>

        <script src="js/script.js"></script>
    </body>
</html>




