import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GymDashboardComponent } from './gym-dashboard/gym-dashboard.component';
import {LoginComponent} from "./login/login.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import { RegisterComponent } from './register/register.component';
import {NgxStripeModule} from "ngx-stripe";
import { PaymentFormComponent } from './payment-form/payment-form.component';

@NgModule({
  declarations: [
    AppComponent,
    GymDashboardComponent,
    LoginComponent,
    RegisterComponent,
    PaymentFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CommonModule,
    ReactiveFormsModule,
    NgxStripeModule.forRoot('pk_test_51NFiiGJoBfdHZHS3XTtMO3VjzFCemXOU5S3v6Fw3X1phgeTb0WV5hvNBsX9kbBYh9cFoJfex1le2UoWXoAWVYWoa009VeU263u')
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
