package IssueTracker.service;

import IssueTracker.model.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    List<Issue> findAll();
    Issue findById(Long id);
    Issue save(Issue issue);
    void deleteById(Long id);
}
