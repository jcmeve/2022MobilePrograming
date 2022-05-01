package com.example.mysprout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.aws.GsonVariablesSerializer;
import com.amplifyframework.api.graphql.SimpleGraphQLRequest;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userinformation extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation);

        Button nextBtn;
        nextBtn = findViewById(R.id.nextbtn_start);
        nextBtn.setOnClickListener(v -> {
            EditText email = findViewById(R.id.input_email);
            EditText password = findViewById(R.id.input_password);
            EditText password_check = findViewById(R.id.input_password_check);
            EditText nickname = findViewById(R.id.input_yourname);
            EditText sproutname = findViewById(R.id.input_sproutname);

            String email_txt = email.getText().toString();
            String password_txt = password.getText().toString();
            String password_check_txt = password_check.getText().toString();
            String nickname_txt = nickname.getText().toString();
            String sproutname_txt = sproutname.getText().toString();
            String birthday_txt = "1999.01.01";
            if(password_txt.compareTo(password_check_txt) != 0){
                Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다.",Toast.LENGTH_LONG).show();
                return;
            }


            AuthSignUpOptions.Builder<?> builder = AuthSignUpOptions.builder();
            builder.userAttribute(AuthUserAttributeKey.email(), email.getText().toString());
            AuthUserAttribute[] authUserAttributes = {
                    new AuthUserAttribute(AuthUserAttributeKey.email(), email_txt),
                    new AuthUserAttribute(AuthUserAttributeKey.nickname(), nickname_txt),
                    new AuthUserAttribute(AuthUserAttributeKey.birthdate(), birthday_txt)
            };
            builder.userAttributes(Arrays.asList(authUserAttributes));
            AuthSignUpOptions options = builder.build();



            Amplify.Auth.signUp(email_txt,password_txt,options,
                result -> {
                    Log.i("AuthQuickStart", "Result: " + result);
                    if(result.isSignUpComplete()) {
                        signUpInit();
                    }
                },
                error->Log.e("AuthQuickStart", "Sign up failed", error)
            );



        });

    }

    private void signUpInit(){
        EditText email = findViewById(R.id.input_email);
        EditText password = findViewById(R.id.input_password);

        String email_txt = email.getText().toString();
        String password_txt = password.getText().toString();

        Amplify.Auth.signIn(email_txt,password_txt,
                result-> {
                    Log.i("AuthQuickstart", result.isSignInComplete()?"Sign in succeeded": "Sign in not complete");
                    Log.i("asdfasdfasdf", result.toString());
                    InitUser(result.isSignInComplete());
                },
                error-> {
                    if(!(error.getClass() == AuthException.InvalidParameterException.class))
                        Log.e("AuthQuickstart",error.toString());
                }
        );





    }

    private void InitUser(boolean isSuccess){
        EditText email = findViewById(R.id.input_email);
        EditText nickname = findViewById(R.id.input_yourname);
        EditText sproutname = findViewById(R.id.input_sproutname);

        String email_txt = email.getText().toString();
        String nickname_txt = nickname.getText().toString();
        String sproutname_txt = sproutname.getText().toString();

        User user = User.builder().email(email_txt).nickname(nickname_txt).sproutName(sproutname_txt).sproutExp(0).carbonSave(0).build();

        String document =
                "mutation MyMutation($carbon_save: Int!, $sprout_name: String!, $sprout_exp: Int!, $nickname: String!) {" +
                        "msCreateUser(" +
                        "nickname: $nickname," +
                        "sprout_exp: $sprout_exp," +
                        "sprout_name: $sprout_name," +
                        "carbon_save: $carbon_save" +
                        ")"+
                        "}";
        Map<String, Object> variables = new HashMap<>();
        variables.put("nickname", user.getNickname());
        variables.put("sprout_exp", user.getSproutExp());
        variables.put("sprout_name", user.getSproutName());
        variables.put("carbon_save", user.getCarbonSave());

        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );



        Intent intent = new Intent(userinformation.this, userinformation_2.class);
        startActivity(intent);
    }
}