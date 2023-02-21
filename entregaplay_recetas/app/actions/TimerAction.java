package actions;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public class TimerAction extends Action<Timed>{
    @Override
    public CompletionStage<Result> call(Http.Request req) {
        long start = System.currentTimeMillis();
        CompletionStage<Result> ret = this.delegate.call(req);
        long end = System.currentTimeMillis();
        System.out.println(req.toString() + " in " + (end - start) + " ms");
        return ret;
    }
}
