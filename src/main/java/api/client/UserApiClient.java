package api.client;

import api.endpoints.UserEndpoints;
import io.restassured.response.Response;

import java.util.Map;

public class UserApiClient extends BaseApiClient {

    @Override
    protected Response get(String endpoint, Map<String, ?> queryParams) {
        return null;
    }

    @Override
    protected Response get(String endpoint, Map<String, ?> queryParams, Map<String, ?> headers) {
        return null;
    }

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
