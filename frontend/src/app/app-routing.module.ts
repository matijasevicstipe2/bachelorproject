import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GymDashboardComponent} from "./gym-dashboard/gym-dashboard.component";
import {LoginComponent} from "./login/login.component";
import {LoggedInGuard} from "./security/logged-in.guard";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {ForbiddenPageComponent} from "./forbidden-page/forbidden-page.component";
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./register/register.component";
import {StatsComponent} from "./stats/stats.component";
import {FindGymComponent} from "./find-gym/find-gym.component";
import {MembershipComponent} from "./membership/membership.component";
import {OneOnOneTrainingComponent} from "./one-on-one-training/one-on-one-training.component";
import {GroupClassesComponent} from "./group-classes/group-classes.component";
import {PaymentFormComponent} from "./payment-form/payment-form.component";

const routes: Routes = [
  { path: 'gym', component: GymDashboardComponent},
  { path: 'gym-dashboard', component: GymDashboardComponent},
  { path: 'payment/:id', component: PaymentFormComponent, canActivate: [LoggedInGuard] },
  { path: 'stats', component: StatsComponent, canActivate: [LoggedInGuard]},
  { path: 'find-gym', component: FindGymComponent, canActivate: [LoggedInGuard]},
  { path: 'one-on-one-training', component: OneOnOneTrainingComponent, canActivate: [LoggedInGuard]},
  { path: 'group-classes', component: GroupClassesComponent, canActivate: [LoggedInGuard]},
  { path: 'membership', component: MembershipComponent, canActivate: [LoggedInGuard]},
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
