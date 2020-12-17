package epam.com.tests;

import io.restassured.response.Response;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeletePostTest extends BaseApiTest{

    @Parameters({"postId"})
    @Test
    public void deletePost(int postId) {

        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("id", String.valueOf(postId));

        Response response = restApiClient.sendRequest(RequestMethod.DELETE, "/posts/{id}", null, null, null, pathParams);
        response.then().assertThat().statusCode(200);
        assertThat(response.asString(), equalTo("{}"));
    }
}
