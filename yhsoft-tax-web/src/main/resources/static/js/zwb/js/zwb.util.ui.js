zwb.namespace("zwb.util.ui");

zwb.util.ui = {

    renderZTree: function (id, sqlId, options) {
        options = $.extend({
            showCheck: false,
            chkboxType: {"Y": "ps", "N": "ps"},
            chkStyle: "checkbox",
            bizKey: ""
        }, options);
        var isAsync = options.isAsync;
        var onClickHandler = options.onClickHandler;
        var onDblClickHandler = options.onDblClickHandler;
        var onRightClickHandler = options.onRightClickHandler;
        var rootName = options.rootName;
        var zTreeObj;
        if (isAsync) {
            isAsync = 1;
        } else {
            isAsync = 0;
        }
        var url = zwb.settings.contextPath + "/common/ztree/getNodes?sqlId="
            + sqlId + "&isAsync=" + isAsync + "&bizKey=" + options.bizKey;
        var dataFilterHandler = null;
        var dblClickExpandHandler = null;
        if (rootName) {
            dataFilterHandler = function (treeId, parentNode, responseData) {
                if (!parentNode) {
                    responseData.push({
                        id: 'root', name: rootName, pId: null, level: 0, open: true
                    });
                }
                return responseData;
            };
            dblClickExpandHandler = function (treeId, treeNode) {
                return treeNode.id != "root";
            };
        }
        var setting = {
            async: {
                enable: true,
                url: url,
                autoParam: ["id=pId", "name", "level"],
                dataFilter: dataFilterHandler
            },
            check: {
                enable: options.showCheck,
                chkboxType: options.chkboxType
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ''
                }
            },
            callback: {
                onClick: onClickHandler,
                onDblClick: onDblClickHandler,
                onRightClick: onRightClickHandler
            },
            view: {
                dblClickExpand: dblClickExpandHandler
            }
        };
        zTreeObj = $.fn.zTree.init($("#" + id), setting);
        return zTreeObj;
    },

    fileUpload: function (pickerId, tplId, bizId, successHandler, options) {
        var uploader;
        var pickerHtml = $("#" + pickerId).html();
        options = options || {};
        options = $.extend({
            multiple: false,
            accept: {},
            maxFileSize: 1024//单位：M
        }, options);
        uploader = WebUploader.create({
            // swf文件路径
            swf: zwb.settings.contextPath + '/js/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: zwb.settings.contextPath + '/common/fileupload/upload?tplId=' + tplId + '&bizId=' + bizId,
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {id: '#' + pickerId, multiple: options.multiple},
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            accept: options.accept
        });
        uploader.on("beforeFileQueued", function (file) {
            if (file.size > options.maxFileSize * 1024 * 1024) {
                alert("文件" + file.name + "大小超过" + options.maxFileSize + "M，不能上传！")
                return false;
            }
            return true;
        });
        uploader.on("fileQueued", function (file) {

        });
        uploader.on("filesQueued", function (files) {
            uploader.upload();
        });
        uploader.on("uploadProgress", function (file, percentage) {

        });
        uploader.on("uploadSuccess", function (file, response) {
            if (successHandler) {
                successHandler(response.data);
            }
        });
        uploader.on("uploadError", function (file, reason) {
            zwb.util.lay.alert("文件" + file.name + "上传失败！");
        });
        uploader.on("startUpload", function () {
            $("#" + pickerId + " .webuploader-pick").html('正在上传…');
        });
        uploader.on("uploadFinished", function () {
            $("#" + pickerId + " .webuploader-pick").html(pickerHtml);
        });
        return uploader;
    },

    getFileDownloadUrl: function (fId) {
        return zwb.settings.contextPath + '/common/fileupload/download?fId=' + fId;
    }

}