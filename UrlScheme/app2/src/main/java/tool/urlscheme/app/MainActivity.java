package tool.urlscheme.app;

import android.app.Activity;
import android.compact.utils.FileCompactUtil;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.limpoxe.fairy.content.PluginDescriptor;
import com.limpoxe.fairy.core.FairyGlobal;
import com.limpoxe.fairy.core.PluginIntentResolver;
import com.limpoxe.fairy.manager.PluginManagerHelper;
import com.limpoxe.fairy.util.CheckUtil;

import java.io.File;

import tools.android.apfmanager.CpPluginTransition;
import tools.android.apfmanager.PluginUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    protected void initData() {
        CpPluginTransition.Companion.get().setFetchUrl("http://45.32.40.65/g/urlscheme");
    }

    protected void initView() {
        final Button request = (Button) findViewById(R.id.btn_request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PluginUtil.Companion.checkAndInstallPlugins(getApplicationContext(), false);
            }
        });
        final CheckBox cp_plugin_switcher = (CheckBox) findViewById(R.id.cp_plugin_switcher);
        final ViewGroup cp_plugin_panel = (ViewGroup) findViewById(R.id.cp_plugin_panel);
        cp_plugin_switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                if (checked) {
                    for (PluginDescriptor pd : PluginManagerHelper.getPlugins()) {
                        final String pluginPackageName = pd.getPackageName();
                        boolean pluginEnable = pd.isEnabled();
                        boolean pluginStandAlone = pd.isStandalone();
                        String pluginVersion = pd.getVersion();
                        String pluginPath = pd.getInstalledPath();
                        String pluginMd5 = FileCompactUtil.getMD5(new File(pluginPath));
                        String desc = pluginPackageName + "|version|" + pluginVersion + "|enable|" + pluginEnable + "|standalone|" + pluginStandAlone + "|path|" + pluginPath + "|md5|" + pluginMd5;
                        final TextView text = new TextView(cp_plugin_switcher.getContext());
                        text.setText(desc);
                        text.setTag(pluginPackageName);
                        text.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                String tag = (String) v.getTag();
                                int code = PluginManagerHelper.remove(tag);
                                Toast.makeText(v.getContext(), PluginUtil.Companion.getPluginErrMsg(v.getContext().getApplicationContext(), code), Toast.LENGTH_LONG).show();
                                cp_plugin_panel.removeView((View) text.getParent());
                                cp_plugin_panel.invalidate();
                                return true;
                            }
                        });

                        final Button btn1 = new Button(cp_plugin_switcher.getContext());
                        btn1.setText("StartActivity");
                        btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentFilter iff;
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setPackage(pluginPackageName);
                                Uri uri = Uri.parse("usfg://urlscheme/start?android.intent.extra.TEXT=T01022184");
                                intent.setData(uri);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                if (CheckUtil.checkBeforeStartIntent(v.getContext(), intent, pluginPackageName, pluginPackageName + ".StartActivity")) {
                                    if (FairyGlobal.hasPluginFilter() && FairyGlobal.filterPlugin(intent)) {
                                        PluginIntentResolver.resolveActivity(intent);
                                    }
                                    startActivity(intent);
                                }
                            }
                        });
                        final Button btn2 = new Button(cp_plugin_switcher.getContext());
                        btn2.setText("SettingActivity");
                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setPackage(pluginPackageName);
                                intent.setData(Uri.parse("usfg://urlscheme/setting?android.intent.extra.TEXT=T01022185"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                if (CheckUtil.checkBeforeStartIntent(v.getContext(), intent, pluginPackageName, pluginPackageName + ".SettingActivity")) {
                                    if (FairyGlobal.hasPluginFilter() && FairyGlobal.filterPlugin(intent)) {
                                        PluginIntentResolver.resolveActivity(intent);
                                    }
                                    startActivity(intent);
                                }
                            }
                        });
                        final Button btn3 = new Button(cp_plugin_switcher.getContext());
                        btn3.setText("APIActivity");
                        btn3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setPackage(pluginPackageName);
                                intent.setData(Uri.parse("usfg://urlscheme/api?android.intent.extra.TEXT=T01022186"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                if (CheckUtil.checkBeforeStartIntent(v.getContext(), intent, pluginPackageName, pluginPackageName + ".APIActivity")) {
                                    if (FairyGlobal.hasPluginFilter() && FairyGlobal.filterPlugin(intent)) {
                                        PluginIntentResolver.resolveActivity(intent);
                                    }
                                    startActivity(intent);
                                }
                            }
                        });

                        LinearLayout cont = new LinearLayout(cp_plugin_switcher.getContext());
                        cont.setOrientation(LinearLayout.HORIZONTAL);
                        cont.setGravity(Gravity.CENTER_VERTICAL);
                        LinearLayout.LayoutParams textPl = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                        cont.addView(text, textPl);
                        LinearLayout buttonsLayout = new LinearLayout(cp_plugin_switcher.getContext());
                        buttonsLayout.setOrientation(LinearLayout.VERTICAL);
                        buttonsLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                        buttonsLayout.addView(btn1);
                        buttonsLayout.addView(btn2);
                        buttonsLayout.addView(btn3);
                        LinearLayout.LayoutParams btnPl = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f);
                        cont.addView(buttonsLayout, btnPl);
                        cp_plugin_panel.addView(cont);
                    }
                    cp_plugin_panel.invalidate();
                } else {
                    cp_plugin_panel.removeAllViews();
                    cp_plugin_panel.invalidate();
                }
            }
        });
    }
}
