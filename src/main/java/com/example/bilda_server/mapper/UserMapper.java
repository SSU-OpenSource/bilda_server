package com.example.bilda_server.mapper;

import com.example.bilda_server.domain.entity.Team;
import com.example.bilda_server.domain.entity.User;
import com.example.bilda_server.response.PendingUserDTO;
import com.example.bilda_server.response.TeamsOfSubjectDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public PendingUserDTO ToPendingUserDto(User user) {

        return new PendingUserDTO(
                user.getUserId(),
                user.getName(),
                user.getDepartment()
        );
    }
}
