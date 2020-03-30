package wanbao.yongchao.com.wanbao.ui.login.mvp.bean;

public class BusinessTypeDetailsBean {
    private String name;
    private int name_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getName_id() {
        return name_id;
    }

    public void setName_id(int name_id) {
        this.name_id = name_id;
    }

    public BusinessTypeDetailsBean(String name, int name_id) {
        this.name = name;
        this.name_id = name_id;
    }
}
