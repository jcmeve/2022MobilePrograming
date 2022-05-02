

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
                TableName: "User-xvaekiaoorcq7chyutyarir7gy-strkey",
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
            const at_params = {
                TableName: 	"TransportationData-xvaekiaoorcq7chyutyarir7gy-strkey",
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
            //이동수단 종류 있는지 확인
            console.log("TRANSPORATTION CHECK COMPLERE");
            const at_params2 = {
                TableName: "User-xvaekiaoorcq7chyutyarir7gy-strkey",
                Key: {
                    id: event.identity.sub
                }
            }
            var user;
            await ddb.get(at_params2, function(err, data) {
                user = data;
                if (err) console.log(err);
                else console.log(data);
            }).promise();
            console.log(user);

            var ck = false;

            for(let x of user.Item.transportation){
                if(x == event.arguments.transportation_name){
                    ck = true;
                    break;
                }
            }
            if(ck == false){
                const params = {
                    TableName: "UserTransportation-xvaekiaoorcq7chyutyarir7gy-strkey",
                    Item: {
                        id: event.identity.sub + event.arguments.transportation_name,
                        transportation_name: event.arguments.transportation_name,
                        count: [event.arguments.count]
                    }
                }
                await ddb.put(params).promise();

                const params2 = {
                    TableName: "User-xvaekiaoorcq7chyutyarir7gy-strkey",
                    Key: {id: event.identity.sub},

                    UpdateExpression: "set transportation =  list_append( transportation, :val1)",
                    ExpressionAttributeValues: {
                        ":val1" : [event.arguments.transportation_name],
                    }
                }
                await ddb.update(params2, function(err, data) {
                    if (err) {
                        console.log("Error", err);
                    } else {
                        console.log("Success", data);
                    }
                }).promise();


            }else{
                const params = {
                    TableName: "UserTransportation-xvaekiaoorcq7chyutyarir7gy-strkey",
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



            return true;
        case "msAddFoodData":
            return true;
        case "msAddActionData":
            return true;
        case "msGetUserInfo":
        case "msGetTotalExp":
        case "msGetTransportationList":
        case "msGetFoodList":
        case "msGetActionList":


        default:
            callback("Unknown field, unable to resolve" + event.field, null);
            break;
    }


};

