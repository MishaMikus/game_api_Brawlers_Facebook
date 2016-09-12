import model.CollectProductionResponse;
import model.GetGameInfoResponse;
import model.PostRequest;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

class RecourceCollector {
    private static final Logger LOGGER = Logger.getLogger(RecourceCollector.class);

    private HttpConnector httpConnector = new HttpConnector();
    private String sid;
    private PostRequestFabric postRequestFabric;
    void collect() throws Exception {
        postRequestFabric = new PostRequestFabric();
        //game info
        PostRequest get_game_infoPostRequest = postRequestFabric.makePostRequest("GET_GAME_INFO");
        GetGameInfoResponse getGameInfoResponse = new GetGameInfoResponse(httpConnector.post(get_game_infoPostRequest));
        getGameInfoResponse.getBuildingMap();
        LOGGER.info("ItemMap : "+getGameInfoResponse.getItemMap());
        sid = getGameInfoResponse.getSid();

        //collect res
        for (Map.Entry<String, Set<String>> entry : getGameInfoResponse.getBuildingMap().entrySet()) {
            String id = entry.getKey();
            for (String type : entry.getValue()) {
                LOGGER.info("START collect [id : " + id + "] [type : " + type + "]");
                String collectValue = new CollectProductionResponse(httpConnector.post(postRequestFabric.makePostRequest("COLLECT_PRODUCTION", sid, id, type))).getCollectValue();
                LOGGER.info("END collect [id : " + id + "] [type : " + type + "] [collectValue : " + collectValue + "]");
            }
        }

        //make weapon
        executeAction("meteor_buy_action");
        executeAction("heal_buy_action");
        executeAction("horror_buy_action");
        executeAction("shield_buy_action");

        //get rewards
        getRewardAction("iron_league_take_action");
        getRewardAction("wooden_league_take_action");

    }

    private void getRewardAction(String reward) throws Exception {
        executeAction(reward);
        PostRequest get_championship_roomPostRequest = postRequestFabric.makePostRequest("EXECUTE_ACTION_CHAMPIONSHIP",sid);
        LOGGER.info("getRewardAction(" + reward + ") : " + httpConnector.post(get_championship_roomPostRequest));
    }

    private void executeAction(String action) throws Exception {
        PostRequest meteor_buy_actionPostRequest = postRequestFabric.makePostRequest("EXECUTE_ACTION",sid,action);
        LOGGER.info("executeAction(" + action + ") : " + httpConnector.post(meteor_buy_actionPostRequest));
    }
}
