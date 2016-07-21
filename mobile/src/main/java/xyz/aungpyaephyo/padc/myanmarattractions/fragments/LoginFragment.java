package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.UserSessionController;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;
import xyz.aungpyaephyo.padc.myanmarattractions.views.PasswordVisibilityListener;

/**
 * Created by aung on 7/15/16.
 */
public class LoginFragment extends Fragment {

    @BindView(R.id.lbl_login_title)
    TextView lblLoginTitle;

    @BindView(R.id.lbl_forget_password)
    TextView lblForgetPassword;

    @BindView(R.id.lbl_to_register)
    TextView lblToRegister;

    @BindView(R.id.et_password)
    TextView etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.et_email)
    EditText etEmail;

    private UserSessionController mUserSessionController;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUserSessionController = (UserSessionController) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        lblLoginTitle.setText(Html.fromHtml(getString(R.string.lbl_login_title)));
        lblForgetPassword.setText(Html.fromHtml(getString(R.string.lbl_forget_password)));
        lblToRegister.setText(Html.fromHtml(getString(R.string.lbl_to_register)));
        etPassword.setOnTouchListener(new PasswordVisibilityListener());

        return rootView;
    }

    @OnClick(R.id.btn_login)
    public void onTapLogin(Button btnLogin){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            etEmail.setError("need to enter your email.");
        }else if(TextUtils.isEmpty(password)){
            etPassword.setError("need to enter password.");
        }else if(!isEmailValid(email)){
            etEmail.setError("please enter valid email.");
        }else{
            mUserSessionController.onLogin(email,password);
        }
    }

    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(MyanmarAttractionsConstants.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    //Success Login
    public void onEventMainThread(DataEvent.DatePickedEvent event) {
        //tvDateOfBirth.setText(event.getDateOfBrith());
    }
}
