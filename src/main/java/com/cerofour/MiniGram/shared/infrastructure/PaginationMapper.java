package com.cerofour.MiniGram.shared.infrastructure;

import com.cerofour.MiniGram.shared.domain.pagination.PageMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationMapper {

    public static PageMetadata from(Pageable p) {
        return new PageMetadata(
                p.getPageSize(),
                p.getPageNumber(),
                0,
                0,
                0,
                p.getSort().isSorted()
                        ? new PageMetadata.Sort(
                        p.getSort().iterator().next().getProperty(),
                        p.getSort().iterator().next().isAscending()
                                ? PageMetadata.Sort.Direction.ASC
                                : PageMetadata.Sort.Direction.DESC )
                        : null
        );

    }

    public static Pageable toPageable(PageMetadata pageMetadata) {

        if (pageMetadata == null) {
            return Pageable.unpaged();
        }

        if (pageMetadata.sort() == null) {
            return org.springframework.data.domain.PageRequest.of(
                    pageMetadata.page(),
                    pageMetadata.size()
            );
        }

        Sort.Direction direction =
                pageMetadata.sort().direction() == PageMetadata.Sort.Direction.ASC
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        return org.springframework.data.domain.PageRequest.of(
                pageMetadata.page(),
                pageMetadata.size(),
                Sort.by(direction, pageMetadata.sort().field())
        );
    }

    public static PageMetadata from(Page page) {

        if (page == null) {
            return null;
        }

        PageMetadata.Sort sort = null;

        if (page.getSort().isSorted()) {
            Sort.Order order = page.getSort().iterator().next();

            sort = new PageMetadata.Sort(
                    order.getProperty(),
                    order.isAscending()
                            ? PageMetadata.Sort.Direction.ASC
                            : PageMetadata.Sort.Direction.DESC
            );
        }

        return new PageMetadata(
                page.getSize(),     // page size
                page.getNumber(),   // current page
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages(),
                sort
        );
    }


}
