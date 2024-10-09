package com.xsustain.xsustaincrm.service;

import com.nylas.models.ListResponse;
import com.nylas.models.Message;
import com.nylas.models.Response;
import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.model.InboxEmail;

import jakarta.mail.MessagingException;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface NylasService {

    ResponseDto saveGrandId(String code) throws MessagingException;

    ResponseEntity<String> generateGrantId(String code) throws MessagingException;

    ListResponse<Message> getEmail() throws MessagingException;

    ListResponse<Message> getOneInbox(String code) throws MessagingException;

    Response<Message> getOneInboxOneItem(String code, String messageId) throws MessagingException;

    ListResponse<Message> searchInobx(String code, String search) throws MessagingException;

    ResponseDto sendEmail(String code, EmailObjectDTO email) throws MessagingException;

    ResponseDto scheduleEmail(String code, EmailTimeObjectDTO email) throws MessagingException;

    List<InboxEmail> getMyCurrentEmails() throws MessagingException;

}
