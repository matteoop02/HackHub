package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import unicam.ids.HackHub.enums.ReportType;

public record ReportRequest (

        @NotEmpty(message = "Il nome del team deve essere specificato!")
        String teamName,

        @NotEmpty(message = "L'username dell'organizzatore deve essere specificato!")
        String organizerUsername,

        @NotNull(message = "Il tipo di report non può essere null")
        ReportType reportType,

        @NotEmpty(message = "La motivazione deve essere specificata!")
        String description
) {}
