package com.cerofour.MiniGram.auth.application.in;

import com.cerofour.MiniGram.auth.domain.SignUpCommand;

public interface SignUpUseCase {
    void signUp(SignUpCommand command);
}
