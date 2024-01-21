package AI;

import java.util.logging.Level;
import java.util.logging.Logger;

import jep.Jep;
import jep.JepException;
import jep.SharedInterpreter;

public class Model {
    private static final Logger LOGGER = Logger.getLogger(Model.class.getName());
    private static Jep jep;

    public Model() {
        try {
            jep = new SharedInterpreter();
            jep.runScript("app/src/main/python/ML/PredictHumanLikeMove.py");
            jep.eval("phlm = PredictHumanLikeMove()");
        } catch (JepException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public Double predict() {
        try {
            jep.eval("result = phlm.predict().tolist()");
            Object result = jep.getValue("result");

            //System.out.println(result + " " + result.getClass());
            
            return (Double) result;
        } catch (JepException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
}