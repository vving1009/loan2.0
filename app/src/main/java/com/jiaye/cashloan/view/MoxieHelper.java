package com.jiaye.cashloan.view;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.Toast;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.phone.UpdatePhoneRequest;
import com.jiaye.cashloan.http.data.taobao.UpdateTaoBaoRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.moxie.client.exception.ExceptionType;
import com.moxie.client.exception.MoxieException;
import com.moxie.client.manager.MoxieCallBack;
import com.moxie.client.manager.MoxieCallBackData;
import com.moxie.client.manager.MoxieContext;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.model.MxLoginCustom;
import com.moxie.client.model.MxParam;
import com.moxie.client.model.TitleParams;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;

public enum MoxieHelper {

    INSTANCE;

    private static final String TAG = "MoxieHelper : ";
    
    private MxParam mxParam;

    MoxieHelper() {
        initMxParam();
    }

    private void initMxParam() {
        mxParam = new MxParam();
        mxParam.setApiKey(BuildConfig.MOXIE_APIKEY);
        mxParam.setUserId(LoanApplication.getInstance().getDbHelper().queryUser().getToken());
        mxParam.setThemeColor("#425FBB");
        // 设置协议地址
        mxParam.setAgreementUrl(BuildConfig.MOXIE_PROTOCOL_URL);
        // 设置协议文字
        mxParam.setAgreementEntryText("数据采集服务协议");
        initTitle();
    }

    private void initTitle() {
        Resources resources = LoanApplication.getInstance().getApplicationContext().getResources();
        TitleParams titleParams = new TitleParams.Builder()
                //设置返回键的icon，不设置此方法会默认使用魔蝎的icon
                .leftNormalImgResId(R.drawable.function_ic_left)
                //用于设置selector，表示按下的效果，不设置默认使leftNormalImgResId()设置的图片
                //.leftPressedImgResId(R.drawable.back)
                //标题字体颜色
                .titleColor(resources.getColor(R.color.color_gray_light))
                //title背景色
                .backgroundColor(resources.getColor(R.color.color_blue))
                //设置右边icon
                //.rightNormalImgResId(R.drawable.refresh)
                //是否支持沉浸式
                .immersedEnable(true)
                //title返回按钮旁边的文字（关闭）的颜色
                //.leftTextColor(resources.getColor(R.color.colorWhite))
                //title返回按钮旁边的文字，一般为关闭
                //.leftText(“关闭”)
                .build();
        mxParam.setTitleParams(titleParams);
    }

    public MxParam getMxParam() {
        return mxParam;
    }

    public void setTaskType(String type) {
        mxParam.setTaskType(type);
        if (type.equals(MxParam.PARAM_TASK_CARRIER)) {
            setCarrierParam();
        }
    }

    private void setCarrierParam() {
        MxLoginCustom loginCustom = new MxLoginCustom();
        Map<String, Object> loginParam = new HashMap<>();
        String phoneNum = LoanApplication.getInstance().getDbHelper().queryUser().getPhone();
        if (!TextUtils.isEmpty(phoneNum)) {
            loginParam.put("phone", phoneNum);          // 手机号
        }
        String userName = LoanApplication.getInstance().getDbHelper().queryUser().getName();
        if (!TextUtils.isEmpty(userName)) {
            loginParam.put("name", userName);               // 姓名
        }
        String idCard = LoanApplication.getInstance().getDbHelper().queryUser().getId();
        idCard = !TextUtils.isEmpty(idCard) ? idCard : "";
        if (!TextUtils.isEmpty(idCard)) {
            loginParam.put("idcard", idCard);  // 身份证
        }
        // 是否允许用户修改以上信息（目前仅支持运营商）
        // MxParam.PARAM_COMMON_NO:不允许用户在页面上修改身份证/手机号/姓名/密码
        // MxParam.PARAM_COMMON_YES:允许用户在页面上修改身份证/手机号/姓名/密码
        loginCustom.setEditable(MxParam.PARAM_COMMON_NO);
        loginCustom.setLoginParams(loginParam);
        mxParam.setLoginCustom(loginCustom);
    }

    public void start(Activity activity, String taskType) {
        setTaskType(taskType);
        try {
            MoxieSDK.getInstance().start(activity, mxParam, new MoxieCallBack() {
                /**
                 *
                 *  物理返回键和左上角返回按钮的back事件以及sdk退出后任务的状态都通过这个函数来回调
                 *
                 * @param moxieContext       可以用这个来实现在魔蝎的页面弹框或者关闭魔蝎的界面
                 * @param moxieCallBackData  我们可以根据 MoxieCallBackData 的code来判断目前处于哪个状态，以此来实现自定义的行为
                 * @return 返回true表示这个事件由自己全权处理，返回false会接着执行魔蝎的默认行为(比如退出sdk)
                 *
                 *   # 注意，假如设置了MxParam.setQuitOnLoginDone(MxParam.PARAM_COMMON_YES);
                 *   登录成功后，返回的code是MxParam.ResultCode.IMPORTING，不是MxParam.ResultCode.IMPORT_SUCCESS
                 */
                @Override
                public boolean callback(MoxieContext moxieContext, MoxieCallBackData moxieCallBackData) {
                    /**
                     *  # MoxieCallBackData的格式如下：
                     *  1.1.没有进行账单导入，未开始！(后台没有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_UNSTART, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "", "loginDone": false, "businessUserId": ""
                     *  1.2.平台方服务问题(后台没有通知)
                     *      "code" : MxParam.ResultCode.THIRD_PARTY_SERVER_ERROR, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  1.3.魔蝎数据服务异常(后台没有通知)
                     *      "code" : MxParam.ResultCode.MOXIE_SERVER_ERROR, "taskType" : "mail", "taskId" : "", "message" : "", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  1.4.用户输入出错（密码、验证码等输错且未继续输入）
                     *      "code" : MxParam.ResultCode.USER_INPUT_ERROR, "taskType" : "mail", "taskId" : "", "message" : "密码错误", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  2.账单导入失败(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_FAIL, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": false, "businessUserId": ""
                     *  3.账单导入成功(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORT_SUCCESS, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": true, "businessUserId": "xxxx"
                     *  4.账单导入中(后台有通知)
                     *      "code" : MxParam.ResultCode.IMPORTING, "taskType" : "mail",  "taskId" : "ce6b3806-57a2-4466-90bd-670389b1a112", "account" : "xxx", "loginDone": true, "businessUserId": "xxxx"
                     *
                     *  code           :  表示当前导入的状态
                     *  taskType       :  导入的业务类型，与MxParam.setTaskType()传入的一致
                     *  taskId         :  每个导入任务的唯一标识，在登录成功后才会创建
                     *  message        :  提示信息
                     *  account        :  用户输入的账号
                     *  loginDone      :  表示登录是否完成，假如是true，表示已经登录成功，接入方可以根据此标识判断是否可以提前退出
                     *  businessUserId :  第三方被爬取平台本身的userId，非商户传入，例如支付宝的UserId
                     */
                    if (moxieCallBackData != null) {
                        Logger.d(TAG + "MoxieSDK Callback Data : " + moxieCallBackData.toString());
                        switch (moxieCallBackData.getCode()) {
                            /**
                             * 账单导入中
                             *
                             * 如果用户正在导入魔蝎SDK会出现这个情况，如需获取最终状态请轮询贵方后台接口
                             * 魔蝎后台会向贵方后台推送Task通知和Bill通知
                             * Task通知：登录成功/登录失败
                             * Bill通知：账单通知
                             */
                            case MxParam.ResultCode.IMPORTING:
                                if (moxieCallBackData.isLoginDone()) {
                                    //状态为IMPORTING, 且loginDone为true，说明这个时候已经在采集中，已经登录成功
                                    Logger.d(TAG + "任务已经登录成功，正在采集中，SDK退出后不会再回调任务状态，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");

                                } else {
                                    //状态为IMPORTING, 且loginDone为false，说明这个时候正在登录中
                                    Logger.d(TAG + "任务正在登录中，SDK退出后不会再回调任务状态，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");
                                }
                                break;
                            /**
                             * 任务还未开始
                             *
                             * 假如有弹框需求，可以参考 {@link BigdataFragment#showDialog(MoxieContext)}
                             *
                             * example:
                             *  case MxParam.ResultCode.IMPORT_UNSTART:
                             *      showDialog(moxieContext);
                             *      return true;
                             * */
                            case MxParam.ResultCode.IMPORT_UNSTART:
                                Logger.d(TAG + "任务未开始");
                                break;
                            case MxParam.ResultCode.THIRD_PARTY_SERVER_ERROR:
                                Logger.d(TAG + "导入失败(平台方服务问题)");
                                break;
                            case MxParam.ResultCode.MOXIE_SERVER_ERROR:
                                Logger.d(TAG + "导入失败(魔蝎数据服务异常)");
                                break;
                            case MxParam.ResultCode.USER_INPUT_ERROR:
                                Logger.d(TAG + "导入失败(" + moxieCallBackData.getMessage() + ")");
                                break;
                            case MxParam.ResultCode.IMPORT_FAIL:
                                Logger.d(TAG + "导入失败");
                                break;
                            case MxParam.ResultCode.IMPORT_SUCCESS:
                                Logger.d(TAG + "任务采集成功，任务最终状态会从服务端回调，建议轮询APP服务端接口查询任务/业务最新状态");
                                //根据taskType进行对应的处理
                                switch (moxieCallBackData.getTaskType()) {
                                    case MxParam.PARAM_TASK_CARRIER:
                                        Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                                                .map(user -> {
                                                    UpdatePhoneRequest request = new UpdatePhoneRequest();
                                                    request.setLoanId(user.getLoanId());
                                                    return request;
                                                })
                                                .compose(new SatcatcheResponseTransformer<UpdatePhoneRequest, EmptyResponse>("updatePhone"))
                                                .compose(new ViewTransformer<>())
                                                .subscribe(emptyResponse -> moxieContext.finish(), t -> {
                                                    if (t != null && !TextUtils.isEmpty(t.getMessage())) {
                                                        Toast.makeText(moxieContext.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    moxieContext.finish();
                                                }, moxieContext::finish);
                                        break;
                                    case MxParam.PARAM_TASK_TAOBAO:
                                        Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                                                .map(user -> {
                                                    UpdateTaoBaoRequest request = new UpdateTaoBaoRequest();
                                                    request.setLoanId(user.getLoanId());
                                                    return request;
                                                })
                                                .compose(new SatcatcheResponseTransformer<UpdateTaoBaoRequest, EmptyResponse>("updateTaoBao"))
                                                .compose(new ViewTransformer<>())
                                                .subscribe(emptyResponse -> moxieContext.finish(), t -> {
                                                    if (t != null && !TextUtils.isEmpty(t.getMessage())) {
                                                        Toast.makeText(moxieContext.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    moxieContext.finish();
                                                }, moxieContext::finish);
                                        break;
                                    default:
                                        Logger.d(TAG + "导入成功");
                                        moxieContext.finish();
                                }
                                return true;
                        }
                    }
                    return false;
                }

                /**
                 * @param moxieContext    可能为null，说明还没有打开魔蝎页面，使用前要判断一下
                 * @param moxieException  通过exception.getExceptionType();来获取ExceptionType来判断目前是哪个错误
                 */
                @Override
                public void onError(MoxieContext moxieContext, MoxieException moxieException) {
                    super.onError(moxieContext, moxieException);
                    if (moxieException != null) {
                        if (moxieException.getExceptionType() == ExceptionType.SDK_HAS_STARTED) {
                            Logger.d(TAG + moxieException.getMessage());
                        } else if (moxieException.getExceptionType() == ExceptionType.SDK_LACK_PARAMETERS) {
                            Logger.d(TAG + moxieException.getMessage());
                        } else if (moxieException.getExceptionType() == ExceptionType.WRONG_PARAMETERS) {
                            Logger.d(TAG + moxieException.getMessage());
                        }
                        Logger.d(TAG + "MoxieSDK onError : " + moxieException.toString());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG + "start: MoxieActivity error: " + e.getMessage());
        }
    }
}
