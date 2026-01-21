package com.cerofour.MiniGram.shared.domain.pagination;

public record PageRequest(
        int size,
        int page,
        Sort sort
) {

    public record Sort(String field, Direction direction) {
        public enum Direction { ASC, DESC };
    }

}
