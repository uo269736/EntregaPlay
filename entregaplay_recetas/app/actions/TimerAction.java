package actions;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public class TimerAction extends Action<Timed>{

    /**
     * Metodo para medir el tiempo que tarda en ejecutar la llamada
     * @param req request
     * @return tiempo de ejecución de la llamada
     *
     */
    @Override
    public CompletionStage<Result> call(Http.Request req) {
        long start = System.currentTimeMillis();
        CompletionStage<Result> ret = this.delegate.call(req);
        long end = System.currentTimeMillis();
        System.out.println(req.toString() + " in " + (end - start) + " ms");
        return ret;
    }
}
