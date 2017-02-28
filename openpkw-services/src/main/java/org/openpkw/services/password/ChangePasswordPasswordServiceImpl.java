package org.openpkw.services.password;

import org.openpkw.model.entity.PasswordChangeRequest;
import org.openpkw.model.entity.User;
import org.openpkw.repositories.PasswordChangeRequestRepository;
import org.openpkw.services.mail.MailService;
import org.openpkw.services.mail.MailTemplate;
import org.openpkw.services.password.dto.ChangePasswordRequestDTO;
import org.openpkw.services.user.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.*;

@Service
public class ChangePasswordPasswordServiceImpl implements ChangePasswordService {


    @Inject
    private PasswordChangeRequestRepository passwordChangeRequestRepository;

    @Inject
    private MailService mailService;

    @Inject
    private UserService userService;

    @Override
    public void createChangePasswordRequest(ChangePasswordRequestDTO changePasswordRequest) {
        Optional<PasswordChangeRequest> passwordChangeRequestOptional = passwordChangeRequestRepository.findByUser_EmailAndActiveTrue(changePasswordRequest.getEmail());
        if (passwordChangeRequestOptional.isPresent()) {
            PasswordChangeRequest passwordChangeRequest = passwordChangeRequestOptional.get();
            passwordChangeRequest.setActive(false);
            passwordChangeRequestRepository.save(passwordChangeRequest);
        }

        Optional<User> userOptional = userService.get(changePasswordRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest()
                    .withUser(user)
                    .withToken(createToken())
                    .withActive(true);

            passwordChangeRequestRepository.save(passwordChangeRequest);

            mailService.sendMail(user.getEmail(), MailTemplate.PASSWORD_CHANGE, createModel(passwordChangeRequest));
        } else {
            throw new NotFoundException();
        }

    }

    @Override
    public void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        Optional<PasswordChangeRequest> passwordChangeRequestOptional = passwordChangeRequestRepository.findByUser_EmailAndActiveTrue(changePasswordRequestDTO.getEmail());
        if (passwordChangeRequestOptional.isPresent()) {
            PasswordChangeRequest passwordChangeRequest = passwordChangeRequestOptional.get();
            passwordChangeRequest.setActive(false);
            passwordChangeRequest.setChangeDate(new Date());
            passwordChangeRequest.setPasswordChanged(true);
            passwordChangeRequestRepository.save(passwordChangeRequest);
            userService.setNewPassword(changePasswordRequestDTO.getEmail(), changePasswordRequestDTO.getPassword());
        } else {
            throw new NotFoundException();
        }
    }

    private Map<String, Object> createModel(PasswordChangeRequest passwordChangeRequest) {
        Map<String, Object>  model = new HashMap<>();
        model.put("firstName", passwordChangeRequest.getUser().getFirstName());
        model.put("lastName", passwordChangeRequest.getUser().getLastName());
        model.put("token", passwordChangeRequest.getToken());
        return model;
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }
}
