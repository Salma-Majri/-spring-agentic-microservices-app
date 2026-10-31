package ma.majrisalma.customerservice.model;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {
    private Long id;
    private String name;
    private String email;
}