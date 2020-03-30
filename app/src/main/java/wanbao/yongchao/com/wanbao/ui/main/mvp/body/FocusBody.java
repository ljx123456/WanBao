package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class FocusBody {

    /**
     * id : 11
     * focusType : 1
     * sourceId : 1
     * sourceType : 2
     */

    private String id;
    private String focusType;
    private String sourceId;
    private String sourceType;

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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public FocusBody(String id, String focusType, String sourceId, String sourceType) {
        this.id = id;
        this.focusType = focusType;
        this.sourceId = sourceId;
        this.sourceType = sourceType;
    }

    public FocusBody(String id, String focusType) {
        this.id = id;
        this.focusType = focusType;
    }
}
