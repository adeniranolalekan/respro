package results;

import results.model.Scoresheet;

import java.util.ArrayList;
import java.util.List;

public interface ResultService {

     void updateScoresheet(Long subjectId, String ArmId);
     void updateStudentPosition(ArrayList<Long> primaryKey, ArrayList<Double> value);
     void updateSubjectPosition(ArrayList<Long> primaryKey, ArrayList<Double> value);
    int findMax(ArrayList<Double> inList);
    void updateStudentOfferedSubject(Object[] studentIds, boolean add);
     boolean gradeStudent(Scoresheet clientItemP);
     void UpdateCAs();

}
