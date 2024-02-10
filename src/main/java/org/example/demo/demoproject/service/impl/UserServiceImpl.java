package org.example.demo.demoproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.demoproject.apiexeption.ApiException;
import org.example.demo.demoproject.entity.EmailDataEntity;
import org.example.demo.demoproject.entity.PhoneDataEntity;
import org.example.demo.demoproject.entity.UserEntity;
import org.example.demo.demoproject.model.User;
import org.example.demo.demoproject.model.request.UserAmountRequest;
import org.example.demo.demoproject.model.request.UserDataForChangeRequest;
import org.example.demo.demoproject.model.request.UserDataRequest;
import org.example.demo.demoproject.model.request.UserRequest;
import org.example.demo.demoproject.model.response.MessageResponse;
import org.example.demo.demoproject.model.response.UserResponse;
import org.example.demo.demoproject.repository.EmailRepository;
import org.example.demo.demoproject.repository.PhoneRepository;
import org.example.demo.demoproject.repository.UserRepository;
import org.example.demo.demoproject.securety.model.UserDetail;
import org.example.demo.demoproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final ObjectMapper mapper;

    private static final String USER_NOT_FOUND = "User not found";

    @Override
    public UserResponse getUsers(Map<String, String> params) {

        UserRequest userRequest = mapper.convertValue(params, UserRequest.class);

        if (userRequest.getOffset() == null || userRequest.getSize() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "offset, size must not be null");
        }

        List<UserEntity> userEntities = userRepository.findUsers(userRequest);

        Integer pages = new BigDecimal(userRepository.getPages(userRequest))
                .divide(new BigDecimal(userRequest.getSize()), 0, RoundingMode.UP).intValue();
        List<User> users = userEntities.stream().map(user -> mapper.convertValue(user, User.class)).collect(Collectors.toList());

        return new UserResponse(users, pages);
    }

    @Override
    @Transactional
    public MessageResponse deleteUsersData(UserDataRequest dataRequest) {
        switch (dataRequest.getSubject()) {
            case EMAIL:
                emailRepository.deleteEmailByEmail(dataRequest.getData());
                break;
            case PHONE:
                phoneRepository.deletePhoneByPhone(dataRequest.getData());
                break;
        }

        return new MessageResponse("Data deleted");
    }

    @Override
    @Transactional
    public MessageResponse addUsersData(UserDataRequest dataRequest) {
        UserEntity user = userRepository.findById(getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        switch (dataRequest.getSubject()) {
            case EMAIL:
                EmailDataEntity emailDataEntity = new EmailDataEntity()
                        .setEmail(dataRequest.getData())
                        .setUser(user);
                emailRepository.save(emailDataEntity);
                break;
            case PHONE:
                PhoneDataEntity phoneDataEntity = new PhoneDataEntity()
                        .setPhone(dataRequest.getData())
                        .setUser(user);
                phoneRepository.save(phoneDataEntity);
                break;
        }

        return new MessageResponse("Data added");
    }

    @Override
    @Transactional
    public MessageResponse changeUserData(UserDataForChangeRequest dataRequest) {
        switch (dataRequest.getSubject()) {
            case EMAIL:
                EmailDataEntity emailDataEntity = emailRepository.findByEmail(dataRequest.getOldData())
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Email not found"));
                emailDataEntity.setEmail(dataRequest.getNewData());
                emailRepository.save(emailDataEntity);
                break;
            case PHONE:
                PhoneDataEntity phoneDataEntity = phoneRepository.findByPhone(dataRequest.getOldData())
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Phone not found"));
                phoneDataEntity.setPhone(dataRequest.getNewData());
                phoneRepository.save(phoneDataEntity);
                break;
        }

        return new MessageResponse("Data changed");
    }

    @Override
    @Retryable(maxAttempts = 15, exclude = {ApiException.class})
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public MessageResponse transferAmount(UserAmountRequest amountRequest) {
        UserEntity userFrom = userRepository.findById(getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
        UserEntity userTo = userRepository.findById(amountRequest.getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));

        if (userFrom.getAccount().getBalance().compareTo(amountRequest.getAmount()) < 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Not enough money");
        }

        userFrom.getAccount().setBalance(userFrom.getAccount().getBalance().subtract(amountRequest.getAmount()));
        userTo.getAccount().setBalance(userTo.getAccount().getBalance().add(amountRequest.getAmount()));

        userRepository.save(userFrom);
        userRepository.save(userTo);

        log.info("Amount {} transferred from user {} to user {}",
                amountRequest.getAmount(), userFrom.getId(), userTo.getId());

        return new MessageResponse("Amount transferred");
    }

    /**
     * Retrieves the user ID from the current authentication context.
     *
     * @return the user ID
     */
    private Long getUserId() {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return userDetail.getId();
    }

}
