import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {OAuthModule} from "angular-oauth2-oidc";
import {ProjectModule} from "./project/project.module";
import {RouterModule} from "@angular/router";
import {AppRoutes} from "./app.routing.module";
import { DashboardComponent } from './dashboard/dashboard.component';

@NgModule({
	declarations: [
		AppComponent,
  DashboardComponent
	],
	imports: [
		BrowserModule,
		HttpClientModule,
		OAuthModule.forRoot({
			resourceServer: {
				allowedUrls: ["http://localhost:8080"],
				sendAccessToken: true
			}
		}),
		ProjectModule,
		RouterModule.forRoot(AppRoutes)
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule {
}
