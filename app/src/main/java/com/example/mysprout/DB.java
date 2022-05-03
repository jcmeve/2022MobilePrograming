package com.example.mysprout;

import android.util.Log;

import com.amplifyframework.api.aws.GsonVariablesSerializer;
import com.amplifyframework.api.graphql.SimpleGraphQLRequest;
import com.amplifyframework.core.Amplify;

import java.util.HashMap;
import java.util.Map;

public class DB {
    public static boolean CreateUser( String nickname, String  sprout_name){
        //User user = User.builder().transportation(new ArrayList<>()).food(new ArrayList<>()).action(new ArrayList<>()).nickname(nickname).sproutName(sprout_name).sproutExp(0).carbonSave(0).build();

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
        variables.put("nickname", nickname);
        variables.put("sprout_exp", 0);
        variables.put("sprout_name", sprout_name);
        variables.put("carbon_save", 0);

        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        return true;
    }


    public static boolean AddTransportationData(  String transportation_name,  int count){
        String document =
                "mutation MyMutation($transportation_name: String!, $count: Int!) {" +
                    "msAddTransportationData(" +
                        "transportation_name: $transportation_name," +
                        "count: $count" +
                    ")"+
                "}";
        Map<String, Object> variables = new HashMap<>();
        variables.put("transportation_name", transportation_name);
        variables.put("count", count);

        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        return true;
    }
    /*
    msAddFoodData(  food_name: String, count: Int) :Boolean @function(name: "mysprout034be500-${env}")
    msAddActionData(  action_name: String, count: Int) :Boolean @function(name: "mysprout034be500-${env}")
    msGetUserInfo:User@function(name: "mysprout034be500-${env}")
    msGetTotalExp:Int @function(name: "mysprout034be500-${env}")
    msGetTransportationList:[TransportationData] @function(name: "mysprout034be500-${env}")
    msGetFoodList:[FoodData] @function(name: "mysprout034be500-${env}")
    msGetActionList:[ActionData] @function(name: "mysprout034be500-${env}")
    */
}
