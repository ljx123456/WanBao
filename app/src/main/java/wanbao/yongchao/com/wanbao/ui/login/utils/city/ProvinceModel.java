package wanbao.yongchao.com.wanbao.ui.login.utils.city;

import java.util.ArrayList;

public class ProvinceModel {
    private String key;
    private ArrayList<CitiesBean> list;

    public ProvinceModel(String key, ArrayList<CitiesBean> list) {
        this.key = key;
        this.list = list;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<CitiesBean> getList() {
        return list;
    }

    public void setList(ArrayList<CitiesBean> list) {
        this.list = list;
    }

    public ProvinceModel() {
    }
}
