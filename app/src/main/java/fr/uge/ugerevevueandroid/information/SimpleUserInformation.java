package fr.uge.ugerevevueandroid.information;

import java.util.Set;
import java.util.stream.Collectors;

public class SimpleUserInformation {

    long id;
    String username;
    Set<SimpleUserInformation> followed;
    boolean isAdmin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<SimpleUserInformation> getFollowed() {
        return followed;
    }

    public void setFollowed(Set<SimpleUserInformation> followed) {
        this.followed = followed;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public SimpleUserInformation() {
    }

    public SimpleUserInformation(long id, String username, Set<SimpleUserInformation> followed, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.followed = followed;
        this.isAdmin = isAdmin;
    }

    public Set<String> allFollowedName(){
        return followed.stream().map(user -> {return user.getUsername();}).collect(Collectors.toSet());
    }
}
