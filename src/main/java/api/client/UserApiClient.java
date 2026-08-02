package api.client;

import api.endpoints.UserEndpoints;
import io.restassured.response.Response;

import java.util.Map;

public class UserApiClient extends BaseApiClient {

    public Response getUser(int userid){
        return get(UserEndpoints.userById(userid));
    }

    public Response getUserByPage(int pageNumber){
        return get(UserEndpoints.usersByPage(pageNumber));
    }

    public Response getUsers(){
        return get(UserEndpoints.users());
    }
}
