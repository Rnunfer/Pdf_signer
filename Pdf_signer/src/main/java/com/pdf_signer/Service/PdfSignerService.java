package com.pdf_signer.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Utilities;
import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfLiteral;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfPKCS7;
import com.lowagie.text.pdf.PdfReader;

@Service
public class PdfSignerService {

	public PdfSignerService() {}
	
	public boolean pdfSigner() throws DocumentException, IOException, NoSuchAlgorithmException, SignatureException {
		
		String pdf = "src/main/resources/pdf_digital_signature_timestamp.pdf";
		
		 List<X509Certificate> certificates = new ArrayList<>();
	        System.out.println("pdf name: " + pdf);

	        KeyStore kall = PdfPKCS7.loadCacertsKeyStore();

	        try (PdfReader reader = new PdfReader(pdf)) {
	            AcroFields fields = reader.getAcroFields();

	            List<String> signatures = fields.getSignedFieldNames();
	            System.out.println("Signs: " + signatures.size());
	            for (String signature : signatures) {

	                System.out.println("Signature name: " + signature);
	                System.out.println("Signature covers whole document: " + fields.signatureCoversWholeDocument(signature));
	                System.out.println(
	                        "Document revision: " + fields.getRevision(signature) + " of " + fields.getTotalRevisions());

	                PdfPKCS7 pk = fields.verifySignature(signature);
	                Calendar cal = pk.getSignDate();
	                Certificate[] pkc = pk.getCertificates();
	                X509Certificate certificate = pk.getSigningCertificate();
	                certificates.add(certificate);
	                System.out.println("sign date:" + cal.getTime());
	                System.out.println("Subject: " + PdfPKCS7.getSubjectFields(certificate));
	                System.out.println("Document modified: " + !pk.verify());
	                System.out.println("Timestamp valid: " + pk.verifyTimestampImprint());

	                Object[] fails = PdfPKCS7.verifyCertificates(pkc, kall, null, cal);
	                if (fails == null) {
	                    System.out.println("Certificates verified against the KeyStore");
	                } else {
	                    System.out.println("Certificate failed: " + fails[1]);
	                }

	            }
	        }

        
        return true;
    }
    
    private byte[] getSHA256(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(bytes);
    }
}
