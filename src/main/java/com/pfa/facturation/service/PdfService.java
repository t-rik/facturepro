package com.pfa.facturation.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.pfa.facturation.model.Facture;
import com.pfa.facturation.model.LigneFacture;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

@Service
public class PdfService {

    public byte[] generateFacturePdf(Facture facture) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            
            document.add(new Paragraph("FACTUREPRO V2.0")
                    .setFontSize(24));
            
            document.add(new Paragraph("Facture N°: #" + (facture.getId() != null ? facture.getId() : "N/A")));
            
            // Format date safely
            String dateStr = "N/A";
            if (facture.getDateFacture() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dateStr = sdf.format(facture.getDateFacture());
            }
            document.add(new Paragraph("Date: " + dateStr));
            
            // Client name with null check
            String clientName = "N/A";
            if (facture.getClient() != null && facture.getClient().getNom() != null) {
                clientName = facture.getClient().getNom();
            }
            document.add(new Paragraph("Client: " + clientName));
            document.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 2, 2, 2}))
                    .useAllAvailableWidth();
            
            table.addHeaderCell("Description");
            table.addHeaderCell("Quantité");
            table.addHeaderCell("Prix Unitaire");
            table.addHeaderCell("Total");

            if (facture.getLignes() != null && !facture.getLignes().isEmpty()) {
                for (LigneFacture ligne : facture.getLignes()) {
                    // Null-safe cell additions
                    table.addCell(ligne.getDesignation() != null ? ligne.getDesignation() : "");
                    table.addCell(ligne.getQuantite() != null ? String.valueOf(ligne.getQuantite()) : "0");
                    
                    Double prixUnit = ligne.getPrixUnitaire() != null ? ligne.getPrixUnitaire() : 0.0;
                    table.addCell(String.format("%.2f DH", prixUnit));
                    
                    Integer quantite = ligne.getQuantite() != null ? ligne.getQuantite() : 0;
                    double totalLigne = prixUnit * quantite;
                    table.addCell(String.format("%.2f DH", totalLigne));
                }
            } else {
                // Add empty row if no lines
                table.addCell("Aucune ligne");
                table.addCell("-");
                table.addCell("-");
                table.addCell("-");
            }

            document.add(table);
            document.add(new Paragraph("\n"));
            
            Double montantTotal = facture.getMontantTotal() != null ? facture.getMontantTotal() : 0.0;
            document.add(new Paragraph(String.format("Montant Total: %.2f DH", montantTotal))
                    .setFontSize(14));
            
            String statusStr = facture.getStatus() != null ? facture.getStatus().toString() : "N/A";
            document.add(new Paragraph("Statut: " + statusStr));
            
        } catch (Exception e) {
            e.printStackTrace(); // Log for debugging on Koyeb
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
        
        return baos.toByteArray();
    }
}
