package com.xsustain.xsustaincrm.service;


import com.nylas.models.ListResponse;
import com.nylas.models.Message;
import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.model.Permission;
import jakarta.mail.MessagingException;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface NylasService {
    
    ResponseEntity<String> saveGrandId(String code) throws MessagingException;
    ListResponse<Message> getEmail(String code) throws MessagingException;
    ListResponse<Message> searchInobx(String code,String search) throws MessagingException;
    ResponseDto sendEmail(String code,EmailObjectDTO email) throws MessagingException;
    ResponseDto scheduleEmail(String code,EmailObjectDTO email) throws MessagingException;
    
    //ResponseEntity<String> trackEmail(String code) throws MessagingException;
    //ResponseEntity<String> getOneEmail(String code) throws MessagingException;
}
