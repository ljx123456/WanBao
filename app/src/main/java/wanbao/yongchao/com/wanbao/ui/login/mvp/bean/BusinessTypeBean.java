package wanbao.yongchao.com.wanbao.ui.login.mvp.bean;

import java.util.List;

public class BusinessTypeBean {
    private String businessTypeName;
    private boolean flag;
    private List<BusinessTypeDetailsBean> list;

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String  businessTypeName) {
        this. businessTypeName =  businessTypeName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<BusinessTypeDetailsBean> getList() {
        return list;
    }

    public void setList(List<BusinessTypeDetailsBean> list) {
        this.list = list;
    }

    public BusinessTypeBean(String businessTypeName, boolean flag) {
        this.businessTypeName = businessTypeName;
        this.flag = flag;
    }

    public BusinessTypeBean(String businessTypeName, boolean flag, List<BusinessTypeDetailsBean> list) {
        this.businessTypeName = businessTypeName;
        this.flag = flag;
        this.list = list;
    }
}
