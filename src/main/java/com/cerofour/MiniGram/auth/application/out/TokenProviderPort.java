package com.cerofour.MiniGram.auth.application.out;

import com.cerofour.MiniGram.auth.domain.AuthenticationResult;
import com.cerofour.MiniGram.user.domain.User;

public interface TokenProviderPort {
    AuthenticationResult getToken(User user);
}
