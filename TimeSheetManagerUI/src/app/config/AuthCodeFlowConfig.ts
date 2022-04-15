import {AuthConfig} from "angular-oauth2-oidc";

export const AuthCodeFlowConfig: AuthConfig = {
	issuer: "Issuer_Uri",
	redirectUri: window.location.origin,
	clientId: "Client_Id",
	responseType: "code",
	scope: "openid profile email",
	showDebugInformation: true
};
