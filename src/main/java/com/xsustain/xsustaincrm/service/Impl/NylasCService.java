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
import com.xsustain.xsustaincrm.dto.EmailTimeObjectDTO;
import com.xsustain.xsustaincrm.dto.ResponseDto;
import com.xsustain.xsustaincrm.exception.UserServiceCustomException;
import com.xsustain.xsustaincrm.model.InboxEmail;
import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.repository.InboxRepository;
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
    private InboxRepository inboxEmailRepository;

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
    public ResponseEntity<String> generateGrantId(String code) throws MessagingException {
        System.out.println("dddddddddddddddd" + code);
        CodeExchangeRequest codeRequest = new CodeExchangeRequest(
                "http://localhost:8085/api/v1/mailing/oauth/exchange", // redirectUri
                code, // authorization code
                "c9314065-1236-412b-a943-9b947170d56b", // client ID
                null, // access token (if applicable)
                "nylas");

        try {
            CodeExchangeResponse codeResponse = nylasClient.auth().exchangeCodeForToken(codeRequest);

            String encodedGrantId = URLEncoder.encode(codeResponse.getGrantId(), StandardCharsets.UTF_8.toString());

            String redirectUrl = "http://localhost:3000/react/template/processing?redirectURI=http://localhost:3000/react/template/application/email&grantId="
                    + encodedGrantId;
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
    public ResponseDto saveGrandId(String code) throws MessagingException {

        User user = sessionService.getUserBySession().get();

        InboxEmail saveEmail = new InboxEmail();
        saveEmail.setEmail(user.getEmail());
        saveEmail.setGrantId(code);
        saveEmail.setUser(user);

        try {

            inboxEmailRepository.save(saveEmail);

            return responseUtil.createResponse("Your Token is successfully sended", "SUCCESS",
                    "EMail has been sent");
        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
            e.printStackTrace();
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

    @Override
    public ListResponse<Message> getEmail() throws MessagingException {

        ListMessagesQueryParams queryParams = new ListMessagesQueryParams.Builder().limit(10).build();
        User user = sessionService.getUserBySession().get();

        try {
            ListResponse<Message> message = nylasClient.messages().list(user.getGrandId(), queryParams);
            return message;

        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

    @Override
    public ListResponse<Message> getOneInbox(String code) throws MessagingException {

        ListMessagesQueryParams queryParams = new ListMessagesQueryParams.Builder().limit(10).build();
        User user = sessionService.getUserBySession().get();

        try {
            ListResponse<Message> message = nylasClient.messages().list(code, queryParams);
            return message;

        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

    @Override
    public Response<Message> getOneInboxOneItem(String code, String messageId) throws MessagingException {

        // User user = sessionService.getUserBySession().get();

        try {
            Response<Message> message = nylasClient.messages().find(code, messageId);
            return message;

        } catch (Exception e) {
            System.err.println("Error during code exchange: " + e.getMessage());
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
        System.out.println(email.getReceipient() + email.getName());
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

    @Override
    public ResponseDto scheduleEmail(String code, EmailTimeObjectDTO email) throws MessagingException {

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

    @Override
    public List<InboxEmail> getMyCurrentEmails() throws MessagingException {

        try {

            User user = sessionService.getUserBySession().get();
            return inboxEmailRepository.findByUser(user);

        } catch (Exception e) {

            System.err.println("Error during code exchange: " + e.getMessage());
            e.printStackTrace();
            throw new MessagingException("Failed to exchange code for token: " + e.getMessage());
        }

        // Return success response
    }

}
