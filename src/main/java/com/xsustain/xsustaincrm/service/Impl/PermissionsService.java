package com.xsustain.xsustaincrm.service.Impl;

import com.xsustain.xsustaincrm.dao.mapper.PermissionsMapper;
import com.xsustain.xsustaincrm.dto.PermissionsDTO;
import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.repository.PermissionsRepository;
import com.xsustain.xsustaincrm.service.PermissionsIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionsService implements PermissionsIService {
    @Autowired
    private PermissionsRepository permissionsRepository;
    @Autowired
    private PermissionsMapper permissionsMapper;
    @Override
    public PermissionsDTO createPermissions(Permission permission) {
        Permission permission1=permissionsRepository.save(permission);
        return permissionsMapper.mapToPermissionsDTO(permission1);
    }
}
