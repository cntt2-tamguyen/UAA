package vn.edu.leading.uaa.services;

import vn.edu.leading.uaa.models.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> findAll();

    List<UserModel> search(String term);

    UserModel findById(Long id);

    boolean update(UserModel user);

    void save(UserModel user);

    boolean delete(Long id);
}
