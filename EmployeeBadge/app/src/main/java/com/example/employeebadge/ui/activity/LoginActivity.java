package com.example.employeebadge.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeebadge.R;
import com.example.employeebadge.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener{
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private static final String TAG = "EmailPassword";


    @BindView(R.id.emailSignInButton) Button emailSignInButton;
    @BindView(R.id.fieldEmail) EditText mEmailField;
    @BindView(R.id.fieldPassword) EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        emailSignInButton.setOnClickListener(this);
        mEmailField.setText("imt@gmail.com");
//        mPasswordField.setText("123456");
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        Utils.hideSoftKeyboard(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

//    private void changePass() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        user.getEmail();
//        AuthCredential credential = EmailAuthProvider
//                .getCredential("imt@gmail.com", "123456789");
//
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            user.updatePassword("123456").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Log.d(TAG, "Password updated");
//                                    } else {
//                                        Log.d(TAG, "Error password not updated");
//                                    }
//                                }
//                            });
//                        } else {
//                            Log.d(TAG, "Error auth failed");
//                        }
//                    }
//                });
//    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideLoadingDialog();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //move to main
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.emailSignInButton) {
            if (TextUtils.isEmpty(mPasswordField.getText().toString()) || TextUtils.isEmpty(mEmailField.getText().toString())) {
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
            } else {
                showLoadingDialog();
                Utils.hideSoftKeyboard(this);
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        }
    }


    public void showLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Đang xử lý...");
            mProgressDialog.show();
        } else if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
