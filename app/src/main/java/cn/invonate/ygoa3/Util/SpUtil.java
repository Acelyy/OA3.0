package cn.invonate.ygoa3.Util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liyangyang on 2017/5/22.
 */

public class SpUtil {
    private static final String FIRST = "first";
    private static final String USER_SHARED = "user_shared";
    private static final String APP_NAME = "oa";

    /**
     * 读取用户名
     *
     * @param context
     * @return
     */
    public static String getName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SHARED, MODE_PRIVATE);
        return sp.getString("userName", "");
    }

    /**
     * 读取用户名
     *
     * @param context
     * @return
     */
    public static String getPass(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SHARED, MODE_PRIVATE);
        return sp.getString("password", "");
    }

    /**
     * 保存用户名
     *
     * @param context
     * @param name
     */
    public static void setName(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences(USER_SHARED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userName", name);
        editor.apply();
    }

    /**
     * 保存密码
     *
     * @param context
     * @param pass
     */
    public static void setPass(Context context, String pass) {
        SharedPreferences sp = context.getSharedPreferences(USER_SHARED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", pass);
        editor.apply();
    }

    /**
     * 获取是否第一次登陆
     *
     * @param context
     * @return
     */
    public static boolean getFirst(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FIRST, MODE_PRIVATE);
        return sp.getBoolean("first", true);
    }

    /**
     * 设置是否第一次
     *
     * @param context
     * @param is
     */
    public static void setFirst(Context context, boolean is) {
        SharedPreferences sp = context.getSharedPreferences(FIRST, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("first", is);
        editor.apply();
    }


    public static String readString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static void writeString(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static void remove(Context context, String key) {
        getSharedPreferences(context).edit().remove(key).apply();
    }

    public static void removeAll(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

}
