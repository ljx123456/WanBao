package wanbao.yongchao.com.wanbao.ui.release.mvp.body;

import java.util.List;

public class ReleaseCommunityBody {

    /**
     * content : @张三 &h;@lisi &h;@fjdsk &h;@nn &h;@fz &h;
     * video :
     * images : ["http://","http://"]
     * topicId : 1
     * locationId : B001786RF6
     * longitude : 66.11324564
     * latitude : 45.52132
     */

    private String content;
    private String video;
    private String topicId;
    private String locationId;
    private String longitude;
    private String latitude;
    private List<String> images;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


}
