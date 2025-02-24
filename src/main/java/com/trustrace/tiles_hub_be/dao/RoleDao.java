package com.trustrace.tiles_hub_be.dao;

import com.trustrace.tiles_hub_be.model.user.Role;
import com.trustrace.tiles_hub_be.template.RoleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleDao {

    @Autowired
    private RoleTemplate roleTemplate;

    public Optional<Role> findByName(String userRole) {
        Optional<Role> role= roleTemplate.findByName(userRole);
        return role;
    }
}
