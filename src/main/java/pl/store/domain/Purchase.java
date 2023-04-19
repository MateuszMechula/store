package pl.store.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private Long id;
    private Customer customer;
    private Product product;
    private Integer quantity;
    private OffsetDateTime dateTime;


}
