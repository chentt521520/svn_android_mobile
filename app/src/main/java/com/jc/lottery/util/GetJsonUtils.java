package com.jc.lottery.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author lr
 * @description:
 * @date:${DATA} 10:15
 */

public class GetJsonUtils {

    public String readTextFromSDcard(Context context) {
        InputStreamReader inputStreamReader;
        String resultString = "";
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open("encrypted_state.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            resultString = stringBuilder.toString();
            Log.i("TAG", stringBuilder.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    public String readOrderFromSDcard(Context context) {
        InputStreamReader inputStreamReader;
        String resultString = "";
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open("order_test.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            resultString = stringBuilder.toString();
            Log.i("TAG", stringBuilder.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
