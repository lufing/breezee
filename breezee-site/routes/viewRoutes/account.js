var request = require('request');

var accountRoute = {};

accountRoute.myAccount = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : 'http://127.0.0.1:10248/services/user/'+queryData.userId,
        mockData : '/account/myAccount',
        json:{},
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    }, function(error, response, body){
        callback(body);
    });
};
accountRoute.account = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/account/myAccount',
        mockData : '/account/myAccount',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};
accountRoute.address = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : 'http://127.0.0.1:10248/services/user/shippingAddress/user/'+queryData.userId,
        mockData : '/account/address',
        json:{},
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        }
    }, function(error, response, body){
        callback(body);
    });
};
accountRoute.accountBalance = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/account/balance',
        mockData : '/account/balance',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};
accountRoute.accountDetail = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/account/detail',
        mockData : '/account/detail',
        form : queryData
    }, function(error, response, body){
        callback(body);
    });
};

module.exports = accountRoute;