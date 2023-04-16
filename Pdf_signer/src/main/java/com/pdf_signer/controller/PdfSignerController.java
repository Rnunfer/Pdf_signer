package com.pdf_signer.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.pdf_signer.Service.PdfSignerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/pdf-signer")
public class PdfSignerController {

	private final PdfSignerService pdfSignerService;
	
	public PdfSignerController (PdfSignerService pdfSignerService) {
		this.pdfSignerService = pdfSignerService;
	}
	
	@GetMapping()
	public void pdfSigner() throws DocumentException, NoSuchAlgorithmException, IOException {
		this.pdfSignerService.pdfSigner();
	}
}
