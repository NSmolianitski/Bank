package co.baboon.bank.user.handlers;

import co.baboon.bank.user.UserDao;
import org.springframework.http.ResponseEntity;

public class GetUserHandler {
    private final UserDao userDao;

    public GetUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public ResponseEntity<?> handle(Integer id) {
        var optionalUser = userDao.tryGetUserById(id);
     
        var response = optionalUser.map(ResponseEntity::ok);
        return response.isPresent() ?
                response.get() :
                ResponseEntity.notFound().build();
    }
}
