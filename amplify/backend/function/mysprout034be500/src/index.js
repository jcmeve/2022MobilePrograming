

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
                TableName: "User-q2vg5zi6bvcavondcdicixh6zi-dev",
                Item: {
                    id: event.identity.sub,
                    carbon_save: event.arguments.carbon_save,
                    nickname: event.arguments.nickname,
                    sprout_exp: event.arguments.sprout_exp,
                    sprout_name: event.arguments.sprout_name,
                    transportation: [],
                    food: [],
                    action: []

                }
            }
            await ddb.put(params).promise();
            return true;

        case "msAddTransportationData":
            //이동수단 종류 있는지 확인
            const at_params = {
                TableName: 	"TransportationData-q2vg5zi6bvcavondcdicixh6zi-dev",
                Key: {
                    name: event.arguments.transportation_name
                }
            }
            var trans;
            await ddb.get(at_params, function(err, data){
                if (err) {console.log(err);}
                else {console.log(data); trans = data;}
            }).promise();
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
                    TableName: "UserTransportation-q2vg5zi6bvcavondcdicixh6zi-dev",
                    Item: {
                        id: event.identity.sub + event.arguments.transportation_name,
                        transportation_name: event.arguments.transportation_name,
                        count: [event.arguments.count]
                    }
                }
                await ddb.put(params).promise();

                //유저 테이블에 기록. / 삭제가능
                const params2 = {
                    TableName: "User-q2vg5zi6bvcavondcdicixh6zi-dev",
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
                    TableName: "UserTransportation-q2vg5zi6bvcavondcdicixh6zi-dev",
                    Key: {id: event.identity.sub + event.arguments.transportation_name},

                    UpdateExpression: "set #count = list_append( #count ,:val1)",
                    ExpressionAttributeNames:{
                        "#count" : "count",
                    },
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.count],
                    }
                }

                await ddb.update(params, function(err, data) {
                    if (err) {
                        console.log("Error", err);
                    } else {
                        console.log("Success", data);
                    }
                }).promise();
            }

            const carbon = event.arguments.count * trans.Item.carbon_per_unit;
            addCarbonSave(event.identity.sub, carbon);


            return true;

        case "msAddFoodData":

            //음식 종류 있는지 확인
            const af_params = {
                TableName: 	"FoodData-q2vg5zi6bvcavondcdicixh6zi-dev",
                Key: {
                    name: event.arguments.food_name
                }
            }
            var food;
            await ddb.get(af_params, function(err, data){
                if (err) {console.log(err);}
                else {console.log(data); food = data;}
            }).promise();
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
                const params = {
                    TableName: "UserFood-q2vg5zi6bvcavondcdicixh6zi-dev",
                    Item: {
                        id: event.identity.sub + event.arguments.food_name,
                        food_name: event.arguments.food_name,
                        count: [event.arguments.count]
                    }
                }
                await ddb.put(params).promise();

                //유저 테이블에 기록. / 삭제가능
                const params2 = {
                    TableName: "User-q2vg5zi6bvcavondcdicixh6zi-dev",
                    Key: {id: event.identity.sub},

                    UpdateExpression: "set food =  list_append( food, :val1)",
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.food_name],
                    }
                }
                ddb.update(params2, function(err, data) {
                    if (err) {
                        console.log("Error", err);
                    } else {
                        console.log("Success", data);
                    }
                });
            }else{//음식을 사용한 적이 있는 경우
                //음식 사용량 기록
                const params = {
                    TableName: "UserFood-q2vg5zi6bvcavondcdicixh6zi-dev",
                    Key: {id: event.identity.sub + event.arguments.food_name},

                    UpdateExpression: "set #count = list_append( #count ,:val1)",
                    ExpressionAttributeNames:{
                        "#count" : "count",
                    },
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.count],
                    }
                }

                await ddb.update(params, function(err, data) {
                    if (err) {
                        console.log("Error", err);
                    } else {
                        console.log("Success", data);
                    }
                }).promise();
            }
            const carbon2 = event.arguments.count * food.Item.carbon_per_unit;
            await addCarbonSave(event.identity.sub, carbon);


            return true;

        case "msAddActionData":
            //액션 종류 있는지 확인
            const aa_params = {
                TableName: 	"ActionData-q2vg5zi6bvcavondcdicixh6zi-dev",
                Key: {
                    name: event.arguments.action_name
                }
            }
            var action;
            await ddb.get(aa_params, function(err, data){
                if (err) {console.log(err);}
                else {console.log(data); action = data;}
            }).promise();
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
                    TableName: "UserAction-q2vg5zi6bvcavondcdicixh6zi-dev",
                    Item: {
                        id: event.identity.sub + event.arguments.action_name,
                        action_name: event.arguments.action_name,
                        count: [event.arguments.count]
                    }
                }
                await ddb.put(params).promise();

                //유저 테이블에 기록. / 삭제가능
                const params2 = {
                    TableName: "User-q2vg5zi6bvcavondcdicixh6zi-dev",
                    Key: {id: event.identity.sub},

                    UpdateExpression: "set action =  list_append( action, :val1)",
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.action_name],
                    }
                }
                ddb.update(params2, function(err, data) {
                    if (err) {
                        console.log("Error", err);
                    } else {
                        console.log("Success", data);
                    }
                });
            }else{//액션을 사용한 적이 있는 경우
                //액션 사용량 기록
                const params = {
                    TableName: "UserAction-q2vg5zi6bvcavondcdicixh6zi-dev",
                    Key: {id: event.identity.sub + event.arguments.action_name},

                    UpdateExpression: "set #count = list_append( #count ,:val1)",
                    ExpressionAttributeNames:{
                        "#count" : "count",
                    },
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.count],
                    }
                }

                await ddb.update(params, function(err, data) {
                    if (err) {
                        console.log("Error", err);
                    } else {
                        console.log("Success", data);
                    }
                }).promise();
            }
            const carbon3 = event.arguments.count * action.Item.save_carbon;
            await addCarbonSave(event.identity.sub, carbon);

            return true;

        case "msGetUserInfo":
            return await getUser(event.identity.sub);

        case "msGetTotalExp":

            console.log("!@#!DSAFAWSF");
            //유저가 해당 액션 사용한 적 있는지 확인 ,/ 제거 가능
            var user = await getUser(event.identity.sub);
            var exp = user.Item.carbon_save;
            return exp;
        case "msGetTransportationList":
        case "msGetFoodList":
        case "msGetActionList":


        default:
            callback("Unknown field, unable to resolve" + event.field, null);
            break;
    }


};

async function getUser(id){
    //유저 return
    const gu_params = {
        TableName: "User-q2vg5zi6bvcavondcdicixh6zi-dev",
        Key: {
            id: id
        }
    }
    var user;
    await ddb.get(gu_params, function(err, data) {
        user = data;
        if (err) console.log("ERRPR" + err);
        else console.log(data);
    }).promise();

    return user;

}

async function addCarbonSave(id, value){
    //유저 테이블에 기록. / 삭제가능
    const params2 = {
        TableName: "User-q2vg5zi6bvcavondcdicixh6zi-dev",
        Key: {id: id},

        UpdateExpression: "set carbon_save = carbon_save + :val1",
        ExpressionAttributeValues: {
            ":val1" : value,
        }
    }
    ddb.update(params2, function(err, data) {
        if (err) {
            console.log("Error", err);
        } else {
            console.log("Success", data);
        }
    });

}