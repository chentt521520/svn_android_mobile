package com.jc.lottery.zxing.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jc.lottery.R;
import com.mylhyl.zxing.scanner.common.Scanner;

/**
 * 纯文本显示
 */
public class TextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            ((TextView) findViewById(R.id.textView3)).setText(extras.getString(Scanner.Scan.RESULT));
    }

    public static void gotoActivity(Activity activity, Bundle bundle) {
        activity.startActivity(new Intent(activity, TextActivity.class).putExtras(bundle));
    }
}
