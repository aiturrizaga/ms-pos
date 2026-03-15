package com.sisuz.pos.domain.sale.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.sisuz.pos.domain.sale.entity.PosSale;
import com.sisuz.pos.domain.sale.entity.PosSaleLine;
import com.sisuz.pos.domain.sale.entity.PosSalePayment;
import com.sisuz.pos.domain.sale.repository.PosSaleLineRepository;
import com.sisuz.pos.domain.sale.repository.PosSalePaymentRepository;
import com.sisuz.pos.domain.sale.repository.PosSaleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PosSaleReceiptService {

    private final PosSaleRepository saleRepository;
    private final PosSaleLineRepository lineRepository;
    private final PosSalePaymentRepository paymentRepository;

    private static final float PAGE_WIDTH = 226f; // 80mm
    private static final float PAGE_HEIGHT = 600f;
    private static final float MARGIN = 10f;

    private static final Font FONT_NORMAL = new Font(Font.COURIER, 8, Font.NORMAL);
    private static final Font FONT_BOLD = new Font(Font.COURIER, 8, Font.BOLD);
    private static final Font FONT_TITLE = new Font(Font.COURIER, 10, Font.BOLD);
    private static final Font FONT_SMALL = new Font(Font.COURIER, 7, Font.NORMAL);

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.of("America/Lima"));

    public byte[] generate(Long saleId) {
        PosSale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada: " + saleId));

        List<PosSaleLine> lines = lineRepository.findAllBySaleId(saleId);
        List<PosSalePayment> payments = paymentRepository.findAllBySaleId(saleId);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Rectangle pageSize = new Rectangle(PAGE_WIDTH, PAGE_HEIGHT);
            Document doc = new Document(pageSize, MARGIN, MARGIN, MARGIN, MARGIN);
            PdfWriter.getInstance(doc, out);
            doc.open();

            addHeader(doc, sale);
            addDivider(doc);
            addLines(doc, lines);
            addDivider(doc);
            addTotals(doc, sale);
            addDivider(doc);
            addPayments(doc, payments);
            addDivider(doc);
            addFooter(doc);

            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando recibo PDF", e);
        }
    }

    private void addHeader(Document doc, PosSale sale) throws DocumentException {
        doc.add(centeredParagraph("FARMACIA SAN MIGUEL S.A.C.", FONT_TITLE));
        doc.add(centeredParagraph("RUC: 20604578931", FONT_NORMAL));
        doc.add(centeredParagraph("Av. Libertadores 4820, Lima", FONT_SMALL));
        doc.add(new Paragraph(" "));
        doc.add(centeredParagraph("RECIBO DE VENTA", FONT_BOLD));
        doc.add(centeredParagraph("N° " + sale.getSaleNumber(), FONT_BOLD));
        doc.add(new Paragraph(" "));
        doc.add(paragraph("Fecha   : " + FMT.format(sale.getPaidAt()), FONT_NORMAL));
        doc.add(paragraph("Cajero  : " + sale.getCashierId(), FONT_NORMAL));
        doc.add(paragraph("Terminal: " + sale.getTerminal().getName(), FONT_NORMAL));
        if (sale.getCustomerId() != null) {
            doc.add(paragraph("Cliente : " + sale.getCustomerId(), FONT_NORMAL));
        }
    }

    private void addLines(Document doc, List<PosSaleLine> lines) throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{3.5f, 1f, 1.5f, 1.5f});
        table.setWidthPercentage(100);

        addHeaderCell(table, "Producto");
        addHeaderCell(table, "Cant");
        addHeaderCell(table, "P.U.");
        addHeaderCell(table, "Total");

        for (PosSaleLine l : lines) {
            addCell(table, l.getSkuName(), Element.ALIGN_LEFT);
            addCell(table, l.getQty().stripTrailingZeros().toPlainString(), Element.ALIGN_CENTER);
            addCell(table, fmt(l.getUnitPrice()), Element.ALIGN_RIGHT);
            addCell(table, fmt(l.getLineTotal()), Element.ALIGN_RIGHT);
        }

        doc.add(table);
    }

    private void addTotals(Document doc, PosSale sale) throws DocumentException {
        doc.add(row("Subtotal", fmt(sale.getSubtotal()), FONT_NORMAL));
        if (sale.getDiscountTotal().compareTo(java.math.BigDecimal.ZERO) > 0)
            doc.add(row("Descuento", "-" + fmt(sale.getDiscountTotal()), FONT_NORMAL));
        doc.add(row("IGV", fmt(sale.getTaxTotal()), FONT_NORMAL));
        doc.add(row("TOTAL " + sale.getCurrencyCode(), fmt(sale.getTotal()), FONT_BOLD));
    }

    private void addPayments(Document doc, List<PosSalePayment> payments) throws DocumentException {
        doc.add(paragraph("Pagos:", FONT_BOLD));
        for (PosSalePayment p : payments) {
            doc.add(row(p.getPaymentMethod().getName(), fmt(p.getAmount()), FONT_NORMAL));
        }
    }

    private void addFooter(Document doc) throws DocumentException {
        doc.add(new Paragraph(" "));
        doc.add(centeredParagraph("¡Gracias por su compra!", FONT_BOLD));
        doc.add(centeredParagraph("Conserve su recibo", FONT_SMALL));
    }

    private void addDivider(Document doc) throws DocumentException {
        doc.add(paragraph("--------------------------------", FONT_SMALL));
    }

    // --- helpers ---

    private Paragraph centeredParagraph(String text, Font font) {
        Paragraph p = new Paragraph(text, font);
        p.setAlignment(Element.ALIGN_CENTER);
        return p;
    }

    private Paragraph paragraph(String text, Font font) {
        return new Paragraph(text, font);
    }

    private Paragraph row(String label, String value, Font font) {
        Paragraph p = new Paragraph();
        Chunk l = new Chunk(label, font);
        Chunk v = new Chunk(value, font);
        p.add(l);
        // relleno manual para alinear a la derecha
        int spaces = Math.max(1, 32 - label.length() - value.length());
        p.add(new Chunk(" ".repeat(spaces), font));
        p.add(v);
        return p;
    }

    private void addHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FONT_BOLD));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(Color.BLACK);
        cell.setPadding(2);
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FONT_NORMAL));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(2);
        table.addCell(cell);
    }

    private String fmt(java.math.BigDecimal value) {
        return value != null ? value.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString() : "0.00";
    }
}
