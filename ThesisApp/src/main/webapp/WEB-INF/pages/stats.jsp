<%-- 
    Document   : stats
    Created on : Jun 15, 2024, 2:50:19 PM
    Author     : tid83
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<script
src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
</script>

<script>
    let avgScoreData = [];
    let avgScoreLabels = [];

    <c:forEach items="${avgScoresByYear}" var="avgScore">
    avgScoreLabels.push('${avgScore[0]}');
    avgScoreData.push(${avgScore[1]});
    </c:forEach>

    let thesisParticipationData = [];
    let thesisParticipationLabels = [];

    <c:forEach items="${thesisParticipation}" var="participation">
    thesisParticipationLabels.push('${participation[0]}');
    thesisParticipationData.push(${participation[1]});
    </c:forEach>

    document.addEventListener("DOMContentLoaded", function () {
        let avgScoreCtx = document.getElementById("avgScoreChart").getContext('2d');
        let avgScoreChart = new Chart(avgScoreCtx, {
            type: 'bar',
            data: {
                labels: avgScoreLabels,
                datasets: [{
                        label: 'Điểm trung bình khoá luận theo niên khoá',
                        data: avgScoreData,
                        backgroundColor: 'rgba(54, 162, 235, 0.6)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        let thesisParticipationCtx = document.getElementById("thesisParticipationChart").getContext('2d');
        let thesisParticipationChart = new Chart(thesisParticipationCtx, {
            type: 'bar',
            data: {
                labels: thesisParticipationLabels,
                datasets: [{
                        label: 'Tần suất tham gia làm khoá luận theo ngành',
                        data: thesisParticipationData,
                        backgroundColor: 'rgba(255, 99, 132, 0.6)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });
</script>





