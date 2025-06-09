package IssueTracker.service;

import IssueTracker.model.Issue;
import IssueTracker.repositorry.IssueRepository;
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
        Optional<Issue> result = issueRepository.findById(id);

        // let controller handle this case
        return result.orElse(null);
    }

    @Override
    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    public void deleteById(Long id) {
        issueRepository.deleteById(id);
    }
}
