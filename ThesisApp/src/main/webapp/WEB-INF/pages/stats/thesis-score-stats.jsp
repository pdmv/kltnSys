<%-- 
    Document   : thesis-score-stats
    Created on : Jun 21, 2024, 4:24:03 PM
    Author     : phamdominhvuong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container mt-4">
    <div class="card">
        <div class="card-body">
            <h1 class="text-center mt-3 mb-4">Thống kê <strong>điểm khoá luận</strong></h1>

            <form method="get" action="<c:url value="/admins/stats/thesis-scores" />" class="row g-2">
                <div class="col-md-10">
                    <div class="form-floating">
                        <select class="form-control" id="facultyId" name="facultyId">
                            <c:forEach items="${faculties}" var="f">
                                <option value="${f.id}" <c:if test="${f.id == selectedFacultyId}">selected</c:if>>${f.name}</option>
                            </c:forEach>
                        </select>
                        <label for="facultyId">Chọn khoa:</label> 
                    </div>
                </div>
                <div class="col-md-2 d-flex align-items-center justify-content-center"> 
                    <button type="submit" class="btn btn-primary">Thống kê</button>
                </div>
            </form>

            <div id="chartDataContainer" style="display: flex; justify-content: space-between; margin-top: 20px;">
                <table class="table table-striped table-bordered table-hover" style="flex: 1; margin-right: 20px;">
                    <thead>
                        <tr>
                            <th>Năm học</th>
                            <th>Điểm trung bình</th>
                        </tr>
                    </thead>
                    <tbody id="tableData">
                        <c:forEach items="${avgScores.entrySet()}" var="entry">
                            <tr>
                                <td>${entry.key}</td>
                                <td>${entry.value}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div style="flex: 1;">
                    <canvas id="chartCanvas"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // Chuyển đổi dữ liệu từ Model sang dạng ChartJS có thể sử dụng
    const chartData = {
        labels: [],
        datasets: [{
                label: 'Điểm trung bình khóa luận',
                data: [],
                backgroundColor: 'rgba(75,192,192,0.6)'
            }]
    };

    <c:forEach items="${avgScores.entrySet()}" var="entry">
    chartData.labels.push('${entry.key}');
    chartData.datasets[0].data.push(${entry.value});
    </c:forEach>

    // Tạo biểu đồ ChartJS
    const chartCanvas = document.getElementById('chartCanvas').getContext('2d');
    new Chart(chartCanvas, {
        type: 'bar',
        data: chartData,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top'
                },
                title: {
                    display: true,
                    text: 'Điểm trung bình khóa luận theo năm'
                }
            }
        }
    });
</script>