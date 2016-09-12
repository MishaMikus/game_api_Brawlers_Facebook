import model.PostRequest;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class PostRequestFabric {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    //COMMON
    private static final String AUTH_KEY = "49cfaafa260cc2584fc9f8ca380d7b30";
    private static final String UID = "fb:1398167796865915";
    private static final String BASE_URL = "https://game-r06ww.rjgplay.com";
    private static final String BASE_URL_COMMAND = BASE_URL + "/command";

    //GET_GAME_INFO
    private static final String GET_GAME_INFO_REQUEST_URL = BASE_URL_COMMAND + "/get_game_info";
    private static final String GET_GAME_INFO_REQUEST_BODY = "<get_game_info uid=\"" + UID + "\" auth_key=\"" + AUTH_KEY + "\"/>";
    private static final Map<String, String> GET_GAME_INFO_REQUEST_HEADER_MAP = new HashMap<String, String>() {{
        put("Referer", BASE_URL_COMMAND + "/dungeons-game.swf");
    }};

    // COLLECT_PRODUCTION
    private static final String COLLECT_PRODUCTION_REQUEST_URL = BASE_URL_COMMAND + "/collect_production";
    private static final String COLLECT_PRODUCTION_REQUEST_BODY = "<collect_production UID=\"" + UID + "\" AUTH_KEY=\"" + AUTH_KEY + "\" sid=\"%s\"><id>%s</id><type>%s</type></collect_production>";

    //EXECUTE_ACTION_CHAMPIONSHIP
    private static final String EXECUTE_ACTION_CHAMPIONSHIP_REQUEST_URL = BASE_URL_COMMAND + "/execute_action";
    private static final String EXECUTE_ACTION_CHAMPIONSHIP_REQUEST_BODY = "<get_championship_room UID=\"" + UID + "\" " + "AUTH_KEY=\"" + AUTH_KEY + "\" sid=\"%s\"/>";

    //EXECUTE_ACTION
    private static final String EXECUTE_ACTION_REQUEST_URL = BASE_URL_COMMAND + "/execute_action";
    private static final String EXECUTE_ACTION_REQUEST_BODY = "<execute_action UID=\"" + UID + "\" AUTH_KEY=\"" + AUTH_KEY + "\" sid=\"%s\"> <type>%s</type> </execute_action>";

    PostRequest makePostRequest(String requestType, String... args) {
        LOGGER.info("START makePostRequest(" + requestType + ", " + Arrays.asList(args) + ")");
        PostRequest res = null;
        switch (requestType) {
            case "GET_GAME_INFO": {
                res = new PostRequest(GET_GAME_INFO_REQUEST_URL, GET_GAME_INFO_REQUEST_BODY, GET_GAME_INFO_REQUEST_HEADER_MAP);
                break;
            }
            case "COLLECT_PRODUCTION": {
                int expectedArgLength = new StringArgCounter(COLLECT_PRODUCTION_REQUEST_BODY).length();
                if (args.length != expectedArgLength) {
                    warnLengthLog(requestType, expectedArgLength, args);
                    break;
                }
                res = makeRequest(COLLECT_PRODUCTION_REQUEST_URL, COLLECT_PRODUCTION_REQUEST_BODY, args);
                break;
            }
            case "EXECUTE_ACTION_CHAMPIONSHIP": {
                int expectedArgLength = new StringArgCounter(EXECUTE_ACTION_CHAMPIONSHIP_REQUEST_BODY).length();
                if (args.length != expectedArgLength) {
                    warnLengthLog(requestType, expectedArgLength, args);
                    break;
                }
                res = makeRequest(EXECUTE_ACTION_CHAMPIONSHIP_REQUEST_URL, EXECUTE_ACTION_CHAMPIONSHIP_REQUEST_BODY, args);
                break;
            }
            case "EXECUTE_ACTION": {
                int expectedArgLength = new StringArgCounter(EXECUTE_ACTION_REQUEST_BODY).length();
                if (args.length != expectedArgLength) {
                    warnLengthLog(requestType, expectedArgLength, args);
                    break;
                }
                res = makeRequest(EXECUTE_ACTION_REQUEST_URL, EXECUTE_ACTION_REQUEST_BODY, args);
                break;
            }
        }
        LOGGER.info("END makePostRequest() : "+res);
        return res;
    }

    private PostRequest makeRequest(String url, String body, String[] args) {
        return new PostRequest(url, new Formatter().format(body, args).toString());
    }

    private void warnLengthLog(String requestType, int expectedArgLength, String[] args) {
        LOGGER.warn("build '" + requestType + "' WARN : Invalid args. Expect args.length==" + expectedArgLength + ". Actual : " + Arrays.asList(args));
    }

}
