package utils;

import java.io.File;

import org.hibernate.Session;

public class Persist {
	
	public static boolean OK = true;
	public static boolean KO = false;
	
	public static int ERROR = 2;
	public static int SUCCESS = 200;
	
	/*
	 * Syntax error codes: [100 - 110]
	 */
	public static int ERROR_NULL_PARAMETER = 101;
	
	/*
	 * User error codes: [300 - 310]
	 */
	public static int ERROR_PASSWORD_NOT_VALID = 301;
	public static int ERROR_PASSWORDS_DIFFERENT = 302;
	public static int ERROR_USER_NOT_FOUND = 303;
	public static int ERROR_USER_PASSWORD_NOT_MATCH = 304;
	
	/*
	 * SessionKey error codes : [320 - 330]
	 */
	public static int ERROR_SESSION_KEY_NOT_FOUND = 320;
	
	/*
	 * Database error codes: [500 - 510]
	 */
	public static int ERROR_DB_USERNAME_ALREADY_USED = 501;
	public static int ERROR_DB_USERS_COUNT = 502;
	public static int ERROR_DB_USER_NOT_FOUND = 503;
	public static int ERROR_DB_LOGOUT_IMPOSSIBLE = 504;
	public static int ERROR_DB_NO_ROW_AFFECTED = 505;
	public static int ERROR_DB_IMAGE_CANNOT_ADD_NEW_INSTANCE = 506;
	public static int ERROR_DB_IMAGE_NOT_FOUND = 507;
	public static int ERROR_DB_LIBRARY_CANNOT_ADD_NEW_INSTANCE = 508;
	
	/*
	 * Files error codes: [600 - 610]
	 */
	public static int ERROR_FILE_NOT_FOUND = 601;
	public static int ERROR_FILE_INPUT_STREAM = 602;
	public static int ERROR_FILE_UPLOAD_EXCEPTION = 603;
	public static int ERROR_INPUT_STREAM_NULL = 604;
	
	/*
	 * Process return codes: [700 - 710]
	 */
	public static int PROCESS_NOT_COMPLETED_YET = 701;
	public static int PROCESS_COMPLETE = 707;
		
	public static Integer PROCESS_COMPLETABLE_FUTURE_TASKS_STARTED = 717;
	
	
	
	public static String USERS_TASKS = "userTasks";
	
	
	public static String FROM_REPOSITORY_PATH = "images_save";
	public static File FROM_REPOSITORY = new File(Persist.FROM_REPOSITORY_PATH);
	public static String DEST_MOZAIK_REPOSITORY_PATH = "mozaikRslt";
	public static File DEST_MOZAIK_REPOSITORY = new File(Persist.DEST_MOZAIK_REPOSITORY_PATH);
	
	
	
	/*
	 * Hibernate User Session
	 */
	public static Session OPENED_SESSION = null;
	
}
