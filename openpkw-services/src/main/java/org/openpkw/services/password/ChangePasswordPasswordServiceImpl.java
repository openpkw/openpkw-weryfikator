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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
        Optional<User> userOptional = userService.get(changePasswordRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest()
                    .withUser(user)
                    .withToken(createToken());

            passwordChangeRequestRepository.save(passwordChangeRequest);

            mailService.sendMail(user.getEmail(), MailTemplate.PASSWORD_CHANGE, createModel(passwordChangeRequest));
        } else {

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
