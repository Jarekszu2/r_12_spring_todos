package jarek.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String content;

    @CreationTimestamp
    private LocalDate dateAdded;

    private LocalDate dateFinished;
    private TaskStatus taskStatus;

    @ManyToOne
    private Account account;
}
