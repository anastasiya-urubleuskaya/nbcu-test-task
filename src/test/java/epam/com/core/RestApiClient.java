package epam.com.core;

import epam.com.tests.BaseApiTest;
import epam.com.utils.PropertyUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Nonnull;
import java.util.Map;

public class RestApiClient {

    private static final Logger logger = LogManager.getLogger(BaseApiTest.class);
    private static final String BASE_PATH = PropertyUtil.getProperty("base_url");

    public Response sendRequest(@Nonnull RequestMethod method, @Nonnull String apiEndpoint, String body, Map<String, String> headers, Map<String, String> queryParams, Map<String, String> pathParams) {

        RestAssured.baseURI = BASE_PATH;
        RequestSpecification request = RestAssured.given().contentType(ContentType.JSON);
        StringBuilder logMessage = new StringBuilder();
        logMessage.append(String.format("Request info:\nSend %s request:%s", method, BASE_PATH + apiEndpoint));

        if (headers != null) {
            request.headers(headers);
            logMessage.append("\nHeaders:" + headers);
        }
        if (queryParams != null) {
            request.queryParams(queryParams);
            logMessage.append("\nQueryParams:" + queryParams);
        }
        if (pathParams != null) {
            request.pathParams(pathParams);
            logMessage.append("\nPathParams:" + pathParams);
        }
        if (body != null) {
            request.body(body);
            logMessage.append("\nBody:" + body);
        }

        logger.info(logMessage);

        Response response;
        switch (method) {
            case GET:
                response = request.get(apiEndpoint);
                break;
            case POST:
                response = request.post(apiEndpoint);
                break;
            case PUT:
                response = request.put(apiEndpoint);
                break;
            case PATCH:
                response = request.patch(apiEndpoint);
                break;
            case DELETE:
                response = request.delete(apiEndpoint);
                break;
            default:
                throw new RuntimeException(String.format("Unsupported request type: '%s'", method.toString()));
        }

        logger.info("Response info:\nStatus: " + response.getStatusCode() + "\nBody: \n" + response.getBody().asString());

        return response;
    }
}
