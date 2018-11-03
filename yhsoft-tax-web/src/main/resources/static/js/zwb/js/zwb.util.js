zwb.namespace("zwb.util");

zwb.util = {

    test: function () {
        alert("zwb.util.test");
    },

    serializeEntity: function (formId) {
        var arr = $("#" + formId).serializeArray();
        var entity = {};
        for (var i = 0; i < arr.length; i++) {
            entity[arr[i].name] = arr[i].value;
        }
        return entity;
    },

    getPostData: function (formIdOrObj) {
        var args;
        if (typeof(formIdOrObj) == "string") {
            var entity = zwb.util.serializeEntity(formIdOrObj);
            args = {data: entity};
        } else {
            args = formIdOrObj;
        }
        var data = {args: JSON.stringify(args)};
        return data;
    },

    post: function (action, data, successHandler) {
        $.ajax(zwb.settings.contextPath + action,
            {
                type: "POST",
                data: data,
                //contentType: "application/json; charset=utf-8",
                beforeSend: function () {

                },
                complete: function () {

                },
                success: function (data, textStatus, jqXHR) {
                    // var objData = eval("(" + data + ")");
                    var objData = data;
                    if (successHandler) {
                        successHandler(objData);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    debugger;
                    console.log("XMLHttpRequest.status:" + XMLHttpRequest.status
                        + "\nXMLHttpRequest.readyState:"
                        + XMLHttpRequest.readyState + "\ntextStatus:"
                        + textStatus);
                }
            });
    },

    fillForm: function (formId, data) {
        for (var key in data) {
            if (typeof(data[key]) == "function") {

            } else {
                var formItem = $("#" + formId).find("[name=" + key + "]");

                formItem.val(data[key]);
            }
        }
    },

    disableForm: function (formId) {
        var $form = $("#" + formId);
        $form.find("input,textarea").attr("disabled", "disabled");
        $form.find("button,i.layui-edge").remove();
        $form.find(".layui-select-title").unbind("click");
    }

}