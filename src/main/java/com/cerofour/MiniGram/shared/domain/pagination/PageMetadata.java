package com.cerofour.MiniGram.shared.domain.pagination;

public record PageMetadata(
        int size,
        int page,
        int numberOfElements,
        long totalElements,
        int totalPages,
        Sort sort
) {

    public record Sort(String field, Direction direction) {
        public enum Direction { ASC, DESC };
    }

}
