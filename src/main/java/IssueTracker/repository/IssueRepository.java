package IssueTracker.repository;

import IssueTracker.model.Issue;
import IssueTracker.model.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByStatus(IssueStatus status);
    //no need to write any code here as jparepository provides all of it. except if you
    //wish to write custom queries

}
