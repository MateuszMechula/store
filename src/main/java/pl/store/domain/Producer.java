package pl.store.domain;

import lombok.*;

@With
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producer {
    private Long id;
    private String producer_name;
    private String address;
}
