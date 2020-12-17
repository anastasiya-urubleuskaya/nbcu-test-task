package epam.com.tests;

import epam.com.model.Post;
import epam.com.utils.JsonUtil;
import io.restassured.response.Response;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreatePostTest extends BaseApiTest{

    @Test
    public void createPost() {

        String body = JsonUtil.getJsonFromObject(new Post.PostBuilder().setTitle("silver").setBody("is my lovely cat").setUserId(13).build());
        Response response = restApiClient.sendRequest(RequestMethod.POST, "/posts", body, null, null, null);

        response.then().assertThat().
                statusCode(201).
                body("title", equalTo("silver")).
                body("body", equalTo("is my lovely cat")).
                body("userId", equalTo(13)).
                body("id", equalTo(101));
    }
}
