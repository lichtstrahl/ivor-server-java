package root.id.service;

import root.id.db.DBContentLoader;

public class EvaluationService {
    public static boolean evaluation(String request, long id, int eval) {
        if (request.equals("communication")) {
            return DBContentLoader.getInstance().evaluationCommunication(id, eval);
        }
        if (request.equals("communication_key")) {
            return DBContentLoader.getInstance().evaluationCommunicationKey(id, eval);
        }
        return false;
    }
}
