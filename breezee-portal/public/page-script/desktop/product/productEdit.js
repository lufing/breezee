Dolphin.defaults.mockFlag = false;
$(function () {
    Dolphin.form.parse();

    var page = {
        categoryAttr : null,
        productData : null,

        baseInfo : $('#baseInfo'),
        categoryId : $('#categoryId'),
        categoryAttrPanel : $('#extendAttrPanel'),
        categoryAttrPanelBody : $('#categoryAttrPanelBody'),
        save : $('#save')
    };

    page.connect = {
        productDetail : {
            url : '/data/pcm/product/'
        },
        category : {
            url : '/data/pcm/category/cateAttrs/{id}'
        },
        productInfo : {
            url : '/data/pcm/product/{id}'
        },
        productSave : {
            url : '/data/pcm/product/',
            type : Dolphin.requestMethod.PUT
        }
    };

    page.init = function () {
        this.initPage();
        this.initEvent();

        return this;
    };

    page.initPage = function () {
        var _this = this;

        if(REQUEST_MAP.data.id){
            Dolphin.ajax({
                url : _this.connect.productInfo.url,
                pathData : {
                    id : REQUEST_MAP.data.id
                },
                mockPathData : ['id'],
                loading : true,
                onSuccess : function (reData) {
                    _this.initValue(reData.value);
                }
            });
        }
    };

    page.initEvent = function () {
        var _this = this;
        _this.categoryId.change(function () {
            if(this.value){
                _this.renderForm(this.value);
            }else{
                _this.categoryAttrPanel.hide();
            }
        });

        _this.save.click(function (){
            var submitData = $.extend({}, _this.productInfo, Dolphin.form.getValue(_this.baseInfo), {
                productData : Dolphin.form.getValue(_this.categoryAttrPanel)
            });

            Dolphin.ajax($.extend({}, _this.connect.productSave, {
                loading : true,
                data : Dolphin.json2string(submitData),
                onSuccess : function (reData) {
                    Dolphin.alert(reData.msg || '保存成功', {
                        callback : function () {
                            Dolphin.goUrl('/product/productList');
                        }
                    })
                }
            }))
        })
    };

    page.initValue = function(data){
        var _this = this;
        _this.productInfo = data;
        Dolphin.form.setValue(data, _this.baseInfo);
        _this.renderForm(data.cateId, function (reData) {
            Dolphin.form.setValue(data.productData, _this.categoryAttrPanel);
        });
    };

    page.renderForm = function (id, callback) {
        var _this = this;
        Dolphin.ajax({
            url : _this.connect.category.url,
            mockPathData : ['id'],
            pathData : {
                id : id
            },
            loading : true,
            onSuccess : function (reData) {
                _this.categoryAttr = reData.rows;

                _this.categoryAttrPanelBody.empty();
                _this.categoryAttrPanel.show();
                $.each(_this.categoryAttr, function (i, attr) {
                    _this.renderField(attr, _this.categoryAttrPanelBody);
                });
                if(typeof callback == 'function'){
                    callback.call(_this, reData);
                }
            }
        });
    };
    page.renderField = function (attr, panel, param) {
        var col, control, inputLabel, inputPanel, input,
            _this = this,
            opts = $.extend({}, param);

        if(typeof opts.eachField === 'function'){
            if(opts.eachField.call(_this, attr) === false){
                return false;
            }
        }

        if(!attr.display){
            $('<input type="hidden" name="'+attr.id+'" />').prependTo(panel);
        }else{
            col = $('<div class="dolphin-col-12">').appendTo(panel);
            control = $('<div class="form-group">').attr({
                'code': attr.code,
                'id': attr.id
            }).appendTo(col);

            inputLabel = $('<label class="col-sm-2 control-label">').html(attr.name).appendTo(control);
            inputPanel = $('<div class="col-sm-10">').appendTo(control);

            switch (attr.attrType){
                case "integer":
                case "numberic":
                    input = $('<div class="input-group">');
                    $('<input type="text" class="form-control">').attr({
                        name : attr.id
                    }).appendTo(input);
                    $('<span class="input-group-addon">').html(attr.unitCode || '&nbsp;').appendTo(input);
                    break;
                case "dict":
                    input = $('<select class="form-control">');
                    input.attr($.extend({}, {options:attr.enumCode}, attr.param));
                    input.attr('name', attr.id);
                    Dolphin.form.parseSelect(input);
                    break;
                default :
                    input = $('<input type="text" class="form-control" />');
                    input.attr('name', attr.id);
            }
            input.appendTo(inputPanel)
        }
    };

    window.page = page.init();
});