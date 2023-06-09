import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GymDashboardComponent } from './gym-dashboard/gym-dashboard.component';
import {LoginComponent} from "./login/login.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import { RegisterComponent } from './register/register.component';
import {NgxStripeModule} from "ngx-stripe";
import { PaymentFormComponent } from './payment-form/payment-form.component';
import { StatsComponent } from './stats/stats.component';
import { MembershipComponent } from './membership/membership.component';
import { FindGymComponent } from './find-gym/find-gym.component';
import { GroupClassesComponent } from './group-classes/group-classes.component';
import { OneOnOneTrainingComponent } from './one-on-one-training/one-on-one-training.component';
import {AuthenticationInterceptor} from "./security/authentication.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    GymDashboardComponent,
    LoginComponent,
    RegisterComponent,
    PaymentFormComponent,
    StatsComponent,
    MembershipComponent,
    FindGymComponent,
    GroupClassesComponent,
    OneOnOneTrainingComponent,
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
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
