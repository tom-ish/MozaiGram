package utils;

import java.io.File;

import org.hibernate.Session;

public class Persist {
	
	public static final boolean OK = true;
	public static final boolean KO = false;
	
	public static final int ERROR = 2;
	public static final int SUCCESS = 200;
	
	/*
	 * Syntax error codes: [100 - 110]
	 */
	public static final int ERROR_NULL_PARAMETER = 101;
	
	/*
	 * User error codes: [300 - 310]
	 */
	public static final int ERROR_PASSWORD_NOT_VALID = 301;
	public static final int ERROR_PASSWORDS_DIFFERENT = 302;
	public static final int ERROR_USER_NOT_FOUND = 303;
	public static final int ERROR_USER_PASSWORD_NOT_MATCH = 304;
	
	/*
	 * Friendship error/return codes: [311 - 319]
	 */
	public static final int ERROR_FRIEND_NOT_FOUND = 311;
	public static final int ERROR_FRIENDSHIP_NOT_FOUND = 312;
	public static final int STATUS_FRIENDSHIP_REQUEST_SENT = 313;
	public static final int STATUS_FRIENDSHIP_REQUEST_ACCEPTED = 314;

	
	/*
	 * SessionKey error codes : [320 - 330]
	 */
	public static final int ERROR_SESSION_KEY_NOT_FOUND = 320;
	
	/*
	 * Database error codes: [500 - 510]
	 */
	public static final int ERROR_DB_USERNAME_ALREADY_USED = 501;
	public static final int ERROR_DB_USERS_COUNT = 502;
	public static final int ERROR_DB_USER_NOT_FOUND = 503;
	public static final int ERROR_DB_LOGOUT_IMPOSSIBLE = 504;
	public static final int ERROR_DB_NO_ROW_AFFECTED = 505;
	public static final int ERROR_DB_IMAGE_CANNOT_ADD_NEW_INSTANCE = 506;
	public static final int ERROR_DB_IMAGE_NOT_FOUND = 507;
	public static final int ERROR_DB_LIBRARY_CANNOT_ADD_NEW_INSTANCE = 508;
	
	/*
	 * Files error codes: [600 - 610]
	 */
	public static final int ERROR_FILE_NOT_FOUND = 601;
	public static final int ERROR_FILE_INPUT_STREAM = 602;
	public static final int ERROR_FILE_UPLOAD_EXCEPTION = 603;
	public static final int ERROR_INPUT_STREAM_NULL = 604;
	
	/*
	 * Process return codes: [700 - 710]
	 */
	public static final int PROCESS_NOT_COMPLETED_YET = 701;
	public static final int PROCESS_COMPLETE = 707;
		
	public static final Integer PROCESS_COMPLETABLE_FUTURE_TASKS_STARTED = 717;
	
	/*
	 * Amazon S3 error codes: [800]
	 */
	public static final int ERROR_AMAZON_S3_CLIENT = 801;
	
	public static final String AMAZON_S3_BUCKET_NAME = "mozaigram";
	public static final String AWS_ACCESS_KEY_ID = "AWS_ACCESS_KEY_ID";
	public static final String AWS_SECRET_KEY_ID = "AWS_SECRET_KEY";

	public static final String USERS_TASKS = "userTasks";
	
	
	public static final String FROM_REPOSITORY_PATH = "images_save";
	public static final File FROM_REPOSITORY = new File(Persist.FROM_REPOSITORY_PATH);
	public static final String DEST_MOZAIK_REPOSITORY_PATH = "mozaikRslt";
	public static final File DEST_MOZAIK_REPOSITORY = new File(Persist.DEST_MOZAIK_REPOSITORY_PATH);
	
	
	/*
	 * Hibernate User Session
	 */
	public static Session OPENED_SESSION = null;
	
}
