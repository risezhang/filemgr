package controllers;

import models.user.User;

import org.apache.commons.lang.StringUtils;

import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;
import utils.Ctrls;

@With(Auth.class)
public class Users extends Controller {

	/**
	 * List all users
	 */
	public void list() {
		
	}
	
	/**
	 * Display a user's detail
	 * @param user
	 */
	public void detail(User user) {
		
	}
	
	/**
	 * Register a new user
	 * @param loginName
	 * @param password
	 * @param displayName
	 * @param retypePassword
	 */
	public static void register(String loginName, String password,
			String displayName, String retypePassword) {
		if (Ctrls.isPost()) {
			validation.required(loginName).message("Require loginName.");
			validation.required(displayName).message("Require displayName.");
			validation.required(password).message("Require password.");
			validation.required(retypePassword).message(
					"Require re-type password.");
			if (StringUtils.isNotEmpty(loginName)) {
				// TODO check if there is a user existed
				//validation
				//		.isTrue(!securityService.isExistLoginName(StringUtils
				//				.lowerCase(loginName)))
				//		.message(
				//				"Login Name is existed in system, please change your login name.");
			}
			if (StringUtils.isNotEmpty(password)
					&& StringUtils.isNotEmpty(retypePassword)) {
				validation
						.isTrue(password.equals(retypePassword))
						.message(
								"You must enter the same password twice in order to confirm it.");
			}
			if (!Validation.hasErrors()) {
				// TODO register
				// securityService.register(loginName, password, displayName);
			} else {
				renderArgs.put("loginName", loginName);
				renderArgs.put("displayName", displayName);
			}
		}

		render();
	}
	
	/**
	 * Change the users status
	 * @param user
	 * @param disabled
	 */
	public void changeStatus(@Required User user, boolean disabled) {
		
	}
}
