

/**
 * @type {import('@types/aws-lambda').APIGatewayProxyHandler}
 */
 const AWS = require('aws-sdk');
 const ddb = new AWS.DynamoDB.DocumentClient({region: 'us-west-2'});

exports.handler = async (event, context, callback) => {
    console.log(`EVENT: ${JSON.stringify(event)}`);
    console.log(`EVENT: ${JSON.stringify(context)}`);
    console.log(`EVENT: ${JSON.stringify(event.typeName)}`);
    console.log(`EVENT: ${JSON.stringify(event.fieldName)}`);
    console.log(`IDENTITY : ${JSON.stringify(event.identity)}`);
    console.log("Got an Invoke Request.");

    switch(event.fieldName){
        case "msCreateUser":
            const params = {
                TableName: "User-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                Item: {
                    id: event.identity.sub,
                    carbon_save: event.arguments.carbon_save,
                    nickname: event.arguments.nickname,
                    sprout_exp: event.arguments.sprout_exp,
                    sprout_name: event.arguments.sprout_name,
                    transportation: [],
                    food: [],
                    action: [],
                    meat_carbon: 0,
                    transportation_carbon: 0,
                    mcreatedAt: parseInt(Date.now() / 1000)

                }
            }
            await ddb.put(params).promise();
            return true;



        case  "msGetUserTransportationHistory":
            var user = await getUser(event.identity.sub);
            console.log(user);

            var result = [];
            for(let transportation_name of user.Item.transportation){
                var ret = await getHistory("UserTransportation", event.identity.sub + transportation_name);
                result.push(...ret);
            }
            return result;

        case  "msGetUserFoodHistory":
            var user = await getUser(event.identity.sub);
            console.log(user);

            var result = [];
            for(let food_name of user.Item.food){
                var ret = await getHistory("UserFood", event.identity.sub + food_name);
                result.push(...ret);
            }

            return result;

        case  "msGetUserActionHistory":
            var user = await getUser(event.identity.sub);
            console.log(user);

            var result = [];
            for(let action_name of user.Item.action){
                var ret = await getHistory("UserAction", event.identity.sub + action_name);
                result.push(...ret);
            }

            return result;

        case "msSetMeatCarbon":

            const params_meat = {
                TableName: "User-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                Key: {id: event.identity.sub},

                UpdateExpression: "set meat_carbon = :val1",
                ExpressionAttributeValues: {
                    ":val1" : event.arguments.meat_carbon,
                }
            }
            await ddb.update(params_meat).promise();
            return true;

        case "msSetTransportationCarbon":
            const params_trans = {
                TableName: "User-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                Key: {id: event.identity.sub},

                UpdateExpression: "set transportation_carbon = :val1",
                ExpressionAttributeValues: {
                    ":val1" : event.arguments.transportation_carbon,
                }
            }
            await ddb.update(params_trans).promise();
            return true;

        case "msAddTransportationData":
            //이동수단 종류 있는지 확인
            const at_params = {
                TableName: 	"TransportationData-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                Key: {
                    name: event.arguments.transportation_name
                }
            }
            var trans;
            trans = await ddb.get(at_params).promise();
            if(trans.Item == null)
                return false;
            console.log("TRANSPORATTION CHECK COMPLERE");

            //유저가 해당 이동수단 사용한 적 있는지 확인 ,/ 제거 가능
            var user = await getUser(event.identity.sub);
            console.log(user);
            var ck = false;

            for(let x of user.Item.transportation){
                if(x == event.arguments.transportation_name){
                    ck = true;
                    break;
                }
            }

            console.log("check : " + ck);
            if(ck == false){//한번도 해당 이동수단을 사용한 적 없는 경우
                //유저-이동수단 테이블 생성 및 사용량 기록
                const params = {
                    TableName: "UserTransportation-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Item: {
                        id: event.identity.sub + event.arguments.transportation_name,
                        transportation_name: event.arguments.transportation_name,
                        count: [event.arguments.count],
                        date: [parseInt(Date.now() / 1000)]
                    }
                }
                await ddb.put(params).promise();

                //유저 테이블에 기록. / 삭제가능
                const params2 = {
                    TableName: "User-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Key: {id: event.identity.sub},

                    UpdateExpression: "set #transportation =  list_append( #transportation, :val1)",
                    ExpressionAttributeNames:{
                        "#transportation" : "transportation",
                    },
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.transportation_name],
                    }
                }
                console.log([event.arguments.transportation_name]);
                ddb.update(params2, function(err, data) {
                    if (err) {
                        console.log("Error", err);
                    } else {
                        console.log("Success", data);
                    }
                });


            }else{//이동수단을 사용한 적이 있는 경우
                //이동수단 사용량 기록
                const params = {
                    TableName: "UserTransportation-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Key: {id: event.identity.sub + event.arguments.transportation_name},

                    UpdateExpression: "set #count = list_append( #count ,:val1), #date = list_append( #date, :val2)",
                    ExpressionAttributeNames:{
                        "#count" : "count",
                        "#date" : "date",
                    },
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.count],
                        ":val2" : [parseInt(Date.now() / 1000)],
                    }
                }

                await ddb.update(params).promise();
            }

            const carbon = event.arguments.count * trans.Item.carbon_per_unit;


            return true;

        case "msAddFoodData":

            //음식 종류 있는지 확인
            const af_params = {
                TableName: 	"FoodData-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                Key: {
                    name: event.arguments.food_name
                }
            }
            var food;
            food = await ddb.get(af_params).promise();
            if(food.Item == null)
                return false;
            console.log("FOOD CHECK COMPLERE");

            //유저가 해당 음식 사용한 적 있는지 확인 ,/ 제거 가능
            var user = await getUser(event.identity.sub);

            var ck = false;

            for(let x of user.Item.food){
                if(x == event.arguments.food_name){
                    ck = true;
                    break;
                }
            }

            if(ck == false){//한번도 해당 음식을 사용한 적 없는 경우
                //유저-음식 테이블 생성 및 사용량 기록
                console.log("asdfasdfsadf");
                const params = {
                    TableName: "UserFood-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Item: {
                        id: event.identity.sub + event.arguments.food_name,
                        food_name: event.arguments.food_name,
                        count: [event.arguments.count],
                        date: [parseInt(Date.now() / 1000)],
                        mtime: [event.arguments.mtime]
                    }
                };
                await ddb.put(params).promise();

                //유저 테이블에 기록. / 삭제가능
                const params2 = {
                    TableName: "User-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Key: {id: event.identity.sub},

                    UpdateExpression: "set food =  list_append( food, :val1)",
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.food_name],
                    }
                };
                await ddb.update(params2).promise();
            }else{//음식을 사용한 적이 있는 경우
                //음식 사용량 기록
                const params = {
                    TableName: "UserFood-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Key: {id: event.identity.sub + event.arguments.food_name},

                    UpdateExpression: "set #count = list_append( #count ,:val1), #date = list_append( #date, :val2), #mtime = list_append( #mtime, :val3)",
                    ExpressionAttributeNames:{
                        "#count" : "count",
                        "#date" : "date",
                        "#mtime" : "mtime",
                    },
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.count],
                        ":val2" : [parseInt(Date.now() / 1000)],
                        ":val3" : [event.arguments.mtime],
                    }
                };
                ddb.update(params, function(err, data) {
                    if (err) {
                        console.log("Error", err);
                    } else {
                        console.log("Success", data);
                    }
                });
            }
            const carbon2 = event.arguments.count * food.Item.carbon_per_unit;


            return true;

        case "msAddActionData":
            //액션 종류 있는지 확인
            const aa_params = {
                TableName: 	"ActionData-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                Key: {
                    name: event.arguments.action_name
                }
            }
            var action;
            action = await ddb.get(aa_params).promise();
            if(action.Item == null)
                return false;
            console.log("ACTION CHECK COMPLERE");

            //유저가 해당 액션 사용한 적 있는지 확인 ,/ 제거 가능
            var user = await getUser(event.identity.sub);

            var ck = false;

            for(let x of user.Item.action){
                if(x == event.arguments.action_name){
                    ck = true;
                    break;
                }
            }

            if(ck == false){//한번도 해당 액션을 사용한 적 없는 경우
                //유저-액션 테이블 생성 및 사용량 기록
                const params = {
                    TableName: "UserAction-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Item: {
                        id: event.identity.sub + event.arguments.action_name,
                        action_name: event.arguments.action_name,
                        count: [event.arguments.count],
                        date: [parseInt(Date.now() / 1000)]
                    }
                }
                await ddb.put(params).promise();

                //유저 테이블에 기록. / 삭제가능
                const params2 = {
                    TableName: "User-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Key: {id: event.identity.sub},

                    UpdateExpression: "set #action =  list_append( #action, :val1)",
                    ExpressionAttributeNames:{
                        "#action" : "action",
                    },
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.action_name],
                    }
                }
                await ddb.update(params2).promise();
            }else{//액션을 사용한 적이 있는 경우
                //액션 사용량 기록
                const params = {
                    TableName: "UserAction-lxhzoxuizzfwrbzgkmwilkkpse-dev",
                    Key: {id: event.identity.sub + event.arguments.action_name},

                    UpdateExpression: "set #count = list_append( #count ,:val1), #date = list_append( #date, :val2)",
                    ExpressionAttributeNames:{
                        "#count" : "count",
                        "#date" : "date",
                    },
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.count],
                        ":val2" : [parseInt(Date.now() / 1000)],
                    }
                }

                await ddb.update(params).promise();
            }
            const carbon3 = event.arguments.count * action.Item.save_carbon;
            await addCarbonSave(event.identity.sub, carbon3);

            return true;

        case "msGetUserInfo":
            var user = await getUser(event.identity.sub);
            return user.Item ;
        case "msGetTotalExp":

            console.log("!@#!DSAFAWSF");
            //유저가 해당 액션 사용한 적 있는지 확인 ,/ 제거 가능
            var user = await getUser(event.identity.sub);
            var exp = user.Item.carbon_save;
            return exp;

        case "msGetTransportationList":
            return await getDataList("TransportationData");
        case "msGetFoodList":
            return await getDataList("FoodData");
        case "msGetActionList":
            return await getDataList("ActionData");


        case "msAddSaveCarbon":
            return await addCarbonSave(event.identity.sub, event.arguments.carbon);



        default:
            callback("Unknown field, unable to resolve" + event.field, null);
            break;
    }


};

async function getUser(id){
    //유저 return
    const gu_params = {
        TableName: "User-lxhzoxuizzfwrbzgkmwilkkpse-dev",
        Key: {
            id: id
        }
    }
    var user;
    user = await ddb.get(gu_params).promise();

    return user;

}

async function addCarbonSave(id, value){
    //유저 테이블에 기록. / 삭제가능
    const params2 = {
        TableName: "User-lxhzoxuizzfwrbzgkmwilkkpse-dev",
        Key: {id: id},

        UpdateExpression: "set carbon_save = carbon_save + :val1",
        ExpressionAttributeValues: {
            ":val1" : value,
        }
    }
    await ddb.update(params2).promise();

}

async function getDataList(name){
    const params = {
        TableName: name + "-lxhzoxuizzfwrbzgkmwilkkpse-dev",
    };
    const scanResults = [];
    var items;
    do{
        items =  await ddb.scan(params).promise();
        items.Items.forEach((item) => scanResults.push(item));
        params.ExclusiveStartKey  = items.LastEvaluatedKey;
    }while(typeof items.LastEvaluatedKey !== "undefined");

    return scanResults;
}


async function getHistory(type, _id){

    const params = {
        TableName: type + "-lxhzoxuizzfwrbzgkmwilkkpse-dev",
        KeyConditionExpression: "#id = :val",
        ExpressionAttributeNames:{
            "#id": "id"
        },
        ExpressionAttributeValues: {
            ":val": _id
        }
    };
    const scanResults = [];
    var items;
    do{
        items =  await ddb.query(params).promise();

        items.Items.forEach((item) => scanResults.push(item));
        params.ExclusiveStartKey  = items.LastEvaluatedKey;
    }while(typeof items.LastEvaluatedKey !== "undefined");
    return scanResults;
}