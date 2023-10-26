package com.example.paymentms.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFormation;
    private String userName;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Categori categori;

    private String lien;
    @CreationTimestamp
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"formation"})
    private List<ImageForm> imageFormations;

}
