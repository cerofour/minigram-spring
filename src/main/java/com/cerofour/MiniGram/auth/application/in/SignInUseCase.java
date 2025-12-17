package com.cerofour.MiniGram.auth.application.in;

import com.cerofour.MiniGram.auth.domain.AuthenticationResult;
import com.cerofour.MiniGram.auth.domain.SignInCommand;

public interface SignInUseCase {
    AuthenticationResult signIn(SignInCommand command);
}
