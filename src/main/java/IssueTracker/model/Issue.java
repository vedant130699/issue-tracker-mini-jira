package IssueTracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.lang.reflect.Type;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    @NotBlank(message = "Reporter is required")
    private String reporter;

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

    public Issue() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", reporter='" + reporter + '\'' +
                ", status=" + status +
                '}';
    }
}
