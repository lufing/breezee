var request = require('request');

var route = {};

route.confirmSite = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/restaurant/',
        mockData : '/order/site',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        body.createTime = global.myUtil.dateFormatter(new Date(body.createTime), "yyyy-MM-dd hh:mm");
        body.time = global.myUtil.dateFormatter(new Date(body.time), "yyyy-MM-dd hh:mm");
        callback(body);
    });
};
route.orderFood = function (queryData, res, callback) {
    global.myUtil.request({
        method : 'get',
        uri : '/restaurant/',
        mockData : '/restaurant/restaurantDetail',
        form : queryData
    }, function(error, response, body){
        if(error){
            throw error;
        }
        body.createTime = global.myUtil.dateFormatter(new Date(body.createTime), "yyyy-MM-dd hh:mm");
        body.time = global.myUtil.dateFormatter(new Date(body.time), "yyyy-MM-dd hh:mm");
        callback(body);
    });
};
module.exports = route;