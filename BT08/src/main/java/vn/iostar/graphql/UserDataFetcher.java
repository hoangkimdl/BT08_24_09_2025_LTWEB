package vn.iostar.graphql;

import java.util.List;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import vn.iostar.entity.User;
import vn.iostar.service.UserService;

@DgsComponent
public class UserDataFetcher {

    private final UserService userService;

    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @DgsQuery
    public List<User> users() {
        return userService.findAll();
    }

    @DgsQuery
    public User user(@InputArgument Long id) {
        return userService.findById(id).orElse(null);
    }

    @DgsMutation
    public User addUser(@InputArgument String fullname,
                        @InputArgument String email,
                        @InputArgument String password,
                        @InputArgument String phone) {
        User u = new User();
        u.setFullname(fullname);
        u.setEmail(email);
        u.setPassword(password);
        u.setPhone(phone);
        return userService.save(u);
    }

    @DgsMutation
    public User updateUser(@InputArgument Long id,
                           @InputArgument String fullname,
                           @InputArgument String email,
                           @InputArgument String password,
                           @InputArgument String phone) {
        return userService.findById(id).map(u -> {
            if (fullname != null) u.setFullname(fullname);
            if (email != null) u.setEmail(email);
            if (password != null) u.setPassword(password);
            if (phone != null) u.setPhone(phone);
            return userService.save(u);
        }).orElse(null);
    }

    @DgsMutation
    public Boolean deleteUser(@InputArgument Long id) {
        return userService.delete(id);
    }
}
