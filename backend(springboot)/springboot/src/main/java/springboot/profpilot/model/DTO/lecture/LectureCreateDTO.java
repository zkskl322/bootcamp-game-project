package springboot.profpilot.model.DTO.lecture;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LectureCreateDTO {
    private String lectureName;
    private String lectureDay;
    private String lectureStartTime;
    private String lectureEndTime;
    private String building;
    private String room;
    private String password;
}
