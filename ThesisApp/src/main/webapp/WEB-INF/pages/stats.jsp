<%-- 
    Document   : stats
    Created on : Jun 15, 2024, 2:50:19 PM
    Author     : tid83
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Statistics</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class="container mt-5">
            <h1 class="text-center mb-5">Statistics</h1>

            <!-- Chart 1: Average Scores -->
            <div class="row mb-5">
                <div class="col-md-5">
                    <h3>Average Scores by School Year and Faculty</h3>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>School Year</th>
                                <th>Faculty</th>
                                <th>Average Score</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${avgScoresBySchoolYearAndFaculty}" var="score">
                                <tr>
                                    <td>${score[0]}</td>
                                    <td>${score[1]}</td>
                                    <td>${String.format("%.2f", score[2])}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-7">
                    <canvas id="avgScoresChart"></canvas>
                </div>
            </div>

            <!-- Chart 2: Participation Frequency -->
            <div class="row mb-5">
                <div class="col-md-5">
                    <h3>Participation Frequency by Major, Faculty, and School Year</h3>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Major</th>
                                <th>Faculty</th>
                                <th>School Year</th>
                                <th>Student Count</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${participationFrequencyByMajorFacultyAndSchoolYear}" var="freq">
                                <tr>
                                    <td>${freq[0]}</td>
                                    <td>${freq[1]}</td>
                                    <td>${freq[2]}</td>
                                    <td>${freq[3]}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-7">
                    <canvas id="participationChart"></canvas>
                </div>
            </div>
        </div>

        <script src="<c:url value='/resources/js/script.js' />"></script>
    </body>
</html>



