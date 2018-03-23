package cn.invonate.ygoa3.Entry;

import java.util.List;

/**
 * Created by liyangyang on 2018/3/20.
 */

public class RequestGroup {
    private String group_name;
    private String group_desc;
    private List<Contacts> members;
    private String group_id;

    public RequestGroup(String group_name, String group_desc, List<Contacts> members, String group_id) {
        this.group_name = group_name;
        this.group_desc = group_desc;
        this.members = members;
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }

    public List<Contacts> getMembers() {
        return members;
    }

    public void setMembers(List<Contacts> members) {
        this.members = members;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
