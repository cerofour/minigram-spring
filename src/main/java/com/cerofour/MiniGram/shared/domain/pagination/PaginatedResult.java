package com.cerofour.MiniGram.shared.domain.pagination;

import java.util.List;

public record PaginatedResult<T> (
    List<T> data,
    PageMetadata pagination
) { }
