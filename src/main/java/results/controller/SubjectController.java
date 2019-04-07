package results.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import results.ResultService;
import results.exception.ResourceNotFoundException;
import results.model.ClassArm;
import results.model.Scoresheet;
import results.model.Student;
import results.model.Subject;
import results.repository.ClassArmRepository;
import results.repository.ScoresheetRepository;
import results.repository.StudentRepository;
import results.repository.SubjectRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ScoresheetRepository scoresheetRepository;
    @Autowired
    private ClassArmRepository classArmRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ResultService resultService;

    private Scoresheet scoresheet=new Scoresheet();

    @GetMapping("/subjects")
    public Page<Subject> getAllSubjects(Pageable pageable){
        return subjectRepository.findAll(pageable);
    }

    @PostMapping("/arms/{armId}/subjects")
    public Subject createSubject(@PathVariable (value = "armId") Long armId, @Valid @RequestBody Subject subject){
        ClassArm arm=classArmRepository.findById(armId).get();

        subject.setClassArm(arm);
        studentRepository.findAllByClassArm(armId).forEach(i->{
            scoresheet.setSubject(subject);
            scoresheet.setStudent(i);
            scoresheetRepository.save(scoresheet);
            scoresheet=new Scoresheet();
        });
        return subjectRepository.save(subject);
    }

    @PutMapping("/subjects/{subjectId}")
    public Subject updateSubject(@PathVariable Long subjectId, @Valid @RequestBody Subject postRequest){
        return subjectRepository.findById(subjectId).map(subject -> {
            subject.setSubjectName(postRequest.getSubjectName());
            subject.setFClassAvg(postRequest.getFClassAvg());
            return subjectRepository.save(subject);
        }).orElseThrow(()->new ResourceNotFoundException("SubjectId" + subjectId+"not found"));
    }
    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long subjectId){
        return subjectRepository.findById(subjectId).map(subject -> {
            ArrayList<Object> students=new ArrayList<Object>();
              scoresheetRepository.findAllBySubject(subjectId).forEach(i->{

                  students.add(i.getStudent().getStudentId());
                  studentRepository.findById(i.getStudent().getStudentId()).map(student -> {
                      student.setFTotal(student.getFTotal()-i.getFTotal());
                      scoresheetRepository.delete(i);
                      return studentRepository.save(student);

                  });
              });
              resultService.updateStudentOfferedSubject(students.toArray(),false);
            subjectRepository.delete(subject);
           resultService.updateScoresheet(0, subject.getClassArm().getArmId().toString());
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("SubjectId" + subjectId+"not found"));
    }

}
