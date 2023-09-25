package kaiqueefigui.com.github.springmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "registry")
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
    @Column(name = "dueDate")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPayer")
    private Participant payer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idReceiver")
    private Participant receiver;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idStatus")
    private Status status;
}
