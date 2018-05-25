package android.test.urlscheme;

import android.compact.utils.IntentCompactUtil;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final TextView content = (TextView) findViewById(R.id.content);
        if (getIntent() != null) {
            String strIntent = IntentCompactUtil.convertIntentToString(getIntent());
            Log.d("PPP", getClass().getSimpleName() + "|strIntent|" + strIntent);
            String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            if (TextUtils.isEmpty(text)) {
                Uri uri = getIntent().getData();
                if (uri != null) {
                    text = uri.getQueryParameter(Intent.EXTRA_TEXT);
                }
            }
            if (!TextUtils.isEmpty(text)) {
                content.setText("调起并接收到StringExtra：" + text);
            }
        }
    }
}
