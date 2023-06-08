import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Stripe, StripeCardElement, StripeElements, loadStripe } from '@stripe/stripe-js';

@Component({
  selector: 'app-payment-form',
  templateUrl: './payment-form.component.html',
  styleUrls: ['./payment-form.component.css']
})
export class PaymentFormComponent implements OnInit {
  stripe!: Stripe | null;
  paymentForm!: FormGroup;
  elements!: StripeElements;
  card!: StripeCardElement | undefined;
  submitting: boolean = false;
  @ViewChild('cardElement') cardElement!: ElementRef;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit() {
    const publishableKey = 'pk_test_51NFiiGJoBfdHZHS3XTtMO3VjzFCemXOU5S3v6Fw3X1phgeTb0WV5hvNBsX9kbBYh9cFoJfex1le2UoWXoAWVYWoa009VeU263u';
    loadStripe(publishableKey).then((stripe) => {
      this.stripe = stripe;
      this.setupStripeElements();
    });
  }

  private setupStripeElements() {
    const elements = this.stripe?.elements();
    const style = {
      base: {
        fontSize: '16px',
        color: '#32325d',
      }
    };

    this.card = elements?.create('card', { style });
    this.card?.mount('#card-element');

    // Handle card element changes and validation
    this.card?.on('change', (event) => {
      // Handle validation errors
      // e.g., this.showError(event.error?.message);
    });
  }

  onSubmit() {
    // Get the card token from the card element
    this.stripe?.createToken(this.cardElement.nativeElement).then((result) => {
      if (result.error) {
        // Handle error
        console.error(result.error);
      } else {
        // Send the token to your backend
        const token = result.token.id;
        const amount = 100/* specify the payment amount */;
        const currency = '$'/* specify the payment currency */;
        const description = 'des'/* specify the payment description */;

        const paymentRequest = {
          token,
          amount,
          currency,
          description
        };

        this.http.post('/api/pay', paymentRequest).subscribe(
          (response) => {
            // Payment successful
            console.log('Payment successful:', response);
          },
          (error) => {
            // Payment error
            console.error('Payment error:', error);
          }
        );
      }
    });
  }
}
