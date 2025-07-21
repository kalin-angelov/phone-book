package app.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactEditRequest {

    private String firstName;
    private String lastName;
    private String region;
    private String avatar;
    private String phoneNumber;
}
