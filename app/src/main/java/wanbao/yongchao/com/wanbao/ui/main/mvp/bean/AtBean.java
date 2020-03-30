package wanbao.yongchao.com.wanbao.ui.main.mvp.bean;

public class AtBean {

    private String name;
    private String avatar;
    private boolean flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public AtBean(String name, String avatar, boolean flag) {
        this.name = name;
        this.avatar = avatar;
        this.flag = flag;
    }

    public AtBean() {
    }

    @Override
    public String toString() {
        return "AtBean{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", flag=" + flag +
                '}';
    }
}
