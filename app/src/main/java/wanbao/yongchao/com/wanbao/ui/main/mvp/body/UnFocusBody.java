package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class UnFocusBody {

    /**
     * id : 15
     * focusType : 0
     */

    private String id;
    private String focusType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFocusType() {
        return focusType;
    }

    public void setFocusType(String focusType) {
        this.focusType = focusType;
    }

    public UnFocusBody(String id, String focusType) {
        this.id = id;
        this.focusType = focusType;
    }
}
