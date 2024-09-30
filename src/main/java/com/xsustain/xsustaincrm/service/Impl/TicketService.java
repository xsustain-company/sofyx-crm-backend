package com.xsustain.xsustaincrm.service.Impl;

import com.xsustain.xsustaincrm.dao.mapper.TicketMapper;
import com.xsustain.xsustaincrm.dao.mapper.UserMapper;
import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.model.Ticket;
import com.xsustain.xsustaincrm.repository.PermissionsRepository;
import com.xsustain.xsustaincrm.repository.TicketRepository;
import com.xsustain.xsustaincrm.repository.UserRepository;
import com.xsustain.xsustaincrm.service.TicketIService;
import jakarta.mail.MessagingException;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements TicketIService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    @Override
    public TicketDto createTicket(TicketDto ticketDto) throws MessagingException {
        Ticket ticket = new Ticket();
        // ticket.setIdTicket(ticketDto.getIdTicket());
        ticket.setTicketName(ticketDto.getTicketName());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setCloseDate(ticketDto.getCloseDate());
        ticket.setCreatedDate(ticketDto.getCreatedDate());
        ticket.setPipeline(ticketDto.getPipeline());
        ticket.setStatus(ticketDto.getStatus());
        ticket.setPriority(ticketDto.getPriority());
        ticket.setSource(ticketDto.getSource());
        ticket.setOwner(ticketDto.getOwner());
        ticket.setAssigned(ticketDto.getAssigned());
        ticketRepository.save(ticket);

        return ticketDto;
    }

    @Override
    public TicketDto updateTicket(TicketDto ticketDto, long idTicket) throws MessagingException {
        Ticket ticket = ticketRepository.findById(idTicket).get();
        ticket.setTicketName(ticketDto.getTicketName());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setCloseDate(ticketDto.getCloseDate());
        ticket.setCreatedDate(ticketDto.getCreatedDate());
        ticket.setPipeline(ticketDto.getPipeline());
        ticket.setStatus(ticketDto.getStatus());
        ticket.setPriority(ticketDto.getPriority());
        ticket.setSource(ticketDto.getSource());
        ticket.setOwner(ticketDto.getOwner());
        ticket.setAssigned(ticketDto.getAssigned());
        ticketRepository.save(ticket);

        return ticketDto;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAllTicketsAsList();
        tickets.stream().forEach(
                ticket -> {
                    ticket.setOwner(userMapper.mapToUser(ticket.getOwner()));
                    ticket.setAssigned(userMapper.mapToUserList(ticket.getAssigned()));
                });

        return tickets;
    }

    @Override
    public TicketDto getOneTicket(long idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).get();
        ticket.setOwner(userMapper.mapToUser(ticket.getOwner()));
        ticket.setAssigned(userMapper.mapToUserList(ticket.getAssigned()));
        return ticketMapper.mapToTicketDto(ticket);
    }

    @Override
    public TicketDto deleteTicket(long idTicket) {
        // Fetch ticket from repository
        Ticket ticket = ticketRepository.findById(idTicket).orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Initialize the assigned collection to avoid PersistentBag issues
        ticket.setAssigned(new ArrayList<>(ticket.getAssigned()));

        // Delete the ticket from the repository
        ticketRepository.delete(ticket);

        // Map to DTO after ensuring assigned list is initialized properly
        return ticketMapper.mapToTicketDto(ticket);
    }
}
