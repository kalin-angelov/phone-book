package app.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {

    @NotNull
    private String firstName;

    private String lastName;

    private String region;

    private String avatar;

    @NotNull
    private String phoneNumber;
}
