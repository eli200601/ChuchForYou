package chuch.chuchforyou.DB;

import java.io.Serializable;

import chuch.chuchforyou.Info.UserStatus;

/**
 * Created by eli200601 on 30/06/2017.
 */

public class CrawlingList implements Serializable {

    private String user_name;
    private UserStatus user_status;

    private CrawlingList() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public CrawlingList(String user_name, UserStatus user_status) {
        this.user_name = user_name;
        this.user_status = user_status;
    }

    public CrawlingList(String user_name) {
        this.user_name = user_name;
        this.user_status = UserStatus.UN_CRAWL;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public UserStatus getUser_status() {
        return user_status;
    }

    public void setUser_status(UserStatus user_status) {
        this.user_status = user_status;
    }
}
