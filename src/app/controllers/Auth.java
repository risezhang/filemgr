package controllers;

import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * This controller handles login & logout request
 * 
 */
public class Auth extends Controller {

	/**
	 * Check logged in or not for current request
	 */
	@Before(unless = { "login", "logout", "captcha" })
	protected static void checkLogin() {
		// TODO check if the user is login or if there is access token 
	}

}
