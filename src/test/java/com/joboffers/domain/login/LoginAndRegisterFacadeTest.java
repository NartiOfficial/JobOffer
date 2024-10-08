package com.joboffers.domain.login;

import com.joboffers.domain.login.dto.RegisterUserDto;
import com.joboffers.domain.login.dto.RegistrationResultDto;
import com.joboffers.domain.login.dto.UserDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginFacade = new LoginAndRegisterFacade(
            new InMemoryLoginRepository()
    );

    @Test
    @DisplayName("Should register user")
    public void should_register_user() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "pass");

        // when
        RegistrationResultDto register = loginFacade.register(registerUserDto);

        // then
        assertAll(
                () -> assertThat(register.isCreated()).isTrue(),
                () -> assertThat(register.username()).isEqualTo("username")
        );
    }

    @Test
    @DisplayName("Should find user by username")
    public void should_find_user_by_username() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "pass");
        RegistrationResultDto register = loginFacade.register(registerUserDto);

        // when
        UserDto userByName = loginFacade.findByUsername(register.username());

        // then
        assertThat(userByName).isEqualTo(new UserDto(register.id(), "pass", "username"));
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    public void should_throw_exception_when_user_not_found() {
        // given
        String username = "someUser";

        // when
        Throwable thrown = catchThrowable(() -> loginFacade.findByUsername(username));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }
}