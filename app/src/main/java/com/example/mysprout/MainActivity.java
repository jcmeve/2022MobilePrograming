package com.example.mysprout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;

import com.amplifyframework.core.Action;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.datastore.generated.model.Todo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());

            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

/*
        Todo todo = Todo.builder()
                .name("Msadffffffffffffffffffffffffffffftodo")
                .description("todo description")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(todo),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
*/
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.i("AmplifyQuickstart", result.toString());
                    if(result.isSignedIn()){

                        Intent intent = new Intent(MainActivity.this, sprout.class);
                        startActivity(intent);
                    }
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );





        setContentView(R.layout.activity_main);
    }

    //남은수
    public void onClick1(View v) {
        Intent intent = new Intent(getApplicationContext(), userinformation.class);
        startActivity(intent);

    }

    //남은수
    public void onClick2(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}