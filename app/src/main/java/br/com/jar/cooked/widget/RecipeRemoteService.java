package br.com.jar.cooked.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeRemoteService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeFactory(this.getApplicationContext());
    }

}