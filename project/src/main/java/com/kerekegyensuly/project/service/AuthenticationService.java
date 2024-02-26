package com.kerekegyensuly.project.service;

import com.kerekegyensuly.project.config.MessageStrings;
import com.kerekegyensuly.project.exceptions.AuthenticationFailException;
import com.kerekegyensuly.project.model.AuthenticationToken;
import com.kerekegyensuly.project.model.User;
import com.kerekegyensuly.project.repository.TokenRepository;
import com.kerekegyensuly.project.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    TokenRepository repository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    public User getUser(String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if (!Helper.notNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }

}
