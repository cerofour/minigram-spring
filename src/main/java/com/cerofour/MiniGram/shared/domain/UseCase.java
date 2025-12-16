package com.cerofour.MiniGram.shared.domain;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface UseCase {
    // Es solo una etiqueta vacía, no tiene código de Spring dentro
}
