import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PdfSignerService {

  private apiURL = "http://localhost:8080/pdf-signer";

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private httpClient: HttpClient) { }

  pdfSign(): Observable<boolean> {
    return this.httpClient.get<boolean>(this.apiURL);
  }
}
