package wanbao.yongchao.com.wanbao.ui.main.utils.explore_banner;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import wanbao.yongchao.com.wanbao.R;
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo;
import wanbao.yongchao.com.wanbao.ui.image.ImageExploreInfo;
import wanbao.yongchao.com.wanbao.ui.image.ImageInfo;
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad;
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils;

public class BannerUtil {

    public static void setBanner(ConvenientBanner banner, ArrayList<ImageExploreInfo> list) {

        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new LocalImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_explore_image;
            }
        }, list)
                .setPageIndicator(new int[]{R.drawable.white_circle, R.drawable.yellow_circle})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(4000)
                .setOnItemClickListener(new OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onItemClick(int position) {
//                        if (list.get(position).isAddButton()) {
////                            intentUtils.INSTANCE.intentImage(list.get(position).getTitle(),list.get(position).getUrl());
//                        } else {
//                            ArrayList imagelist = new ArrayList<ImageInfo>();
//                            list.forEach(new Consumer<ImageExploreInfo>() {
//                                @Override
//                                public void accept(ImageExploreInfo imageInfo) {
//                                    imagelist.add(new ImageInfo(imageInfo.getImageUrl(), imageInfo.isAddButton(), imageInfo.getImageId()));
//                                }
//                            });
//                            intentUtils.INSTANCE.intentImage(false, imagelist, position);
//                        }
                        if (list.get(position).getType()==1){
                            intentUtils.INSTANCE.intentWebBanner(list.get(position).getId()+"");
                        }else  if (list.get(position).getType()==2){
                            intentUtils.INSTANCE.intentActivitiesDetails(list.get(position).getObjectId());
                        }else {
                            intentUtils.INSTANCE.intentBusinessHomePage(list.get(position).getObjectId().toString());
                        }

                    }
                })
                .setCanLoop(true);
    }


    public static class LocalImageHolderView extends Holder<ImageExploreInfo> {
        private RoundedImageView imageView;


        public LocalImageHolderView(View itemView) {
            super(itemView);
        }


        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.itemImage);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void updateUI(ImageExploreInfo data) {
            //1视频 2图片
//            if (data.isAddButton()) {
//                textView.setVisibility(View.GONE);
//            } else {
//                if (data.getImageId() == 1) {
//                    textView.setVisibility(View.VISIBLE);
//                } else {
//                    textView.setVisibility(View.GONE);
//                }
//            }

            ImageLoad.INSTANCE.setImage(data.getImageUrl(), imageView);
        }
    }
}
