package com.xsustain.xsustaincrm.service.Impl;

import com.nylas.NylasClient;
import com.nylas.models.CodeExchangeRequest;
import com.nylas.models.CodeExchangeResponse;
import com.nylas.models.CreateAttachmentRequest;
import com.nylas.models.EmailName;
import com.nylas.models.ListMessagesQueryParams;
import com.nylas.models.ListResponse;
import com.nylas.models.Message;
import com.nylas.models.Response;
import com.nylas.models.SendMessageRequest;
import com.nylas.models.TrackingOptions;
import com.nylas.util.FileUtils;

import jakarta.mail.MessagingException;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xsustain.xsustaincrm.dto.EmailObjectDTO;
import com.xsustain.xsustaincrm.dto.ResponseDto;
import com.xsustain.xsustaincrm.exception.UserServiceCustomException;
import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.repository.UserRepository;
import com.xsustain.xsustaincrm.service.NylasService;
import com.xsustain.xsustaincrm.utility.NylasConfigManager;
import com.xsustain.xsustaincrm.utility.ResponseUtil;

@Service
public class NylasCService implements NylasService {

    private final NylasClient nylasClient;
    private final String callbackUri;
    private final String clientId;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseUtil responseUtil;

    // Constructor for dependency injection
    public NylasCService() {
        NylasConfigManager configManager = NylasConfigManager.getInstance();
        this.nylasClient = configManager.getNylasClient();
        this.callbackUri = configManager.getCallbackUri(); // Assuming you have this method
        this.clientId = configManager.getClientId();
    }

    @Autowired
    SessionService sessionService;

    @Override
    public ResponseEntity<String> saveGrandId(String code) throws MessagingException {
        System.out.println("Starting code exchange for: " + code);
        CodeExchangeRequest codeRequest = new CodeExchangeRequest(
                "http://localhost:8085/api/v1/mailing/oauth/exchange", // redirectUri
                code, // authorization code
                "c9314065-1236-412b-a943-9b947170d56b", // client ID
                null, // access token (if applicable)
                "nylas");

        System.out.println("Attempting code exchange with callbackUri: " + callbackUri + ", clientId: " + clientId);

        try {
            CodeExchangeResponse codeResponse = nylasClient.auth().exchangeCodeForToken(codeRequest);
            System.out.println("Code exchange successful. Grant ID: " + codeResponse.getGrantId());
            String encodedGrantId = URLEncoder.encode(codeResponse.getGrantId(), StandardCharsets.UTF_8.toString());

            // return responseUtil.createResponse("User updated", "SUCCESS",
            // codeResponse.getGrantId());
            String redirectUrl = "http://localhost:3000/redirect?grantId=" + encodedGrantId;
            return ResponseEntity.status(302)
                    .location(URI.create(redirectUrl))
                    .build();
        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
            e.printStackTrace();
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

    @Override
    public ListResponse<Message> getEmail(String code) throws MessagingException {
        System.out.println("Starting code exchange for: " + code);
        ListMessagesQueryParams queryParams = new ListMessagesQueryParams.Builder().limit(5).build();

        try {
            ListResponse<Message> message = nylasClient.messages().list(code, queryParams);
            return message;

        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
            e.printStackTrace();
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

    @Override
    public ListResponse<Message> searchInobx(String code, String search) throws MessagingException {
        System.out.println("Starting code exchange for: " + code);
        ListMessagesQueryParams queryParams = new ListMessagesQueryParams.Builder()
                .searchQueryNative("subject:" + search).limit(5).build();

        try {
            ListResponse<Message> message = nylasClient.messages().list(code, queryParams);
            return message;

        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
            e.printStackTrace();
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

    @Override
    public ResponseDto sendEmail(String code, EmailObjectDTO email) throws MessagingException {

        List<EmailName> emailNames = new ArrayList<>();
        TrackingOptions options = new TrackingOptions("Track this message", true, true, true);

        emailNames.add(new EmailName(email.getReceipient(), email.getName()));

        // CreateAttachmentRequest attachment =
        // FileUtils.attachFileRequestBuilder("src/main/java/JavaLogo.png");
        // List<CreateAttachmentRequest> request = new ArrayList<>();
        // request.add(attachment);

        try {
            SendMessageRequest requestBody = new SendMessageRequest.Builder(emailNames).subject(email.getSubject())
                    .body(email.getContent())
                    .trackingOptions(options)
                    .build();

            Response<Message> emails = nylasClient.messages().send(code, requestBody);

            return responseUtil.createResponse("Your Token is successfully sended", "SUCCESS",
                    "EMail has been sent");

        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
            e.printStackTrace();
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

    public ResponseDto scheduleEmail(String code, EmailObjectDTO email) throws MessagingException {

        List<EmailName> emailNames = new ArrayList<>();
        emailNames.add(new EmailName(email.getReceipient(), email.getName()));

        // CreateAttachmentRequest attachment =
        // FileUtils.attachFileRequestBuilder("src/main/java/JavaLogo.png");
        // List<CreateAttachmentRequest> request = new ArrayList<>();
        // request.add(attachment);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime = LocalDateTime.parse(email.getTime(), formatter);

        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Long timestamp = instant.getEpochSecond();

        int timestampAsInt = timestamp.intValue();

        try {
            SendMessageRequest requestBody = new SendMessageRequest.Builder(emailNames).subject(email.getSubject())
                    .body(email.getContent())
                    .sendAt(timestampAsInt)
                    .build();

            Response<Message> emails = nylasClient.messages().send(code, requestBody);

            return responseUtil.createResponse("Your Token is successfully sended", "SUCCESS",
                    "EMail has been sent");

        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
            e.printStackTrace();
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

}
