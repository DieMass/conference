package die.mass.conference.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Response<T> {

    private T result;
    private Error error;

    public Response(T result) {
        setResult(result);
    }

    public Response(Error error) {
        setError(error);
    }

    public static Response<String> ok() {
        return new Response<>(HttpStatus.OK.name());
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Error {

        private String message;
        private Type type;

        @Getter
        @AllArgsConstructor
        public enum Type {
            CONFERENCE_ACCESS_DENIED("access-to-conference-denied"),
            TALK_TIME_ALREADY_USED("time-already-used"),
            INVALID_LOGIN_DATA("invalid-login-data"),
            ENTITY_NOT_FOUND("entity-not-found")

            ;

            private final String type;
        }
    }
}