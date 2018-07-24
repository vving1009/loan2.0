package com.jiaye.cashloan.view.login.source;

import android.database.Cursor;
import android.net.Uri;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.login.Login;
import com.jiaye.cashloan.http.data.login.LoginShortcutRequest;
import com.jiaye.cashloan.http.data.login.VerificationCodeRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.utils.RSAUtil;
import com.jiaye.cashloan.utils.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * LoginShortcutRepository
 *
 * @author 贾博瑄
 */

public class LoginShortcutRepository implements LoginShortcutDataSource {

    @Override
    public Flowable<Login> requestLogin(final String phone, String code, String password) {
        LoginShortcutRequest request = new LoginShortcutRequest();
        request.setPhone(phone);
        request.setCode(code);
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64(password, BuildConfig.PUBLIC_KEY));
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<LoginShortcutRequest, Login>("loginShortcut"))
                .map(login -> {
                    LoanApplication.getInstance().getDbHelper().insertUser(phone, login.getToken(),
                            login.getId(), login.getName());
                    LoanApplication.getInstance().setupBuglyUserId(phone);
                    return login;
                });
    }

    @Override
    public Flowable<EmptyResponse> requestVerificationCode(String phone) {
        VerificationCodeRequest request = new VerificationCodeRequest();
        request.setPhone(phone);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<VerificationCodeRequest, EmptyResponse>
                        ("verificationCode"));
    }

    @Override
    public Flowable<String> querySmsCode(Uri uri) {
        return Flowable.create(e -> {
            if (uri.toString().equals("content://sms/raw")) {
                return;
            }
            Uri inboxUri = Uri.parse("content://sms/inbox");
            Cursor c = LoanApplication.getInstance().getContentResolver().query(inboxUri,
                    null, null, null, "date desc");
            if (c != null) {
                if (c.moveToFirst()) {
                    // 手机号
                    String address = c.getString(c.getColumnIndex("address"));
                    // 短信内容
                    String body = c.getString(c.getColumnIndex("body"));
                    if (!address.matches(RegexUtil.smsPhoneNum())) {
                        return;
                    }
                    // 正则表达式截取验证码
                    Pattern pattern = Pattern.compile(RegexUtil.smsCode());
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        e.onNext(matcher.group());
                    }
                }
                c.close();
            }
        }, BackpressureStrategy.DROP);
    }
}
