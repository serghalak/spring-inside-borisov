package quoters;

import javax.annotation.PostConstruct;

/**
 * Created by Serg on 20.06.2019.
 */
@Profiling
public class TerminatorQuoter implements Quoter {
    @InjectRandomInt(min=2,max=7)
    private int repeat;
    private String message;

    public TerminatorQuoter() {
        System.out.println("constructor 1   --  " + repeat);
    }

    @PostConstruct
    public void init(){
        System.out.println("init 1 ----   " + repeat);
    }

    public void sayQuote() {
        for (int i = 0; i <repeat ; i++) {
            System.out.println("message " + message);
        }


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
