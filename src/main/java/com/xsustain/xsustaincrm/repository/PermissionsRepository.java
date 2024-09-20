package com.xsustain.xsustaincrm.repository;

import com.xsustain.xsustaincrm.model.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends CrudRepository<Permission,Long> {
}
