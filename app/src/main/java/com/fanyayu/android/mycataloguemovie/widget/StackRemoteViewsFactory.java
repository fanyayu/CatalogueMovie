package com.fanyayu.android.mycataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.fanyayu.android.mycataloguemovie.R;
import com.fanyayu.android.mycataloguemovie.entity.FavoriteItems;

import java.util.concurrent.ExecutionException;

import static com.fanyayu.android.mycataloguemovie.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int mAppWidgetId;
    private Cursor listFaveWidget;

    public StackRemoteViewsFactory(Context context, Intent intent){
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        listFaveWidget = mContext.getContentResolver().query(
                CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listFaveWidget.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        FavoriteItems item = getItem(i);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w185"+item.getPosterpath())
                    .apply(new RequestOptions().centerCrop())
                    .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

        remoteViews.setImageViewBitmap(R.id.img_widget, bitmap);

        Bundle bundle = new Bundle();
        bundle.putInt(FaveMovieWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.img_widget, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private FavoriteItems getItem(int position){
        if (!listFaveWidget.moveToPosition(position)) {
            throw  new IllegalStateException("Position invalid");
        }

        return new FavoriteItems(listFaveWidget);
    }
}
