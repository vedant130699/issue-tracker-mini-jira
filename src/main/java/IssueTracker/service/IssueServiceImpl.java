package IssueTracker.service;

import IssueTracker.exception.ResourceNotFoundException;
import IssueTracker.model.Issue;
import IssueTracker.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{


    private IssueRepository issueRepository;

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
    public void deleteById(Long id) {
        Issue issue  = issueRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Issue not found " + id));
        issueRepository.delete(issue);
    }
}
