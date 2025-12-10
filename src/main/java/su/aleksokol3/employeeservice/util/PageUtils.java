package su.aleksokol3.employeeservice.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageUtils {

    private static final int maxPageSize = 1000;

    public static Pageable preparePageable(Pageable pageable) {
        if (pageable.getPageSize() > maxPageSize) {
            return sortIgnoreCase(PageRequest.of(pageable.getPageNumber(), maxPageSize, pageable.getSort()));
        }
        return sortIgnoreCase(pageable);
    }
    public static Pageable sortIgnoreCase(Pageable pageable) {
        List<Sort.Order> ordersIgnoreCase = pageable.getSort().stream()
                .map(Sort.Order::ignoreCase)
                .toList();
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(ordersIgnoreCase));
    }
}
