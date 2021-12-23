package com.token.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.MainNetParams;
import org.web3j.protocol.Web3j;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlowLayout flowLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createCode();
    }

    private void createCode() {
        flowLayout=findViewById(R.id.flowLayout);
        flowLayout.relayoutToCompress();//压缩
        flowLayout.relayoutToAlign();//对齐



        byte bytes2[] = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes2);
        MnemonicCode mnemonicCode = null;
        try {
            mnemonicCode = new MnemonicCode();
            List<String> mnemoniclist = mnemonicCode.toMnemonic(bytes2);
            for (String text : mnemoniclist) {
                TextView textView = buildLabel(text);
                flowLayout.addView(textView);
            }
        } catch (IOException | MnemonicException.MnemonicLengthException e) {
            e.printStackTrace();
        }


        NetworkParameters params = MainNetParams.get();
        ECKey ecKey = new ECKey();
        String publicKey = ecKey.getPublicKeyAsHex();
        String privateKey = ecKey.getPrivateKeyAsHex();
        String canImportPrivateKey = ecKey.getPrivateKeyAsWiF(params);


        LogUtils.e("公钥: " + publicKey);
        LogUtils.e("私钥: " + privateKey);
        LogUtils.e("可导入私钥：" + canImportPrivateKey);



    }


    private TextView buildLabel(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setPadding((int)dpToPx(16), (int)dpToPx(8), (int)dpToPx(16), (int)dpToPx(8));
        return textView;
    }

    private float dpToPx(float dp){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}