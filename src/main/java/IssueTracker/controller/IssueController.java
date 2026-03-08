package IssueTracker.controller;

import IssueTracker.exception.ResourceNotFoundException;
import IssueTracker.model.Issue;
import IssueTracker.model.IssueStatus;
import IssueTracker.service.IssueService;
import IssueTracker.service.IssueServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping()
    public ResponseEntity<List<Issue>> getAllIssues(){
        return ResponseEntity.ok(issueService.findAll());
    }

    //get issue by id
    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id){
        return ResponseEntity.ok(issueService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Issue> addIssue(@RequestBody Issue theIssue){
        //incase they pass some id in json, set that id to 0
        //this is to force save of the new item instead of an update
        theIssue.setId(0);
        Issue savedIssue = issueService.save(theIssue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIssue);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/issue")
    public ResponseEntity<Issue> updateIssue(@RequestBody Issue issue){
        return ResponseEntity.ok(issueService.save(issue));
    }

    //patch mapping... used for partial update
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/issue/{id}")
    public ResponseEntity<Issue> patchIssue(@PathVariable long id, @RequestBody Map<String, Object>patchPayLoad ){
        Issue tempIssue = issueService.findById(id);

        //throw exception if request body contains "id" key
        if(patchPayLoad.containsKey("id")){
            throw new RuntimeException("Issue id is not allowed in request body");
        }

        Issue patchedIssue = apply(patchPayLoad, tempIssue);
        Issue dbIssue = issueService.save(patchedIssue);

        return ResponseEntity.ok(dbIssue);

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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteIssue(@PathVariable long id){
        issueService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/issue/status/{status}")
    public ResponseEntity<List<Issue>> findByStatus(@PathVariable String status){
        IssueStatus issueStatus = null;
        try {
            issueStatus = IssueStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Issue> issue = issueService.findByStatus(issueStatus);
        if(issue.isEmpty()){
            throw new ResourceNotFoundException("No issues with the status: " + status);
        }
        return ResponseEntity.ok(issue);
    }









}
