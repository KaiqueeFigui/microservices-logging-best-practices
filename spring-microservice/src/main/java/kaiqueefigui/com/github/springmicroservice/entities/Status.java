package kaiqueefigui.com.github.springmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "status")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
}
