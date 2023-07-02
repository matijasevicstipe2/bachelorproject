import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {loadStripe} from '@stripe/stripe-js';
import {ActivatedRoute} from '@angular/router';
import {MembershipOption} from "../membership/membership-option";
import {AuthenticationService} from "../security/authentication.service";
import { Observable } from 'rxjs';

@Component({
  selector: 'app-payment-form',
  templateUrl: './payment-form.component.html',
  styleUrls: ['./payment-form.component.css']
})
export class PaymentFormComponent implements OnInit {
  paymentForm!: FormGroup;
  submitting: boolean = false;
  optionId!: number;
  private amount!: number;
  private backendUrl = 'http://localhost:8080';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private route: ActivatedRoute,
    private authService: AuthenticationService
  ) {}

  ngOnInit() {
    loadStripe('pk_test_51NFiiGJoBfdHZHS3XTtMO3VjzFCemXOU5S3v6Fw3X1phgeTb0WV5hvNBsX9kbBYh9cFoJfex1le2UoWXoAWVYWoa009VeU263u')
      .then(stripe => {
      (<any>window).Stripe = stripe;
    });

    this.paymentForm = this.fb.group({
      cardNumber: ['', Validators.required],
      expiry: ['', Validators.required],
      cvc: ['', Validators.required]
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
  }

  formatExpiryDate(event: Event) {
    const input = event.target as HTMLInputElement;
    let formattedValue = input.value.replace(/\D/g, '');

    if (formattedValue.length > 2) {
      formattedValue = formattedValue.replace(/(\d{2})(\d{2})/, '$1/$2');
    }

    input.value = formattedValue;
  }

  onSubmit() {
    const cardNumber = this.paymentForm.get('cardNumber')?.value;
    const expiry = this.paymentForm.get('expiry')?.value;
    const [expMonth, expYear] = expiry.split('/').map((item: string) => item.trim());


    const cvc = this.paymentForm.get('cvc')?.value;

    (<any>window).Stripe.card.createToken({
      number: cardNumber,
      exp_month: expMonth,
      exp_year: expYear,
      cvc: cvc
    }, (status: number, response: any) => {
      if (status === 200) {
        const token = response.id;
        const amount = this.amount;
        const currency = 'USD';
        const description = 'payment';
        const username = this.authService.getAuthenticatedUserUsername();
        const option = this.optionId;

        const paymentRequest = {
          token,
          amount,
          currency,
          description,
          username,
          option
        };
        this.chargeCard(paymentRequest);
      } else {
        console.log(response.error.message);
      }
    });
  }

  chargeCard(paymentRequest: any) {
    this.http.post(this.backendUrl + '/api/pay', paymentRequest)
      .subscribe((resp: any) => {
        console.log(resp);
      })
  }

}
