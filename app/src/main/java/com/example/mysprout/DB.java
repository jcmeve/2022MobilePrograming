package com.example.mysprout;

import android.util.Log;

import com.amplifyframework.api.aws.GsonVariablesSerializer;
import com.amplifyframework.api.graphql.SimpleGraphQLRequest;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.ActionData;
import com.amplifyframework.datastore.generated.model.FoodData;
import com.amplifyframework.datastore.generated.model.TransportationData;
import com.amplifyframework.datastore.generated.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public  class DB {
    public static DB getInstance(){
        return new DB();
    }

    public boolean CreateUser( String nickname, String  sprout_name){
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


    public boolean AddTransportationData(String transportation_name,  int count){
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

    public boolean AddFoodData(String food_name,  int count){
            String document =
                "mutation MyMutation($food_name: String!, $count: Int!) {" +
                    "msAddFoodData(" +
                        "food_name: $food_name," +
                        "count: $count" +
                    ")"+
                "}";
        Map<String, Object> variables = new HashMap<>();
        variables.put("food_name", food_name);
        variables.put("count", count);

        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        return true;
    }
    public boolean AddActionData(String action_name,  int count){
        String document =
                "mutation MyMutation($action_name: String!, $count: Int!) {" +
                        "msAddActionData(" +
                        "action_name: $action_name," +
                        "count: $count" +
                        ")"+
                        "}";
        Map<String, Object> variables = new HashMap<>();
        variables.put("action_name", action_name);
        variables.put("count", count);

        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
        return true;
    }

    public interface getExpCallBack{
        void callback(int value);
    }
    private getExpCallBack expCallBack;
    public void GetTotalExp(getExpCallBack _callBack){
        expCallBack = _callBack;
        String document =
                "query MyQuery {" +
                        "msGetTotalExp" +
                        "}";

        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, String.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData());
                    try {
                        JSONObject jsonObject = new JSONObject(response.getData().toString());
                        expCallBack.callback(jsonObject.getInt("msGetTotalExp"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

    }
    public interface getUserCallBack{
        void callback(User user);
    }
    private getUserCallBack userCallBack;
    public void GetUserInfo(getUserCallBack _callBack) {
        userCallBack = _callBack;

        String document =

                "query MyQuery {" +
                        "  msGetUserInfo {" +
                        "    nickname" +
                        "    sprout_exp" +
                        "    sprout_name" +
                        "    carbon_save" +
                        "  }" +
                        "}";
        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, User.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GETUSER!!: " + response);
                    User user = (User)response.getData();
                    userCallBack.callback(user);

                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

    }

    public interface getTransportationListCallBack{
        void callback(TransportationData[] transportationData);
    }
    private getTransportationListCallBack transportationListCallBack;
    public void GetTransportationList(getTransportationListCallBack _callback) {
        transportationListCallBack = _callback;

        String document =
                "query MyQuery {" +
                "  msGetTransportationList {" +
                "    name" +
                "    unit" +
                "    carbon_per_unit" +
                "  }" +
                "}";

        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, String.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GETTRansportaion!!: " + response);
                    TransportationData[] data = {};
                    try {
                        JSONObject jsonObject = new JSONObject(response.getData().toString());
                        JSONArray arr = (JSONArray) jsonObject.get("msGetTransportationList");
                        Gson gson = new Gson();
                        data = new TransportationData[arr.length()];
                        for(int i = 0; i < arr.length();i++){
                            Log.i(arr.getString(i),arr.getString(i));
                            data[i] = (gson.fromJson(arr.getString(i),TransportationData.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    transportationListCallBack.callback(data);
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

    }

    public interface getFoodListCallBack{
        void callback(FoodData[] foodData);
    }
    private getFoodListCallBack foodListCallBack;
    public void GetFoodList(getFoodListCallBack _callback) {
        foodListCallBack = _callback;

        String document =
                "query MyQuery {" +
                "  msGetFoodList {" +
                "    carbon_per_unit" +
                "    name" +
                "    unit" +
                "  }" +
                "}";

        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, String.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GETFood!!: " + response);
                    FoodData[] data = {};
                    try {
                        JSONObject jsonObject = new JSONObject(response.getData().toString());
                        JSONArray arr = (JSONArray) jsonObject.get("msGetFoodList");
                        Gson gson = new Gson();
                        data = new FoodData[arr.length()];
                        for(int i = 0; i < arr.length();i++){
                            Log.i(arr.getString(i),arr.getString(i));
                            data[i] = (gson.fromJson(arr.getString(i),FoodData.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    foodListCallBack.callback(data);
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

    }


    public interface getActionListCallBack{
        void callback(ActionData[] actionData);
    }
    private getActionListCallBack actionListCallBack;
    public void GetActionList(getActionListCallBack _callback) {
        actionListCallBack = _callback;

        String document =
                "query MyQuery {" +
                "  msGetActionList {" +
                "    name" +
                "    save_carbon" +
                "  }" +
                "}";

        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, String.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GETAction!!: " + response);
                    ActionData[] data = {};
                    try {
                        JSONObject jsonObject = new JSONObject(response.getData().toString());
                        JSONArray arr = (JSONArray) jsonObject.get("msGetActionList");
                        Gson gson = new Gson();
                        data = new ActionData[arr.length()];
                        for(int i = 0; i < arr.length();i++){
                            Log.i(arr.getString(i),arr.getString(i));
                            data[i] = (gson.fromJson(arr.getString(i),ActionData.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    actionListCallBack.callback(data);
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

    }
}
