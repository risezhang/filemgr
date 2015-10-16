package utils;

import org.apache.commons.lang.StringUtils;

import play.mvc.Http;
import play.mvc.Http.Request;

public class Ctrls {
	
	public static Request getRequest() {
		return Request.current();
	}
	
	public static boolean isOptions() {
		return StringUtils.equals(getRequest().method, "OPTIONS");
	}

	public static boolean isGet() {
		return StringUtils.equals(getRequest().method, "GET");
	}

	public static boolean isHead() {
		return StringUtils.equals(getRequest().method, "HEAD");
	}

	public static boolean isPost() {
		return StringUtils.equals(getRequest().method, "POST");
	}

	public static boolean isPut() {
		return StringUtils.equals(getRequest().method, "PUT");
	}

	public static boolean isDelete() {
		return StringUtils.equals(getRequest().method, "DELETE");
	}

	public static boolean isTrace() {
		return StringUtils.equals(getRequest().method, "TRACE");
	}
	
	public static boolean isJSON() {
		return StringUtils.equals(getRequest().format, "json");
	}

}
