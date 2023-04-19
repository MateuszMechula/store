package pl.store.domain;

import lombok.*;

@With
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producer {
    private Long id;
    private String producerName;
    private String address;
}
