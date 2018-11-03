zwb.namespace("zwb.util.lay");

zwb.util.lay = {

    initTable: function () {
        var $tables = $(".layui-table");
        $tables.each(function () {
            var $table = $(this);
            var formData = $($table.attr("_formId")).serialize();
            formData = decodeURIComponent(formData, true);
            var url = "/common/pagination/getLayUITablePage"
            if ($table.attr("_url")) {
                url = $table.attr("_url");
            }
            var layData = eval("(" + $table.attr("lay-data") + ")");
            layData = $.extend({
                id: $table.attr("id"),
                url: zwb.settings.contextPath + url,
                toolbar: $table.attr("_toolbar"),
                method: 'post',
                page: true,
                height: "full-80",
                where: {
                    sql: $table.attr("_sql"),
                    order: $table.attr("_order"),
                    param: formData
                }
            }, layData);
            $table.attr("lay-data", JSON.stringify(layData));
            $table.attr("lay-filter", $table.attr("id"));
        });
    },

    reloadTable: function (id) {
        id = id || "table1";
        var $table = $("#" + id);
        if ($table.length == 0) {
            alert("找不到Id“" + id + "”为的table!")
            return false;
        }
        var formData = $($table.attr("_formId")).serialize();
        formData = decodeURIComponent(formData, true);
        var layData = eval("(" + $table.attr("lay-data") + ")");
        layData = $.extend(layData, {
            where: {
                sql: $table.attr("_sql"),
                order: $table.attr("_order"),
                param: formData
            }
        });
        layui.table.reload(id, layData);
        return false;
    },

    openDialog: function (url, width, height, yesCallback, options) {
        if (url.substr(0, 1) == "/") {
            url = zwb.settings.contextPath + url;
        } else {
            var pathName = window.location.pathname;
            var basePath = pathName.substr(0, pathName.lastIndexOf('/'));
            url = basePath + '/' + url;
        }
        var openDialog;
        if (zwb.settings.openDialogInParent) {
            openDialog = parent.zwb.util.lay.openDialog2;
        } else {
            openDialog = zwb.util.lay.openDialog2;
        }
        openDialog(url, width, height, yesCallback, options, window);
    },

    openDialog2: function (url, width, height, yesCallback, options, originOpener) {
        if (!yesCallback) {
            yesCallback = function (index, layero) {
                var iframeWin = window[layero.find('iframe')[0]['name']];
                iframeWin.doSave(index, layero, originOpener);
            }
        }
        options = options || {};
        options = $.extend(
            {
                type: 2,
                content: url,
                area: [width, height],
                btn: ['保存', '关闭'],
                yes: yesCallback
            }, options
        );
        return layui.layer.open(options);
    },

    closeDialog: function (index) {
        var closeDialog;
        if (zwb.settings.openDialogInParent) {
            closeDialog = parent.zwb.util.lay.closeDialog2;
        } else {
            closeDialog = zwb.util.lay.closeDialog2;
        }
        closeDialog(index);
    },

    closeDialog2: function (index) {
        layui.layer.close(index)
    },

    handleException: function (data) {
        if (!data.success) {
            zwb.util.lay.alert(data.message);
            return false;
        } else if (!data.valid) {
            zwb.util.lay.alert(data.message);
            return false;
        }
        return true;
    },

    alert: function (msg) {
        if (msg == null) {
            msg = "null";
        }
        layui.layer.msg(msg);
    },

    confirm: function (msg, onOkHandler) {
        /*
         if(confirm(msg))
         {
         onOkHandler();
         }
         */
        layui.layer.confirm(msg, function (index) {
            onOkHandler();
            layui.layer.close(index);
        });
    },

    openSelectDialogTree: function (sqlId, onOkHandler, options) {
        options = $.extend({
            isSingle: 1,
            title: "",
            width: "500px",
            height: "450px"
        }, options);
        var url = "/common/select/tree_dialog?sqlId=" + sqlId + "&isSingle=" + options.isSingle;
        zwb.util.lay.openDialog(url, options.width, options.height, function (index, layero) {
            var iframeWin = window[layero.find('iframe')[0]['name']];
            var arr = iframeWin.getSelectedArray();
            if (arr.length == 0) {
                zwb.util.lay.alert("没有选择记录！");
                return;
            }
            onOkHandler(arr);
            layui.layer.close(index);
        }, {
            title: options.title,
            btn: ['确定', '关闭']
        });
    },

    validateForm: function (formId, validCallback) {
        var $form = $("#" + formId);
        var $btnSubmit;
        if ($form.find("[lay-submit]").length == 0) {

            var $btnSubmit = $("<button>");
            $btnSubmit.attr("lay-submit", "");
            $btnSubmit.attr("lay-filter", "*");
            $btnSubmit.hide();

            $("#" + formId).prepend($btnSubmit);
        } else {
            $btnSubmit = $form.find("[lay-submit]");
        }
        layui.form.on('submit(*)', function (data) {
            validCallback(data);
            return false;
        });
        $btnSubmit.click();
    },

    initForm: function () {
        var $forms = $(".layui-form");
        var $requiredInputs = $forms.find("[lay-verify*='required']");
        $requiredInputs.each(function () {
            var $self = $(this);
            var $label = $self.parent().find("label");

            if ($label.length == 0) {
                $label = $self.parent().parent().find("label");
            }
            $label.prepend($("<span>").html("*").css({
                "color": "red"
            }));
        });
    },

    openSelectDialogUser: function (onOkHandler) {
        zwb.util.lay.openDialog("/common/select/user_dialog", "950px", "530px", function (index, layero) {
            var iframeWin = window[layero.find('iframe')[0]['name']];
            var arr = iframeWin.getSelectedArray();
            if (arr.length == 0) {
                zwb.util.lay.alert("没有选择记录！");
                return;
            }
            if (onOkHandler) {
                onOkHandler(arr);
            }
            layui.layer.close(index);
        }, {
            title: "用户选择",
            btn: ['确定', '关闭']
        });
    }

}