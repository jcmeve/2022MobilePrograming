package com.example.mysprout;

import android.util.Log;

import com.amplifyframework.api.aws.GsonVariablesSerializer;
import com.amplifyframework.api.graphql.SimpleGraphQLRequest;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.ActionData;
import com.amplifyframework.datastore.generated.model.FoodData;
import com.amplifyframework.datastore.generated.model.MeatLevelData;
import com.amplifyframework.datastore.generated.model.TransportationData;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.datastore.generated.model.UserAction;
import com.amplifyframework.datastore.generated.model.UserFood;
import com.amplifyframework.datastore.generated.model.UserTransportation;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
                response -> Log.i("MyAmplifyApp", "Added CreateUser with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "CreateUser failed", error)
        );
        return true;
    }

    public boolean AddSaveCarbon( int carbon){
        //User user = User.builder().transportation(new ArrayList<>()).food(new ArrayList<>()).action(new ArrayList<>()).nickname(nickname).sproutName(sprout_name).sproutExp(0).carbonSave(0).build();

        String document =
                "mutation MyMutation($carbon: Int!) {\n" +
                        "  msAddSaveCarbon(carbon: $carbon)\n" +
                        "}\n";
        Map<String, Object> variables = new HashMap<>();
        variables.put("carbon", carbon);

        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added SaveCarbon with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "SaveCarbon failed", error)
        );
        return true;
    }



    public boolean SetMeatCarbon(int meat_carbon){
        String document =
                "mutation MyMutation($meat_carbon: Int!) {" +
                        "msSetMeatCarbon(" +
                        "meat_carbon: $meat_carbon," +
                        ")"+
                        "}";
        Map<String, Object> variables = new HashMap<>();
        variables.put("meat_carbon", meat_carbon);

        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added SetMeatCarbon with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "SetMeatCarbon failed", error)
        );
        return true;
    }

    public boolean SetTransportationCarbon(int transportation_carbon){
        String document =
                "mutation MyMutation($transportation_carbon: Int!) {" +
                        "msSetTransportationCarbon(" +
                        "transportation_carbon: $transportation_carbon," +
                        ")"+
                        "}";
        Map<String, Object> variables = new HashMap<>();
        variables.put("transportation_carbon", transportation_carbon);

        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added SetTransportationCarbon with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "SetTransportationCarbon failed", error)
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
                response -> Log.i("MyAmplifyApp", "Added AddTransportationData with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "AddTransportationData failed", error)
        );
        return true;
    }

    public enum TIME{
        BREAKFAST,LUNCH,DINNER
    }
    public boolean AddFoodData(String food_name,  int count, TIME _time){
        String mtime = null;
        switch (_time){
            case BREAKFAST:
                mtime = "b";
                break;
            case LUNCH:
                mtime = "l";
                break;
            case DINNER:
                mtime = "d";
                break;
            default:
                Log.e("DB ERROR","TIME");
        }

        String document =
            "mutation MyMutation($food_name: String!, $count: Int!, $mtime: String!) {" +
                "msAddFoodData(" +
                    "food_name: $food_name," +
                    "count: $count" +
                    "mtime: $mtime" +
                ")"+
            "}";

        document =
                "mutation MyMutation($count: Int!, $food_name: String!, $mtime: String!) {" +
                    "msAddFoodData(" +
                        "count: $count," +
                        "food_name: $food_name," +
                        "mtime: $mtime" +
                        ")" +
                "}";


        Map<String, Object> variables = new HashMap<>();
        variables.put("food_name", food_name);
        variables.put("count", count);
        variables.put("mtime", mtime);
        Log.i("TAG" ,"food_name : " + food_name + "count : "+ count + "mtime : " + mtime);
        Amplify.API.mutate(
                new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                response -> Log.i("MyAmplifyApp", "Added AddFoodData with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "AddFoodData failed", error)
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
                response -> Log.i("MyAmplifyApp", "Added AddActionData with id: " + response.getData()),
                error -> Log.e("MyAmplifyApp", "AddActionData failed", error)
        );
        return true;
    }

    public interface getExpCallBack{
        void callback(int exp, int lvl);
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
                        int exp = jsonObject.getInt("msGetTotalExp");
                        expCallBack.callback(exp, ExpToLevel(exp));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

    }
    public static int ExpToLevel(int exp){
        if(exp == 0)
            return  1;
        exp += 1000;
        exp /= 1000;
        int lvl = (int)(Math.log(exp)/Math.log(2) + 1e-10);
        return lvl + 1;
    }
    //레벨3이라면 2->3지점을 알려줌
    public static int GetStartExp(int level){
        int exp = 0;
        for (int i = 0; i < level -1; ++i){
            exp += Math.pow(2,i);
        }
        exp *= 1000;
        return  exp;
    }

    public interface getUserCallBack{
        void callback(User user);
    }
    private getUserCallBack userCallBack;
    public void GetUserInfo(getUserCallBack _callBack) {
        userCallBack = _callBack;

        String document =

                "query MyQuery {\n" +
                        "  msGetUserInfo {\n" +
                        "    action\n" +
                        "    carbon_save\n" +
                        "    mcreatedAt\n" +
                        "    food\n" +
                        "    meat_carbon\n" +
                        "    nickname\n" +
                        "    sprout_exp\n" +
                        "    sprout_name\n" +
                        "    transportation\n" +
                        "    transportation_carbon\n" +
                        "  }\n" +
                        "}\n";
        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, User.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GET USER!!: " + response);
                    User user = (User)response.getData();
                    userCallBack.callback(user);

                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

    }

    public interface getMeatLevelDataCallBack{
        void callback(MeatLevelData data);
    }
    private getMeatLevelDataCallBack meatLevelDataCallBack;
    public void msGetMeatLevelData(getMeatLevelDataCallBack _callBack) {
        meatLevelDataCallBack = _callBack;

        String document =

                "query MyQuery($id: ID!) {" +
                        "  getMeatLevelData(id: $id) {" +
                        "    high_carbon" +
                        "    low_carbon" +
                        "    no_carbon" +
                        "  }" +
                        "}";
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", 1);

        Amplify.API.query(
                new SimpleGraphQLRequest<>(document,variables, MeatLevelData.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GET MeatLevelData!!: " + response);
                    MeatLevelData data = (MeatLevelData)response.getData();
                    meatLevelDataCallBack.callback(data);

                },
                error -> Log.e("MyAmplifyApp", "msGetMeatLevelData failed", error)
        );

    }

    //msGetMeatLevelData:MeatLevelData @function(name: "mysprout034be500-${env}")



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
                "    tag" +
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
                "    tag" +
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

    public class Transportation_Pair{
        public Transportation_Pair(UserTransportation a, TransportationData b){
            transportation_history = a;
            data = b;
        }
        public UserTransportation transportation_history;
        public TransportationData data;
    }

    public interface getTransportationHistoryCallBack{
        void callback(Transportation_Pair[] result);
    }
    private getTransportationHistoryCallBack transportationHistoryCallBack;
    public void GetUserTransportationHistory(getTransportationHistoryCallBack _callback){
        transportationHistoryCallBack = _callback;

        String document =
                "query MyQuery {" +
                        "  msGetUserTransportationHistory {" +
                        "    count" +
                        "    date" +
                        "    transportation_name" +
                        "    data {" +
                        "      carbon_per_unit" +
                        "      unit" +
                        "      name" +
                        "    }" +
                        "  }" +
                        "}";

        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, String.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GET Transportation History!!: " + response);
                    Transportation_Pair[] data = {};
                    try {
                        JSONObject jsonObject = new JSONObject(response.getData().toString());
                        JSONArray arr = (JSONArray) jsonObject.get("msGetUserTransportationHistory");
                        Gson gson = new Gson();
                        data = new Transportation_Pair[arr.length()];
                        for(int i = 0; i < arr.length();i++){
                            Log.i(arr.getString(i),arr.getString(i));
                            JSONArray counts = arr.getJSONObject(i).getJSONArray("count");
                            JSONArray dates = arr.getJSONObject(i).getJSONArray("date");

                            ArrayList count = new ArrayList();
                            ArrayList date = new ArrayList();

                            JSONObject t = arr.getJSONObject(i).getJSONObject("data");
                            TransportationData tt = TransportationData.builder().name(t.getString("name")).unit(t.getString("unit")).carbonPerUnit(t.getInt("carbon_per_unit")).build();
                            for(int j = 0; j<counts.length(); j++){
                                count.add( counts.getDouble(j));
                                date.add( new Temporal.Timestamp(dates.getLong(j), TimeUnit.SECONDS));
                            }
                            data[i] = new Transportation_Pair(UserTransportation.builder().transportationName(arr.getJSONObject(i).getString("transportation_name")).count(count).date(date).build(), tt);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    transportationHistoryCallBack.callback(data);
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
    }

    public class Food_Pair{
        public Food_Pair(UserFood a, FoodData b){
            food_history = a;
            data = b;
        }
        public UserFood food_history;
        public FoodData data;
    }

    public interface getFoodHistoryCallBack{
        void callback(Food_Pair[] result);
    }
    private getFoodHistoryCallBack foodHistoryCallBack;
    public void GetUserFoodHistory(getFoodHistoryCallBack _callback){
        foodHistoryCallBack = _callback;

        String document =
                "query MyQuery {" +
                        "  msGetUserFoodHistory {" +
                        "    count" +
                        "    food_name" +
                        "    date" +
                        "    mtime" +
                        "    data {" +
                        "      carbon_per_unit" +
                        "      name" +
                        "      tag" +
                        "      unit" +
                        "    }" +
                        "  }" +
                        "}";

        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, String.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GET Food History!!: " + response);
                    Food_Pair[] data = {};
                    try {
                        JSONObject jsonObject = new JSONObject(response.getData().toString());
                        JSONArray arr = (JSONArray) jsonObject.get("msGetUserFoodHistory");
                        Gson gson = new Gson();
                        data = new Food_Pair[arr.length()];
                        for(int i = 0; i < arr.length();i++){
                            Log.i(arr.getString(i),arr.getString(i));
                            JSONArray counts = arr.getJSONObject(i).getJSONArray("count");
                            JSONArray dates = arr.getJSONObject(i).getJSONArray("date");
                            JSONArray times = arr.getJSONObject(i).getJSONArray("mtime");

                            ArrayList count = new ArrayList();
                            ArrayList date = new ArrayList();
                            ArrayList time = new ArrayList();
                            JSONObject t = arr.getJSONObject(i).getJSONObject("data");
                            FoodData tt = FoodData.builder().name(t.getString("name")).unit(t.getString("unit")).carbonPerUnit(t.getInt("carbon_per_unit")).tag("tag").build();
                            for(int j = 0; j<counts.length(); j++){
                                count.add( counts.getInt(j));
                                date.add( new Temporal.Timestamp(dates.getLong(j), TimeUnit.SECONDS));
                                time.add(times.getString(j));
                            }
                            data[i] = new Food_Pair(UserFood.builder().foodName(arr.getJSONObject(i).getString("food_name")).count(count).date(date).mtime(time).build(), tt);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    foodHistoryCallBack.callback(data);
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
    }


    public class Action_Pair{
        public Action_Pair(UserAction a, ActionData b){
            action_history = a;
            data = b;
        }
        public UserAction action_history;
        public ActionData data;
    }
    public interface getActionHistoryCallBack{
        void callback(Action_Pair[] result);
    }
    private getActionHistoryCallBack actionHistoryCallBack;
    public void GetUserActionHistory(getActionHistoryCallBack _callback){
        actionHistoryCallBack = _callback;

        String document =
                "query MyQuery {" +
                        "  msGetUserActionHistory {" +
                        "    action_name" +
                        "    date" +
                        "    count" +
                        "    data {" +
                        "      name" +
                        "      tag" +
                        "      save_carbon" +
                        "    }" +
                        "  }" +
                        "}";

        Amplify.API.query(
                new SimpleGraphQLRequest<>(document, String.class, new GsonVariablesSerializer()),
                response -> {
                    Log.i("MyAmplifyApp", "GET Action History!!: " + response);
                    Action_Pair[] data = {};
                    try {
                        JSONObject jsonObject = new JSONObject(response.getData().toString());
                        JSONArray arr = (JSONArray) jsonObject.get("msGetUserActionHistory");
                        Gson gson = new Gson();
                        data = new Action_Pair[arr.length()];
                        for(int i = 0; i < arr.length();i++){
                            Log.i(arr.getString(i),arr.getString(i));
                            JSONArray counts = arr.getJSONObject(i).getJSONArray("count");
                            JSONArray dates = arr.getJSONObject(i).getJSONArray("date");

                            ArrayList count = new ArrayList();
                            ArrayList date = new ArrayList();
                            for(int j = 0; j<counts.length(); j++){
                                count.add( counts.getInt(j));
                                date.add( new Temporal.Timestamp(dates.getLong(j), TimeUnit.SECONDS));
                            }
                            JSONObject t = arr.getJSONObject(i).getJSONObject("data");
                            ActionData tt = ActionData.builder().name(t.getString("name")).tag(t.getString("tag")).saveCarbon(t.getInt("save_carbon")).build();
                            data[i] = new Action_Pair(UserAction.builder().actionName(arr.getJSONObject(i).getString("action_name")).count(count).date(date).build(), tt);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    actionHistoryCallBack.callback(data);
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
    }


/*
    public void DBINPUT(Context context){
        InputStreamReader inputStreamReader = new InputStreamReader(context.getResources().openRawResource(R.raw.food_data));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        CSVReader reader = new CSVReader(bufferedReader);
        String[] nextLine = null;
        while(true){
            try {
                if (!((nextLine = reader.readNext())!= null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            String tag =  nextLine[0];
            String name = nextLine[1];
            int carbon = (int)Float.parseFloat(nextLine[2]);
            String unit =  nextLine[3];

            String document ="mutation MyMutation($carbon_per_unit: Int!, $name: String! , $tag: String!, $unit: String! ) {\n" +
                    "  createFoodData(input: {carbon_per_unit: $carbon_per_unit, name: $name, tag: $tag, unit: $unit}) {\n" +
                    "    carbon_per_unit\n" +
                    "    name\n" +
                    "    tag\n" +
                    "    unit\n" +
                    "  }\n" +
                    "}\n";

            Map<String, Object> variables = new HashMap<>();
            variables.put("tag", tag);
            variables.put("name", name);
            variables.put("carbon_per_unit", carbon);
            variables.put("unit", unit);

            Amplify.API.mutate(
                    new SimpleGraphQLRequest<>(document, variables, String.class, new GsonVariablesSerializer()),
                    response -> Log.i("MyAmplifyApp", "Added sodo with id: " + response),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );



        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 */

}


