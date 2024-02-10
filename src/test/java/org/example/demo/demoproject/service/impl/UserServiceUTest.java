package org.example.demo.demoproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo.demoproject.apiexeption.ApiException;
import org.example.demo.demoproject.entity.EmailDataEntity;
import org.example.demo.demoproject.entity.PhoneDataEntity;
import org.example.demo.demoproject.entity.UserEntity;
import org.example.demo.demoproject.model.request.UserDataForChangeRequest;
import org.example.demo.demoproject.model.request.UserDataRequest;
import org.example.demo.demoproject.model.response.MessageResponse;
import org.example.demo.demoproject.repository.EmailRepository;
import org.example.demo.demoproject.repository.PhoneRepository;
import org.example.demo.demoproject.repository.UserRepository;
import org.example.demo.demoproject.securety.model.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.example.demo.demoproject.model.enums.EnumSubject.EMAIL;
import static org.example.demo.demoproject.model.enums.EnumSubject.PHONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PhoneRepository phoneRepository;
    @Mock
    private EmailRepository emailRepository;
    @Mock
    private ObjectMapper objectMapper;

    UserServiceImpl userService;

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PHONE = "77777777744";

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, emailRepository, phoneRepository, objectMapper);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        UserDetail userDetail = new UserDetail(1L, null, null, null);
        lenient().when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetail);
    }

    @Test
    void deleteUsersData_whenSubjectIsEmail() {
        UserDataRequest dataRequest = new UserDataRequest();
        dataRequest.setData(TEST_EMAIL);
        dataRequest.setSubject(EMAIL);

        MessageResponse response = userService.deleteUsersData(dataRequest);

        verify(emailRepository, times(1)).deleteEmailByEmail(TEST_EMAIL);
        verify(phoneRepository, never()).deletePhoneByPhone(anyString());
        assertEquals("Data deleted", response.getMessage());
    }

    @Test
    void deleteUsersData_whenSubjectIsPhone() {
        UserDataRequest dataRequest = new UserDataRequest();
        dataRequest.setData(TEST_EMAIL);
        dataRequest.setSubject(PHONE);

        MessageResponse response = userService.deleteUsersData(dataRequest);

        verify(phoneRepository, times(1)).deletePhoneByPhone(TEST_EMAIL);
        verify(emailRepository, never()).deleteEmailByEmail(anyString());
        assertEquals("Data deleted", response.getMessage());
    }

    @Test
    void addUsersData_whenSubjectIsEmail() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserDataRequest dataRequest = new UserDataRequest();
        dataRequest.setData(TEST_EMAIL);
        dataRequest.setSubject(EMAIL);

        MessageResponse response = userService.addUsersData(dataRequest);

        verify(emailRepository, times(1)).save(argThat(emailDataEntity ->
                emailDataEntity.getEmail().equals(TEST_EMAIL) && emailDataEntity.getUser().getId().equals(userId)));
        assertEquals("Data added", response.getMessage());
    }

    @Test
    void addUsersData_whenSubjectIsPhone() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserDataRequest dataRequest = new UserDataRequest();
        dataRequest.setData(TEST_PHONE);
        dataRequest.setSubject(PHONE);

        MessageResponse response = userService.addUsersData(dataRequest);

        verify(phoneRepository, times(1)).save(argThat(phoneDataEntity ->
                phoneDataEntity.getPhone().equals(TEST_PHONE) && phoneDataEntity.getUser().getId().equals(userId)));
        assertEquals("Data added", response.getMessage());
    }


    @Test
    void changeUserData_whenSubjectIsEmail() {
        UserDataForChangeRequest request = new UserDataForChangeRequest();
        request.setSubject(EMAIL);
        request.setOldData(TEST_EMAIL);
        request.setNewData("newEmail@example.com");
        EmailDataEntity emailDataEntity = new EmailDataEntity();
        emailDataEntity.setEmail(TEST_EMAIL);

        when(emailRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(emailDataEntity));

        MessageResponse response = userService.changeUserData(request);

        verify(emailRepository, times(1)).save(emailDataEntity);
        assertEquals("newEmail@example.com", emailDataEntity.getEmail());
        assertEquals("Data changed", response.getMessage());
    }

    @Test
    void changeUserData_whenSubjectIsPhone() {
        UserDataForChangeRequest request = new UserDataForChangeRequest();
        request.setSubject(PHONE);
        request.setOldData(TEST_PHONE);
        request.setNewData("73435665434");
        PhoneDataEntity phoneDataEntity = new PhoneDataEntity();
        phoneDataEntity.setPhone(TEST_PHONE);

        when(phoneRepository.findByPhone(TEST_PHONE)).thenReturn(Optional.of(phoneDataEntity));

        MessageResponse response = userService.changeUserData(request);

        verify(phoneRepository, times(1)).save(phoneDataEntity);
        assertEquals("73435665434", phoneDataEntity.getPhone());
        assertEquals("Data changed", response.getMessage());
    }

    @Test
    void changeUserData_whenSubjectIsEmailAndEmailNotFound() {
        UserDataForChangeRequest request = new UserDataForChangeRequest();
        request.setSubject(EMAIL);
        request.setOldData(TEST_EMAIL);
        request.setNewData("newEmail@example.com");

        when(emailRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> userService.changeUserData(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Email not found", exception.getMessage());
    }

    @Test
    void changeUserData_whenSubjectIsPhoneAndPhoneNotFound() {
        UserDataForChangeRequest request = new UserDataForChangeRequest();
        request.setSubject(PHONE);
        request.setOldData(TEST_PHONE);
        request.setNewData("73435665434");
        when(phoneRepository.findByPhone(TEST_PHONE)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> userService.changeUserData(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Phone not found", exception.getMessage());
    }

}
