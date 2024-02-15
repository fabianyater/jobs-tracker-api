package com.api.jobstracker.commons.utilities;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.domain.Pageable;

public class Utilities {
    /**
     * @param pageable paginación
     * @return página actual
     */
    public static int getCurrentPage(Pageable pageable) {
        int page = pageable.getPageNumber();
        if (pageable.getPageNumber() != 0) {
            page -= 1;
        }
        return page;
    }

    /**
     * @param prefix prefijo del identificador aleatorio
     * @return identificador generado
     */
    public static String generateRandomId(String prefix) {
        return prefix + RandomStringUtils.randomAlphanumeric(17);
    }

    private Utilities() {
    }
}
