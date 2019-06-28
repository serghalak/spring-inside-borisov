package quoters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Serg on 20.06.2019.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();
    int max();
}
