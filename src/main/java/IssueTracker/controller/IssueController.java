package IssueTracker.controller;

import IssueTracker.model.Issue;
import IssueTracker.model.IssueStatus;
import IssueTracker.service.IssueService;
import IssueTracker.service.IssueServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobKOctets;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/issues")
public class IssueController {
    private final IssueService issueService;
    private final ObjectMapper objectMapper;

    @Autowired
    public IssueController(IssueService theIssueService, ObjectMapper theObjectMapper){
        issueService = theIssueService;
        objectMapper = theObjectMapper;
    }

    //get all issues
    @GetMapping()
    public List<Issue> getAllIssues(){
        return issueService.findAll();
    }

    //get issue by id
    @GetMapping("/issues/{id}")
    public Issue getIssueById(@PathVariable Long id){
        Issue issue = issueService.findById(id);
        if(issue == null){
            throw new RuntimeException("Issue not found " + id);
        }
        return issue;
    }

    @PostMapping("/issue")
    public Issue addIssue(@RequestBody Issue theIssue){
        //incase they pss some id in json, set that id to 0
        //this is to force save of the new item instead of an update
        theIssue.setId(0);
        Issue dbIssue = issueService.save(theIssue);
        return dbIssue;
    }

    @PutMapping("/issue")
    public Issue updateIssue(@RequestBody Issue issue){
        Issue dbIssue = issueService.save(issue);
        return dbIssue;
    }

    //patch mapping... used for partial update
    @PatchMapping("/issue/{id}")
    public Issue patchIssue(@PathVariable long id, @RequestBody Map<String, Object>patchPayLoad ){
        Issue tempIssue = issueService.findById(id);
        if(tempIssue==null){
            throw new RuntimeException("Issue not found "+ id);
        }
        //throw exception if request body contains "id" key
        if(patchPayLoad.containsKey("id")){
            throw new RuntimeException("Issue Id not allowd in request body");
        }

        Issue patchedIssue = apply(patchPayLoad, tempIssue);
        Issue dbIssue = issueService.save(patchedIssue);

        return dbIssue;

    }

    public Issue apply(Map<String, Object> patchPayLoad, Issue tempIssue){
        //convert the issue object into json object node
        ObjectNode issueNode = objectMapper.convertValue(tempIssue, ObjectNode.class);

        //convert patchedpayload object into json object node
        ObjectNode patchedNode = objectMapper.convertValue(patchPayLoad, ObjectNode.class);

        //merge the patched updates to issue
        issueNode.setAll(patchedNode);
        return objectMapper.convertValue(issueNode, Issue.class);

    }

    @DeleteMapping("/issue/{id}")
    public String deleteIssue(@PathVariable long id){
        Issue tempIssue = issueService.findById(id);
        if(tempIssue == null){
            throw new RuntimeException("Issue id not found: " + id);
        }
        issueService.deleteById(id);
        return "Deleted issue: " + id;
    }









}
