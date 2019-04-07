package results;

import results.model.Scoresheet;

import java.util.List;

public interface ResultService {

     void updateScoresheet(int subjectId, String ArmId);
     void updateStudentPosition(List<Object> primaryKey, List<Object> value);
     void updateSubjectPosition(List<Object> primaryKey, List<Object> value);
    int findMax(List<Object> inList);
    void updateStudentOfferedSubject(Object[] studentIds, boolean add);
     boolean gradeStudent(Scoresheet[] clientItemP);
     void UpdateCAs();

}
