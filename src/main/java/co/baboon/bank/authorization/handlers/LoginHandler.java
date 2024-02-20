package co.baboon.bank.authorization.handlers;

import co.baboon.bank.authorization.dto.LoginRequestDto;
import co.baboon.bank.user.UserDao;
import co.baboon.bank.utilities.JwtUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginHandler {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;

    public LoginHandler(UserDao userDao, PasswordEncoder passwordEncoder, JwtUtility jwtUtility) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtility = jwtUtility;
    }

    public ResponseEntity<?> handle(LoginRequestDto requestDto) {
        var optionalUser = userDao.tryGetUserByLogin(requestDto.login());
        
        if (optionalUser.isEmpty())
            return ResponseEntity.ok("User not found");
        
        var user = optionalUser.get();
        if (!passwordEncoder.matches(requestDto.password(), user.password()))
            return ResponseEntity.ok("Wrong password");
        
        var jwt = jwtUtility.createJwtWithUserId(user.id());        
        return ResponseEntity.ok(jwt);
    }
}
