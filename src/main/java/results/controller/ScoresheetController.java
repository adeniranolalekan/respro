package results.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import results.ResultService;
import results.exception.ResourceNotFoundException;
import results.model.Scoresheet;
import results.model.Student;
import results.repository.ScoresheetRepository;
import results.repository.StudentRepository;

import javax.validation.Valid;
import java.util.ArrayList;

public class ScoresheetController {
    @Autowired
    private ScoresheetRepository scoresheetRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/scoresheets")
    public Page<Scoresheet> getAllScoresheets(Pageable pageable) {
        return scoresheetRepository.findAll(pageable);
    }

    @Autowired
    private ResultService resultService;

    @PostMapping("/scoresheets")
    public void createScoresheet(@Valid @RequestBody Scoresheet[] scoresheets) {
        ArrayList<Long> SubjectIds = new ArrayList<Long>();
        ArrayList<Long> students = new ArrayList<Long>();
        Long inArmId = scoresheets[0].getStudent().getClassArm().getArmId();
        for (Scoresheet scoresheet : scoresheets) {
            if (!SubjectIds.contains(scoresheet.getSubject().getSubjectId()))
                SubjectIds.add((long) scoresheet.getSubject().getSubjectId());
            students.add(scoresheet.getStudent().getStudentId());
            scoresheetRepository.save(scoresheet);
        }
        resultService.updateStudentOfferedSubject(students.toArray(), true);
        for (long id : SubjectIds) {
            resultService.updateScoresheet(id, inArmId.toString());
        }


    }

    @PutMapping("/scoresheets/{scoresheetId}")
    public Scoresheet updateScoresheet(@PathVariable Long scoresheetId, @Valid @RequestBody Scoresheet postRequest) {
        return scoresheetRepository.findById(scoresheetId).map(scoresheet -> {
            double currentTotal;
            double previousTotal;
            currentTotal = postRequest.getFca1() + postRequest.getFca2() + postRequest.getFca3() + postRequest.getFExam();
            previousTotal = scoresheet.getFTotal();
            scoresheet.setFTotal(postRequest.getFTotal());
            scoresheet.setFPosition(postRequest.getFPosition());
            scoresheet.setFca1(postRequest.getFca1());
            scoresheet.setFca2(postRequest.getFca2());
            scoresheet.setFca3(postRequest.getFca3());
            scoresheet.setFExam(postRequest.getFExam());
            scoresheet.setFTotal(currentTotal);
            scoresheet.setCumAvg((postRequest.getSTotal() + postRequest.getTTotal() + currentTotal) / 3);
            scoresheet.setPosition(postRequest.getPosition());
            scoresheet.setFGrade(postRequest.getFGrade());
            scoresheet.setFRemark(postRequest.getFRemark());

            Student thisStudent = postRequest.getStudent();
            thisStudent.setFTotal(thisStudent.getFTotal() - previousTotal + currentTotal);
            resultService.updateScoresheet(thisStudent.getStudentId(), thisStudent.getClassArm().getArmId().toString());
            resultService.gradeStudent(postRequest);
            studentRepository.save(thisStudent);
            return scoresheetRepository.save(scoresheet);


        }).orElseThrow(() -> new ResourceNotFoundException("ScoresheetId" + scoresheetId + "not found"));
    }

    @DeleteMapping("/scoresheets/{scoresheetId}")
    public ResponseEntity<?> deleteScoresheet(@PathVariable Long[] scoresheets) {
        ArrayList<Long> students = new ArrayList<Long>();
        Scoresheet sheet;
        for (Long scoresheet : scoresheets) {

           sheet=scoresheetRepository.findById(scoresheet).get();
            students.add(sheet.getStudent().getStudentId());
            scoresheetRepository.delete(sheet);
        }
        resultService.updateStudentOfferedSubject(students.toArray(),false);
        return ResponseEntity.ok().build();
    }

}
