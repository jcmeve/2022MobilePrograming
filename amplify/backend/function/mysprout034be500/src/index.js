

/**
 * @type {import('@types/aws-lambda').APIGatewayProxyHandler}
 */
exports.handler = async (event, context, callback) => {
    console.log(`EVENT: ${JSON.stringify(event)}`);
    console.log(`EVENT: ${JSON.stringify(event.typeName)}`);
    console.log(`EVENT: ${JSON.stringify(event.fieldName)}`);
    console.log(`IDENTITY : ${JSON.stringify(event.identity)}`);
    console.log("Got an Invoke Request.");

    switch(event.fieldName){
        case "msCreateUser":
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
