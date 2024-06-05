package springboot.profpilot.model.DTO.page;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MainPageDTO {

    private String LectureName;
    private String LectureDay;
    private String LectureStartTime;
    private String LectureEndTime;
    private String LectureBuilding;
    private String LectureRoom;
    private String LectureProfessor;


    public MainPageDTO(String lectureName, String lectureDay, String lectureStartTime, String lectureEndTime, String lectureBuilding, String lectureRoom, String lectureProfessor) {
        LectureName = lectureName;
        LectureDay = lectureDay;
        LectureStartTime = lectureStartTime;
        LectureEndTime = lectureEndTime;
        LectureBuilding = lectureBuilding;
        LectureRoom = lectureRoom;
        LectureProfessor = lectureProfessor;
    }
}
