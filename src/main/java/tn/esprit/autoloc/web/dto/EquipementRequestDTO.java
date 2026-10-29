package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EquipementRequestDTO(
        @NotBlank(message = "Le libellé est obligatoire")
        @Size(max = 100)
        String libelle,

        Long idVehicule
) {
}
