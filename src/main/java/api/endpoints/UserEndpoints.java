package api.endpoints;

public final class UserEndpoints {

    private UserEndpoints() {
        throw new UnsupportedOperationException("Cannot instantiate a UserEndpoints.");
    }

    public static String users(){
        return "/api/users";
    }

    public static String userById(int id){
        return "/api/users/"+id;
    }

    public static String usersByPage(int page){
        return "/api/users?page="+page;
    }

}
