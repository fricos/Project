package com.kerekegyensuly.project.service;

import com.kerekegyensuly.project.config.MessageStrings;
import com.kerekegyensuly.project.dto.ResponseDto;
import com.kerekegyensuly.project.dto.user.SignInDto;
import com.kerekegyensuly.project.dto.user.SignInResponseDto;
import com.kerekegyensuly.project.dto.user.SignupDto;
import com.kerekegyensuly.project.dto.user.UserCreateDto;
import com.kerekegyensuly.project.enums.ResponseStatus;
import com.kerekegyensuly.project.enums.Role;
import com.kerekegyensuly.project.exceptions.AuthenticationFailException;
import com.kerekegyensuly.project.exceptions.CustonException;
import com.kerekegyensuly.project.model.AuthenticationToken;
import com.kerekegyensuly.project.model.User;
import com.kerekegyensuly.project.repository.UserRepository;
import com.kerekegyensuly.project.utils.Helper;
import io.swagger.models.Response;
import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kerekegyensuly.project.config.MessageStrings.USER_CREATED;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseDto signUp(SignupDto signupDto) throws CustonException{
        if (Helper.notNull(userRepository.findByEmail(signupDto.getEmail()))){
            throw new CustonException("A felhasznalo mar letezik.");
        }
        String encryptedPassword = signupDto.getPassword();
        try{
            encryptedPassword = hashPassword(signupDto.getPassword());
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }
        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), Role.user, encryptedPassword);

        User createdUser;
        try{
            createdUser = userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
        }catch (Exception e){
            throw new CustonException(e.getMessage());
        }
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws CustonException{
        User user = userRepository.findByEmail(signInDto.getEmail());
        if (!Helper.notNull(user)){
            throw new AuthenticationFailException("Felhasznalo nincs jelen");
        }
        try{
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustonException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (!Helper.notNull(token)){
            throw new CustonException("token nincs jelen.");
        }
        return new SignInResponseDto("success", token.getToken());
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public ResponseDto createUser(String token, UserCreateDto userCreateDto) throws CustonException, AuthenticationFailException {
        User creatingUser = AuthenticationService.getUser(token);
        if (!canCrudUser(creatingUser.getRole())){
            throw new AuthenticationFailException(MessageStrings.USER_NOT_PERMITTED);
        }
        String encryptedPassword = userCreateDto.getPassword();
        try{
            encryptedPassword = hashPassword(userCreateDto.getPassword());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        User user = new User(userCreateDto.getFirstName(), userCreateDto.getLastName(), userCreateDto.getEmail(), userCreateDto.getRole(), encryptedPassword);
        User createdUser;
        try{
            createdUser = userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new ResponseDto(ResponseStatus.success.toString(), );
        }catch(Exception e){
            throw new CustonException(e.getMessage());
        }
    }

    boolean canCrudUser(Role role){
        if (role == Role.admin || role == Role.manager){
            return true;
        }
        if (role == Role.user && userUpdating.getId() == userIdBeindUpdated){
            return true;
        }
        return false;    }
}
