package results;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import results.model.Grade;
import results.model.Scoresheet;
import results.model.Student;
import results.model.Subject;
import results.repository.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ScoresheetRepository scoresheetRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private GradeRepository gradeRepository;
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
        DecimalFormat df= new DecimalFormat("#.00");
        if (subjectId != 0)
        {
           Subject sub= subjectRepository.findById(subjectId).get();
           double val=classAvg/noOfStudent;

           sub.setFClassAvg(Double.parseDouble(df.format(val)));
           subjectRepository.save(sub);

        }
        Iterable<Student> dt2=studentRepository.findAll();

        for(Student sc2:dt2) {
            if (sc2.getClassArm().getArmId().toString() == ArmId) {
                totalPrimaryKey2.add(sc2.getStudentId());
                totalValue2.add(sc2.getFTotal());
                sc2.setFPercentage(Double.parseDouble(df.format(sc2.getFTotal()/sc2.getNoOfSubjectOffered())));
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
    public boolean gradeStudent(Scoresheet[] clientItemP){
        //Method to be implemented in the next commit

        boolean returnValue = false;
        Dictionary<Integer, Grade> lsg=new Hashtable<Integer, Grade>();
        int i = 0;
        int term = settingRepository.findById(1).get().getTerm();
        Iterable<Grade> grades= gradeRepository.findAll();
        Iterable<Scoresheet> scoresheets=scoresheetRepository.findAll();
        for(Grade grade :grades)

        {

            while (i <=grade.getRangeHigh())
            {
                lsg.put(i, grade);
                i++;
            }

        }
        if (lsg.size() > 0) {
            if (clientItemP == null) {
                Scoresheet dr2;
                if (term == 1) {

                    for (Scoresheet scoresheet : scoresheets) {
                        dr2 = scoresheetRepository.findById(scoresheet.getSheetId()).get();
                        dr2.setFGrade(lsg.get(Integer.parseInt(Double.toString(scoresheet.getFTotal()))).getGradeName());
                        dr2.setFRemark(lsg.get(Integer.parseInt(Double.toString(scoresheet.getFTotal()))).getRemark());
                        scoresheetRepository.save(dr2);
                    }

                } else if (term == 2) {
                    for (Scoresheet scoresheet : scoresheets) {
                        dr2 = scoresheetRepository.findById(scoresheet.getSheetId()).get();
                        dr2.setSGrade(lsg.get(Integer.parseInt(Double.toString(scoresheet.getSTotal()))).getGradeName());
                        dr2.setSRemark(lsg.get(Integer.parseInt(Double.toString(scoresheet.getSTotal()))).getRemark());
                        scoresheetRepository.save(dr2);
                    }
                } else {
                    for (Scoresheet scoresheet : scoresheets) {
                        dr2 = scoresheetRepository.findById(scoresheet.getSheetId()).get();
                        dr2.setTGrade(lsg.get(Integer.parseInt(Double.toString(scoresheet.getTTotal()))).getGradeName());
                        dr2.setTRemark(lsg.get(Integer.parseInt(Double.toString(scoresheet.getTTotal()))).getRemark());
                        scoresheetRepository.save(dr2);
                    }
                }
            } else {
                Scoresheet dr2;
                if (term == 1) {

                    for (Scoresheet scoresheet : clientItemP) {
                        dr2 = scoresheetRepository.findById(scoresheet.getSheetId()).get();
                        dr2.setFGrade(lsg.get(Integer.parseInt(Double.toString(scoresheet.getFTotal()))).getGradeName());
                        dr2.setFRemark(lsg.get(Integer.parseInt(Double.toString(scoresheet.getFTotal()))).getRemark());
                        scoresheetRepository.save(dr2);
                    }

                } else if (term == 2) {
                    for (Scoresheet scoresheet : clientItemP) {
                        dr2 = scoresheetRepository.findById(scoresheet.getSheetId()).get();
                        dr2.setSGrade(lsg.get(Integer.parseInt(Double.toString(scoresheet.getSTotal()))).getGradeName());
                        dr2.setSRemark(lsg.get(Integer.parseInt(Double.toString(scoresheet.getSTotal()))).getRemark());
                        scoresheetRepository.save(dr2);
                    }
                } else {
                    for (Scoresheet scoresheet : clientItemP) {
                        dr2 = scoresheetRepository.findById(scoresheet.getSheetId()).get();
                        dr2.setTGrade(lsg.get(Integer.parseInt(Double.toString(scoresheet.getTTotal()))).getGradeName());
                        dr2.setTRemark(lsg.get(Integer.parseInt(Double.toString(scoresheet.getTTotal()))).getRemark());
                        scoresheetRepository.save(dr2);
                    }
                }
            }
        }

        return true;
    }
    @Override
    public void UpdateCAs(){
        //Method to be implemented in the next commit
    }
}
