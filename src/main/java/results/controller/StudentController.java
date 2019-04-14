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
import java.util.ArrayList;

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
    public void createStudent(@PathVariable (value = "armId") Long armId,@Valid @RequestBody Student[] students){
         for (Student student : students) {
             Psychomotor psychomotor = new Psychomotor();
             Affective affective = new Affective();
             psychomotor.setStudent(student);
             affective.setStudent(student);

             ClassArm arm = classArmRepository.findById(armId).get();

             student.setClassArm(arm);
             subjectRepository.findAllByClassArm(armId).forEach(i -> {
                 scoresheet.setStudent(student);
                 scoresheet.setSubject(i);
                 scoresheetRepository.save(scoresheet);
                 studentRepository.save(student);
                 scoresheet = new Scoresheet();
             });

         }
     }

     @PutMapping("/students/{studentId}")
    public Student[] updateStudent(@PathVariable Long studentId, @Valid @RequestBody Student[] postRequest){
         ArrayList<Student> returnValue= new ArrayList<Student>();
         for(Student student:postRequest)
         {
             studentRepository.save(student);
        returnValue.add(student);
     }
         return (Student[]) returnValue.toArray();
     }
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId,@RequestBody Long[] students ){
         Student currentStudent;
       for(Long student : students) {
           currentStudent = studentRepository.findById(student).get();
           studentRepository.delete(currentStudent);
       }
        currentStudent = studentRepository.findById(studentId).get();
            subjectRepository.findAllByClassArm(currentStudent.getClassArm().getArmId()).forEach(i-> {
              resultService.updateScoresheet(i.getSubjectId(), studentRepository.findById(studentId).get().getClassArm().getArmId().toString());
            });


             return ResponseEntity.ok().build();

    }

}
