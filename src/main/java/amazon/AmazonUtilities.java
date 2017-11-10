package amazon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import utils.Persist;

public class AmazonUtilities {
	
	
	public static int uploadImagesAmazonAPI(File toUpload) {
        TransferManager tm = new TransferManager(new ProfileCredentialsProvider());
        // TransferManager processes all transfers asynchronously, 
        // so this call will return immediately.
        System.out.println("Trying to upload " + toUpload.getAbsolutePath());
        Upload upload = tm.upload(Persist.AMAZON_S3_BUCKET_NAME, toUpload.getName(), toUpload);
        System.out.println("----------");

        try {
        	// Or you can block and wait for the upload to finish
        	upload.waitForCompletion();
        	System.out.println("Upload complete.");
        	return Persist.SUCCESS;
        } catch (AmazonClientException amazonClientException) {
        	System.out.println("Unable to upload file, upload was aborted.");
        	System.out.println("Caught an AmazonClienException, which"
					+ "	means the client encountered an internal error"
					+ " while trying to communicate with S3, such as not"
					+ " being able to access the network.");
			System.out.println("Error Message: " + amazonClientException.getMessage());
        	amazonClientException.printStackTrace();
        	return Persist.ERROR_AMAZON_S3_CLIENT;
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Persist.ERROR;
	}

}
