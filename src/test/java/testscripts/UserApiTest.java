package testscripts;

import api.client.UserApiClient;
import api.constants.HttpStatusCodes;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserApiTest {

    @Test
    public void verifyGetUser() {

        UserApiClient client = new UserApiClient();

        Response response = client.getUsers();

        Assert.assertEquals(response.statusCode(), HttpStatusCodes.OK);

        System.out.println(response.asPrettyString());
    }
}
