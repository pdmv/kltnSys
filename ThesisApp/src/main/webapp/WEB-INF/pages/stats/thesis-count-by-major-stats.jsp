<%-- 
    Document   : thesis-count-by-major-stats
    Created on : Jun 21, 2024, 4:27:51 PM
    Author     : phamdominhvuong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container mt-4">
    <div class="card">
        <div class="card-body">
            <h1 class="text-center mb-4">Thống kê <strong>tần suất tham gia</strong></h1>

            <form method="get" action="<c:url value="/admins/stats/thesis-count-by-major" />" class="row g-2">
                <div class="col-md-3">
                    <div class="form-floating">
                        <select class="form-control" id="schoolYearId" name="schoolYearId">
                            <c:forEach items="${schoolYears}" var="y">
                                <option value="${y.id}" <c:if test="${y.id == selectedSchoolYearId}">selected</c:if>>${y.startYear}-${y.endYear}</option>
                            </c:forEach>
                        </select>
                        <label for="schoolYearId">Chọn niên khóa:</label>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="form-floating">
                        <select class="form-control" id="facultyId" name="facultyId">
                            <c:forEach items="${faculties}" var="f">
                                <option value="${f.id}" <c:if test="${f.id == selectedFacultyId}">selected</c:if>>${f.name}</option>
                            </c:forEach>
                        </select>
                        <label for="facultyId">Chọn khoa:</label>
                    </div>
                </div>
                <div class="col-md-1 d-flex align-items-center justify-content-center"> 
                    <button type="submit" class="btn btn-primary">Thống kê</button>
                </div>
            </form>

            <c:if test="${thesisCounts != null}">
                <div class="d-flex mt-4">
                    <table class="table table-striped table-bordered table-hover" style="flex: 1; margin-right: 20px;">
                        <thead>
                            <tr>
                                <th>Ngành</th>
                                <th>Tần suất tham gia</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${thesisCounts.entrySet()}" var="entry"> 
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

                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <script>
                    // Chuyển đổi dữ liệu từ Model sang dạng ChartJS có thể sử dụng
                    const chartData = {
                        labels: [],
                        datasets: [{
                                label: 'Tần suất tham gia',
                                data: [],
                                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            }]
                    };

                    <c:forEach items="${thesisCounts.entrySet()}" var="entry">
                    chartData.labels.push('${entry.key}');
                    chartData.datasets[0].data.push(${entry.value});
                    </c:forEach>

                    const chartCanvas = document.getElementById('chartCanvas').getContext('2d');
                    new Chart(chartCanvas, {
                        type: 'bar',
                        data: chartData,
                        options: {
                            maintainAspectRatio: false,
                            responsive: true,
                            plugins: {
                                legend: {
                                    position: 'top',
                                },
                                title: {
                                    display: true,
                                    text: 'Thống kê tần suất tham gia khoá luận theo ngành'
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: 'Tần suất tham gia'
                                    }
                                },
                                x: {
                                    title: {
                                        display: true,
                                        text: 'Ngành'
                                    }
                                }
                            }
                        }
                    });
                </script>
            </c:if>
        </div>
    </div>
</div>
