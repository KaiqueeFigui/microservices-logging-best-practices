package kaiqueefigui.com.github.springmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "value")
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPayer")
    private Participant payer;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idReceiver")
    private Participant receiver;

}
