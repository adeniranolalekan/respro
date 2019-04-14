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
import results.repository.SettingRepository;
import results.repository.StudentRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;

public class ScoresheetController {
    @Autowired
    private ScoresheetRepository scoresheetRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SettingRepository settingRepository;

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
        int term = settingRepository.findById(1).get().getTerm();
        ArrayList<Scoresheet> returnValue= new ArrayList<Scoresheet>();
        if(term==1) {
        for (Scoresheet scoresheet : postRequest) {
            double currentTotal;
            double previousTotal;

            currentScoresheet = scoresheetRepository.findById(scoresheet.getSheetId()).get();
            currentTotal = scoresheet.getFca1() + scoresheet.getFca2() + scoresheet.getFca3() + scoresheet.getFExam();
            previousTotal = currentScoresheet.getFTotal();
            currentScoresheet.setFca1(scoresheet.getFca1());
            currentScoresheet.setFca2(scoresheet.getFca2());
            currentScoresheet.setFca3(scoresheet.getFca3());
            currentScoresheet.setFExam(scoresheet.getFExam());
            currentScoresheet.setFTotal(currentTotal);
            currentScoresheet.setFPosition(scoresheet.getFPosition());
            currentScoresheet.setSca1(scoresheet.getSca1());
            currentScoresheet.setSca2(scoresheet.getSca2());
            currentScoresheet.setSca3(scoresheet.getSca3());
            currentScoresheet.setSExam(scoresheet.getSExam());
            currentScoresheet.setSTotal(scoresheet.getSTotal());
            currentScoresheet.setSPosition(scoresheet.getSPosition());
            currentScoresheet.setTca1(scoresheet.getTca1());
            currentScoresheet.setTca2(scoresheet.getTca2());
            currentScoresheet.setTca3(scoresheet.getTca3());
            currentScoresheet.setTExam(scoresheet.getTExam());
            currentScoresheet.setTTotal(scoresheet.getTTotal());
            currentScoresheet.setCumAvg((scoresheet.getSTotal() + scoresheet.getTTotal() + currentTotal) / 3);
            currentScoresheet.setPosition(scoresheet.getPosition());
            currentScoresheet.setFGrade(scoresheet.getFGrade());
            currentScoresheet.setFRemark(scoresheet.getFRemark());
            Student thisStudent = scoresheet.getStudent();
            thisStudent.setFTotal(thisStudent.getFTotal() - previousTotal + currentTotal);
            resultService.gradeStudent(postRequest);
            studentRepository.save(thisStudent);
            returnValue.add(currentScoresheet);
            scoresheetRepository.save(currentScoresheet);
        }
        }
        else if(term==2) {
            for (Scoresheet scoresheet : postRequest) {
                double currentTotal;
                double previousTotal;

                currentScoresheet = scoresheetRepository.findById(scoresheet.getSheetId()).get();
                currentTotal = scoresheet.getSca1() + scoresheet.getSca2() + scoresheet.getSca3() + scoresheet.getSExam();
                previousTotal = currentScoresheet.getSTotal();
                currentScoresheet.setFca1(scoresheet.getFca1());
                currentScoresheet.setFca2(scoresheet.getFca2());
                currentScoresheet.setFca3(scoresheet.getFca3());
                currentScoresheet.setFExam(scoresheet.getFExam());
                currentScoresheet.setFTotal(scoresheet.getFTotal());
                currentScoresheet.setFPosition(scoresheet.getFPosition());
                currentScoresheet.setSca1(scoresheet.getSca1());
                currentScoresheet.setSca2(scoresheet.getSca2());
                currentScoresheet.setSca3(scoresheet.getSca3());
                currentScoresheet.setSExam(scoresheet.getSExam());
                currentScoresheet.setSTotal(currentTotal);
                currentScoresheet.setSPosition(scoresheet.getSPosition());
                currentScoresheet.setTca1(scoresheet.getTca1());
                currentScoresheet.setTca2(scoresheet.getTca2());
                currentScoresheet.setTca3(scoresheet.getTca3());
                currentScoresheet.setTExam(scoresheet.getTExam());
                currentScoresheet.setTTotal(scoresheet.getTTotal());
                currentScoresheet.setCumAvg((scoresheet.getFTotal() + scoresheet.getTTotal() + currentTotal) / 3);
                currentScoresheet.setPosition(scoresheet.getPosition());
                currentScoresheet.setFGrade(scoresheet.getSGrade());
                currentScoresheet.setFRemark(scoresheet.getSRemark());
                Student thisStudent = scoresheet.getStudent();
                thisStudent.setFTotal(thisStudent.getFTotal() - previousTotal + currentTotal);
                resultService.gradeStudent(postRequest);
                studentRepository.save(thisStudent);
                returnValue.add(currentScoresheet);
                scoresheetRepository.save(currentScoresheet);

            }
        }
        else  {
            for (Scoresheet scoresheet : postRequest) {
                double currentTotal;
                double previousTotal;

                currentScoresheet = scoresheetRepository.findById(scoresheet.getSheetId()).get();
                currentTotal = scoresheet.getTca1() + scoresheet.getTca2() + scoresheet.getTca3() + scoresheet.getTExam();
                previousTotal = currentScoresheet.getTTotal();
                currentScoresheet.setFca1(scoresheet.getFca1());
                currentScoresheet.setFca2(scoresheet.getFca2());
                currentScoresheet.setFca3(scoresheet.getFca3());
                currentScoresheet.setFExam(scoresheet.getFExam());
                currentScoresheet.setFTotal(scoresheet.getFTotal());
                currentScoresheet.setFPosition(scoresheet.getFPosition());
                currentScoresheet.setSca1(scoresheet.getSca1());
                currentScoresheet.setSca2(scoresheet.getSca2());
                currentScoresheet.setSca3(scoresheet.getSca3());
                currentScoresheet.setSExam(scoresheet.getSExam());
                currentScoresheet.setSTotal(scoresheet.getSTotal());
                currentScoresheet.setSPosition(scoresheet.getSPosition());
                currentScoresheet.setTca1(scoresheet.getTca1());
                currentScoresheet.setTca2(scoresheet.getTca2());
                currentScoresheet.setTca3(scoresheet.getTca3());
                currentScoresheet.setTExam(scoresheet.getTExam());
                currentScoresheet.setTTotal(currentTotal);
                currentScoresheet.setCumAvg((scoresheet.getSTotal() + scoresheet.getFTotal() + currentTotal) / 3);
                currentScoresheet.setPosition(scoresheet.getPosition());
                currentScoresheet.setTGrade(scoresheet.getTGrade());
                currentScoresheet.setTRemark(scoresheet.getTRemark());
                Student thisStudent = scoresheet.getStudent();
                thisStudent.setTTotal(thisStudent.getTTotal() - previousTotal + currentTotal);
                resultService.gradeStudent(postRequest);
                studentRepository.save(thisStudent);
                returnValue.add(currentScoresheet);
                scoresheetRepository.save(currentScoresheet);

            }
        }
        resultService.updateScoresheet(postRequest[0].getSubject().getSubjectId(), postRequest[0].getStudent().getClassArm().getArmId().toString());
        resultService.gradeStudent(postRequest);
        return (Scoresheet[]) returnValue.toArray();
    }



    @DeleteMapping("/scoresheets/{scoresheetId}")
    public ResponseEntity<?> deleteScoresheet(@PathVariable @RequestBody Long[] scoresheets) {
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
