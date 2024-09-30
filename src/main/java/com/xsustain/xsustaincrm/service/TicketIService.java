package com.xsustain.xsustaincrm.service;

import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.Ticket;

import jakarta.mail.MessagingException;

import java.util.List;

public interface TicketIService {
    public TicketDto createTicket(TicketDto ticketDto) throws MessagingException;

    public TicketDto updateTicket(TicketDto ticketDto, long idTicket) throws MessagingException;

    public List<Ticket> getAllTickets();

    public TicketDto getOneTicket(long idTicket);

    public TicketDto deleteTicket(long idTicket);

}
