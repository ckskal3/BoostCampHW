package org.chokh.boostcamphw;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MyApplication extends Application {
    private  static MyApplication myApplication;
    AppCompatDialog dialogProgress;

    public static MyApplication newInstance(){
        return  myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

    }


    public void onDialogShow(Activity activity){
        if(dialogProgress == null || !dialogProgress.isShowing() ){
            dialogProgress = new AppCompatDialog(activity);
            dialogProgress.setCancelable(false);
            dialogProgress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogProgress.setContentView(R.layout.dialog_progress);
            dialogProgress.show();
        }

        TextView loadingMessageTv  = dialogProgress.findViewById(R.id.loading_message_tv);
        ImageView loadingIconIv = dialogProgress.findViewById(R.id.loading_icon_iv);
        Animation rotateLoadingIv = AnimationUtils.loadAnimation(dialogProgress.getContext(), R.anim.rotate);


        assert loadingMessageTv != null;
        loadingMessageTv.setText(getString(R.string.waiting_plz_kr));
        loadingIconIv.startAnimation(rotateLoadingIv);

    }

    public void onDialogHide(){
        if(dialogProgress !=null && dialogProgress.isShowing()){
            dialogProgress.dismiss();
        }
    }
}
