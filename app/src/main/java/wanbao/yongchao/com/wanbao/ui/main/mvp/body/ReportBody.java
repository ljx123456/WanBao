package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class ReportBody {

    /**
     * objectType : 1
     * objectId : 1
     * frozenId : 1
     * content : 举报内容
     */

    private String objectType;
    private String objectId;
    private String frozenId;
    private String content;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFrozenId() {
        return frozenId;
    }

    public void setFrozenId(String frozenId) {
        this.frozenId = frozenId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportBody(String objectType, String objectId, String frozenId, String content) {
        this.objectType = objectType;
        this.objectId = objectId;
        this.frozenId = frozenId;
        this.content = content;
    }
}
