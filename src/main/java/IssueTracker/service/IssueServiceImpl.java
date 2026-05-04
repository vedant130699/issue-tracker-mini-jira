package IssueTracker.service;

import IssueTracker.exception.ResourceNotFoundException;
import IssueTracker.model.Issue;
import IssueTracker.model.IssueStatus;
import IssueTracker.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{


    private final IssueRepository issueRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository theIssueRepository){
        issueRepository = theIssueRepository;
    }


    @Override
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    @Override
    public Issue findById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Issue not found " + id));
    }

    @Override
    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        Issue issue  = issueRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Issue not found " + id));
        issueRepository.delete(issue);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Issue updateById(Long id, Issue updatedIssue) {
        return issueRepository.findById(id).map(
                issue -> {
                    issue.setTitle(updatedIssue.getTitle());
                    issue.setDescription(updatedIssue.getDescription());
                    issue.setReporter(updatedIssue.getReporter());
                    issue.setStatus(updatedIssue.getStatus());
                    return issueRepository.save(issue);
                }
        )
                .orElseThrow(()-> new ResourceNotFoundException("Id not found" + id));
    }

    @Override
    public List<Issue> findByStatus(IssueStatus status){
        return issueRepository.findByStatus(status);
    }

}
