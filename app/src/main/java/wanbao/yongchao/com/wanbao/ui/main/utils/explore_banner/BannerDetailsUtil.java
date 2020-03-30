package wanbao.yongchao.com.wanbao.ui.main.utils.explore_banner;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import wanbao.yongchao.com.wanbao.R;
import wanbao.yongchao.com.wanbao.ui.image.ImageBannerInfo;
import wanbao.yongchao.com.wanbao.ui.image.ImageInfo;
import wanbao.yongchao.com.wanbao.ui.main.utils.banner.BannerUtil;
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad;
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils;

public class BannerDetailsUtil {

        public static void setBanner(ConvenientBanner banner, ArrayList<ImageBannerInfo> list) {

            banner.setPages(new CBViewHolderCreator() {
                @Override
                public Holder createHolder(View itemView) {
                    return new wanbao.yongchao.com.wanbao.ui.main.utils.banner.BannerUtil.LocalImageHolderView(itemView);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_image;
                }
            }, list)
                    .setPageIndicator(new int[]{R.drawable.white_circle, R.drawable.yellow_circle})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .startTurning(4000)
                    .setOnItemClickListener(new OnItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onItemClick(int position) {
                            if (list.get(position).isAddButton()) {
//                            intentUtils.INSTANCE.intentImage(list.get(position).getTitle(),list.get(position).getUrl());
                            } else {
                                ArrayList imagelist = new ArrayList<ImageInfo>();
                                list.forEach(new Consumer<ImageBannerInfo>() {
                                    @Override
                                    public void accept(ImageBannerInfo imageInfo) {
                                        imagelist.add(new ImageInfo(imageInfo.getImageUrl(), imageInfo.isAddButton(), imageInfo.getImageId()));
                                    }
                                });
                                intentUtils.INSTANCE.intentImage(false, imagelist, position);
                            }

                        }
                    })
                    .setCanLoop(true);
        }


        public static class LocalImageHolderView extends Holder<ImageBannerInfo> {
            private ImageView imageView;
//            private ImageView textView;

            public LocalImageHolderView(View itemView) {
                super(itemView);
            }


            @Override
            protected void initView(View itemView) {
                imageView = itemView.findViewById(R.id.itemImage);
//                textView = itemView.findViewById(R.id.textView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override
            public void updateUI(ImageBannerInfo data) {
                //1视频 2图片
//                if (data.isAddButton()) {
//                    textView.setVisibility(View.GONE);
//                } else {
//                    if (data.getImageId() == 1) {
//                        textView.setVisibility(View.VISIBLE);
//                    } else {
//                        textView.setVisibility(View.GONE);
//                    }
//                }

                ImageLoad.INSTANCE.setImage(data.getImageUrl(), imageView);
            }
        }


}
