import { Component } from '@angular/core';
import { PdfSignerService } from './pdf-signer.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  constructor( private service : PdfSignerService) {}

  signPdf() {
    this.service.pdfSign();
  }
}
