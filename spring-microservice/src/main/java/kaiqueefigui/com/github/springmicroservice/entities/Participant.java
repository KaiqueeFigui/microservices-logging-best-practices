package kaiqueefigui.com.github.springmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participant", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "document")
    private String document;
    @Column(name = "agency")
    private String agency;
    @Column(name = "account")
    private String account;

    @Column(name = "paymentId")
    private Long paymentId;
}
