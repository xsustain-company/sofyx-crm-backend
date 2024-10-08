package com.xsustain.xsustaincrm.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import com.xsustain.xsustaincrm.model.InboxEmail;
import com.xsustain.xsustaincrm.model.User;

public interface InboxRepository extends CrudRepository<InboxEmail, Long> {
    List<InboxEmail> findByUser(User user);
}
