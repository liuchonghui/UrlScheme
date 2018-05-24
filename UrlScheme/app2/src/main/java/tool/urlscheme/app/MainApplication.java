package tool.urlscheme.app;

import android.app.Application;
import android.content.Context;

import com.limpoxe.fairy.core.FairyGlobal;
import com.limpoxe.fairy.core.PluginFilter;

import tools.android.apfmanager.PluginUtil;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (PluginUtil.Companion.isPluginProcess(this)) {
            return;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (!FairyGlobal.hasPluginFilter()) {
            FairyGlobal.setPluginFilter(new PluginFilter() {
                @Override
                public boolean accept(String packageName) {
                    return packageName.contains("urlscheme");
                }
            });
        }
        PluginUtil.Companion.applicationAttachBaseContext(this, base);
    }

    @Override
    public Context getBaseContext() {
        if (!FairyGlobal.hasPluginFilter()) {
            FairyGlobal.setPluginFilter(new PluginFilter() {
                @Override
                public boolean accept(String packageName) {
                    return packageName.contains("urlscheme");
                }
            });
        }
        return PluginUtil.Companion.applicationGetBaseContext(this, super.getBaseContext());
    }
}
