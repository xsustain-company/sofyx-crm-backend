package com.xsustain.xsustaincrm.repository;

import com.xsustain.xsustaincrm.dto.TicketDto;
import com.xsustain.xsustaincrm.model.Ticket;
import com.xsustain.xsustaincrm.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t")
    List<Ticket> findAllTicketsAsList();

}
