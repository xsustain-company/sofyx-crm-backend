package com.xsustain.xsustaincrm.controller;

import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.exception.UserServiceCustomException;
import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.Ticket;
import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.service.TicketIService;
import com.xsustain.xsustaincrm.service.UserIService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket/")
public class TicketController {

    @Autowired
    TicketIService ticketIService;

    @PostMapping("/create")
    public TicketDto createTicket(@Valid @RequestBody TicketDto ticketDto) throws MessagingException {
        return ticketIService.createTicket(ticketDto);
    }

    @PutMapping("/update/{idTicket}")
    public TicketDto updateTicket(@Valid @RequestBody TicketDto ticketDto, @PathVariable long idTicket)
            throws MessagingException {
        return ticketIService.updateTicket(ticketDto, idTicket);
    }

    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return ticketIService.getAllTickets();
    }

    @GetMapping("/getByAssignedUserId/{userId}")
    public List<Ticket> getTicketsByAssignedUserId(@PathVariable long userId) {
        return ticketIService.getTicketsByAssignedUserId(userId);
    }

    @GetMapping("/getByOwnerId/{ownerId}")
    public List<Ticket> getTicketsByOwnerId(@PathVariable long ownerId) {
        return ticketIService.getTicketsByOwnerId(ownerId);
    }

    @GetMapping("/get/{idTicket}")
    public TicketDto getOneTicket(@PathVariable long idTicket) {
        return ticketIService.getOneTicket(idTicket);
    }

    @DeleteMapping("/delete/{idTicket}")
    public TicketDto deleteTicket(@PathVariable long idTicket) {
        return ticketIService.deleteTicket(idTicket);
    }
}
