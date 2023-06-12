import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Stripe, StripeCardElement, StripeElements, loadStripe } from '@stripe/stripe-js';
import { ActivatedRoute } from '@angular/router';
import { MembershipOption } from "../membership/membership-option";

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
  optionId!: number;
  private amount!: number;
  private backendUrl = 'http://localhost:8080';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.paymentForm = new FormGroup({
      cardNumber: new FormControl('', Validators.required),
      expiry: new FormControl('', Validators.required),
      cvc: new FormControl('', Validators.required)
    });
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.optionId = id ? +id : 0;
      // You can use the optionId value here or perform any other logic based on it
    });
    this.http.get<MembershipOption>(this.backendUrl + `/api/membership-options/${this.optionId}`).subscribe(
      (response) => {
        this.amount = response.fee;
        console.log(response);
      },
      (error) => {
        // Handle the error
        console.error(error);
      }
    );
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

    const cardElement = elements?.create('card', { style });

    this.card = cardElement;

    // Handle card element changes and validation
    this.card?.on('change', (event) => {
      // Handle validation errors
      // e.g., this.showError(event.error?.message);
    });
  }

  // ...

  onSubmit() {
    const cardNumber = this.paymentForm.get('cardNumber')?.value;
    const expiry = this.paymentForm.get('expiry')?.value;
    const cvc = this.paymentForm.get('cvc')?.value;

    if (cardNumber && expiry && cvc) {
      const cardData = {
        card: {
          number: cardNumber,
          exp_month: expiry.split('-')[1],
          exp_year: expiry.split('-')[0],
          cvc: cvc
        }
      };

      this.stripe?.createToken(cardData).then((result) => {
        if (result.error) {
          // Handle error
          console.error(result.error);
        } else {
          // Send the token to your backend
          const token = result.token.id;
          const amount = this.amount;
          const currency = '$' /* specify the payment currency */;
          const description = 'des' /* specify the payment description */;

          const paymentRequest = {
            token,
            amount,
            currency,
            description
          };

          this.http.post(this.backendUrl + '/api/pay', paymentRequest).subscribe(
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
}
