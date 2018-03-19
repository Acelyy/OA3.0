package cn.invonate.ygoa3.httpUtil;

import android.content.Context;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liyangyang on 2017/3/22.
 */

public class HttpUtil {
    //public static final String BASE_URL = "http://esale.yong-gang.com/";
    //private static final String BASE_URL = "http://192.168.202.180:8000/";
    //public static final String BASE_URL = "http://172.20.1.17:8000";
    //private static final String BASE_URL = "http://192.168.2.1/";
    //public static final String BASE_URL = "http://192.168.3.97:8080";
    // public static final String BASE_URL = "http://192.168.1.6";
//    public static final String BASE_URL = "http://192.168.1.101:8080";
    public static final String BASE_URL = "http://ygoa.yong-gang.cn";
    //public static final String BASE_URL = "http://172.23.134.14:8080";
    public static final String URL_FILE = "http://ygoa.yong-gang.cn/ygoa/upload/";

    private HttpService httpService;

    private static HttpUtil INSTANCE;

    private HttpUtil(Context context, boolean isSaveCookie) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context,isSaveCookie))
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    private OkHttpClient getOkHttpClient(Context context, boolean isSaveCookie) {
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BASIC;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("lyy","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp

        int DEFAULT_TIMEOUT = 5;

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);//设置超时时间;
        if (isSaveCookie) {
            httpClientBuilder.interceptors().add(new ReceivedCookiesInterceptor(context));
        }
        if (!isSaveCookie) {
            httpClientBuilder.interceptors().add(new AddCookiesInterceptor(context));
        }

        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }

    public static HttpUtil getInstance(Context context, boolean isSaveCookie) {
//        if (INSTANCE == null) {
        INSTANCE = new HttpUtil(context, isSaveCookie);
//        }
        return INSTANCE;
    }

    /**
     * 用来统一处理Http的flag,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
//    public static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
//
//        @Override
//        public T call(HttpResult<T> httpResult) {
//            Log.i("result", httpResult.toString());
//            if (httpResult.getResultCode() == 1) {
//                String msg = httpResult.getResultMsg();
//                if (msg == null) {
//                    msg = "msg为空";
//                }
//                throw new RuntimeException(msg);
//            }
//            return httpResult.getData();
//        }
//    }

    /**
     * 统一配置观察者
     *
     * @param o
     * @param <T>
     */
    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * @param subscriber 观察者监听
     * @param userName   用户名
     * @param password   密码
     */
    public void login(Subscriber subscriber, String userName, String password) {
        Observable observable = httpService.login(userName, password);
        toSubscribe(observable, subscriber);
    }

    /**
     * 通讯录查询部门
     *
     * @param subscriber
     * @param id
     */
    public void getDepartment(Subscriber subscriber, String id) {
        Observable observable = httpService.getDepartment(id);
        toSubscribe(observable, subscriber);
    }

    /**
     * 通讯录部门成员
     *
     * @param subscriber
     * @param id
     */
    public void getContacts(Subscriber subscriber, String id) {
        Observable observable = httpService.getContacts(id);
        toSubscribe(observable, subscriber);
    }

    /**
     * 模糊搜索员工信息
     *
     * @param subscriber
     * @param keyword
     * @param page
     * @param row
     */
    public void getMembers(Subscriber subscriber, String keyword, int page, int row) {
        Observable observable = httpService.getMembers(keyword, page, row);
        toSubscribe(observable, subscriber);
    }

    /**
     * 初始化密码
     *
     * @param subscriber
     * @param code
     * @param identify
     */
    public void initPass(Subscriber subscriber, String code, String identify) {
        Observable observable = httpService.initPass(code, identify);
        toSubscribe(observable, subscriber);
    }

    /**
     * 修改密码
     *
     * @param subscriber
     * @param oldPassword_
     * @param password
     * @param code
     */
    public void changePass(Subscriber subscriber, String oldPassword_, String password, String code) {
        Observable observable = httpService.changePass(oldPassword_, password, code);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取好友列表
     *
     * @param subscriber
     * @param code
     */
    public void getFriends(Subscriber subscriber, String code) {
        Observable observable = httpService.getFriends(code);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取群组列表
     *
     * @param subscriber
     * @param code
     */
    public void getGroups(Subscriber subscriber, String code) {
        Observable observable = httpService.getGroups(code);
        toSubscribe(observable, subscriber);
    }


    /**
     * 获取群组成员
     *
     * @param subscriber
     * @param id
     */
    public void getGroup_members(Subscriber subscriber, String id) {
        Observable observable = httpService.getGroup_members(id);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取代办任务列表
     *
     * @param subscriber
     * @param sessionId
     */
    public void getTask(Subscriber subscriber, String sessionId) {
        Log.i("LogsessionId", "getTask-->sessionId=" + sessionId);
        Observable observable = httpService.getTasks(sessionId);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取代办详情
     *
     * @param subscriber
     * @param sessionId
     * @param businessId
     * @param id
     * @param workflowType
     */
    public void getTaskDetail(Subscriber subscriber, String sessionId, String businessId, String id, String workflowType) {
        Observable observable = httpService.getTaskDetail(sessionId, businessId, id, workflowType);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取审批轨迹
     *
     * @param subscriber
     * @param businessId
     */
    public void getTaskLine(Subscriber subscriber, String businessId) {
        Observable observable = httpService.getTaskLine(businessId);
        toSubscribe(observable, subscriber);
    }

    /**
     * 已审批流程
     *
     * @param subscriber
     * @param sessionId
     */
    public void getApproved(Subscriber subscriber, String sessionId, int page) {
        Log.i("LogsessionId", "getApproved-->sessionId=" + sessionId);
        Observable observable = httpService.getApproved(sessionId, page);
        toSubscribe(observable, subscriber);
    }

    /**
     * 已发起
     *
     * @param subscriber
     * @param sessionId
     */
    public void getStartTask(Subscriber subscriber, String sessionId, int page) {
        Log.i("LogsessionId", "getStartTask-->sessionId=" + sessionId);
        Observable observable = httpService.getStartTask(sessionId, page);
        toSubscribe(observable, subscriber);
    }

    /**
     * 处理任务
     *
     * @param subscriber
     * @param url
     * @param params
     */
    public void processTask(Subscriber subscriber, String url, Map<String, String> params) {
        Observable observable = httpService.processTask(url, params);
        toSubscribe(observable, subscriber);
    }

    /**
     * 抄送任务
     *
     * @param subscriber
     * @param sessionId
     */
    public void getCopyTask(Subscriber subscriber, String sessionId) {
        Observable observable = httpService.getCopyTask(sessionId);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取随手拍列表
     *
     * @param subscriber
     * @param page
     * @param rows
     */
    public void getLomoList(Subscriber subscriber, int page, int rows) {
        Observable observable = httpService.getLomoList(page, rows);
        toSubscribe(observable, subscriber);
    }


    /**
     * 获取资产列表
     *
     * @param subscriber
     * @param sessionId
     */
    public void getProperty(Subscriber subscriber, String sessionId) {
        Observable observable = httpService.getProperty(sessionId);
        toSubscribe(observable, subscriber);
    }


    /**
     * 获取工资
     *
     * @param subscriber
     * @param sessionId
     */
    public void getSalary(Subscriber subscriber, String sessionId, int newValue) {
        Observable observable = httpService.getSalary(sessionId, newValue);
        toSubscribe(observable, subscriber);
    }

    /**
     * 福利
     *
     * @param subscriber
     * @param sessionId
     */
    public void getWelfare(Subscriber subscriber, String sessionId) {
        Observable observable = httpService.getWelfare(sessionId);
        toSubscribe(observable, subscriber);
    }

    /**
     * 电子钱包
     *
     * @param subscriber
     * @param sessionId
     * @param start_date
     * @param end_date
     * @param page_Size
     * @param page_Index
     */
    public void getFund(Subscriber subscriber, String sessionId, String start_date, String end_date, int page_Size, int page_Index) {
        Observable observable = httpService.getFund(sessionId, start_date, end_date, page_Index, page_Size);
        toSubscribe(observable, subscriber);
    }

    /**
     * 电子钱包余额
     *
     * @param subscriber
     * @param sessionId
     */
    public void getAmount(Subscriber subscriber, String sessionId) {
        Observable observable = httpService.getAmount(sessionId);
        toSubscribe(observable, subscriber);
    }

    /**
     * 常用联系人
     *
     * @param subscriber
     * @param sessionId
     * @param page
     * @param size
     */
    public void getCyContacts(Subscriber subscriber, String sessionId, int page, int size) {
        Observable observable = httpService.getCyContacts(sessionId, page, size);
        toSubscribe(observable, subscriber);
    }

    /**
     * 导入手机通讯录
     *
     * @param subscriber
     * @param jsonData
     */
    public void save_contacts(Subscriber subscriber, String jsonData) {
        Observable observable = httpService.save_contacts(jsonData);
        toSubscribe(observable, subscriber);
    }


    /**
     *
     *
     * @param subscriber
     * @param jsonData
     */
    public void delete_contacts(Subscriber subscriber, String jsonData) {
        Observable observable = httpService.delete_contacts(jsonData);
        toSubscribe(observable, subscriber);
    }
}
