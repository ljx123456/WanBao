package wanbao.yongchao.com.wanbao.ui.login.utils.city;

import java.util.ArrayList;

public class AreaModel {
    private String key;
    private ArrayList<CitiesBean.CityBean.AreaBean> list;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<CitiesBean.CityBean.AreaBean> getList() {
        return list;
    }

    public void setList(ArrayList<CitiesBean.CityBean.AreaBean> list) {
        this.list = list;
    }

    public AreaModel() {
    }

    public AreaModel(String key, ArrayList<CitiesBean.CityBean.AreaBean> list) {
        this.key = key;
        this.list = list;
    }
}
