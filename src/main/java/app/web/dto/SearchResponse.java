package app.web.dto;

import app.model.Contact;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResponse {

    private int status;
    private List<Contact> result;
}
