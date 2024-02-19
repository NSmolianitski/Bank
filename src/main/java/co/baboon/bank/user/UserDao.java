package co.baboon.bank.user;

import co.baboon.bank.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TableField;

import java.util.List;
import java.util.Optional;

import static co.baboon.bank.jooq.Tables.USERS;

public class UserDao {
    private final DSLContext context;
    
    private final List<TableField<UsersRecord, ?>> USER_FIELDS = List.of(
            USERS.ID,
            USERS.NAME,
            USERS.LOGIN,
            USERS.PASSWORD
    );

    public UserDao(DSLContext context) {
        this.context = context;
    }
    
    public Boolean userWithIdExists(String login) {
        var optionalUser = tryGetUserByLogin(login);
        return optionalUser.isPresent();
    }
    
    public Optional<User> tryGetUserById(Integer id) {
        return context
                .select(USER_FIELDS)
                .from(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptional(UserDao::buildUser);
    }
    
    public Optional<User> tryGetUserByLogin(String login) {
        return context
                .select(USER_FIELDS)
                .from(USERS)
                .where(USERS.LOGIN.eq(login))
                .fetchOptional(UserDao::buildUser);
    }
    
    public Optional<User> addUser(User user) {
        return context.insertInto(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.LOGIN, user.getLogin())
                .set(USERS.PASSWORD, user.getPassword())
                .returning()
                .fetchOptional(UserDao::buildUser);
    }
    
    private static User buildUser(Record record) {
        return User.builder()
                .withId(record.get(USERS.ID))
                .withName(record.get(USERS.NAME))
                .withLogin(record.get(USERS.LOGIN))
                .withPassword(record.get(USERS.PASSWORD))
                .build();
    }
}
