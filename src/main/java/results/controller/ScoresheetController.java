package results.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import results.exception.ResourceNotFoundException;
import results.model.Scoresheet;
import results.model.Student;
import results.repository.ScoresheetRepository;
import results.repository.StudentRepository;

import javax.validation.Valid;

public class ScoresheetController {
    @Autowired
    private ScoresheetRepository scoresheetRepository;

    @GetMapping("/scoresheets")
    public Page<Scoresheet> getAllScoresheets(Pageable pageable){
        return  scoresheetRepository.findAll(pageable);
    }

    @PostMapping("/scoresheets")
    public Scoresheet createScoresheet(@Valid @RequestBody Scoresheet scoresheet){
        return  scoresheetRepository.save(scoresheet);
    }

    @PutMapping("/scoresheets/{scoresheetId}")
    public Scoresheet updateStudent(@PathVariable Long scoresheetId, @Valid @RequestBody Scoresheet postRequest){
        return scoresheetRepository.findById(scoresheetId).map(scoresheet -> {
            scoresheet.setFTotal(postRequest.getFTotal());
            scoresheet.setFPosition(postRequest.getFPosition());
            scoresheet.setFca1(postRequest.getFca1());
            scoresheet.setFca2(postRequest.getFca2());
            scoresheet.setFca3(postRequest.getFca3());
            scoresheet.setCumAvg(postRequest.getCumAvg());
            scoresheet.setPosition(postRequest.getPosition());
            scoresheet.setFGrade(postRequest.getFGrade());
            scoresheet.setFRemark(postRequest.getFRemark());


            return scoresheetRepository.save(scoresheet);
        }).orElseThrow(()->new ResourceNotFoundException("ScoresheetId" + scoresheetId+"not found"));
    }
    @DeleteMapping("/scoresheets/{scoresheetId}")
    public ResponseEntity<?> deleteScoresheet(@PathVariable Long scoresheetId){
        return scoresheetRepository.findById(scoresheetId).map(scoresheet -> {

            scoresheetRepository.delete(scoresheet);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("ScoresheetId" + scoresheetId+"not found"));
    }

}
