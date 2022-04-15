const LOGOUT_EVENT_NAMES = [
	'jwks_load_error',
	'invalid_nonce_in_state',
	'discovery_document_load_error',
	'discovery_document_validation_error',
	'user_profile_load_error',
	'token_error',
	'code_error',
	'token_refresh_error',
	'silent_refresh_error',
	'silent_refresh_timeout',
	'token_validation_error',
	'token_expires',
	'session_error',
	'session_terminated',
	'logout',
	'token_revoke_error'
];

const LOGIN_EVENT_NAMES = [
	"token_received"
];

export {
	LOGOUT_EVENT_NAMES,
	LOGIN_EVENT_NAMES
}
