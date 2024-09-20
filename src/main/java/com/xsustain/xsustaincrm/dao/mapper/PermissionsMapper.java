package com.xsustain.xsustaincrm.dao.mapper;

import com.xsustain.xsustaincrm.dto.PermissionsDTO;
import com.xsustain.xsustaincrm.model.Permission;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PermissionsMapper {
    @Autowired
    private ModelMapper modelMapper;

    public PermissionsDTO mapToPermissionsDTO(Permission permission){
        return modelMapper.map(permission, PermissionsDTO.class);
    }
    public Permission mapToPermissions(PermissionsDTO permissionsDTO){
        return modelMapper.map(permissionsDTO, Permission.class);
    }
}
