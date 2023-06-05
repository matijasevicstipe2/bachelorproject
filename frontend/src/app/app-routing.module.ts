import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GymDashboardComponent} from "./gym-dashboard/gym-dashboard.component";
import {LoginComponent} from "./login/login.component";
import {LoggedInGuard} from "./security/logged-in.guard";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {ForbiddenPageComponent} from "./forbidden-page/forbidden-page.component";
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./register/register.component";

const routes: Routes = [
  { path: 'gym', component: GymDashboardComponent},
  { path: 'gym-dashboard', component: GymDashboardComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [LoggedInGuard]},
  {path: 'forbidden', component: ForbiddenPageComponent},
  {path: 'signup', component: RegisterComponent},
  {path: '**', component: PageNotFoundComponent}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
