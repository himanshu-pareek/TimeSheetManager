import {Component, OnInit} from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";
import {AuthCodeFlowConfig} from "./config/AuthCodeFlowConfig";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {
	title = 'TimeSheetManagerUI';

	constructor(
		private oauthService: OAuthService
	) {
		this.oauthService.configure(AuthCodeFlowConfig);
		this.oauthService.loadDiscoveryDocumentAndTryLogin();
	}

	login() {
		this.oauthService.initCodeFlow();
	}

	logout() {
		this.oauthService.logOut();
	}

	get isLoggedIn(): boolean {
		return this.oauthService.hasValidAccessToken();
	}
}
