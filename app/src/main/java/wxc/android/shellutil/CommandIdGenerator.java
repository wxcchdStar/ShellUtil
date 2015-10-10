package wxc.android.shellutil;

import java.util.concurrent.atomic.AtomicInteger;

public final class CommandIdGenerator {

    public static AtomicInteger sId = new AtomicInteger(0);

    public static int getId() {
        return sId.addAndGet(1);
    }
}
