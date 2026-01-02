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

@Service
public class PdfService {

    public byte[] generateFacturePdf(Facture facture) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            
            document.add(new Paragraph("FACTUREPRO V2.0")
                    .setFontSize(24));
            
            document.add(new Paragraph("Facture N°: #" + facture.getId()));
            document.add(new Paragraph("Date: " + facture.getDateFacture()));
            document.add(new Paragraph("Client: " + (facture.getClient() != null ? facture.getClient().getNom() : "N/A")));
            document.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 2, 2, 2}))
                    .useAllAvailableWidth();
            
            table.addHeaderCell("Description");
            table.addHeaderCell("Quantité");
            table.addHeaderCell("Prix Unitaire");
            table.addHeaderCell("Total");

            if (facture.getLignes() != null) {
                for (LigneFacture ligne : facture.getLignes()) {
                    table.addCell(ligne.getDesignation());
                    table.addCell(String.valueOf(ligne.getQuantite()));
                    table.addCell(ligne.getPrixUnitaire() + " DH");
                    double totalLigne = (ligne.getPrixUnitaire() != null ? ligne.getPrixUnitaire() : 0) * 
                                      (ligne.getQuantite() != null ? ligne.getQuantite() : 0);
                    table.addCell(totalLigne + " DH");
                }
            }

            document.add(table);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Montant Total: " + facture.getMontantTotal() + " DH")
                    .setFontSize(14));
            
            document.add(new Paragraph("Statut: " + facture.getStatus()));
            
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
        
        return baos.toByteArray();
    }
}
