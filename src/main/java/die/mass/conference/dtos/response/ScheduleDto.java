package die.mass.conference.dtos.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScheduleDto {

    private List<RoomSchedule> roomSchedule;


    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class RoomSchedule {
        private String roomName;
        private List<ScheduleCellDto> talks;
    }
}