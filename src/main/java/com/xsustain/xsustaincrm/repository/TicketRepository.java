package com.xsustain.xsustaincrm.repository;

import com.xsustain.xsustaincrm.model.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t")
    List<Ticket> findAllTicketsAsList();

    @Query("SELECT t FROM Ticket t JOIN t.assigned a WHERE a.idUser = :userId")
    List<Ticket> findTicketsByAssignedUserId(@Param("userId") long userId);

    @Query("SELECT t FROM Ticket t WHERE t.owner.idUser = :ownerId")
    List<Ticket> findTicketsByOwnerId(@Param("ownerId") long ownerId);

}
