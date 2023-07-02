import {Component, OnInit} from '@angular/core';
import {StripeService} from '../stripe.service';
import {CardService} from './card.service';
import {environment} from '../../environments/environment';
import {MembershipOption} from "../membership/membership-option";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {AuthenticationService} from "../security/authentication.service";

declare var Stripe: any;

/**
 * Simple UI to display Stripe.js card
 * @author Jonathan Miller <john@essolutions.io>
 */
@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent implements OnInit {

  private stripe: any = null;
  private card: any = null;
  private elements: any = null;
  public cardError: string = "";
  public chargeError: any = null;
  public charge: any = null;
  public membershipOption!: MembershipOption;
  private optionId!: number;
  private backendUrl = 'http://localhost:8080';

  constructor(
    private readonly stripeService: StripeService,
    private readonly cardService: CardService,
    private http: HttpClient,
    private route: ActivatedRoute,
    private authService: AuthenticationService
    ) { }

  public ngOnInit() {
    this.stripeService.initializeStripe().subscribe(() => {
      this.stripe = Stripe(environment.stripePublicKey);
      this.elements = this.stripe.elements();
      this.card = this.elements.create('card');
      this.card.mount('#card-element');
      this.card.addEventListener('change',
        (event: any) => event.error ? this.cardError = event.error.message : null);
    });

    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      this.optionId = id ? +id : 0;
      // You can use the optionId value here or perform any other logic based on it
    });

    this.http.get<MembershipOption>(`${this.backendUrl}/api/membership-options/${this.optionId}`).subscribe(
      (response) => {
        this.membershipOption = response;
        console.log(response);
      },
      (error) => {
        console.error(error);
      }
    );
  }

  /**
   * Submits the Stripe token to the backend and creates a charge
   * @param token The Stripe.js token
   */
  public createCharge(token: any) {

    const username = this.authService.getAuthenticatedUserUsername();
    this.charge = null;
    this.chargeError = null;
    this.cardService
      .createCharge(token, this.membershipOption.fee, 'usd', this.membershipOption.description, username, this.optionId)
      .subscribe({
        next: (response: string) => {
         console.log(response)
        },
        error: (error: any) => {
      console.log("Error:", JSON.stringify(error));
    }

  });
  }

  /**
   * Gets a Stripe token from the Stripe.js API
   */
  public getToken() {
    this.stripe.createToken(this.card).then((result: any) => {
      if (result.error) {
        this.cardError = result.error.message;
      } else {
        this.createCharge(result.token);
      }
    });
  }
}
