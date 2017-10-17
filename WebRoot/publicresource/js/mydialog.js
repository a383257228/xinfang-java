/// <reference path="jquery-1.9.1.js" />
///by mrhuo
var autoCloseTimer = 0;
var dialog = {
    removeDialog: function () {
        $("div.normal-dialog").remove();
        $("div#normalDialogMask").fadeOut(function () {
            $(this).remove();
        });
    },
    showNormalDialog: function (dialogTitle, dialogContent, buttons) {
        dialog.removeDialog();

        var mask = $('<div id="normalDialogMask"></div>');
        var normalDialogHtml =
            (
                '<div class="normal-dialog">' +
                '<div class="title"><b>{#title}</b><a href="javascript:;" data-id="close">×</a></div>' +
                '<div class="content">{#content}</div>' +
                '<div class="buttons">{#buttons}</div>' +
                '</div>'
            )
            .replace(/\{#title\}/ig, dialogTitle)
            .replace(/\{#content\}/ig, dialogContent);

        if (buttons) {
            var buttonHtml = [];
            if (buttons.ok) {
                var okHtml = '<button data-id="ok">{#buttonText}</button>'.replace(/\{#buttonText\}/ig, buttons.ok.name || "确认");
                buttonHtml.push(okHtml);
            }
            if (buttons.cancel) {
                var cancelHtml = '<button data-id="cancel">{#buttonText}</button>'.replace(/\{#buttonText\}/ig, buttons.cancel.name || "确认");
                buttonHtml.push(cancelHtml);
            }
            normalDialogHtml = normalDialogHtml.replace(/\{#buttons\}/ig, buttonHtml.join(''));
        } else {
            normalDialogHtml = normalDialogHtml.replace(/\{#buttons\}/ig, "");
        }

        $("body").append(mask);
        $("body").append(normalDialogHtml);

        mask.animate({ opacity: 1 });

        $("div.normal-dialog").find('a[data-id="close"]').click(dialog.removeDialog);
        if (buttons && buttons.ok) {
            var ok = buttons.ok.click || dialog.removeDialog;
            $("div.normal-dialog").find('button[data-id="ok"]').click(buttons.ok.click);
        }
        if (buttons && buttons.cancel) {
            var cancel = buttons.cancel.click || dialog.removeDialog;
            $("div.normal-dialog").find('button[data-id="cancel"]').click(cancel);
        }

        $(document).keyup(function (event) {
            if ($("div.normal-dialog").length && event.which == 27) {
                dialog.removeDialog();
            }
        });
    },
    showMiniDialogtop: function (className, msg, autoClose,height) {
    	if(!height){
    		height=5;
    	}
        $(".mini-dialog").remove();
        if (autoCloseTimer != 0) {
            clearTimeout(autoCloseTimer);
        }
        autoClose = autoClose || 3000;

        var miniDialogHtml =
            '<div class="mini-dialog {#class}" style="display:none;">{#content}</div>'
            .replace(/\{#class\}/ig, className)
            .replace(/\{#content\}/ig, msg);

        $("body").append($(miniDialogHtml));
        
        $(".mini-dialog." + className).stop().fadeIn().animate({ top: height+"%", opacity: 1 });
        autoCloseTimer = setTimeout(function () {
            $(".mini-dialog." + className).stop().animate({ top: "52%", opacity: 0 }, 1000, function () {
                $(this).fadeOut(function () {
                    $(this).remove();
                });
            });
        }, autoClose);
    },
    showMiniDialog: function (className, msg, autoClose) {
        $(".mini-dialog").remove();
        if (autoCloseTimer != 0) {
            clearTimeout(autoCloseTimer);
        }
        autoClose = autoClose || 3000;

        var miniDialogHtml =
            '<div class="mini-dialog {#class}" style="display:none;">{#content}</div>'
            .replace(/\{#class\}/ig, className)
            .replace(/\{#content\}/ig, msg);

        $("body").append($(miniDialogHtml));
        
        $(".mini-dialog." + className).stop().fadeIn().animate({ top: "15%", opacity: 1 });
        autoCloseTimer = setTimeout(function () {
            $(".mini-dialog." + className).stop().animate({ top: "52%", opacity: 0 }, 1000, function () {
                $(this).fadeOut(function () {
                    $(this).remove();
                });
            });
        }, autoClose);
    }
};

