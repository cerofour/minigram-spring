package com.cerofour.MiniGram.shared.infrastructure;

import java.time.LocalDateTime;

public record ApiError(String message, int status, LocalDateTime timestamp) {}
