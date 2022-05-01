

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
                TableName: "User-qatzrpa2ivbw5ooukcvptpcpya-dev",
                Item: {
                    id: event.identity.claims.username,
                    carbon_save: event.arguments.carbon_save,
                    nickname: event.arguments.nickname,
                    sprout_exp: event.arguments.sprout_exp,
                    sprout_name: event.arguments.sprout_name
                }
            }
            await ddb.put(params).promise();


            return true;
        case "msAddTransportationData":
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

