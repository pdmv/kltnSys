/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
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

document.addEventListener("DOMContentLoaded", function() {
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



