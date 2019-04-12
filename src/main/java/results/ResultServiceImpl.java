package results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import results.model.Scoresheet;
import results.model.Student;
import results.model.Subject;
import results.repository.ScoresheetRepository;
import results.repository.StudentRepository;
import results.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ScoresheetRepository scoresheetRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Override
   public void updateScoresheet(Long subjectId, String ArmId){
        ArrayList<Double> totalValue = new ArrayList<Double>();
        ArrayList<Long> totalPrimaryKey = new ArrayList<Long>();
        ArrayList<Double> totalValue2 = new ArrayList<Double>();
        ArrayList<Long> totalPrimaryKey2 = new ArrayList<Long>();
        Iterable<Scoresheet> dt = scoresheetRepository.findAll();
        //int term =settings.Term;
        double classAvg = 0.0;
        int noOfStudent = 0;

        for (Scoresheet sc : dt) {
            if (sc.getSubject().getSubjectId() == subjectId)
            {
                totalPrimaryKey.add(sc.getSheetId());

                totalValue.add(sc.getFTotal());
                classAvg = classAvg + sc.getFTotal();
                noOfStudent++;

            }
        }
        if (subjectId != 0)
        {
           Subject sub= subjectRepository.findById(subjectId).get();
           sub.setFClassAvg(Math.round(classAvg/noOfStudent));
           subjectRepository.save(sub);

        }
        Iterable<Student> dt2=studentRepository.findAll();

        for(Student sc2:dt2) {
            if (sc2.getClassArm().getArmId().toString() == ArmId) {
                totalPrimaryKey2.add(sc2.getStudentId());
                totalValue2.add(sc2.getFTotal());
                sc2.setFPercentage(Math.round(sc2.getFTotal()/sc2.getNoOfSubjectOffered()));
               studentRepository.save(sc2);
            }
        }
        if(subjectId!=0)  updateSubjectPosition(totalPrimaryKey, totalValue);
        updateStudentPosition(totalPrimaryKey2, totalValue2);
    }
    @Override
   public void updateStudentPosition(ArrayList<Long> primaryKey, ArrayList<Double> value){
        int size = value.size();
        int index1 = 0;
        int position = 1;
        double lastmax = 0;
        int lastposition = 1;
       // int term =settings.Term;
        Student student;
        // if (term == 1)
       // {
            for (int i = 0; i < size; i++)
            {

                index1 = findMax(value);
                student=studentRepository.findById(primaryKey.get(index1)).get();
                if (lastmax == student.getFTotal() )
                {
                     student.setFPosition(lastposition);

                }
                else
                {
                    student.setFPosition(position);
                    lastposition = position;

                }
                position++;
                lastmax = student.getFTotal();
                value.remove(index1);
                primaryKey.remove(index1);
               studentRepository.save(student);



            }
       // }
    }
    @Override
   public void updateSubjectPosition(ArrayList<Long> primaryKey, ArrayList<Double> value) {

        int size = value.size();
        int index1 = 0;
        int position = 1;
        double lastmax = 0;
        int lastposition = 1;
        // int term =settings.Term;
        Scoresheet scoresheet;

        //if (term == 1)
        //  {
        for (int i = 0; i < size; i++) {
            index1 = findMax(value);
            scoresheet = scoresheetRepository.findById(primaryKey.get(index1)).get();

            if (lastmax == scoresheet.getFTotal()) {
                scoresheet.setFPosition(lastposition);

            } else {
                scoresheet.setFPosition(position);
                lastposition = position;

            }
            position++;
            lastmax = scoresheet.getFTotal();
            value.remove(index1);
            primaryKey.remove(index1);
            scoresheetRepository.save(scoresheet);
        }

    }
    @Override
   public int findMax(ArrayList<Double> inList){
        int index=0;
        int count=0;
        double max = inList.get(0);
        for (double ob : inList)
        {
            if(max<ob)
            {
                max=ob;
                index=count;
            }
            count++;

        }
        return index;
    }

    @Override
    public void updateStudentOfferedSubject(Object[] studentIds, boolean add){
        Student student;
        if(!add)
            for(Object studentId : studentIds)
        {
           student= studentRepository.findById((Long)studentId).get();
           student.setNoOfSubjectOffered(student.getNoOfSubjectOffered()-1);
           studentRepository.save(student);

        }
            else
            for(Object studentId : studentIds)
        {
            student= studentRepository.findById((Long)studentId).get();
            student.setNoOfSubjectOffered(student.getNoOfSubjectOffered()+1);
            studentRepository.save(student);
        }

    }
    @Override
    public boolean gradeStudent(Scoresheet clientItemP){
        //Method to be implemented in the next commit
        return true;
    }
    @Override
    public void UpdateCAs(){
        //Method to be implemented in the next commit
    }
}
