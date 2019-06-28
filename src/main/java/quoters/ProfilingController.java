package quoters;

/**
 * Created by Serg on 24.06.2019.
 */
public class ProfilingController implements ProfilingControllerMBean {
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
