package com.training.base;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;
import javax.mail.internet.InternetAddress;



public class ReadOTP {

	private static final String CONFIG_FILE_PATH="/Users/priya/google.creds";

    // You must use your 16-character App Password here, not your real password
    public static String getOTP() throws Exception {
    	
    	Properties configProps = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            configProps.load(input);
        } catch (Exception ex) {
            System.err.println("Error reading properties file from path: " + CONFIG_FILE_PATH);
            throw new Exception("Could not find or load config.properties file. Please check the CONFIG_FILE_PATH variable.", ex);
        }

        String email = configProps.getProperty("GMAIL_USER");
        String appPassword = configProps.getProperty("GMAIL_PASSWORD");

        if (email == null || appPassword == null) {
            throw new Exception("GMAIL_USER or GMAIL_PASSWORD not found in " + CONFIG_FILE_PATH);
        }

        String otp = null;
        
        // 1. Set mail properties
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");
        

        // 2. Connect to the store
        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        store.connect(email, appPassword);

        // 3. Open the INBOX
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE); // Use READ_WRITE to mark as read

        // 4. Search for the Salesforce email
        // Wait a few seconds for the email to arrive
        Thread.sleep(10000); // Wait 10 seconds

        SearchTerm sender = new FromTerm(new InternetAddress("noreply@salesforce.com"));
        Message[] messages = inbox.search(sender, inbox.getMessages());

        if (messages.length == 0) {
            throw new Exception("No Salesforce verification email found.");
        }

        // 5. Get the most recent message
        Message latestMessage = messages[messages.length - 1];
        String emailContent = latestMessage.getContent().toString();

        // 6. Parse the OTP from the email body
        // This regex looks for a 5 or 6 digit number. Adjust if needed.
        Pattern pattern = Pattern.compile("Verification Code: (\\d{5,6})");
        Matcher matcher = pattern.matcher(emailContent);

        if (matcher.find()) {
            otp = matcher.group(1); // Found the OTP
        } else {
            throw new Exception("Could not find OTP in email.");
        }

        // 7. Clean up
        latestMessage.setFlag(Flags.Flag.SEEN, true); // Mark as read
        // latestMessage.setFlag(Flags.Flag.DELETED, true); // Optionally delete
        inbox.close(true);
        store.close();

        return otp;
    }
}
