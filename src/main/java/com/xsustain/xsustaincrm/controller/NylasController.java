package com.xsustain.xsustaincrm.controller;

import com.nylas.models.ListResponse;
import com.nylas.models.Message;
import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.exception.UserServiceCustomException;
import com.xsustain.xsustaincrm.model.InboxEmail;
import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.service.NylasService;
import com.xsustain.xsustaincrm.service.UserIService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mailing/")
public class NylasController {

    @Autowired
    private NylasService nylasService;

    @GetMapping("/save-grantId")
    public ResponseDto saveGrantId(@Valid @RequestParam String code) throws MessagingException {

        System.out.println(code);
        return nylasService.saveGrandId(code);
    }    

    @GetMapping("/oauth/exchange")
    public ResponseEntity<String> exhangeToken(@Valid @RequestParam String code) throws MessagingException {

        System.out.println(code);
        return nylasService.generateGrantId(code);
    }

    @GetMapping("/get-my-inbox")
    public List<InboxEmail> getMyEmails() throws MessagingException {
        return nylasService.getMyCurrentEmails();
    }
    
    @GetMapping("/get-email")
    public ListResponse<Message> getEmail() throws MessagingException {
        return nylasService.getEmail();
    }


    @GetMapping("/get-one-inbox")
    public ListResponse<Message> getOneInbx(@Valid @RequestParam String grantId) throws MessagingException {
        return nylasService.getOneInbox(grantId);
    }
    @GetMapping("/search-inbox")
    public ListResponse<Message> searchInbox(@Valid @RequestParam String grantId, @RequestParam String search)
            throws MessagingException {

        System.out.println(grantId);
        return nylasService.searchInobx(grantId, search);
    }

    @PostMapping("/send-email")
    public ResponseDto sendEmail(@Valid @RequestParam String grantId, @RequestBody EmailObjectDTO email)
            throws MessagingException {
        return nylasService.sendEmail(grantId, email);
    }

    @GetMapping("/schedule-email")
    public ResponseDto scheduleEmail(@Valid @RequestParam String grantId, @RequestBody EmailTimeObjectDTO email)
            throws MessagingException {
        return nylasService.scheduleEmail(grantId, email);
    }

}
