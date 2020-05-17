package dslabs.pingpong;

import dslabs.framework.Timer;
import dslabs.pingpong.PingApplication.Ping;
import lombok.Data;

@Data
final class PingTimer implements Timer {
    static final int RETRY_MILLIS = 1000;
    private final Ping ping;
}
