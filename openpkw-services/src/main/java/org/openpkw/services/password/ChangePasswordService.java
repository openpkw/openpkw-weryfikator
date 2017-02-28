package org.openpkw.services.password;


import org.openpkw.services.password.dto.ChangePasswordRequestDTO;

public interface ChangePasswordService {

    void createChangePasswordRequest(ChangePasswordRequestDTO changePasswordRequest);

    void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);

}
