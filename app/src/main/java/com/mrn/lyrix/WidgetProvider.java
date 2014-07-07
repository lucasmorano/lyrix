package com.mrn.lyrix;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Created by lucasmorano on 7/7/14.
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = createRemoteViews(context);
        pushWidgetUpdate(context, remoteViews);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }

    public static RemoteViews createRemoteViews(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        remoteViews.setTextViewText(R.id.title, GlobalResource.getTitle());
        remoteViews.setTextViewText(R.id.lyrics, GlobalResource.getLyrics());
        return remoteViews;
    }


}
