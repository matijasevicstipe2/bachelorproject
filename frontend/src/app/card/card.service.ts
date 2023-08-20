import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import {PaymentResponse} from "./paymentResponse";

@Injectable({
  providedIn: 'root'
})
export class CardService {

  private headers = new HttpHeaders();

  constructor(private httpClient: HttpClient) {
    this.headers.set('Content-Type', 'application/json; charset=utf-8');
  }

  public createCharge(cardToken: any, amount: any, currency: any, description: any, user: any, option: number) {
    const chargeRequest = {
      token: cardToken.id,
      amount,
      description,
      currency,
      user,
      option
    };
    return this.httpClient.post<PaymentResponse>(`${environment.baseUrl}/pay`, chargeRequest, { headers: this.headers });
  }
}
