package org.example.demo.demoproject.service;

import org.example.demo.demoproject.apiexeption.ApiException;
import org.example.demo.demoproject.model.request.UserAmountRequest;
import org.example.demo.demoproject.model.request.UserDataForChangeRequest;
import org.example.demo.demoproject.model.request.UserDataRequest;
import org.example.demo.demoproject.model.response.MessageResponse;
import org.example.demo.demoproject.model.response.UserResponse;

import java.util.Map;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
public interface UserService {

    /**
     * Retrieves users based on the provided parameters.
     *
     * @param params the parameters for querying users
     * @return the response containing the list of users and the number of pages
     * @throws ApiException if offset or size is null
     */
    UserResponse getUsers(Map<String, String> params);

    /**
     * Deletes user data based on the subject specified in the UserDataRequest
     *
     * @param dataRequest the UserDataRequest object containing the subject and data to be deleted
     * @return a MessageResponse indicating the data has been deleted
     */
    MessageResponse deleteUsersData(UserDataRequest dataRequest);

    /**
     * This method adds user data based on the data request provided.
     * If the subject is EMAIL, it adds the email data to the user.
     * If the subject is PHONE, it adds the phone data to the user.
     *
     * @param dataRequest the request containing the subject and data to be added
     * @return a message response indicating that the data has been added
     */
    MessageResponse addUsersData(UserDataRequest dataRequest);

    /**
     * Change user data based on the subject of the change request.
     *
     * @param dataRequest the data change request
     * @return a message response indicating the data change
     */
    MessageResponse changeUserData(UserDataForChangeRequest dataRequest);

    /**
     * Transfers the specified amount from one user's account to another user's account.
     *
     * @param amountRequest the request object containing the user IDs and amount to transfer
     * @return a message response indicating the status of the transfer
     * @throws ApiException if the user is not found or if there is not enough balance in the source account
     */
    MessageResponse transferAmount(UserAmountRequest amountRequest);

}
