package com.dssd.grupo15.backend.utils;

import com.dssd.grupo15.backend.model.Status;
import com.dssd.grupo15.backend.model.enums.StatusEnum;

public class StatusUtils {
    public static String getNextStatus(Status status, boolean aprobado) {
        if (StatusEnum.NEW.name().equalsIgnoreCase(status.getStatus())) {
            return aprobado ? StatusEnum.MESA_ENTRADAS_APROBADO.name() : StatusEnum.MESA_ENTRADAS_RECHAZADO.name();
        }

        //TODO: ver que pasa cuando se corrige el form
        if (StatusEnum.MESA_ENTRADAS_APROBADO.name().equalsIgnoreCase(status.getStatus())
                || StatusEnum.MESA_ENTRADAS_RECHAZADO.name().equalsIgnoreCase(status.getStatus())
                || StatusEnum.LEGALES_RECHAZADO.name().equalsIgnoreCase(status.getStatus())) {
            return aprobado ? StatusEnum.LEGALES_APROBADO.name() : StatusEnum.LEGALES_RECHAZADO.name();
        }

        return null;
    }

    public static String getNextStatus(Status status) {
        if (StatusEnum.LEGALES_APROBADO.name().equalsIgnoreCase(status.getStatus())) {
            return StatusEnum.FINALIZADO.name();
        }

        return null;
    }
}
