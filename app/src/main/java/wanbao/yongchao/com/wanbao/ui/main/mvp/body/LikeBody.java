package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class LikeBody {

    /**
     * objectId : 1
     * objectType : 2
     * type : 3
     */

    private int objectId;
    private int objectType;
    private int type;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LikeBody(int objectId, int objectType, int type) {
        this.objectId = objectId;
        this.objectType = objectType;
        this.type = type;
    }
}
