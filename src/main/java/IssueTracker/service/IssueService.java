package IssueTracker.service;

import IssueTracker.model.Issue;
import IssueTracker.model.IssueStatus;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    List<Issue> findAll();
    Issue findById(Long id);
    Issue save(Issue issue);
    void deleteById(Long id);

    List<Issue> findByStatus(IssueStatus status);
}
