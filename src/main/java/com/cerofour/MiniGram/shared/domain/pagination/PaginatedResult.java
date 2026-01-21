package com.cerofour.MiniGram.shared.domain.pagination;

import lombok.AllArgsConstructor;

import java.util.List;

public record PaginatedResult<T> (
    List<T> data,
    PageRequest pagination
) { }
