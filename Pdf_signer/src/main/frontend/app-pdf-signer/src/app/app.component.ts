import { Component } from '@angular/core';
import { PdfSignerService } from './pdf-signer.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  cambio: boolean= false;

  constructor( private service : PdfSignerService) {}

  signPdf() {
    this.service.pdfSign().subscribe(
      (data) => {this.cambio = data}
    )
  }
}
