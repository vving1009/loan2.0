package com.jiaye.cashloan.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.view.about.AboutFragment;
import com.jiaye.cashloan.view.bankcard.BankCardFragment;
import com.jiaye.cashloan.view.file.FileFragment;
import com.jiaye.cashloan.view.plan.PlanFragment;
import com.jiaye.cashloan.view.sign.SignFragment;
import com.jiaye.cashloan.view.vehicle.VehicleFragment;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.id.IDFragment;
import com.jiaye.cashloan.view.info.InfoFragment;
import com.jiaye.cashloan.view.phone.PhoneFragment;
import com.jiaye.cashloan.view.taobao.TaoBaoFragment;
import com.jiaye.cashloan.view.view.my.credit.AccountFragment;

/**
 * FunctionActivity
 *
 * @author 贾博瑄
 */
public class FunctionActivity extends AppCompatActivity {

    private static final String BUNDLE = "bundle";

    public static void function(Activity activity, String function) {
        function(activity, function, null);
    }

    public static void function(Activity activity, String function, Bundle bundle) {
        Intent intent = new Intent(activity, FunctionActivity.class);
        intent.putExtra("function", function);
        if (bundle != null) {
            intent.putExtra(BUNDLE, bundle);
        }
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity);
        String function = getIntent().getStringExtra("function");
        switch (function) {
            case "Certification":
                // 认证中心
                CertificationFragment certificationFragment = CertificationFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, certificationFragment).commit();
                break;
            case "ID":
                // 身份认证
                IDFragment idFragment = IDFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, idFragment).commit();
                break;
            case "Info":
                // 个人资料
                InfoFragment infoFragment = InfoFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, infoFragment).commit();
                break;
            case "Phone":
                // 手机运营商
                PhoneFragment phoneFragment = PhoneFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, phoneFragment).commit();
                break;
            case "Vehicle":
                // 车辆证件
                VehicleFragment vehicleFragment = VehicleFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, vehicleFragment).commit();
                break;
            case "Taobao":
                // 淘宝
                TaoBaoFragment taoBaoFragment = TaoBaoFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, taoBaoFragment).commit();
                break;
            case "File":
                // 进件资料
                FileFragment fileFragment = FileFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fileFragment).commit();
                break;
            case "Sign":
                // 电子签章
                SignFragment signFragment = SignFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, signFragment).commit();
                break;
            case "Plan":
                // 还款计划
                PlanFragment planFragment = PlanFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, planFragment).commit();
                break;
            case "About":
                // 关于我们
                AboutFragment aboutFragment = AboutFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, aboutFragment).commit();
                break;
            case "Account":
                //账户管理
                AccountFragment accountFragment = AccountFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, accountFragment).commit();
                break;
            case "BankCard":
                // 银行卡绑定
                Bundle bundle = getIntent().getBundleExtra(BUNDLE);
                CreditInfo info = bundle.getParcelable("creditInfo");
                Boolean isBind = bundle.getBoolean("isBind", true);
                BankCardFragment bankCardFragment = BankCardFragment.newInstance(isBind, info);
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, bankCardFragment).commit();
                break;
        }
    }
}
