package ultramodern.activity.mpesadarajatest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.Env;
import com.androidstudy.daraja.util.TransactionType;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    EditText phonenumberinput;
    Button pay_button;
    Daraja daraja;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        pay_button = findViewById(R.id.submit);
        phonenumberinput = findViewById(R.id.phone);

        daraja = Daraja.with("u6EfFHxyOuZQjsxarA5WzM181ltGqHGS", "DBQ7422SDT2fMuMb", Env.PRODUCTION, new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.d(MainActivity.this.getClass().getSimpleName(),accessToken.getAccess_token());
            }

            @Override
            public void onError(String error) {
                Log.e(MainActivity.this.getClass().getSimpleName(), error);
            }
        });
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = phonenumberinput.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)){
                    phonenumberinput.setError("Please provide a Phone Number");
                    return;
                }
                LNMExpress lnmExpress = new LNMExpress(
                        "174379",
                        "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                        TransactionType.CustomerPayBillOnline,
                        "10",
                        "254712311209",
                        "174379",
                        phoneNumber,
                        "http://mpesa-requestbin.herokuapp.com/12oxkfm1",
                        "001ABC",
                        "Goods Payment"
                );
                daraja.requestMPESAExpress(lnmExpress, new DarajaListener<LNMResult>() {
                    @Override
                    public void onResult(@NonNull LNMResult lnmResult) {
                        Log.i(MainActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                    }

                    @Override
                    public void onError(String error) {
                        Log.i(MainActivity.this.getClass().getSimpleName(), error);
                    }
                });
            }
        });


    }
}