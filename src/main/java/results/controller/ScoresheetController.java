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
import java.util.Iterator;

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
        ArrayList<Integer> SubjectIds = new ArrayList<Integer>();
        ArrayList<Long> students = new ArrayList<Long>();
        Long inArmId = scoresheets[0].getStudent().getClassArm().getArmId();
        for (Scoresheet scoresheet : scoresheets) {
            if (!SubjectIds.contains(scoresheet.getSubject().getSubjectId()))
                SubjectIds.add(scoresheet.getSubject().getSubjectId());
            students.add(scoresheet.getStudent().getStudentId());
            scoresheetRepository.save(scoresheet);
        }
        resultService.updateStudentOfferedSubject(students.toArray(), true);
        for (int id : SubjectIds) {
            resultService.updateScoresheet(id, inArmId.toString());
        }


    }

    @PutMapping("/scoresheets/{scoresheetId}")
    public Scoresheet[] updateScoresheet(@PathVariable Long scoresheetId, @Valid @RequestBody Scoresheet[] postRequest) {
        Scoresheet currentScoresheet;
        ArrayList<Scoresheet> returnValue= new ArrayList<Scoresheet>();
        for (Scoresheet scoresheet : postRequest) {
            double currentTotal;
            double previousTotal;

            currentScoresheet = scoresheetRepository.findById(scoresheet.getSheetId()).get();
            currentTotal = scoresheet.getFca1() + scoresheet.getFca2() + scoresheet.getFca3() + scoresheet.getFExam();
            previousTotal = currentScoresheet.getFTotal();
            scoresheet.setFTotal(scoresheet.getFTotal());
            scoresheet.setFPosition(scoresheet.getFPosition());
            scoresheet.setFca1(scoresheet.getFca1());
            scoresheet.setFca2(scoresheet.getFca2());
            scoresheet.setFca3(scoresheet.getFca3());
            scoresheet.setFExam(scoresheet.getFExam());
            scoresheet.setFTotal(currentTotal);
            scoresheet.setCumAvg((scoresheet.getSTotal() + scoresheet.getTTotal() + currentTotal) / 3);
            scoresheet.setPosition(scoresheet.getPosition());
            scoresheet.setFGrade(scoresheet.getFGrade());
            scoresheet.setFRemark(scoresheet.getFRemark());

            Student thisStudent = scoresheet.getStudent();
            thisStudent.setFTotal(thisStudent.getFTotal() - previousTotal + currentTotal);
            resultService.gradeStudent(postRequest);
            studentRepository.save(thisStudent);
            returnValue.add(scoresheet);
            scoresheetRepository.save(scoresheet);


        }
        resultService.updateScoresheet(postRequest[0].getSubject().getSubjectId(), postRequest[0].getStudent().getClassArm().getArmId().toString());
        resultService.gradeStudent(postRequest);
        return (Scoresheet[]) returnValue.toArray();
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
