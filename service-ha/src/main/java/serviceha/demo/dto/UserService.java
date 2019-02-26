package serviceha.demo.dto;


import serviceha.demo.entity.User;

public interface UserService {
    public User create(String username, String password);
}
