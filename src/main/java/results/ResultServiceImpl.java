package results;

import org.springframework.stereotype.Service;
import results.model.Scoresheet;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    //The full implementation of ResultService will be done in the next commit
    @Override
   public void updateScoresheet(int subjectId, String ArmId){

    }
    @Override
   public void updateStudentPosition(List<Object> primaryKey, List<Object> value){

    }
    @Override
   public void updateSubjectPosition(List<Object> primaryKey, List<Object> value){

    }
    @Override
   public int findMax(List<Object> inList){
        return 1;
    }

    @Override
    public void updateStudentOfferedSubject(Object[] studentIds, boolean add){

    }
    @Override
    public boolean gradeStudent(Scoresheet[] clientItemP){
        return true;
    }
    @Override
    public void UpdateCAs(){

    }
}
