package com.ManuelFalchettoni.task_manager_api.enums;

public enum TaskStatus {
    /** El recurso ha sido creado pero no se ha iniciado la acción. */
    PENDING,
    /** La acción está ocurriendo en este momento. */
    IN_PROGRESS,
    /** La acción terminó con éxito. */
    COMPLETED,
    /** La acción fue interrumpida y no se terminará. */
    CANCELLED
}
