package die.mass.conference;

import die.mass.conference.dtos.request.*;
import die.mass.conference.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DtoUtils {

    public static final SignInDto ADMIN_SIGN_IN_DTO = SignInDto.builder()
            .username("admin1@gmail.com")
            .password("password")
            .build();

    public static final SignInDto SPEAKER1_SIGN_IN_DTO = SignInDto.builder()
            .username("speaker1@gmail.com")
            .password("password")
            .build();

    public static final SignInDto LISTENER1_SIGN_IN_DTO = SignInDto.builder()
            .username("listener1@gmail.com")
            .password("password")
            .build();

    public static final SignInDto LISTENER1_SIGN_IN_DTO_INCORRECT_LOGIN = SignInDto.builder()
            .username("1listener1@gmail.com")
            .password("password")
            .build();

    public static final SignInDto LISTENER1_SIGN_IN_DTO_INCORRECT_PASSWORD = SignInDto.builder()
            .username("listener1@gmail.com")
            .password("password1")
            .build();

    public static final UserDto LISTENER1_USER_DTO = UserDto.builder().id(4L).build();

    public static final UserDto LISTENER2_USER_DTO = UserDto.builder()
            .id(5L)
            .email("qwe@gmail.com")
            .role(User.Role.ADMIN)
            .build();

    public static final TalkCreationDto TALK_CREATION_DTO = TalkCreationDto.builder()
            .conferenceId(1L)
            .description("Description")
            .title("Title")
            .build();

    public static final TalkTimeDto FIRST_TALK_TIME_DTO = TalkTimeDto.builder()
            .beginTime(LocalDateTime.now().minusMinutes(60))
            .endTime(LocalDateTime.now())
            .roomId(1L)
            .talkId(1L)
            .build();

    public static final TalkTimeDto SECOND_TALK_TIME_DTO = TalkTimeDto.builder()
            .beginTime(LocalDateTime.now().minusMinutes(60))
            .endTime(LocalDateTime.now())
            .roomId(2L)
            .talkId(1L)
            .build();

    public static final TalkUpdatingDto TALK_UPDATING_DTO = TalkUpdatingDto.builder()
            .talkId(1L)
            .title("NewTitle")
            .description("NewDesc")
            .conferenceId(1L)
            .build();

    public static final TalkDeletingDto TALK_DELETING_DTO = TalkDeletingDto.builder()
            .talkId(2L)
            .build();

    public static final ConferenceDto VALID_CONFERENCE_DTO = ConferenceDto.builder().conferenceId(1L).build();
    public static final ConferenceDto INVALID_CONFERENCE_DTO = ConferenceDto.builder().conferenceId(100L).build();
}
