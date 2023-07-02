import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from "./login/login.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {CommonModule, DatePipe} from "@angular/common";
import {RegisterComponent} from './register/register.component';
import {StatsComponent} from './stats/stats.component';
import {MembershipComponent} from './membership/membership.component';
import {FindGymComponent} from './find-gym/find-gym.component';
import {GroupClassesComponent} from './group-classes/group-classes.component';
import {OneOnOneTrainingComponent} from './one-on-one-training/one-on-one-training.component';
import {AuthenticationInterceptor} from "./security/authentication.interceptor";
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {HomeComponent} from "./home/home.component";
import {RouterModule} from "@angular/router";
import {FullCalendarModule} from "@fullcalendar/angular";
import {EventPopupComponent} from './event-popup/event-popup.component';
import {CardComponent} from "./card/card.component";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    StatsComponent,
    MembershipComponent,
    FindGymComponent,
    GroupClassesComponent,
    OneOnOneTrainingComponent,
    HomeComponent,
    EventPopupComponent,
    CardComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    FullCalendarModule,
    ReactiveFormsModule,
    HttpClientModule,
    NoopAnimationsModule
  ],
  providers: [
    DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
