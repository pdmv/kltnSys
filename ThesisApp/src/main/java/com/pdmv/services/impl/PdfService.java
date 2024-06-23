/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.services.impl;

import com.pdmv.dto.report.CriterionScore;
import com.pdmv.dto.report.LecturerScore;
import com.pdmv.dto.report.Report;
import com.pdmv.pojo.CouncilLecturer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

/**
 *
 * @author phamdominhvuong
 */
@Service
public class PdfService {

    public byte[] generateThesisScoreReport(Report report) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        URL resourceUrl = getClass().getClassLoader().getResource("fonts/arial.ttf");
        File fontFile = new File(resourceUrl.getFile());
        PDType0Font unicodeFont = PDType0Font.load(document, fontFile);

        float margin = 50;
        float yStart = 750;
        float pageWidth = page.getMediaBox().getWidth();
        float tableWidth = pageWidth - 2 * margin;
        float rowHeight = 20;
        float cellMargin = 5;
        float columnWidth = tableWidth / (report.getScore().size() + 1); // Divide equally for columns

        // Title
        String title = "BẢNG ĐIỂM TỔNG HỢP KHÓA LUẬN";
        float titleWidth = unicodeFont.getStringWidth(title) / 1000 * 18;
        float titleX = (pageWidth - titleWidth) / 2;
        contentStream.beginText();
        contentStream.setFont(unicodeFont, 18);
        contentStream.newLineAtOffset(titleX, yStart);
        contentStream.showText(title);
        contentStream.endText();

        // Add a horizontal line under the title
        yStart -= 10;
        contentStream.setLineWidth(1.5f);
        contentStream.moveTo(margin, yStart);
        contentStream.lineTo(pageWidth - margin, yStart);
        contentStream.stroke();

        // Thesis Information
        yStart -= 30;
        contentStream.beginText();
        contentStream.setFont(unicodeFont, 10);
        contentStream.newLineAtOffset(margin, yStart);
        contentStream.showText("Tên luận văn: " + report.getThesis().getName());
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Niên khóa: " + report.getCouncil().getSchoolYearId().getStartYear() + " - " + report.getCouncil().getSchoolYearId().getEndYear());
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Sinh viên thực hiện: " + String.join(", ", report.getThesis().getThesisStudentSet().stream().map(ts -> ts.getFullname()).collect(Collectors.toSet())));
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Hội đồng: " + report.getCouncil().getName());
        contentStream.newLineAtOffset(0, -rowHeight);
        contentStream.showText("Điểm trung bình: " + String.format("%.2f", report.getThesis().getAvgScore()));
        contentStream.endText();

        // Score Table
        yStart -= 100;
        List<CriterionScore> criterionScores = report.getScore();
        int numColumns = criterionScores.size() + 1;
        int[] columnWidths = new int[numColumns];
        Arrays.fill(columnWidths, 100);
        columnWidths[0] = 150; // Lecturer

        // Draw header (criteria list)
        float nexty = yStart;
        contentStream.setLineWidth(1.0f);
        contentStream.moveTo(margin, nexty);
        contentStream.lineTo(margin + tableWidth, nexty);
        contentStream.stroke();
        contentStream.setFont(unicodeFont, 10);
        float nextx = margin + cellMargin;
        contentStream.beginText();
        contentStream.newLineAtOffset(nextx, nexty - rowHeight + cellMargin);
        contentStream.showText("Giảng viên");
        contentStream.endText();
        nextx += columnWidths[0];

        for (int i = 0; i < criterionScores.size(); i++) {
            String criterionName = criterionScores.get(i).getCriterionName();
            float textWidth = unicodeFont.getStringWidth(criterionName) / 1000 * 10;
            float cellCenterX = nextx + (columnWidth - textWidth) / 2;
            contentStream.beginText();
            contentStream.newLineAtOffset(cellCenterX, nexty - rowHeight + cellMargin);
            contentStream.showText(criterionName);
            contentStream.endText();
            nextx += columnWidth;
        }

        // Draw score data (lecturer names and scores)
        contentStream.setFont(unicodeFont, 10);
        for (CouncilLecturer lecturer : report.getCouncil().getCouncilLecturerSet()) {
            nexty -= rowHeight;
            contentStream.setLineWidth(1.0f);
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nextx = margin + cellMargin;

            contentStream.beginText();
            contentStream.newLineAtOffset(nextx, nexty - rowHeight + cellMargin);
            contentStream.showText(lecturer.getLecturerId().getLastName() + " " + lecturer.getLecturerId().getFirstName());
            contentStream.endText();
            nextx += columnWidth;

            // Lecturer's scores for each criterion
            for (CriterionScore criterionScore : criterionScores) {
                LecturerScore lecturerScore = criterionScore.getScore().stream()
                        .filter(ls -> ls.getLecturerFullname().equals(lecturer.getLecturerId().getLastName() + " " + lecturer.getLecturerId().getFirstName()))
                        .findFirst()
                        .orElse(null);

                contentStream.beginText();
                contentStream.newLineAtOffset(nextx, nexty - rowHeight + cellMargin);
                if (lecturerScore != null) {
                    contentStream.showText(String.valueOf(lecturerScore.getScore()));
                } else {
                    contentStream.showText("");
                }
                contentStream.endText();
                nextx += columnWidth;
            }
        }

        // Signature area
        contentStream.beginText();
        contentStream.setFont(unicodeFont, 12);
        contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 200, 140);
        contentStream.showText("Lãnh đạo khoa ký");
        contentStream.endText();

        // Print date and time (bottom left, small font)
        contentStream.beginText();
        contentStream.setFont(unicodeFont, 8);
        contentStream.newLineAtOffset(margin, 50);
        contentStream.showText("Ngày in: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(report.getPrintedDate()));
        contentStream.endText();

        contentStream.close();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        document.close();

        return out.toByteArray();
    }
}
