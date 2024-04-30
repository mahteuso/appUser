package com.myconpany.service;

import com.myconpany.repository.UserRepository;
import com.myconpany.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        List<User> userList = new ArrayList<>();
        Iterable<User> userIterable = repo.findAll();
        for (User u : userIterable) {
            userList.add(u);
        }
        return userList;
    }

    public User get(Integer id)  throws UserNotFoundException{

        User user =  repo.findById(id).get();
        if (user.isEnable()){
            return user;
        }
        throw new UserNotFoundException("Usuário com id: " + id + " não foi encontrado");
    }

    public User save(User user){
        return repo.save(user);
    }

    public void delete(int id){
        repo.delete(repo.findById(id).get());
    }

}
