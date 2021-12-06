package anhtuan.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class User implements Validator {

    private Long id;

    private String account;

    private String password;

    public User() {
    }

    public User(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String account = user.getAccount();
        String password = user.getPassword();
        ValidationUtils.rejectIfEmpty(errors, "account", "account.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
        if (account.length()>20 || account.length() <5) {
            errors.rejectValue("account","account.length");
        }
        if (!account.matches("(^[a-zA-Z_0-9]*$)")) {
            errors.rejectValue("account", "account.matches");
        }

        if (password.length()>20 || account.length() <5) {
            errors.rejectValue("password","password.length");
        }
        if (!password.matches("(^[a-zA-Z_0-9]*$)")) {
            errors.rejectValue("password", "password.matches");
        }
    }
}
