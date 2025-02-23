package com.trustrace.tiles_hub_be.service;

import com.trustrace.tiles_hub_be.dao.RoleDao;
import com.trustrace.tiles_hub_be.model.Role;
import com.trustrace.tiles_hub_be.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Optional<Role> findByName(UserRole userRole) {
        Optional<Role> role= roleDao.findByName(userRole);
        return role;
    }
}
