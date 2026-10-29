package tn.esprit.autoloc.web.dto;

public record EmployeResponseDTO(
        Long idEmploye,
        String nom,
        String prenom,
        tn.esprit.autoloc.domain.RoleEmploye role,
        Long idAgence
) {
}
