package IssueTracker.repositorry;

import IssueTracker.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    //no need to write any code here as jparepository provides all of it. except if you
    //wish to write custom queries

}
