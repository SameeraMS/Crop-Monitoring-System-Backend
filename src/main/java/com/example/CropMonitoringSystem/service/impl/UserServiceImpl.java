package com.example.CropMonitoringSystem.service.impl;

import com.example.CropMonitoringSystem.dao.UserDao;
import com.example.CropMonitoringSystem.dto.impl.UserDto;
import com.example.CropMonitoringSystem.entity.impl.UserEntity;
import com.example.CropMonitoringSystem.exception.DataPersistException;
import com.example.CropMonitoringSystem.exception.NotFoundException;
import com.example.CropMonitoringSystem.service.UserService;
import com.example.CropMonitoringSystem.util.Mapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveUser(UserDto userDto) {
        UserEntity save = userDao.save(mapping.toUserEntity(userDto));
        if (save == null) {
            log.error("User not saved");
            throw new DataPersistException("User not saved");
        }
    }

    @Override
    public void updateUser(String userId, UserDto userDto) {
        Optional<UserEntity> searched = userDao.findById(userId);

        if (searched.isPresent()) {
            UserEntity userEntity = searched.get();
            userEntity.setPassword(userDto.getPassword());
            if (userDto.getRole() == null) {
                userEntity.setRole(userEntity.getRole());
            } else {
                userEntity.setRole(userDto.getRole());
            }
            userDao.save(userEntity);
        } else {
            log.error("User +" + userId + " not found");
            throw new DataPersistException("User +" + userId + " not found");
        }
    }

    @Override
    public void deleteUser(String userId) {
        if (userDao.existsById(userId)) {
            userDao.deleteById(userId);
        } else {
            log.error("User +" + userId + " not found");
            throw new DataPersistException("User +" + userId + " not found");
        }
    }

    @Override
    public UserDto getSelectedUser(String userId) {
        Optional<UserEntity> searched = userDao.findById(userId);
        if (searched.isPresent()) {
            return mapping.toUserDto(searched.get());
        } else {
            log.error("User +" + userId + " not found");
            throw new DataPersistException("User +" + userId + " not found");
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        return mapping.toUserDtoList(userDao.findAll());
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userName ->
                (UserDetails) userDao.findById(userName)
                        .orElseThrow(()-> new NotFoundException("User Not Found"));
    }

}
