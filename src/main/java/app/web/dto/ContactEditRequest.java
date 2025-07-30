package app.web.dto;

import app.model.ContactType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactEditRequest {

    @NotBlank
    private String firstName;

    private String lastName;

    private String region;

    private String avatar;

    @NotBlank
    private String phoneNumber;

    private ContactType type;
}
