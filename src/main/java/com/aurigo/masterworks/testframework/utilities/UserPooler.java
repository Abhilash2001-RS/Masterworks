package com.aurigo.masterworks.testframework.utilities;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.aurigo.masterworks.testframework.utilities.models.User;
import com.aurigo.masterworks.testframework.utilities.models.environment.Build;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import com.aurigo.masterworks.testframework.webUI.constants.enums.Role;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserPooler extends BaseFramework {

    private static List<Build> availableBuilds = currentEnvironment.getBuilds();
    private static HashMap<String, List<User>> userMap =new HashMap<>();
    private static  final ThreadLocal<HashMap<String, List<User>>> usersPooledInMethods = ThreadLocal.withInitial(HashMap::new);
    private static final Logger logger = LogManager.getLogger(UserPooler.class.getName());

    /**
     * Method to initialize the User Pool by de-serializing the Users.json file
     */
    public static void initialize(){
        for (var build : availableBuilds){
            userMap.put(build.getName(), JsonUtil.deSerialize((Class<List<User>>) (new TypeToken<List<User>>() {
            }.getType()),Constants.TEST_CLASSES_FOLDER_PATH + "Users.json"));
        }
    }

    /**
     * Method to fetch a free user from the pool
     *
     * @param method - test method context
     * @param build  - Build for which the user is fetched
     * @param role   - role of the user required
     * @return User
     */

    @Step("Get the Free user from the Pool")
    public static User getUser(Method method, Build build, Role role){
        var usersPooledInThisMethod = usersPooledInMethods.get().get(method.getName());
        var users = userMap.get(build.getName());
        var availableUsers = users.stream().filter(x-> !x.isBusy).collect(Collectors.toList());
        String finalRole = role.getValue();
        var userToAssign = availableUsers.stream().filter(x -> x.roles.contains(finalRole)).findFirst().get();
        users.stream().filter(x -> x.username.equals(userToAssign.username)).findFirst().get().isBusy = true;
        userMap.put(build.getName(), users);
        logger().info("User assigned:'" + userToAssign.username + "' in build:" + build.getUrl() + " with roles " + userToAssign.roles.toString());
        if (usersPooledInThisMethod == null) {
            //First user in this method
            List<User> newList = new ArrayList<>();
            newList.add(userToAssign);
            usersPooledInMethods.get().put(method.getName(), newList);
        } else {
            usersPooledInThisMethod.add(userToAssign);
            usersPooledInMethods.get().put(method.getName(), usersPooledInThisMethod);
        }
        return userToAssign;
    }

    /**
     * Disposes an active user by marking it as free in the pool
     *
     * @param build  - current build
     * @param method - test method context
     */
    public static synchronized void disposeUser(Build build, Method method) {
        var usersPooled = usersPooledInMethods.get().get(method.getName());
        if (usersPooled != null) {
            for (var user : usersPooled) {
                logger().info("User '" + user.username + "' disposed in build:" + build.getUrl());
                var users = userMap.get(build.getName());
                users.stream().filter(x -> x.username.equals(user.username)).findFirst().get().isBusy = false;
                userMap.put(build.getName(), users);
            }
            usersPooledInMethods.get().remove(method.getName());
        } else {
            logger().info("No users were pooled in this method");
        }
    }

}
