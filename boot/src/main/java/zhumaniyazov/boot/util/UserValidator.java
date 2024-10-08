package zhumaniyazov.boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import zhumaniyazov.boot.model.User;
import zhumaniyazov.boot.service.PersonDetailsService;
@Component
public class UserValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public UserValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Проверка на пустое имя пользователя
        if (user.getName() == null || user.getName().isEmpty()) {
            errors.rejectValue("name", "", "Имя пользователя не должно быть пустым!");
            return;
        }

        try {
            personDetailsService.loadUserByUsername(user.getName());
            errors.rejectValue("name", "", "Человек с таким именем уже существует!");
        } catch (UsernameNotFoundException e) {
            // Имя пользователя уникально, ошибок нет
        }
    }
}