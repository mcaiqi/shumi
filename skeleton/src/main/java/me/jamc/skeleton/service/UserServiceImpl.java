package me.jamc.skeleton.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;

import me.jamc.skeleton.dao.UserRepository;
import me.jamc.skeleton.model.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    /*
     * 当添加一个新用户时，active字段应该设置为true，update_time应该为当前时间
     * */
    public User addUser(String firstName, String lastName) {
        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setActive(true);
        u.setUpdateTime(new Date());
        userRepo.insert(u);
        return u;
    }

    public User updateUser(int id, String firstName, String lastName) {
        User u = userRepo.selectByPrimaryKey(id);
        if (null != u) {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setUpdateTime(new Date());
            userRepo.updateByPrimaryKeySelective(u);
            return u;
        }
        return null;
    }

    public User getUser(int id) {
        return userRepo.selectByPrimaryKey(id);
    }

    public User deleteUser(int id) {
        User u = userRepo.selectByPrimaryKey(id);
        if (null != u) {
        	u.setActive(false);
        	u.setUpdateTime(new Date());
            userRepo.updateByPrimaryKeySelective(u);
            return u;
        }
        return null;
    }

    public List<User> getAllUsers() {
    	//userRepo.findAll(new org.springframework.data.jpa.domain.Specification() {});
    	/*List<User> list = userRepo.findAll(new Specifications<User>(){
    		 public Predicate toPredicate(Root<User> root,CriteriaQuery<?> query, CriteriaBuilder cb) {  
    			    Path<String> namePath = root.get("ACTIVE");  
    			    *//** 
    			         * 连接查询条件, 不定参数，可以连接0..N个查询条件 
    			         *//*  
    			    query.where(cb.equal(namePath, true)); //这里可以设置任意条查询条件  
    			      
    			    return null;  
    		 }  
    			     
    	});  */  
        Example example = new Example(User.class);  
        example.createCriteria().andEqualTo("active", true); 
        List<User> list = userRepo.selectByExample(example);
       // System.out.println(list.size());
        return list;
    }
    

}

