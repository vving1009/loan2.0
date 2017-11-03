package com.jiaye.loan.cashloan;

import com.jiaye.loan.cashloan.http.data.auth.login.Login;
import com.jiaye.loan.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.Register;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.loan.cashloan.http.utils.NetworkTransformer;
import com.jiaye.loan.cashloan.utils.RSAUtil;
import com.jiaye.loan.cashloan.view.ThrowableConsumer;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Test
 *
 * @author 贾博瑄
 */

// TODO: 2017/11/3 测试方法,后期删除
public class Test {

    public static void login() {
        /*登录*/
        // J201711020956258921
        LoginRequest request = new LoginRequest();
        request.setPhone("13752126558");
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64("123456", BuildConfig.PUBLIC_KEY));
        Observable.just(request)
                .compose(new NetworkTransformer<LoginRequest, Login>("login"))
                .subscribe(new Consumer<Login>() {
                    @Override
                    public void accept(Login login) throws Exception {

                    }
                }, new ThrowableConsumer());
    }

    public static void rsa() {
        String base64 = "MceFn7GkpxRWpmYZqB88S30Dj013EL3QmVN7i+DLuxjeh9KqV6qWvZoNGMrX+43QZE0vnbefYpTOaDnA+9mi+ob/Rbr6y5YZZcIwxWz8izwPMe6fuPtI11B/j87yANCyA+rjrgQ8WalqZzOnksfHp1ysauczZ9tekBXhxGHQnpvO8y804/Bkpj9XaqDdeJ8OUOlBYOGGX8gveHKH4g2Jh+yqfX98zUShIB78gZZgt+RScHtBvGAbElOqDIyS2lCWhxQ4qLTS56oLSndzPOczwLM25BOlpbfftynLP229fSdH9/eRLverblf3coaY5yAImJwnMZOLjTzG2rIt+flIAw==";
        String key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALSoAL178OEPLSsEVgDYJtYzcG7z\n" +
                "4oHRaCn81bwFSftPVawze8Te28LboAvjP5cfe3opNoPyYH7N/SFPrZpj/jjeA4ZL1R/9VIXlm4J0\n" +
                "3vSeAGEMXmzV2391M+ev99ec5KuvMn1OinM06FUTBIPjAIKnIdPhKfbOUX8g6e0Nh3TLAgMBAAEC\n" +
                "gYBsKhbfXMD5j4OkuODheomuQHg2BlH9Fis+0IIMJEKKdJLAGsclNaXwwlzOIU7mpdPhbaGVWN6L\n" +
                "rbu8YR95TBteYONOeZsMDwW4xm6OOfTfh65UM3MlclJab8TS7yCav+rYBgXr24cdOsxJCZ43AnXx\n" +
                "EaubcN/YXH9GuQ8ZwczPwQJBAOdFvXRHpdRuIQQYizWGlhZTVuw6fEMVSLoLrT+OyJZnEaa5ilYI\n" +
                "xGNX2wfOq+u78GWrwkj6DSYusBeq69BjcKECQQDH+NNSAB8Jcti/kH4XVrB7fWNdKi1vDlbrmNfT\n" +
                "y5s5rnlp0bk+DCBWwek8Kmpw6REqWtlwL5dsrXA4CCAIiHHrAkBIwF+AnKlF0f8A0te31saP71eA\n" +
                "qEU+tQtTuyicvcXLylB7KhKiTc+5kIGOSy050r0kvos3ebF5OWabi2DzBNUBAkACKEIHWW78SBvk\n" +
                "fSePEuVWf7TJtYHF9+6iHgT+CO1EwwgWRyfrbnAO34qnloGNdEY2IcLEvg6xInHaeOP3k5k/AkAC\n" +
                "xSoZNeRTbhOn1LGKDA/8HW23Cx144yhqTzK+ozHL/ClGtPFeZQfd/AD3gSBAEkN8Q92CxqgGjJJg\n" +
                "dmjPaa2D";
        Logger.d(RSAUtil.decryptByPrivateKeyToString(base64, key));
    }
}
