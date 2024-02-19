package co.baboon.bank.authorization.handlers;

import co.baboon.bank.authorization.dto.RegistrationRequestDto;
import co.baboon.bank.user.User;
import co.baboon.bank.user.UserDao;
import co.baboon.bank.utilities.JwtUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class RegisterHandler {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;

    public RegisterHandler(UserDao userDao, PasswordEncoder passwordEncoder, JwtUtility jwtUtility) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtility = jwtUtility;
    }

    public ResponseEntity<?> handle(RegistrationRequestDto requestDto) {
        var optionalValidateError = validateRegistrationDto(requestDto);
        if (optionalValidateError.isPresent())
            return optionalValidateError.get();
        
        var hashedPassword = passwordEncoder.encode(requestDto.password());
        var user = User.builder()
                        .withName(requestDto.name())
                        .withLogin(requestDto.login())
                        .withPassword(hashedPassword)
                        .build();
        
        var optionalUserFromDb = userDao.addUser(user);
        if (optionalUserFromDb.isEmpty())
            return ResponseEntity.internalServerError().build();
        var userFromDb = optionalUserFromDb.get();
        
        var jwt = jwtUtility.createJwtWithUserId(userFromDb.getId());
        
        return ResponseEntity.ok(jwt);
    }
    
    private Optional<ResponseEntity<?>> validateRegistrationDto(RegistrationRequestDto dto) {
        if (dto.name().isBlank())
            return Optional.of(ResponseEntity.ok("Name is empty."));
        
        if (dto.login().isBlank())
            return Optional.of(ResponseEntity.ok("Login is empty."));
        
        if (dto.password().length() < 4)
            return Optional.of(ResponseEntity.ok("Password is too short."));
        
        if (userDao.userWithIdExists(dto.login())) {
            return Optional.of(
                    ResponseEntity.ok("User with login: " + dto.login() + " already registered.")
            );
        }
        
        return Optional.empty();
    }
}
