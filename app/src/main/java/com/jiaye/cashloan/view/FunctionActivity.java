package com.jiaye.cashloan.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.about.AboutFragment;
import com.jiaye.cashloan.view.file.FileFragment;
import com.jiaye.cashloan.view.plan.PlanFragment;
import com.jiaye.cashloan.view.sign.SignFragment;
import com.jiaye.cashloan.view.vehicle.VehicleFragment;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.id.IDFragment;
import com.jiaye.cashloan.view.info.InfoFragment;
import com.jiaye.cashloan.view.phone.PhoneFragment;
import com.jiaye.cashloan.view.taobao.TaoBaoFragment;

/**
 * FunctionActivity
 *
 * @author 贾博瑄
 */
public class FunctionActivity extends AppCompatActivity {

    public static void function(Activity activity, String function) {
        Intent intent = new Intent(activity, FunctionActivity.class);
        intent.putExtra("function", function);
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
        }
    }
}
