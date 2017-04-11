package me.jamc.skeleton.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.jamc.skeleton.controller.response.AppResponse;
import me.jamc.skeleton.controller.response.ErrorResponse;
import me.jamc.skeleton.controller.response.ReturnResponse;
import me.jamc.skeleton.model.User;
import me.jamc.skeleton.service.UserService;
import me.jamc.skeleton.utils.MD5Utils;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService service;

    public boolean getVerify(String TIMESTAMP, HttpServletRequest request) {
        if(null == TIMESTAMP)
            return false;
        String URL = request.getRequestURI();
        String METHOD = request.getMethod();
        String TIMESTAMP1 = request.getHeader("TIMESTAMP");
        String md5 = MD5Utils.md5(URL + METHOD + TIMESTAMP1);
        return md5 == TIMESTAMP;
    }

    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResponse addUser(String TIMESTAMP, HttpServletRequest request,
            @PathVariable String firstName, @PathVariable String lastName) {
       /* boolean verify = this.getVerify(TIMESTAMP, request);
        if (!verify) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setSuccess(false);
            errorResponse.setErrorCode("1000");
            errorResponse.setErrorMsg("Failed on signature check");
            return errorResponse;
        }
*/        AppResponse r = new AppResponse();
        if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            r.setSuccess(false);
            return r;
        }
        User user = service.addUser(firstName, lastName);
        r.setSuccess(user != null);
        r.setBody("user", user);

        return r;
    }

    @RequestMapping(value = "/{id}/{firstName}/{lastName}", method = RequestMethod.PUT)
    @ResponseBody
    public ReturnResponse updateUser(String TIMESTAMP, HttpServletRequest request, @PathVariable int id,
            @PathVariable String firstName, @PathVariable String lastName) {
        /*boolean verify = this.getVerify(TIMESTAMP, request);
        if (!verify) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setSuccess(false);
            errorResponse.setErrorCode("1000");
            errorResponse.setErrorMsg("Failed on signature check");
            return errorResponse;
        }
*/        AppResponse r = new AppResponse();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            r.setSuccess(false);
            return r;
        }

        User user = service.updateUser(id, firstName, lastName);
        r.setSuccess(user != null);
        r.setBody("user", user);
        return r;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnResponse getUser(String TIMESTAMP, HttpServletRequest request, @PathVariable int id) {
        /*boolean verify = this.getVerify(TIMESTAMP, request);
        if (!verify) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setSuccess(false);
            errorResponse.setErrorCode("1000");
            errorResponse.setErrorMsg("Failed on signature check");
            return errorResponse;
        }
*/        AppResponse r = new AppResponse();
        if (StringUtils.isEmpty(id)) {
            r.setSuccess(false);
            return r;
        }

        User user = service.getUser(id);
        r.setSuccess(user != null);
        r.setBody("user", user);
        return r;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnResponse deleteUser(String TIMESTAMP, HttpServletRequest request, @PathVariable int id) {
       /* boolean verify = this.getVerify(TIMESTAMP, request);
        if (!verify) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setSuccess(false);
            errorResponse.setErrorCode("1000");
            errorResponse.setErrorMsg("Failed on signature check");
            return errorResponse;
        }*/
        AppResponse r = new AppResponse();
        if (StringUtils.isEmpty(id)) {
            r.setSuccess(false);
            return r;
        }

        User user = service.deleteUser(id);
        r.setSuccess(user != null);
        r.setBody("user", user);
        return r;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ReturnResponse getUsers(String TIMESTAMP, HttpServletRequest request) {
       boolean verify = this.getVerify(TIMESTAMP, request);
        if (!verify) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setSuccess(false);
            errorResponse.setErrorCode("1000");
            errorResponse.setErrorMsg("Failed on signature check");
            return errorResponse;
        }
        AppResponse r = new AppResponse();
        List<User> list = service.getAllUsers();
        if (list == null) {
            r.setSuccess(false);
            return r;
        }
        r.setSuccess(true);
        r.setBody("list", list);
        return r;
    }

}
