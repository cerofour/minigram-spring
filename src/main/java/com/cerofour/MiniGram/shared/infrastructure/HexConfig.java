package com.cerofour.MiniGram.shared.infrastructure;

import com.cerofour.MiniGram.shared.domain.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.cerofour.MiniGram",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = UseCase.class
        )
)
public class HexConfig {
}
