package results.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import results.ResultService;
import results.exception.ResourceNotFoundException;
import results.model.*;
import results.repository.*;

import javax.validation.Valid;

@RestController
public class StudentController {
     @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassArmRepository classArmRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ScoresheetRepository scoresheetRepository;
    @Autowired
    private ResultService resultService;

   private Scoresheet scoresheet=new Scoresheet();

     @GetMapping("/students")
    public Page<Student> getAllStudents(Pageable pageable){
         return studentRepository.findAll(pageable);
     }

     @PostMapping("/arms/{armId}/students")
    public Student createStudent(@PathVariable (value = "armId") Long armId,@Valid @RequestBody Student student){
         Psychomotor psychomotor= new Psychomotor();
         Affective affective=new Affective();
         psychomotor.setStudent(student);
         affective.setStudent(student);

         ClassArm arm=classArmRepository.findById(armId).get();

             student.setClassArm(arm);
             subjectRepository.findAllByClassArm(armId).forEach(i->{
                 scoresheet.setStudent(student);
                 scoresheet.setSubject(i);
                 scoresheetRepository.save(scoresheet);
                 scoresheet=new Scoresheet();
             });
             return studentRepository.save(student);

     }

     @PutMapping("/students/{studentId}")
    public Student updateStudent(@PathVariable Long studentId, @Valid @RequestBody Student postRequest){
         return studentRepository.findById(studentId).map(student -> {
             student.setFTotal(postRequest.getFTotal());
             student.setFPosition(postRequest.getFPosition());
             student.setFTeacherComment(postRequest.getFTeacherComment());
             student.setFPrincipalComment(postRequest.getFPrincipalComment());
             student.setFPercentage(postRequest.getFPercentage());
             student.setFAttendance(postRequest.getFAttendance());
             student.setNoOfSubjectOffered(postRequest.getNoOfSubjectOffered());

             return studentRepository.save(student);
         }).orElseThrow(()->new ResourceNotFoundException("StudentId" + studentId+"not found"));
     }
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId){
        return studentRepository.findById(studentId).map(student -> {
            subjectRepository.findAllByClassArm(student.getClassArm().getArmId()).forEach(i-> {
              resultService.updateScoresheet(i.getSubjectId(), student.getClassArm().getArmId().toString());
            });

             studentRepository.delete(student);
             return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("StudentId" + studentId+"not found"));
    }

}
