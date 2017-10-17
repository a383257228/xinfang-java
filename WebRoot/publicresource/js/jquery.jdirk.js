/**
* jQuery JavaScript Library v1.9.x && v2.x
* http://jquery.com/
*
* Copyright 2005, 2015 jQuery Foundation, Inc. and other contributors
* Released under the MIT license
* http://jquery.org/license
*
* jQuery Extensions Basic Library �����������߰� v1.0 beta
* jquery.jdirk.js
*
* �����jquery 1.11.x/2.1.x late
*
* Copyright 2013-2015 ChenJianwei personal All rights reserved.
* http://www.chenjianwei.org
*/
(function (window, $, undefined) {


    var version = "2015-07-14",

        //  ���� �ַ�������(String) ��չ�����Ԫ
        coreString = function () { return String.apply(this, arguments); },

        //  ���� ���ڶ���(Date) ��չ�����Ԫ
        coreDate = function () { return Date.apply(this, arguments); },

        //  ���� ��ֵ����(Number) ��չ�����Ԫ
        coreNumber = function () { return Number.apply(this, arguments); },

        //  ���� �������(Array) ��չ�����Ԫ
        coreArray = function () { return Array.apply(this, arguments); },

        //  ���� ����ֵ����(Boolean) ��չ�����Ԫ
        coreBoolean = function () { return Boolean.apply(this, arguments); },

        //  ���� ͨ�ù��߷��� ��չ�����Ԫ
        coreUtil = function () { return Object.apply(this, arguments); },

        //  ���� ��ֵ ���ϻ�Ԫ
        coreNullable = {},

        //  ���� jQuery ��չ�����Ԫ
        coreJquery = function () { return $.apply(this, arguments); },

        //  ���� HTML5 ������������Ԫ
        coreHtml5 = {};

    coreString.fn = coreString.prototype = {};
    coreDate.fn = coreDate.prototype = {};
    coreNumber.fn = coreNumber.prototype = {};
    coreArray.fn = coreArray.prototype = {};
    coreBoolean.fn = coreBoolean.prototype = {};
    coreUtil.fn = coreUtil.prototype = {};
    coreJquery.fn = coreJquery.prototype = {};

    coreNullable.String = new String();
    coreNullable.Date = new Date();
    coreNullable.Number = new Number();
    coreNullable.Array = [];
    coreNullable.Boolean = new Boolean();
    coreNullable.Function = new Function();
    coreNullable.Object = new Object();

    coreJquery.string = coreString;
    coreJquery.date = coreDate;
    coreJquery.number = coreNumber;
    coreJquery.array = coreArray;
    coreJquery.boolean = coreBoolean;
    coreJquery.util = coreUtil;
    coreJquery.nullable = coreNullable;
    coreJquery.html5 = coreHtml5;



    var document = coreUtil.document = window.document,
        location = coreUtil.location = window.location,
        docElem = coreUtil.docElem = document.documentElement,
        history = coreUtil.history = window.history,
        parent = coreUtil.parent = window.parent,
        top = coreUtil.top = window.top,
        $$ = coreJquery.emptyJquery = coreJquery.empty$ = coreJquery.$$ = coreUtil.emptyJquery = coreUtil.empty$ = coreUtil.$$ = $(),
        core_string = version,
        //core_date = coreNullable.Date,
        //core_number = coreNullable.Number,
        core_array = coreNullable.Array,
        //core_boolean = coreNullable.Boolean,
        //core_trim = core_string.trim,
        core_push = core_array.push,
        core_slice = core_array.slice,
        core_splice = core_array.splice,
        //core_sort = core_array.sort,
        //core_join = core_array.join,
        core_isArray = Array.isArray;


    //  ����汾
    coreUtil.version = version;

    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript ���ߣ��ṩ���õ� js ���߷�����
    //  �����������ж϶�������͡�url �����ȹ��ܡ�
    ///////////////////////////////////////////////////////////////////////////////////////////////

    //  ��ȡָ����������͡�
    coreUtil.type = $.type;

    //  ���Զ����Ƿ��Ǵ��ڣ��п�����Frame����
    coreUtil.isWindow = $.isWindow;

    //  ���Զ����Ƿ��ǲ�����Boolean������ֵ��
    coreUtil.isBoolean = function (obj) { return coreUtil.type(obj) == "boolean"; };

    //  ���Զ����Ƿ����ַ�����String������ֵ��
    coreUtil.isString = function (obj) { return coreUtil.type(obj) == "string"; };

    //  ���Զ����Ƿ������ڣ�Date������ֵ��
    coreUtil.isDate = function (obj) { return coreUtil.type(obj) == "date"; };

    //  ���Զ����Ƿ���������ʽ��RegExp����
    coreUtil.isRegExp = function (obj) { return coreUtil.type(obj) == "regexp"; };

    //  ���Դ���Ĳ����Ƿ���һ�� javscript ����
    coreUtil.isObject = function (obj) { return coreUtil.type(obj) == "object"; };

    //  ���Զ����Ƿ������飨Array����
    coreUtil.isArray = $.isArray;

    //  ���Զ����Ƿ��Ǻ�����
    //  ע�⣺��IE������������ṩ�ĺ�������'alert'���� DOM Ԫ�صķ������� 'getAttribute' ������Ϊ�Ǻ�����
    coreUtil.isFunction = $.isFunction;

    //  ���Զ����Ƿ�����ֵ����ֵ��ʽ���ַ�����
    //  ����������Ĳ����Ƿ����һ����ֵ������������������� true������������false���ò����������κ����͵ġ�
    coreUtil.isNumeric = coreUtil.likeNumber = coreUtil.likeNumeric = $.isNumeric;

    //  �ж϶����Ƿ�����ֵ���ͣ�
    coreUtil.isNumber = function (obj) { return coreUtil.type(obj) == "number"; };

    //  ���Զ����Ƿ��ǿն��󣨲������κ����ԣ���
    //  ��������ȼ�����������ԣ�Ҳ����ԭ�ͼ̳е����ԣ����û��ʹ��hasOwnProperty����
    coreUtil.isEmptyObject = $.isEmptyObject;

    //  ���Զ����Ƿ�Ϊ�գ��������κ����ԵĿն���null��undefined�����ַ�����ȫ�ո񣩡�
    //  ��������ȼ�����������ԣ�Ҳ����ԭ�ͼ̳е����ԣ����û��ʹ��hasOwnProperty����
    coreUtil.isEmptyObjectOrNull = function (obj) {
        switch (coreUtil.type(obj)) {
            case "string":
                return coreString.isNullOrWhiteSpace(obj);
            case "array":
                return obj.length == 0;
            case "date":
                return Date.parse(obj) == 0;
            case "object":
                return coreUtil.isEmptyObject(obj);
        }
        return obj == null || obj == undefined;
    };

    //  ���Զ����Ƿ��Ǵ���Ķ���ͨ�� "{}" ���� "new Object" �����ģ���
    coreUtil.isPlainObject = $.isPlainObject;

    //  �ж϶����Ƿ�Ϊ "δ����" ֵ(�� undefined)��
    coreUtil.isUndefined = function (obj) { return obj === undefined || typeof obj === "undefined"; };

    //  �ж϶����Ƿ�Ϊ��(Null)ֵ��
    coreUtil.isNull = function (obj) { return obj === null; };

    //  �ж϶����Ƿ�Ϊ "δ����" ֵ(�� undefined)���(Null)ֵ��
    coreUtil.isNullOrUndefined = function (obj) { return coreUtil.isUndefined(obj) || coreUtil.isNull(obj); };

    //  ���Զ���Ϊ "δ����" ֵ(�� undefined)����(Null)ֵ��Boolean-Falseֵ�����ַ���ֵ������0�е��κ�һ�֡�
    coreUtil.isPositive = function (obj) { return obj ? true : false; };

    //  �ж϶����Ƿ�Ϊ "δ����" ֵ(�� undefined)����(Null)ֵ��Boolean-Falseֵ�����ַ���ֵ������0�е�һ�֡�
    coreUtil.isNegative = function (obj) { return obj ? false : true; };

    //  ���Զ����Ƿ��� jQuery ����
    coreUtil.isJqueryObject = function (obj) { return obj != null && obj != undefined && ((obj.jquery ? true : false) || obj.constructor === $$.constructor); };

    //  �ж϶����Ƿ���һ���յ� jQuery ����(�������κ� DOM Ԫ�أ��� length = 0)��
    coreUtil.isEmptyJquery = coreUtil.isEmptyJqueryObject = function (obj) { return coreUtil.isJqueryObject(obj) && !obj.length; };

    //  ����һ���պ���
    coreUtil.noop = coreUtil.isFunction($.noop) ? $.noop : function () { };

    //  �жϴ�����ַ����Ƿ�ΪNull����Ϊ���ַ�������ȫ�ǿո�
    coreUtil.trim = $.trim;

    //  ��һ�� DOM ������߱��ʽ����Ϊ jQuery ����
    //  ����ö�������Ѿ���һ�� jQuery ������ֱ�ӽ��䷵�ء�
    coreUtil.parseJqueryObject = coreUtil.parseJquery = function (obj) { return coreUtil.isJqueryObject(obj) ? obj : $(obj); };

    //  ���һ�������Ƿ�Ϊһ����������������������ԣ���������ķ��ʷ�ʽ������ length ���ԡ��Ҿ���������Ϊ���ֵ�������������
    //  ע�⣺�˷������� �ַ��� ʱִ�У�Ҳ�᷵�� true����Ϊ �ַ��� ��һ���ַ����顣
    coreUtil.likeArray = function (obj) {
        if (obj == null || obj == undefined || coreUtil.isWindow(obj)) {
            return false;
        }
        if (obj.nodeType === 1 && obj.length) {
            return true;
        }
        var type = coreUtil.type(obj);
        return type === "array" || type !== "function" && coreUtil.isNumeric(obj.length) && obj.length >= 0;
    };

    //  ���һ�������Ƿ�Ϊһ����������������������ԣ���������ķ��ʷ�ʽ������ length ���ԡ��Ҿ���������Ϊ���ֵ��������������Ҳ����ַ���
    coreUtil.likeArrayNotString = function (obj) {
        return coreUtil.likeArray(obj) && !coreUtil.isString(obj);
    };

    //  ��ȡ��ǰҳ�� url ������
    //  ����ֵ���÷�������һ�����飬�����е�ÿ��Ԫ�ض���һ�� JSON ���󣬸� JSON ��������������ԣ�
    //      name:   ��ʾ url ���������ƣ�
    //      value:  ��ʾ url ������ֵ��
    //  Ҳ����ͨ��������������ٷ���ĳ���ض����ƵĲ���ֵ�������磺coreUtil.getRequest()["id"]��
    coreUtil.getRequest = function () {
        var search = location.search;
        if (search.substr(0, 1) == "?") { search = search.substr(1, search.length - 1); }
        var result = [];
        if (search.length > 0) {
            var params = search.split("&");
            for (var i = 0; i < params.length; i++) {
                var param = params[i];
                var pos = param.indexOf("=");
                var name = param.substring(0, pos);
                var value = param.substr(pos + 1);
                result.push({ name: name, value: value });
                result[name] = value;
            }
        }
        return result;
    };
    coreUtil.request = coreUtil.getRequest();

    //  ����һ�� Guid(ȫ��Ψһ��ʶ��) ֵ���ú����������²�����
    //      format: String ����ֵ��һ������ʽ˵��������ָʾ��θ�ʽ���� Guid ��ֵ��?format ���������ǣ�
    //          "N":    ����ֵ�ĸ�ʽ 32 λ(xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
    //          "D":    ����ֵ�ĸ�ʽ �����ַ��ָ��� 32 λ����(xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)
    //          "B":    ����ֵ�ĸ�ʽ ���ڴ������С������ַ��ָ��� 32 λ����({xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx})
    //          "P":    ����ֵ�ĸ�ʽ ����Բ�����С������ַ��ָ��� 32 λ����((xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx))
    //          ��� format Ϊ null ����ַ��� ("")����ʹ�� "D"��
    //      length: Number ����ֵ����ʾ�����ַ����ĳ��ȣ����������ò�������ȫ�����ء�
    coreUtil.guid = function (format, length) {
        format = coreUtil.isString(format) ? format.toLowerCase() : "d";
        length = (length == null || length == undefined || !coreUtil.isNumeric(length)) ? 32 : length;
        if (length > 32 || length == 0) { length = 32; }
        if (length < -32) { length = -32; }
        var ret = "";
        for (var i = 1; i <= 32; i++) {
            ret += Math.floor(Math.random() * 16.0).toString(16);
            if ((i == 8) || (i == 12) || (i == 16) || (i == 20)) { ret += "-"; }
        }
        switch (format) {
            case "n": ret = coreString.replaceAll(ret, "-", ""); break;
            case "b": ret = "{" + ret + "}"; break;
            case "p": ret = "(" + ret + ")"; break;
            case "d": default: break;
        }
        return length >= 0 ? coreString.left(ret, length) : coreString.right(ret, Math.abs(length));
    };

    //  ��ȡ��ǰӦ�ó��������������ַ���֣����صĽ����ʽ��( http://127.0.0.1 )
    coreUtil.getHostPath = function () {
        var href = location.href;
        var pathname = location.pathname;
        return href.substr(0, href.lastIndexOf(pathname));
    };
    coreUtil.hostPath = coreUtil.getHostPath();

    //  ���ص�ǰҳ�治������������·����
    coreUtil.currentUri = coreUtil.hostPath + location.pathname;

    //  ���ص�ǰҳ������Ŀ¼������·����
    coreUtil.currentPath = coreUtil.currentUri.substr(0, coreUtil.currentUri.lastIndexOf("/"));

    //  ��ʾ��ǰӦ�ó�����Ƕ�׵�����Ŀ¼�Ĳ�����Ĭ��Ϊ 0����ʾδǶ������Ŀ¼��
    coreUtil.rootDegree = 0;

    //  ���ص�ǰӦ�ó��򣨺�վ������������Ŀ¼·������������·����
    //  �÷���������ȫ�ֲ��� coreUtil.rootDegree ��ֵ��
    //  coreUtil.rootDegree ��ȫ�ֲ�����ʾ ����Ŀ¼ �Ĳ�����
    //  coreUtil.rootDegree ����������ȷ���÷������ܷ�����ȷ�Ľ����
    //  coreUtil.rootDegree Ĭ��ֵΪ 0����Ӧ�ó���û����������Ŀ¼��
    coreUtil.getRootPath = function () {
        var result = coreUtil.hostPath;
        var pathname = location.pathname;
        if (pathname.indexOf("/") > -1) {
            var paths = pathname.split("/");
            var temps = new Array();
            for (var i = 0; i < paths.length; i++) { if (paths[i].length > 0) { temps.push(paths[i]); } }
            for (var i = 0; i < coreUtil.rootDegree && i < temps.length; i++) { result += "/" + temps[i]; }
        }
        return result;
    };
    coreUtil.rootPath = coreUtil.getRootPath();

    //  ���ݴ���� uri ��ַ���ظ� uri �����Ӧ�ó���������ͻ��˷���url��ַ��
    //  ����� uri ��ַӦΪ�����Ӧ�ó����·����������ַ��
    //  �÷��������ڵ�ǰ�ļ��� coreUtil.rootPath ���ԡ�
    coreUtil.resolveClientUrl = coreUtil.resolveUrl = function (url) {
        url = String(url);
        if (coreString.isNullOrEmpty(url)) { return url; }
        if (coreString.isUrl(url)) { return url; }
        url = coreString.replaceAll(url, "\\", "/");
        while (url.substring(0, 2) == "./" || url.substring(0, 1) == "/") { url = url.substring(1, url.length); }
        var tmps1 = coreUtil.rootPath.split("/");
        var tmps2 = url.split("/");
        while (tmps1.length > 3 && tmps2.length > 1 && tmps2[0] == "..") { tmps1.pop(); tmps2.shift(); }
        while (tmps2.length > 1 && tmps2[0] == "..") { tmps2.shift(); }
        return tmps1.join("/") + "/" + tmps2.join("/");
    };

    //  �ڲ������ر���ʾȷ�ϵ������ֱ�ӹرյ�ǰ��������ڡ�
    coreUtil.closeWindowNoConfirm = function () {
        coreUtil.top.opener = null;
        coreUtil.top.open('', '_self', '');
        coreUtil.top.close();
    };

    //  �رյ�ǰ��������ڣ�ͬ window.close��
    coreUtil.closeWindow = window.close;

    //  ���ε�ǰҳ��� HTML Դ���롣
    //  �� bug��������ʹ�á�
    coreUtil.shieldSourceCode = function () {
        var source = document.body.innerHTML;  //��ȡ�ĵ���ԭ������
        document.open();                 //���ĵ�
        document.close();                //�ر��ĵ�
        document.body.innerHTML = source;  //����д�������
    };

    //  ���ε�ǰҳ�������Ҽ�Ĭ���¼���������ָ���Ļص�������
    //  ����ص�����Ϊ�գ���������Ҽ�û���κη�Ӧ��
    coreUtil.shieldContxtMenuEvent = function (callback) {
        document.oncontextmenu = function (e) {
            e.preventDefault();
            if (callback && coreUtil.type(callback) == "function") { callback.apply(this, arguments); }
        };
    };


    function _loadJs(url, callback) {
        var heads = document.getElementsByTagName("head");
        var scripts = heads[0].getElementsByTagName("script");
        var isFunc = coreUtil.isFunction(callback);
        for (var i = 0; i < scripts.length; i++) {
            var s = scripts[i];
            if (s.src == url) { if (isFunc) { callback.call(s); } return; }
        }
        var done = false;
        var script = document.createElement('script');
        script.type = "text/javascript";
        script.language = "javascript";
        script.src = url;
        script.onload = script.onreadystatechange = function () {
            if (!done && (!script.readyState || script.readyState == "loaded" || script.readyState == "complete")) {
                done = true;
                script.onload = script.onreadystatechange = null;
                if (isFunc) { callback.call(script); }
            }
        };
        heads[0].appendChild(script);
    }
    function _loadCss(url, callback) {
        var link = document.createElement('link');
        link.rel = "stylesheet";
        link.type = "text/css";
        link.media = "screen";
        link.href = url;
        document.getElementsByTagName("head")[0].appendChild(link);
        var isFunc = coreUtil.isFunction(callback);
        if (isFunc) { callback.call(link); }
    }

    //  ��̬����һ������ javascript �ű��ļ�����ǰҳ���ĵ�����������ɹ������ָ���Ļص�������
    //  ���� url ��ʾ��Ҫ����� javascript �ű�·���������Ҫһ�����������ű����� url ������һ�����飬������ÿ��Ԫ�ر�ʾ javascript �ű�·����
    coreUtil.loadJs = function (url, callback) {
        if (coreUtil.likeArrayNotString(url)) {
            for (var i = 0; i < url.length; i++) {
                var fn = (i == url.length - 1) ? callback : null;
                _loadJs(url[i], fn);
            }
        } else { _loadJs(url, callback); }
    };

    //  һ����ִ��ĳ�� javascript �ű��ļ�������ִ�гɹ������ָ���Ļص�������
    coreUtil.runJs = function (url, callback) {
        var isFunc = coreUtil.isFunction(callback);
        _loadJs(url, function () {
            document.getElementsByTagName("head")[0].removeChild(this);
            if (isFunc) { callback(); }
        });
    };

    //  ��̬����һ������ css ��ʽ���ļ�����ǰҳ���ĵ�����������ɹ������ָ���Ļص�������
    coreUtil.loadCss = function (url, callback) {
        if (coreUtil.likeArrayNotString(url)) {
            for (var i = 0; i < url.length; i++) {
                var fn = (i == url.length - 1) ? callback : null;
                _loadCss(url, fn);
            }
        } else {
            _loadCss(url, callback);
        }
    };

    //  ��ĳ�����������пɼ����Խ��ж��Ƕ�׵ݹ�ѭ������ĳ���������ú����������²�����
    //      obj:    Ŀ�����
    //      call:   Ҫ�����Ŀ�����ѭ�����õķ���
    //      times:  Ƕ�׵Ĳ���
    coreUtil.eachCall = function (obj, call, times) {
        if (!coreUtil.isFunction(call)) { return; }
        if (obj == undefined) { obj = coreNullable.Object; }
        if (times == undefined || times < 0) { times = 1; }
        if (times == 0) { return obj; }
        call.call(this, obj);
        for (var i = 1; i <= times; i++) { for (var key in obj) { coreUtil.eachCall.call(this, obj[key], call, times - 1); } }
    };

    //  ��ֹ�������������ԡ�
    //  ģ�� Object.preventExtensions ������
    coreUtil.preventExtensions = function (obj, times) {
        coreUtil.eachCall.call(this, obj, Object.preventExtensions, times);
    };
    //  ��ֹ�޸��������Ե����ԣ�����ֹ��������ԡ�
    //  ģ�� Object.seal ������
    coreUtil.seal = function (obj, times) {
        coreUtil.eachCall.call(this, obj, Object.seal, times);
    };
    //  ��ֹ�޸��������Ե����Ժ�ֵ������ֹ��������ԡ�
    //  ģ�� Object.freeze ������
    coreUtil.freeze = function (obj, times) {
        coreUtil.eachCall.call(this, obj, Object.freeze, times);
    };

    //  ��ָ���ĺ���������ú����������ʽ���ú����������²�����
    //      code:       ���衣Ҫ���õĺ�����Ҫִ�е� JavaScript ���봮��
    //      millisec:   ��ѡ����ִ�д���ǰ��ȴ��ĺ�������
    //  ģ�� setTimeout/setImmediate ������
    //  ��ע�������������� millisec ��ò���ֵΪ 0�����Զ����� setImmediate(�÷�������� setTimeout ������Ч�����������Դ����) ������
    coreUtil.delay = function (code, millisec) {
        if (!code) { return; }
        return !millisec && window.setImmediate ? window.setImmediate(code) : window.setTimeout(code, millisec);
    };

    //  ȡ���� coreUtil.delay / $.util.delay �������õĺ����ӳٵ��õȴ����ú����������²�����
    //      id_of_delay: ���롣Ҫȡ�����ӳٵȴ��������õ� handle ֵ����ֵ�ɷ��� coreUtil.delay / $.util.delay ���أ���
    //      undelayJust: ��ѡ��Boolean ����ֵ����ʾҪȡ�����ӳٺ����Ƿ�Ϊ��ʱ���ú�������ͨ�� coreUtil.delay / $.util.delay �趨�ú���ʱδ���� millisec ֵ�� millisec ֵΪ 0�������ò���Ĭ��Ϊ false��
    //  ģ�� clearTimeout/clearImmediate ������
    //  ��ע�������������� handle ��ò���ֵΪ 0����ִ���κζ�����

    coreUtil.undelay = function (id_of_delay, undelayJust) {
        var id = id_of_delay;
        if (!id) return;

        undelayJust = undelayJust == null || undelayJust == undefined ? false : !!undelayJust;
        if (undelayJust) {
            return window.clearImmediate ? window.clearImmediate(id) : window.clearTimeout(id);
        } else {
            return window.clearTimeout(id);
        }
    };

    //  �� try...catch... ��ʽִ��ָ���ĺ�������飻�ú����ṩ�������ط�ʽ��
    //      function (options):�������У����� options ��ʾһ�� JSON-Object.
    //          ��ʽ�� { code: function|string , error: function|string , final: function|string , tryError: boolean , tryFinal: boolean }
    //          ���� code ��ʾ���� try ��ִ�еĺ��������.
    //               error ��ʾ�� code ִ�г���ʱ��ִ�еĴ����.
    //               final ��ʾ�� code �� error ִ����ɺ�ִ�еĴ����.
    //               tryError ָ�� error �Ƿ�ͬ���� try...catch... ��ʽִ��.
    //               tryFinal ָ�� final �Ƿ�ͬ���� try...catch... ��ʽִ��.
    //      function (code, error, final): �����ؽ��ᱻ�Զ�ת���� function ({ code: code, error: error, final: finall, tryError: false, tryFinal: false }) ��ʽִ�У�
    //  ����ֵ����� code �����ִ�гɹ����򷵻ظô�����ִ�з���ֵ��
    //      �����ж� error �� final ������Ƿ���з���ֵ��
    //          �������������鶼�з���ֵ����ȡ final ��ִ�н�����أ�
    //          ��� error �� final ���������ֻ������һ�����з���ֵ���򷵻��Ǹ����з���ֵ�Ĵ�����ִ�н����
    coreUtil.tryExec = function (code, error, final) {
        var defaults = {
            code: null, error: null, final: null, tryError: false, tryFinal: false
        };
        var opts = $.extend(defaults, typeof code == "object" ? code : { code: code, error: error, final: final }), ret;
        if (typeof opts.code == "string") { opts.code = coreString.toFunction(opts.code); }
        if (typeof opts.error == "string") { opts.error = coreString.toFunction(opts.error); }
        if (typeof opts.final == "string") { opts.final = coreString.toFunction(opts.final); }
        try {
            if (coreUtil.isFunction(opts.code)) {
                ret = opts.code();
            }
        } catch (e) {
            if (coreUtil.isFunction(opts.error)) {
                var a = opts.tryError ? coreUtil.tryExec(opts.error) : opts.error(e);
                if (a != undefined) { ret = a; }
            }
        } finally {
            if (coreUtil.isFunction(opts.final)) {
                var b = opts.tryFinal ? coreUtil.tryExec(opts.final) : opts.final();
                if (b != undefined) { ret = b; }
            }
        }
        return ret;
    };





    var _matched, _browser;
    var _userAgentMatch = function (userAgent) {
        userAgent = userAgent.toLowerCase();
        var match = /(chrome)[ \/]([\w.]+)/.exec(userAgent) ||
		    /(webkit)[ \/]([\w.]+)/.exec(userAgent) ||
		    /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(userAgent) ||
		    /(msie) ([\w.]+)/.exec(userAgent) ||
		    userAgent.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(userAgent) || [];
        return { browser: match[1] || "", version: match[2] || "0" };
    };
    _matched = _userAgentMatch(window.navigator.userAgent);
    _browser = {};
    if (_matched.browser) { _browser[_matched.browser] = true; _browser.version = _matched.version; }
    if (_browser.chrome) { _browser.webkit = true; } else if (_browser.webkit) { _browser.safari = true; }

    //  ��ȡ������������Լ��汾�š�
    //  �ж�������汾ʾ�����ж�������Ƿ�ΪIE��  coreUtil.browser.msie == true���ж�������Ƿ�Ϊ Chrome��coreUtil.browser.chrome == true
    //  �ж�������汾�ţ�coreUtil.browser.version��IE�¿��ܵ�ֵΪ 6.0/7.0/8.0/9.0/10.0 �ȵȡ�
    coreUtil.browser = _browser;




    //  ����Ĭ�ϵĶ���ȽϺ������ú�������һ�� bool ֵ��ʾ��������������Ƿ��ֵ��
    coreUtil.equalsCompare = function (item1, item2) { return item1 == item2; };

    //  ����Ĭ�ϵ���ֵ�ȽϺ������ú�������һ�� int ֵ���÷���ֵ���������£�
    //      ������� 0�����ʾ a > b��
    //      ���С�� 0�����ʾ a < b��
    //      ������� 0�����ʾ a == b��
    coreUtil.numericCompare = function (a, b) {
        if (!coreUtil.isNumeric(a) && coreString.isNumeric(a)) { a = parseFloat(a, 10); }
        if (!coreUtil.isNumeric(b) && coreString.isNumeric(b)) { b = parseFloat(b, 10); }
        if (a > b) { return 1; } else if (a < b) { return -1; } else { return 0; }
    };

    //  ȷ������ javascript �����Ƿ��ֵ���ú����������²���:
    //      item1: ���ȽϵĶ���1��
    //      item2: ���ȽϵĶ���2�����ںͶ���1���бȽϣ�
    //      compare: ���ڱȽ�����ĺ������ú������ڱȽ� item1 �Ƿ��� item2 ��ֵ���ú�������һ�� bool ֵ������һ����ѡ������
    //          �ú�����ǩ��Ӧ���� function (item1, item2) { }������ item1 ��ʾԴ�����е��item2 ��ʾҪ���бȽϵ��
    //          ������������ compare����ʹ��Ĭ�ϵıȽ������ "==" ����ֵ��ƥ�䣻
    //  ����ֵ����� item1 �� item2 ��ֵ���򷵻� true�����򷵻� false��
    coreUtil.equals = function (item1, item2, compare) {
        var isFunc = coreUtil.isFunction(compare);
        if (!isFunc) { compare = coreUtil.equalsCompare; }
        return compare.call(this, item1, item2) == true;
    };

    //  �Ƚ����� javascript ����Ĵ�С���ú����������²�����
    //      item1: ���ȽϵĶ���1��
    //      item2: ���ȽϵĶ���2�����ںͶ���1���бȽϣ�
    //      compare: �ȽϺ������ú�����ѭ�����ã����ڱȽ� array ��û����Ĵ�С������һ����ѡ������
    //          �ú�����ǩ��Ϊ function (a, b) { }������ a��b �ֱ��ʾԴ�����еĴ��Ƚϴ�С����ú������뷵��һ����ֵ��ʾ�ȽϺ�Ľ����
    //              ��� a > b ���򷵻�һ�� ���� 0 ��ֵ��
    //              ��� a < b ���򷵻�һ�� С�� 0 ��ֵ��
    //              ��� a == b���򷵻� 0��
    //          ���������ò�������Ĭ�Ͻ� array �е�ÿһ������������бȽϡ�
    //  ����ֵ����� item1 > item2 ���򷵻�һ�� ���� 0 ��ֵ��
    //          ��� item1 < item2 ���򷵻�һ�� С�� 0 ��ֵ��
    //          ��� item1 == item2���򷵻� 0��
    coreUtil.compare = function (item1, item2, compare) {
        var isFunc = coreUtil.isFunction(compare);
        if (!isFunc) { compare = coreUtil.numericCompare; }
        return compare.call(this, item1, item2);
    };



    //  ���� JSON ����ָ�������б�����ԣ������ظ� JSON �����һ���¸������ú����������²�����
    //      obj: �������� JSON ����
    //      propertieNames:��Ҫ�� obj ���ų�����ȡ���������ƣ�Ϊһ��������������е�ÿһ���һ�� String ����ֵ��
    //      excluding: Boolean ����ֵ��Ĭ��Ϊ false��
    //          ���Ϊ true ���ʾ�� obj ����ȡ�б���ʾ������ֵ��
    //          ���Ϊ false ���ʾ�� obj ���ų��б���ʾ������ֵ��
    //  ����ֵ������һ�� JSON ���󣬸ö���Ϊ����� obj ��ȡ���ų�ָ���б���ʾ����ֵ���һ���� JSON-Object ������
    coreUtil.filterProperties = function (obj, propertieNames, excluding) {
        propertieNames = coreUtil.likeArrayNotString(propertieNames) ? propertieNames : [];
        var ret = {};
        for (var k in obj) {
            if (excluding ? coreArray.contains(propertieNames, k) : !coreArray.contains(propertieNames, k)) { ret[k] = obj[k]; }
        }
        return ret;
    };

    //  �ų� JSON ����ָ�������б�����ԣ������ظ� JSON �����һ���¸������ú����������²�����
    //      obj: �������� JSON ����
    //      propertieNames:��Ҫ�� obj ���ų����������ƣ�Ϊһ��������������е�ÿһ���һ�� String ����ֵ��
    //  ����ֵ������һ�� JSON ���󣬸ö������ obj �г� propertieNames �б�����������ԡ�
    coreUtil.excludeProperties = function (obj, propertieNames) {
        return coreUtil.filterProperties(obj, propertieNames, false);
    };

    //  ��ȡ JSON ����ָ�������б�����ԣ������ظ� JSON �����һ���¸������ú����������²�����
    //      obj: �������� JSON ����
    //      propertieNames:��Ҫ�� obj ����ȡ���������ƣ�Ϊһ��������������е�ÿһ���һ�� String ����ֵ��
    //  ����ֵ������һ�� JSON ���󣬸ö������ obj �� propertieNames �б��ƶ����������ԡ�
    coreUtil.extractProperties = function (obj, propertieNames) {
        return coreUtil.filterProperties(obj, propertieNames, true);
    };



    //  ��չ $.string��$.date��$.number��$.array��$.boolean��ʹ��Щ����ֱ���� String��Date��Number��Array��Boolean ����ĳ�Ա������Ϊ�侲̬���ԣ�
    $.each([
        {
            unit: String.prototype, prototype: coreString, methods: [
                "anchor", "big", "blink", "bold", "charAt", "charCodeAt", "concat", "fixed", "fontcolor", "fontsize", "indexOf", "italics",
                "lastIndexOf", "link", "localeCompare", "match", "replace", "search", "slice", "small", "split", "strike", "sub", "substr",
                "substring", "sup", "toLocaleLowerCase", "toLocaleString", "toLocaleUpperCase", "toLowerCase", "toUpperCase", "toString", "trim"
            ]
        },
        {
            unit: Date.prototype, prototype: coreDate, methods: [
                "getDate", "getDay", "getFullYear", "getHours", "getMilliseconds", "getMinutes", "getMonth", "getSeconds", "getTime",
                "getTimezoneOffset", "getUTCDate", "getUTCDay", "getUTCFullYear", "getUTCHours", "getUTCMilliseconds", "getUTCMinutes",
                "getUTCMonth", "getUTCSeconds", "getVarDate", "getYear", "setDate", "setFullYear", "setHours", "setMilliseconds", "setMinutes",
                "setMonth", "setSeconds", "setTime", "setUTCDate", "setUTCFullYear", "setUTCHours", "setUTCMilliseconds", "setUTCMinutes",
                "setUTCMonth", "setUTCSeconds", "setYear", "toDateString", "toGMTString", "toISOString", "toJSON", "toLocaleDateString",
                "toLocaleString", "toLocaleTimeString", "toString", "toTimeString", "toUTCString"
            ]
        },
        { unit: Number.prototype, prototype: coreNumber, methods: ["toExponential", "toFixed", "toLocaleString", "toPrecision", "toString"] },
        {
            unit: Array.prototype, prototype: coreArray, methods: [
                "concat", "every", "filter", "forEach", "indexOf", "join", "lastIndexOf", "map", "pop", "push", "reduce", "reduceRight",
                "reverse", "shift", "slice", "some", "sort", "splice", "toLocaleString", "toString", "unshift"
            ]
        },
        { unit: Boolean.prototype, prototype: coreBoolean, methods: ["toString"] }
    ], function (i, n) {
        $.each(n.methods, function (l, name) {
            var method = n.unit[name], hasMethod = method && coreUtil.isFunction(method) ? true : false;
            if (hasMethod && (n.prototype[name] == null || n.prototype[name] == undefined)) {
                n.prototype[name] = function () {
                    var thisArg = arguments[0], args = $.array.removeAt(arguments, 0);
                    return method.apply(thisArg, args);
                };
            }
        });
    });





    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript �ַ�(��)������������
    /////////////////////////////////////////////////////////////////////////////////////////////// 

    //  �жϴ���Ķ����Ƿ���һ���ַ�����
    coreString.isString = coreUtil.isString;

    //  �жϴ�����ַ����Ƿ�ΪNull����Ϊ���ַ�����
    coreString.isNullOrEmpty = function (str) { return str === undefined || str === null || str === ""; };
    coreString.prototype.isNullOrEmpty = function () { return coreString.isNullOrEmpty(this); };

    //  �жϴ�����ַ����Ƿ�ΪNull����Ϊ���ַ�������ȫ�ǿո�
    coreString.isNullOrWhiteSpace = function (str) { return coreString.isNullOrEmpty(str) || coreString.trim(String(str)) === ""; };
    coreString.prototype.isNullOrWhiteSpace = function () { return coreString.isNullOrWhiteSpace(this); };

    //  �жϴ�����ַ����Ƿ�Ϊ HTML ����Ρ�
    coreString.isHtmlText = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.length >= 3 && str.charAt(0) === "<" && str.charAt(str.length - 1) === ">";
    };
    coreString.prototype.isHtmlText = function () { return coreString.isHtmlText(this); };

    //  �����ַ����滻������ַ���ƥ��������Ӵ����÷���������Դ�ַ���������һ��������������ı�Դ�ַ�����ֵ��
    coreString.replaceAll = function (str, substr, replacement, ignoreCase) {
        if (!substr || substr == replacement) { return str; }
        var regexp = coreUtil.isRegExp(substr) ? substr : new RegExp(String(substr), ignoreCase ? "gm" : "igm");
        return str.replace(regexp, replacement);
    };
    coreString.prototype.replaceAll = function (substr, replacement) { return coreString.replaceAll(this, substr, replacement); };

    //  ��ʽ���ַ����������� .NET �е� string.format ��������
    //  ʹ�÷�����coreString.format('�ַ���{0}�ַ���{1}�ַ���','��һ������','�ڶ�������','����������', ...'�� N ������');
    //  �÷���������Դ�ַ���������һ��������������ı�Դ�ַ�����ֵ��
    coreString.format = function (str, arg1, arg2, arg3, argn) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var isArray = coreUtil.likeArrayNotString(arg1),
            data = (isArray && !coreUtil.isString(arg1)) || coreUtil.isObject(arg1) ? arg1 : coreArray.range(arguments, 1);
        if (isArray) {
            for (var i = 0; i < data.length; i++) {
                value = data[i] ? data[i] : "";
                str = str.replace(new RegExp("\\{" + i + "}", "gm"), value);
            }
        } else {
            for (var key in data) {
                var value = proxy.call(data, key);
                value = (value == null || value == undefined) ? "" : value;
                str = str.replace(new RegExp("\\{" + key + "}", "gm"), value);
            }
        }
        function proxy(key) { try { return eval("this[\"" + key + "\"]"); } catch (ex) { return ""; } }
        return str;
    };
    coreString.prototype.format = function (arg1, arg2, arg3, argn) {
        var args = coreArray.insert(arguments, 0, this);
        return coreString.format.apply(this, args);
    };

    //  ��ȡ�ַ��������� ASCII ���ַ�(�������ġ����ġ����ĵ�)�� byte �ֽڳ��ȡ�
    coreString.getByteLen = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var bytelen = 0,
            i = 0,
            length = str.length,
            charset = (document.charset || "utf-8").toLowerCase(),
            s = charset == "iso-8859-1" ? 5 : 2;
        for (; i < length; i++) {
            bytelen += str.charCodeAt(i) > 255 ? s : 1;
        }
        return bytelen;
    };
    coreString.prototype.getByteLen = function () { return coreString.getByteLen(this); };

    //  ��ȡ�ַ����д� index �����ſ�ʼ��ָ���ֽ������ַ������÷����������²�����
    //      str       : Դͷ�ַ���
    //      index     : str ��ʼλ�õ������ţ��� 0 ��ʼ����
    //      byteLength: ���ص��ַ������ֽ�����
    //  ����ֵ: �÷����� str �� index λ�ÿ�ʼ��ȡ�ֽڳ���Ϊ byteLength �����ݷ��أ������ȡ���ݵ����һ���ַ��ֽ���
    //      ����1��������ֵ������ʱ������ byteLength �����ơ�������������С�� byteLength��ʱ�����������һ���ַ������ء�
    coreString.substrByte = function (str, index, byteLength) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (byteLength == null || byteLength == undefined) {
            return str.substr(index);
        }
        var bytelen = 0,
            i = index,
            length = str.length,
            charset = (document.charset || "utf-8").toLowerCase(),
            s = charset == "iso-8859-1" ? 5 : 2;
        for (; i < length; i++) {
            bytelen += str.charCodeAt(i) > 255 ? s : 1;
            if (bytelen == byteLength) {
                break;
            } else if (bytelen > byteLength) {
                i--;
                break;
            }
        }
        return str.substring(0, i);
    };
    coreString.prototype.substrByte = function (index, byteLength) { return coreString.substrByte(this, index, byteLength); };

    //  �жϵ�ǰ�ַ��������Ƿ����ָ�����ַ������ݡ�
    coreString.contains = function (str, val) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return String(str).indexOf(val) > -1;
    };
    coreString.prototype.contains = function (val) { return coreString.contains(this, val); };

    //  �ַ�����ת���÷���������Դ�ַ���������һ��������������ı�Դ�ַ�����ֵ��
    coreString.reverse = function (str) {
        var charArray = [];
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        for (var i = str.length - 1; i > -1; i--) { charArray.push(str[i]); }
        return charArray.join("");
    };
    coreString.prototype.reverse = function () { return coreString.reverse(this); };

    //  ȥ���ַ�����ߵĿո񣻸÷���������Դ�ַ���������һ��������������ı�Դ�ַ�����ֵ��
    coreString.ltrim = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.replace(/(^\s*)/g, "");
    };
    coreString.prototype.ltrim = function () { return coreString.ltrim(this); };

    //  ȥ���ַ����ұߵĿո񣻸÷���������Դ�ַ���������һ��������������ı�Դ�ַ�����ֵ��
    coreString.rtrim = function () {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.replace(/(\s*$)/g, "");
    };
    coreString.prototype.rtrim = function () { return coreString.rtrim(this); };

    //  ȥ���ַ����������ߵĿո񣻸÷���������Դ�ַ���������һ��������������ı�Դ�ַ�����ֵ��
    coreString.trim = coreString.trim ? coreString.trim : coreUtil.trim;
    coreString.prototype.trim = function () { return coreString.trim(this); };

    //  ����һ�����ַ��������ַ���ͨ���ڴ�ʵ���е��ַ�������ո��ָ���ַ������ﵽָ�����ܳ��ȣ��Ӷ�ʹ��Щ�ַ��Ҷ��롣
    coreString.padLeft = function (str, len, paddingChar) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        paddingChar = coreString.isNullOrEmpty(paddingChar) || !paddingChar.length ? " " : paddingChar;
        len = coreUtil.isNumeric(len) ? len : str.length;
        if (str.length < len) { for (; str.length < len; str = paddingChar + str) { } }
        return str;
    };
    coreString.prototype.padLeft = function (len, paddingChar) { return coreString.padLeft(this, len, paddingChar); };

    //  ����һ�����ַ��������ַ���ͨ���ڴ��ַ����е��ַ��Ҳ����ո��ָ���ַ����ﵽָ�����ܳ��ȣ��Ӷ�ʹ��Щ�ַ������
    coreString.padRight = function (str, len, paddingChar) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        paddingChar = coreString.isNullOrEmpty(paddingChar) || !paddingChar.length ? " " : paddingChar;
        len = coreUtil.isNumeric(len) ? len : str.length;
        if (str.length < len) { for (; str.length < len; str += paddingChar) { } }
        return str;
    };
    coreString.prototype.padRight = function (len, paddingChar) { return coreString.padRight(this, len, paddingChar); };

    //  �����ַ����еĵ��ַ���ע��� 0 ��ʼ��
    coreString.mid = function (str, start, len) {
        if (!start) { start = 0; }
        if (!len) { len = 0; }
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.substr(start, len);
    };
    coreString.prototype.mid = function (start, len) { return coreString.mid(this, start, len); };

    //  �����ַ����Ĵ�ӡ���ȡ�
    coreString.lengthOfPrint = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.replace(/[^\x00-\xff]/g, "**").length;
    };
    coreString.prototype.lengthOfPrint = function () { return coreString.lengthOfPrint(this); };

    //  �жϵ�ǰ String �����Ƿ���ָ�����ַ�����ͷ��
    coreString.startsWith = function (str, val) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.substr(0, val.length) == val;
    };
    coreString.prototype.startsWith = function (val) { return coreString.startsWith(this, val); };

    //  �жϵ�ǰ String �����Ƿ���ָ�����ַ�����β��
    coreString.endsWith = function (str, val) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return str.substr(str.length - val.length) == val;
    };
    coreString.prototype.endsWith = function (val) { return coreString.endsWith(this, val); };

    //  ��ȡ��ǰ�ַ�����ߵ�ָ���������ݡ�
    coreString.left = function (str, len) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (!coreUtil.isNumeric(len)) { len = parseInt(len, 10); }
        if (len < 0 || len > str.length) { len = str.length; }
        return str.substr(0, len);
    };
    coreString.prototype.left = function (len) { return coreString.left(this, len); };

    //  ��ȡ��ǰ�ַ����ұߵ�ָ���������ݡ�
    coreString.right = function (str, len) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (!coreUtil.isNumeric(len)) { len = parseInt(len, 10); }
        if (len < 0 || len > str.length) { len = str.length; }
        return str.substring(str.length - len, str.length);
    };
    coreString.prototype.right = function (len) { return coreString.right(this, len); };

    //  ��ȡ��ǰ�ַ�����ߵ�ָ���ֽڳ������ݡ�
    coreString.leftBytes = function (str, len) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (!coreUtil.isNumeric(len)) { len = parseInt(len, 10); }
        var length = coreString.getByteLen(str), i = 0, bytelen = 0, cc = document.charset;
        if (!cc) { cc = "utf-8"; }
        cc = cc.toLowerCase();
        var s = cc == "iso-8859-1" ? 5 : 2;
        if (len < 0 || len > length) { len = length; }
        for (; i < str.length; i++) {
            bytelen += str.charCodeAt(i) > 255 ? s : 1;
            if (bytelen == len) { break; }
            if (bytelen > len) { i--; break; }
        }
        return coreString.left(str, i + 1);
    };
    coreString.prototype.leftBytes = function (len) { return coreString.leftBytes(this, len); };

    //  ��ȡ��ǰ�ַ����ұߵ�ָ���ֽڳ������ݡ�
    coreString.rightBytes = function (str, len) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (!coreUtil.isNumeric(len)) { len = parseInt(len, 10); }
        var length = coreString.getByteLen(str), i = 0, bytelen = 0, cc = document.charset;
        if (!cc) { cc = "utf-8"; }
        cc = cc.toLowerCase();
        var s = cc == "iso-8859-1" ? 5 : 2;
        if (len < 0 || len > length) { len = length; }
        for (; i < str.length; i++) {
            bytelen += str.charCodeAt(str.length - 1 - i) > 255 ? s : 1;
            if (bytelen == len) { break; }
            if (bytelen > len) { i--; break; }
        }
        return coreString.right(str, i + 1);
    };
    coreString.prototype.rightBytes = function (len) { return coreString.rightBytes(this, len); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�ĳ����ڸ�ʽ��
    coreString.isLongDate = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var r = str.replace(/(^\s*)|(\s*$)/g, "").match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
        if (r == null) { return false; }
        var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
        return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4] && d.getHours() == r[5] && d.getMinutes() == r[6] && d.getSeconds() == r[7]);
    };
    coreString.prototype.isLongDate = function () { return coreString.isLongDate(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�Ķ����ڸ�ʽ��
    coreString.isShortDate = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var r = str.replace(/(^\s*)|(\s*$)/g, "").match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
        if (r == null) { return false; }
        var d = new Date(r[1], r[3] - 1, r[4]);
        return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
    };
    coreString.prototype.isShortDate = function () { return coreString.isShortDate(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�����ڸ�ʽ��
    coreString.isDate = function (str) {
        return coreString.isLongDate(str) || coreString.isShortDate(str);
    };
    coreString.prototype.isDate = function () { return coreString.isDate(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�ĵ绰�����ʽ(�й�)��
    coreString.isTel = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(str);
    };
    coreString.prototype.isTel = function () { return coreString.isTel(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ���ֻ������ʽ(�й�)��
    coreString.isMobile = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^(13|14|15|16|17|18|19)\d{9}$/i.test(str);
    };
    coreString.prototype.isMobile = function () { return coreString.isMobile(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�ĵ绰��������ֻ������ʽ(�й�)
    coreString.isTelOrMobile = function (str) {
        return coreString.isTel(str) || coreString.isMobile(str);
    };
    coreString.prototype.isTelOrMobile = function () { return coreString.isTelOrMobile(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�Ĵ�������ʽ
    coreString.isFax = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(str);
    };
    coreString.prototype.isFax = function () { return coreString.isFax(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� ���������ַ(Email) ��ʽ��
    coreString.isEmail = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(str);
    };
    coreString.prototype.isEmail = function () { return coreString.isEmail(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� ��������(�й�) ��ʽ��
    coreString.isZipCode = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[\d]{6}$/.test(str);
    };
    coreString.prototype.isZipCode = function () { return coreString.isZipCode(this); };

    //  �жϵ�ǰ String �����Ƿ��Ƿ���ں����ַ���
    coreString.existChinese = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        //[\u4E00-\u9FA5]��h�֩o[\uFE30-\uFFA0]��ȫ�Ƿ�̖
        return !/^[\x00-\xff]*$/.test(str);
    };
    coreString.prototype.existChinese = function () { return coreString.existChinese(this); };

    //  ��֤����
    coreString.isChinese = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[\u0391-\uFFE5]+$/i.test(str);
    };
    coreString.prototype.isChinese = function () { return coreString.isChinese(this); };

    //  ��֤Ӣ��
    coreString.isEnglish = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[A-Za-z]+$/i.test(str);
    };
    coreString.prototype.isEnglish = function () { return coreString.isEnglish(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� �ļ�����(·��) ��ʽ��
    coreString.isFileName = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        //return !/[\\\/\*\?\|:"<>]/g.test(str);
        return !/[\\\/\*\?\|:<>]/g.test(str);
    };
    coreString.prototype.isFileName = function () { return coreString.isFileName(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� IPv4 ��ַ��ʽ��
    coreString.isIPv4 = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
        if (reSpaceCheck.test(str)) {
            str.match(reSpaceCheck);
            if (RegExp.$1 <= 255 && RegExp.$1 >= 0
                    && RegExp.$2 <= 255 && RegExp.$2 >= 0
                    && RegExp.$3 <= 255 && RegExp.$3 >= 0
                    && RegExp.$4 <= 255 && RegExp.$4 >= 0) {
                return true;
            } else { return false; }
        } else { return false; }
    };
    coreString.prototype.isIPv4 = function () { return coreString.isIPv4(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� url ��ʽ��
    coreString.isUrl = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        //        var strRegex = "^((https|http|ftp|rtsp|mms)?:               //)"
        //    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"    //ftp��user@
        //          + "(([0-9]{1,3}\.){3}[0-9]{1,3}"                          // IP��ʽ��URL- 199.194.52.184
        //          + "|"                                                     // ����IP��DOMAIN��������
        //          + "([0-9a-z_!~*'()-]+\.)*"                                // ����- www.
        //          + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\."                  // ��������
        //          + "[a-z]{2,6})"                                           // first level domain- .com or .museum
        //          + "|"                                                     // ����Ϊ����
        //          + "localhost|127.0.0.1"                                   // ����Ϊ������ַ
        //          + "(:[0-9]{1,4})?"                                        // �˿�- :80
        //          + "((/?)|"                                                // a slash isn't required if there is no file name
        //          + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        var strRegex = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
        var re = new RegExp(strRegex);
        return re.test(str);
    };
    coreString.prototype.isUrl = function () { return coreString.isUrl(this); };

    //  �ж��Ƿ�Ϊ�Ϸ��� ipv4 ���� url ��ʽ
    coreString.isUrlOrIPv4 = function (str) {
        return coreString.isUrl(str) || coreString.isIP(str);
    };
    coreString.prototype.isUrlOrIPv4 = function () { return coreString.isUrlOrIPv4(this); };

    //  �ж��Ƿ�Ϊ�Ϸ��Ļ��Ҹ�ʽ
    coreString.isCurrency = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^\d{0,}(\.\d+)?$/i.test(str);
    };
    coreString.prototype.isCurrency = function () { return coreString.isCurrency(this); };

    //  �ж��Ƿ�Ϊ�Ϸ��� QQ �����ʽ
    coreString.isQQ = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[1-9]\d{4,11}$/i.test(str);
    };
    coreString.prototype.isQQ = function () { return coreString.isQQ(this); };

    //  �ж��Ƿ�Ϊ�Ϸ��� MSN �ʺŸ�ʽ
    coreString.isMSN = function (str) {
        return coreString.isEmail(str);
    };
    coreString.prototype.isMSN = function () { return coreString.isMSN(this); };

    //  ��֤�Ƿ�����ո�ͷǷ��ַ�Z
    coreString.isUnNormal = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /.+/i.test(str);
    };
    coreString.prototype.isUnNormal = function () { return coreString.isUnNormal(this); };

    //  ��֤�Ƿ�Ϊ�Ϸ��ĳ��ƺ���
    coreString.isCarNo = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(str);
    };
    coreString.prototype.isCarNo = function () { return coreString.isCarNo(this); };

    //  ��֤�Ƿ�Ϊ�Ϸ����������������к�
    coreString.isCarEngineNo = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[a-zA-Z0-9]{16}$/.test(str);
    };
    coreString.prototype.isCarEngineNo = function () { return coreString.isCarEngineNo(this); };

    //  ��֤�Ƿ������Ϊ�Ϸ����û����ַ�(��ĸ��ͷ������6-16�ֽڣ�������ĸ�����»���)
    coreString.isUserName = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(str);
    };
    coreString.prototype.isUserName = function () { return coreString.isUserName(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� ���֤����(�й�) ��ʽ��
    coreString.isIDCard = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var iSum = 0,
            sId = str,
            aCity = {
                11: "����", 12: "���", 13: "�ӱ�", 14: "ɽ��", 15: "���ɹ�",
                21: "����", 22: "����", 23: "������", 31: "�Ϻ�", 32: "����",
                33: "�㽭", 34: "����", 35: "����", 36: "����", 37: "ɽ��",
                41: "����", 42: "����", 43: "����", 44: "�㶫", 45: "����",
                46: "����", 50: "����", 51: "�Ĵ�", 52: "����", 53: "����",
                54: "����", 61: "����", 62: "����", 63: "�ຣ", 64: "����",
                65: "�½�", 71: "̨��", 81: "���", 82: "����", 91: "����"
            };
        if (!/^\d{17}(\d|x)$/i.test(sId)) {
            if (sId.length == 15) { return coreString.isIDCard15(sId); }
            return false;
        }
        sId = sId.replace(/x$/i, "a");
        //�Ƿ�����
        if (aCity[parseInt(sId.substr(0, 2), 10)] == null) {
            return false;
        }
        var sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2)),
            d = new Date(sBirthday.replace(/-/g, "/"));
        //�Ƿ�����
        if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
            return false;
        }
        for (var i = 17; i >= 0; i--) {
            iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
        }
        if (iSum % 11 != 1) {
            return false;
        }
        return true;
    };
    coreString.prototype.isIDCard = function () { return coreString.isIDCard(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� һ�����֤����(�й���15λ) ��ʽ��
    coreString.isIDCard15 = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var iSum = 0,
            sId = str,
            aCity = {
                11: "����", 12: "���", 13: "�ӱ�", 14: "ɽ��", 15: "���ɹ�",
                21: "����", 22: "����", 23: "������", 31: "�Ϻ�", 32: "����",
                33: "�㽭", 34: "����", 35: "����", 36: "����", 37: "ɽ��",
                41: "����", 42: "����", 43: "����", 44: "�㶫", 45: "����",
                46: "����", 50: "����", 51: "�Ĵ�", 52: "����", 53: "����",
                54: "����", 61: "����", 62: "����", 63: "�ຣ", 64: "����",
                65: "�½�", 71: "̨��", 81: "���", 82: "����", 91: "����"
            };
        if (!/^\d{15}$/i.test(sId)) {
            return false;
        }
        sId = sId.replace(/x$/i, "a");
        //�Ƿ�����
        if (aCity[parseInt(sId.substr(0, 2), 10)] == null) {
            return false;
        }
        var sYear = "19", sBirthday = sYear + sId.substr(6, 2) + "-" + Number(sId.substr(8, 2)) + "-" + Number(sId.substr(10, 2)),
            d = new Date(sBirthday.replace(/-/g, "/"));
        //�Ƿ�����
        if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
            return false;
        }
        
        return true;
    };
    coreString.prototype.isIDCard15 = function () { return coreString.isIDCard15(this); };

    //  ��֤�Ƿ�Ϊ������ʽ
    coreString.isInteger = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return /^[+]?[1-9]+\d*$/i.test(str);
    };
    coreString.prototype.isInteger = function () { return coreString.isInteger(this); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� ���� ��ʽ��
    coreString.isNumeric = function (str, flag) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        //��֤�Ƿ�������
        if (isNaN(str)) { return false; }
        if (arguments.length == 0) { return false; }
        switch (flag) {
            case "":
                return true;
            case "+":        //����
                return /(^\+?|^\d?)\d*\.?\d+$/.test(str);
            case "-":        //����
                return /^-\d*\.?\d+$/.test(str);
            case "i":        //����
                return /(^-?|^\+?|\d)\d+$/.test(str);
            case "+i":        //������
                return /(^\d+$)|(^\+?\d+$)/.test(str);
            case "-i":        //������
                return /^[-]\d+$/.test(str);
            case "f":        //������
                return /(^-?|^\+?|^\d?)\d*\.\d+$/.test(str);
            case "+f":        //��������
                return /(^\+?|^\d?)\d*\.\d+$/.test(str);
            case "-f":        //��������
                return /^[-]\d*\.\d$/.test(str);
            default:        //ȱʡ
                return true;
        }
    };
    coreString.prototype.isNumeric = function (flag) { return coreString.isNumeric(this, flag); };

    //  �жϵ�ǰ String �����Ƿ�����ȷ�� ��ɫ(#FFFFFF��ʽ) ��ʽ��
    coreString.isColor = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        if (str == "") { return true; };
        if (str.length != 7) { return false; };
        return (str.search(/\#[a-fA-F0-9]{6}/) != -1);
    };
    coreString.prototype.isColor = function () { return coreString.isColor(this); };

    //  �жϵ�ǰ String �����Ƿ������Ϊ��ȫ�����ַ�(���ַ���������ɣ����� 6 λ).
    coreString.isSafePassword = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(str));
    };
    coreString.prototype.isSafePassword = function () { return coreString.isSafePassword(this); };

    //  ת����ȫ��
    coreString.toCase = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var tmp = "";
        for (var i = 0; i < str.length; i++) {
            if (str.charCodeAt(i) > 0 && str.charCodeAt(i) < 255) { tmp += String.fromCharCode(str.charCodeAt(i) + 65248); }
            else { tmp += String.fromCharCode(str.charCodeAt(i)); }
        }
        return tmp;
    };
    coreString.prototype.toCase = function () { return coreString.toCase(this); };

    //  ���ַ�������Html���롣
    coreString.toHtmlEncode = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        var temp = str;
        temp = temp.replace(/&/g, "&amp;");
        temp = temp.replace(/</g, "&lt;");
        temp = temp.replace(/>/g, "&gt;");
        temp = temp.replace(/\'/g, "&apos;");
        temp = temp.replace(/\"/g, "&quot;");
        temp = temp.replace(/\n/g, "<br />");
        temp = temp.replace(/\ /g, "&nbsp;");
        temp = temp.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");
        return temp;
    };
    coreString.prototype.toHtmlEncode = function () { return coreString.toHtmlEncode(this); };

    //  ת�������ڡ�
    coreString.toDate = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        try { return new Date(str.replace(/-/g, "\/")); }
        catch (e) { return undefined; }
    };
    coreString.prototype.toDate = function () { return coreString.toDate(this); };

    //  ���ַ�������ת���� ����(boolean) ֵ
    coreString.toBoolean = function (str) {
        if (typeof str == "boolean") { return str; }
        str = coreString.isNullOrEmpty(str) ? "" : String(str).toLowerCase();
        str = coreString.trim(str);
        return str == "true" || str == "yes" || str == "y" || str == "t" || str == "1";
    };
    coreString.prototype.toBoolean = function () { return coreString.toBoolean(this); };

    //  ���ַ�������ת���� ����(int) ֵ
    coreString.toInteger = function (str) { return parseInt(str); };
    coreString.prototype.toInteger = function () { return coreString.toInteger(this); };

    //  ���ַ�������ת���� ��ֵ(Number)��
    coreString.toNumber = function (str) { return coreString.toFloat(str); };
    coreString.prototype.toNumber = function () { return coreString.toNumber(this); };

    //  ���ַ�������ת���� ������(float) ֵ
    coreString.toFloat = function (str) { return parseFloat(str); };
    coreString.prototype.toFloat = function () { return coreString.toFloat(this); };

    //  ���ַ�������ת���� ��ֵ
    coreString.toNumeric = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        str = coreString.trim(str);
        return str.indexOf(".") > -1 ? coreString.toFloat(str) : coreString.toInteger(str);
    };
    coreString.prototype.toNumeric = function () { return coreString.toNumeric(this); };

    //  ���ַ�������ת���� ����(Object) ֵ
    coreString.toObject = function (data) {
        return eval("(" + $.trim(data + "") + ")");
    };
    coreString.prototype.toObject = function () { return coreString.toObject(this); };

    coreString.toJSONString = function (str) {
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        str = coreString.trim(str);
        return str.charAt(0) === "<" && str.charAt(str.length - 1) === ">" && str.length >= 3 ? $(str).text() : str;
    };
    coreString.prototype.toJSONString = function () { return coreString.toJSONString(this); };

    //  ���ַ�������ת���� ����(function) ֵ
    coreString.toFunction = function (str) {
        if (coreUtil.isFunction(str)) { return str; }
        str = coreString.isNullOrEmpty(str) ? "" : String(str);
        str = coreString.trim(str);
        if (!str.startsWith("function")) { str = "function(){" + str + "}"; }
        str = "{ \"func\": " + str + " }";
        return coreString.toObject(str).func;
    };
    coreString.prototype.toFunction = function () { return coreString.toFunction(this); };




    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript ����ֵ(Number)�����������䡣
    /////////////////////////////////////////////////////////////////////////////////////////////// 

    //  �ж϶����Ƿ���һ����ֵ����ֵ��ʽ�ַ���
    coreNumber.isNumeric = coreNumber.likeNumber = coreNumber.likeNumeric = coreUtil.isNumeric;

    //  �ж϶����Ƿ���һ����ֵ
    coreNumber.isNumber = coreUtil.isNumber;

    //  ��һ������/����������Ϊָ�����ȵ���ֵ���ú����������²�����
    //      num:    ��Ҫ��������������ֵ;
    //      precision:  ������������ľ���(�⼴��������ΪС��)��Ĭ��Ϊ 0;
    coreNumber.round = function (num, precision) {
        if (!coreUtil.isNumeric(num)) { throw "����Ĳ��� num ������һ����ֵ"; }
        precision = coreUtil.isNumeric(precision) ? precision : 0;
        var str = new Number(num).toFixed(precision);
        return precision ? parseFloat(str) : parseInt(str);
    };
    coreNumber.prototype.round = function (precision) { return coreNumber.round(this, precision); };

    //  ��ȡ��������ֵ����ľ��ȣ��ú��������������أ�
    //      ����һ��function(num)�����������ڻ�ȡ��ֵ�ľ��ȣ������ض������²�����
    //              num:    ��Ҫ��ȡ���ȵ���ֵ��
    //          ����ֵ��������ֵ num �ľ���(С��λ��)��
    //      ���ض���function(num, precision)������������������ֵ�ľ���(��������ֵ�������)�������ض������²�����
    //              num:    ��Ҫ���þ��ȵ���ֵ��
    //              precision: ��Ҫ���õľ��ȡ�
    //          ����ֵ��������ֵ num ����ָ���ľ��Ƚ�������������ֵ��
    //          ��ע�������ػ���ú��� coreNumber.round ������ֵ���������
    coreNumber.precision = function (num, precision) {
        if (!coreUtil.isNumeric(num)) { throw "����Ĳ��� num ������һ����ֵ"; }
        if (coreUtil.isNumeric(precision)) { return coreNumber.round(num, precision); } else {
            var str = String(num), i = str.indexOf(".");
            return i == -1 ? 0 : str.length - str.indexOf(".") - 1;
        }
    };

    //  �жϴ������ֵ�Ƿ���һ���������ú����������²�����
    //      num:    ��Ҫ�жϵ���ֵ��
    //  ����ֵ���������Ĳ��� num ��һ���������򷵻� true�����򷵻� false��
    coreNumber.isOdd = function (num) {
        return (num % 2) == 1;
    };
    coreNumber.prototype.isOdd = function () { return coreNumber.isOdd(this); };

    //  �жϴ������ֵ�Ƿ���һ��ż�����ú����������²�����
    //      num:    ��Ҫ�жϵ���ֵ��
    //  ����ֵ���������Ĳ��� num ��һ��ż�����򷵻� true�����򷵻� false��
    coreNumber.isEven = function (num) {
        return (num % 2) == 0;
    };
    coreNumber.prototype.isEven = function () { return coreNumber.isEven(this); };


    //  ���������ֵת����һ�������ļ���С���ַ������ú����������²�����
    //      num:    ��ת����ʽ����ֵ����ʾ�ļ���С�ֽ���(B)��
    //      str:  ��ת���ĸ�ʽ��String ����ֵ����ѡ��ֵ���� "auto"��"b"��"kb"��"mb"��"gb"
    coreNumber.toFileSize = function (num, str) {
        num = coreUtil.isNumeric(num) ? window.parseFloat(num) : 0;
        str = coreString.trim(String(str)).toLowerCase();
        if (str == "b") { return String(num); }
        if (!coreArray.contains(["b", "kb", "mb", "gb", "auto"], str)) { str = "auto"; }
        var ret = null, kb = 1024, mb = 1048576, gb = 1073741824;
        if (str == "auto") {
            str = (num >= gb) ? "gb" : (num >= mb ? "mb" : (num >= kb ? "kb" : "b"));
        }
        switch (str) {
            case "b": ret = toB(num); break;
            case "kb": ret = toKB(num); break;
            case "mb": ret = toMB(num); break;
            case "gb": ret = toGB(num); break;
            default: ret = toMB(num); break;
        }
        function toB(size) { return String(coreNumber.round(size, 2)) + "B"; };
        function toKB(size) { return String(coreNumber.round(size / kb, 2)) + "KB"; };
        function toMB(size) { return String(coreNumber.round(size / mb, 2)) + "MB"; };
        function toGB(size) { return String(coreNumber.round(size / gb, 2)) + "GB"; };
        return ret;
    };




    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript �����麯���������䡣
    /////////////////////////////////////////////////////////////////////////////////////////////// 

    //  �ж϶����Ƿ���һ������
    coreArray.isArray = core_isArray ? core_isArray : coreUtil.isArray;
    coreUtil.isArray = coreArray.isArray;

    //  ���һ�������Ƿ�Ϊһ�������������������������ͬ coreUtil.likeArray
    coreArray.likeArray = coreUtil.likeArray;

    //  ͬ coreUtil.likeArrayNotString
    coreArray.likeArrayNotString = coreUtil.likeArrayNotString;

    //  �жϴ���� ���� �Ƿ�Ϊ Null ����Ϊ�����顣
    coreArray.isNullOrEmpty = function (array) { return !coreArray.likeArray(array) || !array.length; };
    coreArray.prototype.isNullOrEmpty = function () { return coreArray.isNullOrEmpty(this); };


    //  ����һ�����е�������Ƶ���ǰָ�������У��ú����������²�����
    //      target: Ŀ�����飬Դ���� source �е����������ֵ���������У�
    //      source: Դ�������飬�������ڵ����������ֵ��Ŀ������ target �У�
    //  ע�⣺�÷�����ı�Ŀ������ target �е�Ԫ��������
    //  ����ֵ��Դ�������ݸ��ƹ������Ŀ������ target��
    coreArray.copy = coreArray.copyFrom = function (target, source) {
        target = coreArray.likeArray(target) ? target : [];
        source = coreArray.likeArray(source) ? source : [];
        var l = source.length, i = 0;
        if (coreUtil.isNumeric(l)) {
            while (i < l) { core_push.call(target, source[i++]); };
        } else {
            while (source[i] !== undefined) { core_push.call(target, source[i++]); }
        }
        return target;
    };
    coreArray.prototype.copy = coreArray.prototype.copyFrom = function (source) { return coreArray.copy(this, source); };

    //  ����ǰָ�������е�������Ƶ���һ�����У��ú����������²�����
    //      source: Դ�������飬�������ڵ����������ֵ��Ŀ������ target �У�
    //      target: Ŀ�����飬Դ���� source �е����������ֵ���������У�
    //  ע�⣺�÷�����ı�Ŀ������ target �е�Ԫ��������
    //  ����ֵ��Դ�������ݸ��ƹ������Ŀ������ target��
    coreArray.copyTo = function (source, target) {
        return coreArray.copy(target, source);
    };
    coreArray.prototype.copyTo = function (target) { return coreArray.copy(target, this); };

    //  ����һ���͵�ǰ���������ͬ�����鲢����
    coreArray.clone = function (source) { return coreArray.copy([], source); };
    coreArray.prototype.clone = function () { return coreArray.clone(this); };

    //  ȷ���������Ƿ����ָ����Ԫ�ء��ú����������²�����
    //      array: ���������飻
    //      item: ������Ԫ�أ��жϸ�Ԫ���Ƿ���������� array �У�
    //      compare: ���ڱȽ�����ĺ������ú�����ѭ�����ã����ڱȽ� array �е�ÿһ���Ƿ��� item ��ֵ���ú�������һ�� bool ֵ������һ����ѡ������
    //          �ú�����ǩ��Ӧ���� function (item1, item2) { }������ item1 ��ʾԴ�����е��item2 ��ʾҪ���бȽϵ��
    //          ������������ compare����ʹ��Ĭ�ϵıȽ������ "==" ����ֵ��ƥ�䣻
    coreArray.contains = function (array, item, compare) {
        return coreArray.some(array, function (val) { return coreUtil.equals(val, item, compare); });
    };
    coreArray.prototype.contains = function (item, compare) { return coreArray.contains(this, item, compare); };

    //  �ߵ�������Ԫ�ص�˳��
    //  ����ֵ�����ش���Ĳ��� array �����������Ĳ��� array ����һ�����飬�򷵻�һ���´����Ŀ��������
    //  ע�⣺�÷�����ı�ԭ�������飬�����ᴴ���µ����顣
    coreArray.reverse = function (array) {
        array = coreArray.likeArray(array) ? array : [];
        if (coreArray.isArray(array)) { array.reverse(); return array; }
        var len = array.length, l = len / 2, j;
        for (var i = 0; i < l; i++) {
            j = len - i - 1;
            var a = array[i];
            var b = array[j];
            array[i] = b;
            array[j] = a;
        }
        return array;
    };

    //  ������������ָ��������������������е�һ��ƥ����Ĵ��㿪ʼ���������ú����������²�����
    //      array: Դ�������飻
    //      item:  Ҫ�������
    //      startIndex: ���㿪ʼ����������ʼ���������б��� 0���㣩Ϊ��Чֵ���ò�����ѡ������ò���δ������� 0 ��ʼ��
    //      count: Ҫ�����Ĳ����е�Ԫ�������ò�����ѡ������ò���δ�����������������ĩβ��
    //      compare: ���ڱȽ�����ĺ������ú�����ѭ�����ã����ڱȽ� array �е�ÿһ���Ƿ��� item ��ֵ���ú�������һ�� bool ֵ������һ����ѡ������
    //          �ú�����ǩ��Ӧ���� function (item1, item2) { }������ item1 ��ʾԴ�����е��item2 ��ʾҪ���бȽϵ��
    //          ������������ compare����ʹ��Ĭ�ϵıȽ������ "==" ����ֵ��ƥ�䣻
    //  ����ֵ������������д� startIndex ��ʼ������ count ��Ԫ�ص�Ԫ�ط�Χ���ҵ� item �ĵ�һ��ƥ�����Ϊ����Ĵ��㿪ʼ������������Ϊ -1��
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679977(v=vs.94).aspx
    coreArray.indexOf =
        //coreArray.indexOf ? coreArray.indexOf :
        function (array, item, startIndex, count, compare) {
            array = coreArray.likeArray(array) ? array : [];
            var result = -1;
            if (!array.length) { return result; }
            if (arguments.length > 2) {
                var c = arguments[arguments.length - 1];
                compare = coreUtil.isFunction(c) ? c : null;
                var s = arguments[2];
                startIndex = coreUtil.isNumeric(s) ? s : 0;
                if (startIndex < 0 || array.length < startIndex) { return result; }
                if (arguments.length > 3) {
                    c = arguments[3];
                    count = coreUtil.isNumeric(c) ? c : array.length - startIndex;
                } else {
                    count = array.length - startIndex;
                }
                if (count < 0 || startIndex + count > array.length) { return result; }
            } else {
                startIndex = 0;
                count = array.length - startIndex;
                compare = null;
            }
            var stopIndex = startIndex + count;
            for (var i = startIndex; i < stopIndex; i++) {
                if (coreUtil.equals(array[i], item, compare)) { result = i; break; }
            }
            return result;
        };
    coreArray.prototype.indexOf = function (item, startIndex, count, compare) { return coreArray.indexOf(this, item, startIndex, count, compare); };

    //  ������������ָ������������������������һ��ƥ����Ĵ��㿪ʼ��������
    //      array: Դ�������飻
    //      item:  Ҫ�������
    //      startIndex: ��������Ĵ��㿪ʼ����ʼ�������ò�����ѡ������ò���δ�����������ĩβ��ʼ��
    //      count: Ҫ�����Ĳ����е�Ԫ�������ò�����ѡ������ò���δ�������������������ʼλ�ã�
    //      compare: ���ڱȽ�����ĺ������ú�����ѭ�����ã����ڱȽ� array �е�ÿһ���Ƿ��� item ��ֵ���ú�������һ�� bool ֵ������һ����ѡ������
    //          �ú�����ǩ��Ӧ���� function (item1, item2) { }������ item1 ��ʾԴ�����е��item2 ��ʾҪ���бȽϵ��
    //          ������������ compare����ʹ��Ĭ�ϵıȽ������ "==" ����ֵ��ƥ�䣻
    //  ����ֵ������������а��� count ��Ԫ�ء��� startIndex ����β��Ԫ�ط�Χ���ҵ� item �����һ��ƥ�����Ϊ����Ĵ��㿪ʼ������������Ϊ -1��
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679972(v=vs.94).aspx
    coreArray.lastIndexOf =
        //coreArray.lastIndexOf ? coreArray.lastIndexOf :
        function (array, item, startIndex, count, compare) {
            array = coreArray.likeArray(array) ? array : [];
            var result = -1;
            if (!array.length) { return result; }
            if (arguments.length > 2) {
                var c = arguments[arguments.length - 1];
                compare = coreUtil.isFunction(c) ? c : null;
                var s = arguments[2];
                startIndex = coreUtil.isNumeric(s) ? s : 0;
                if (startIndex < 0 || array.length < startIndex) {
                    return result;
                }
                if (arguments.length > 3) {
                    c = arguments[3];
                    count = coreUtil.isNumeric(c) ? c : array.length - startIndex;
                } else {
                    count = array.length - startIndex;
                }
                if (count < 0 || startIndex + count > array.length) {
                    return result;
                }
            } else {
                startIndex = 0;
                count = array.length - startIndex;
                compare = null;
            }
            var begin = array.length - startIndex - 1,
                end = begin - count;
            for (var i = begin; i > end; i--) {
                if (coreUtil.equals(array[i], item, compare)) {
                    result = i; break;
                }
            }
            return result;
        };
    coreArray.prototype.lastIndexOf = function (item, startIndex, count, compare) { return coreArray.lastIndexOf(this, item, startIndex, count, compare); };

    //  ��ȡָ�������н�������ָ��������֮���Ԫ�ع��ɵ�һ���µ����飻�ú����������²�����
    //      array: Դ�������飻
    //      startIndex: ���衣һ�����ڻ���� 0 ���������涨�Ӻδ���ʼѡȡ���� 0 ��ʼ������
    //      stopIndex: ��ѡ���涨�Ӻδ�����ѡȡ���ò���������Ƭ�Ͻ������������±ꡣ���û��ָ���ò�������ô�зֵ���������� startIndex ���������������Ԫ�ء�
    //  ����ֵ������һ���µ����飬������ startIndex �� stopIndex ����������Ԫ�أ��� arrayObject �е�Ԫ�ء�
    coreArray.range = function (array, startIndex, stopIndex) {
        array = coreArray.likeArray(array) ? array : [];
        startIndex = coreUtil.isNumeric(startIndex) ? startIndex : 0;
        stopIndex = coreUtil.isNumeric(stopIndex) ? stopIndex : array.length;
        return core_slice.call(array, startIndex, stopIndex);
    };
    coreArray.prototype.range = function (startIndex, stopIndex) { return coreArray.range(this, startIndex, stopIndex); };

    //  ��ȡָ�������д� startIndex λ�ÿ�ʼ��ָ��������Ԫ�ع��ɵ�һ���µ����飻�ú����������²�����
    //      array: Դ�������飻
    //      startIndex: һ���Ǹ����������涨Ҫ��ȡ����ʼλ�õ������ţ�
    //      length: һ���Ǹ����������涨Ҫ��ȡ��Ԫ�ص��������ò�����ѡ�����������ò�������һֱ��ȡ�������ĩβ��
    //  ����ֵ������һ���µ����飬������ startIndex ����ʼ�󳤶�Ϊ length ������Ԫ�ء�
    coreArray.rangeLen = function (array, startIndex, length) {
        startIndex = coreUtil.isNumeric(startIndex) ? startIndex : 0;
        length = coreUtil.isNumeric(length) ? length : array.length;
        var stopIndex = startIndex + length;
        return coreArray.range(array, startIndex, stopIndex);
    };
    coreArray.prototype.rangeLen = function (startIndex, length) { return coreArray.rangeLen(this, startIndex, length); };

    //  ��ָ����������з�ҳ���������ط�ҳ��Ľ�����ú����������²�����
    //      array: Դ�������飻
    //      pageIndex: һ���Ǹ���������ʾҪ���ص���������ҳ��������ţ��� 0 ��ʼ���㣻�ò�����ѡ�����δ����ò����򲻺Ϸ�����Ĭ��Ϊ 0��
    //      pageSize: һ���Ǹ���������ʾÿһ����ҳҳ��ĳߴ磬��ÿҳ�ж����м�¼���ò�����ѡ�����δ����ò����򲻺Ϸ�����Ĭ��Ϊ 10��
    //          sortby: ��������ıȽϺ������ú�����ѭ�����ã����ڱȽ� array ��û����Ĵ�С������һ����ѡ������
    //              �ú�����ǩ��Ϊ function (a, b) { }������ a��b �ֱ��ʾԴ�����еĴ��Ƚϴ�С����ú������뷵��һ����ֵ��ʾ�ȽϺ�Ľ����
    //              ��� a > b ���򷵻�һ�� ���� 0 ��ֵ��
    //              ��� a < b ���򷵻�һ�� С�� 0 ��ֵ��
    //              ��� a == b���򷵻� 0��
    //          ���������ò�������Ĭ�Ͻ� array �е�ÿһ������������бȽϡ�
    //  �ú�������һ�������������Ե� JSON ����
    //      pageSize:   һ���Ǹ���������ʾÿһ����ҳҳ��ĳߴ磬��ÿҳ�ж����м�¼��
    //      pageIndex:  һ���Ǹ���������ʾ���ص���������ҳ��������ţ��� 0 ��ʼ���㣻
    //      rowCount:   һ���Ǹ���������ʾ���ص����ݵ�δ��ҳǰ����������
    //      data:       һ�����飬Ϊ����Ĳ��� array ���Ӽ�����ʾ��ҳ���ҳ�����ݣ�
    //      pageCount:  һ���Ǹ���������ʾԴ���������ҳ�����ҳ��������
    //      pageNumber: һ���Ǹ���������ʾ���ص��������ڵ�ҳ�����ţ��� 1 ��ʼ���㣻ͬ pageIndex + 1��
    //      total:      һ���Ǹ�������ͬ rowCount��
    coreArray.splitPage = function (array, pageIndex, pageSize, sortby) {
        array = coreArray.likeArray(array) ? array : [];
        if (!pageIndex || !coreUtil.isNumeric(pageIndex) || pageIndex < 0) { pageIndex = 0; }
        if (!pageSize || !coreUtil.isNumeric(pageSize) || pageSize < 1) { pageSize = 10; }
        var isFunc = coreUtil.isFunction(sortby);
        array = isFunc ? coreArray.clone(array).sort(sortby) : array;
        var startIndex = pageIndex * pageSize;
        var stopIndex = (pageIndex + 1) * pageSize;
        var data = coreArray.range(array, startIndex, stopIndex);
        var rowCount = array.length;
        var pageCount = Math.ceil(parseFloat(rowCount) / pageSize);
        var pageNumber = pageIndex + 1;
        var total = rowCount;
        return { pageSize: pageSize, pageIndex: pageIndex, rowCount: rowCount, data: data, pageCount: pageCount, pageNumber: pageNumber, total: total };
    };
    coreArray.prototype.splitPage = function (pageIndex, pageSize, sortby) { return coreArray.splitPage(this, pageIndex, pageSize, sortby); };

    //  ���������Ƴ�һ����Χ��Ԫ�أ��ú����������²�����
    //      array: Դ�������飻
    //      index: Ҫ�Ƴ���Ԫ�صķ�Χ���㿪ʼ����ʼ�������ò�����ѡ�����������ò�����Ĭ��Ϊ 0��
    //      count: Ҫ�Ƴ���Ԫ�������ò�����ѡ�����������ò�����Ĭ��Ϊ�� index ��ʼλ��һֱ�������ĩβ������Ϊ 0��
    //  ע�⣺�÷�����ı����е����顣
    coreArray.removeRange = function (array, index, count) {
        if (!coreArray.likeArray(array)) { throw "����Ĳ��� array ������һ������"; }
        index = coreUtil.isNumeric(index) ? index : 0;
        count = coreUtil.isNumeric(count) && count >= 0 ? count : array.length;
        core_splice.call(array, index, count);
        return array;
    };
    coreArray.prototype.removeRange = function (index, count) { return coreArray.removeRange(this, index, count); };

    //  ��������е�����Ԫ�ء�
    //  ע�⣺�÷�����ı����е����顣
    coreArray.clear = function (array) {
        if (!coreArray.likeArray(array)) { throw "����Ĳ��� array ������һ������"; }
        core_splice.call(array, 0, array.length);
        return array;
    };
    coreArray.prototype.clear = function () { return coreArray.clear(this); };

    //  �Ƴ������е�ָ������λ�õ���ú����������²�����
    //      array: Դ�������飬���Ƴ���������ڸ������У�
    //      index: ָ��������λ�ã�����⵽Դ���������д��ڸ�������ʱ�����Ƴ�Դ�����еĸ������
    //  ע�⣺�÷�����ı����е����顣
    coreArray.removeAt = function (array, index) { return coreArray.removeRange(array, index, 1); };
    coreArray.prototype.removeAt = function (index) { return coreArray.removeAt(this, index); };

    //  �Ƴ������е�ָ����ú����������²�����
    //      array: Դ�������飬���Ƴ���������ڸ������У�
    //      item: ���Ƴ��������⵽Դ���������д��ڸ���ʱ�����Ƴ�Դ�����еĸ��
    //      compare: ���ڱȽ�����ĺ������ú�����ѭ�����ã����ڱȽ� array �е�ÿһ���Ƿ��� item ��ֵ���ú�������һ�� bool ֵ������һ����ѡ������
    //          �ú�����ǩ��Ӧ���� function (item1, item2) { }������ item1 ��ʾԴ�����е��item2 ��ʾҪ���бȽϵ��
    //          ������������ compare����ʹ��Ĭ�ϵıȽ������ "==" ����ֵ��ƥ�䣻
    //  ע�⣺�÷�����ı����е����顣
    coreArray.remove = function (array, item, compare) {
        var index = coreArray.indexOf(array, item, compare);
        if (index < 0) { return array; }
        return coreArray.removeAt(array, index);
    };
    coreArray.prototype.remove = function (item, compare) { return coreArray.remove(this, item, compare); };

    //  ����һ��������뵽��ǰ�����ָ�����������÷����������²�����
    //      array: Դ�������飻
    //      index: Ӧ���� item ��λ�õ���ʼ������
    //      collect:  ����Ҫ�����Ԫ�ص����飻��ֵ����Ϊ null��
    //  ����ֵ�����ز���Ԫ�غ������������������Ĳ��� array ����һ�����飬�򷵻�һ���´����Ŀ��������
    //  ע�⣺�÷�����ı����е����顣
    coreArray.insertRange = function (array, index, collect) {
        if (!coreArray.likeArray(array)) { throw "����Ĳ��� array ������һ������"; }
        collect = coreArray.likeArray(collect) ? collect : [collect];
        if (!coreUtil.isNumeric(index) || index < 0 || index > array.length) {
            throw "ArgumentOutOfRangeException: ����������� index �������� array �ķ�Χ��";
        }
        var part = coreArray.range(array, index);
        coreArray.removeRange(array, index);
        coreArray.copy(array, collect);
        coreArray.copy(array, part);
        return array;
    };
    coreArray.prototype.insertRange = function (index, collect) { return coreArray.insertRange(this, index, collect); };

    //  ��Ԫ�ز��������ָ�����������÷����������²�����
    //      array: Դ�������飻
    //      index: Ӧ���� item ��λ�õ���ʼ������
    //      item:  Ҫ�����Ԫ�أ���ֵ����Ϊ null��
    //  ����ֵ�����ز���Ԫ�غ������������������Ĳ��� array ����һ�����飬�򷵻�һ���´����Ŀ��������
    //  ע�⣺�÷�����ı����е����顣
    coreArray.insert = function (array, index, item) {
        var collect = [item];
        return coreArray.insertRange(array, index, collect);
    };
    coreArray.prototype.insert = function (index, item) { return coreArray.insert(this, index, item); };

    //  ����һ�����е�Ԫ�ظ��Ƶ���ǰ������һ����Χ��Ԫ���ϣ��ú����������²�����
    //      array: Դ�������飻
    //      index: �� 0 ��ʼ�������������Ӹ�λ�ÿ�ʼ��ֵ collect Ԫ�أ��ò�����ѡ�����������ò�������Ĭ��Ϊ�����ĩβ��
    //      collect: Ҫ����Ԫ�ظ�ֵ�� array �У������鱾����Ϊ null���������԰���Ϊnull ��Ԫ�ء�
    //  ����ֵ����������Ԫ�غ������������������Ĳ��� array ����һ�����飬�򷵻�һ���´����Ŀ��������
    //  ע�⣺�÷�����ı����������е��
    coreArray.setRange = function (array, index, collect) {
        if (!coreArray.likeArray(array)) { throw "����Ĳ��� array ������һ������"; }
        index = coreUtil.isNumeric(index) ? index : 0;
        if (index < 0 || array.length < index) { throw "ArgumentOutOfRangeException: ����������� index �������� array �ķ�Χ��"; }
        collect = coreArray.likeArray(collect) ? collect : [];
        coreArray.removeRange(array, collect.length);
        return coreArray.insertRange(array, index, collect);
    };
    coreArray.prototype.setRange = function (index, collect) { return coreArray.setRange(this, index, collect); };

    //  ���Դ�����в�����ָ������򽫸�����ӵ�Դ�����У��÷����ṩ���²�����
    //      array: Դ�������飻
    //      item: ��Ҫ���ϲ���Դ�����е�����Դ�����в����ڸ�����������Դ���飻
    //      compare: ���ڱȽ�����ĺ������ú�����ѭ�����ã����ڱȽ� array �е�ÿһ���Ƿ��� item ��ֵ���ú�������һ�� bool ֵ������һ����ѡ������
    //          �ú�����ǩ��Ӧ���� function (item1, item2) { }������ item1 ��ʾԴ�����е��item2 ��ʾҪ���бȽϵ��
    //          ������������ compare����ʹ��Ĭ�ϵıȽ������ "==" ����ֵ��ƥ�䣻
    //  ����ֵ���������Ԫ�غ�����������
    //  ע�⣺�÷�����ı����е����顣
    coreArray.attach = function (array, item, compare) {
        if (!coreArray.contains(array, item, compare)) {
            array.push(item);
        }
        return array;
    };
    coreArray.prototype.attach = function (item, compare) { return coreArray.attach(this, item, compare); };

    //  ȥ���������ظ���÷����ṩ���²���:
    //      array: Դ�������飻
    //      compare: ���ڱȽ�����ĺ������ú�����ѭ�����ã����ڱȽ� array �е�ÿһ���Ƿ��� item ��ֵ���ú�������һ�� bool ֵ������һ����ѡ������
    //          �ú�����ǩ��Ӧ���� function (item1, item2) { }������ item1 ��ʾԴ�����е��item2 ��ʾҪ���бȽϵ��
    //          ������������ compare����ʹ��Ĭ�ϵıȽ������ "==" ����ֵ��ƥ�䣻
    //  ����ֵ������ȥ���ظ�Ԫ�غ�����������
    //  ע�⣺�÷�����ı����е����顣
    coreArray.distinct = function (array, compare) {
        if (!coreArray.likeArray(array)) {
            throw "����Ĳ��� array ������һ���������";
        }
        var temps = [];
        for (var i = 0; i < array.length; i++) {
            var item = array[i];
            if (i == 0) {
                temps.push(item);
            } else {
                coreArray.attach(temps, item, compare);
            }
        }
        coreArray.removeRange(array, 0);
        coreArray.copy(array, temps);
        return array;
    };
    coreArray.prototype.distinct = function (compare) { return coreArray.distinct(this, compare); };

    //  �ϲ������������飻�÷����ṩ���²���:
    //      array: ��ʼԴ���飬֮�����е�������ϲ�������飻
    //      item1: �� 1 �����ϲ��
    //      item2: �� 2 �����ϲ��
    //      item3: �� 3 �����ϲ��
    //      itemn... �� n �����ϲ��
    //  ���Ҫ���� merge �����Ĳ��������飬��ô��ӵ��������е�Ԫ�أ����������顣
    //  ����ֵ�����غϲ��������(Ԫ��)������������
    //  ע�⣺�÷�����ı����е����飬item1��item2��item3��itemn...�����еĲ�������ϲ��� array ���顣
    coreUtil.merge = coreArray.merge = function (array, item1, item2, itemn) {
        if (!coreArray.likeArray(array)) {
            throw "����Ĳ��� array ������һ���������";
        }
        if (arguments.length > 1) {
            for (var i = 1; i < arguments.length; i++) {
                var arg = arguments[i];
                arg = coreUtil.likeArrayNotString(arg) ? arg : [arg];
                coreArray.copy(array, arg);
            }
        }
        return array;
    };
    coreArray.prototype.merge = function () {
        var args = coreArray.insert(arguments, 0, this);
        return coreArray.merge(args);
    };

    //  �ϲ������������飬�ظ�����ᱻ�ϲ����÷����ṩ���²���:
    //      array: ��ʼԴ���飻
    //      compare: ���ڱȽ�����ĺ������ú�����ѭ�����ã����ڱȽ� array �е�ÿһ���Ƿ��� item ��ֵ���ú�������һ�� bool ֵ������һ����ѡ������
    //          �ú�����ǩ��Ӧ���� function (item1, item2) { }������ item1 ��ʾԴ�����е��item2 ��ʾҪ���бȽϵ��
    //          ������������ compare����ʹ��Ĭ�ϵıȽ������ "==" ����ֵ��ƥ�䣻
    //      item1: �� 1 �����ϲ��
    //      item2: �� 2 �����ϲ��
    //      item3: �� 3 �����ϲ��
    //      itemn... �� n �����ϲ��
    //  ���Ҫ���� unique �����Ĳ��������飬��ô��ӵ��������е�Ԫ�أ����������顣
    //  ����ֵ�����غϲ��������(Ԫ��)������������
    //  ע�⣺�÷�����ı����е����飬item1��item2��item3��itemn...�����еĲ�������ϲ��� array ���顣
    //  �÷����� coreArray.merge �������������ڣ�
    //      merge �����ὫԴ���������е� item ���кϲ���
    //      unique �������ж�Դ�������Ƿ������Ӧ�� item����������򲻺ϲ����������Դ�����б����Ԫ����������ظ���Ҳ����� distinct ����
    coreUtil.unique = coreArray.unique = function (array, compare, item1, item2, itemn) {
        var args = coreArray.clone(arguments);
        args.callee = arguments.callee;
        if (coreUtil.isFunction(compare)) { coreArray.removeAt(args, 1); }
        coreArray.merge.apply(this, args);
        coreArray.distinct(array, compare);
        return array;
    };
    coreArray.prototype.unique = function (compare, item1, item2, itemn) {
        var args = coreArray.clone(arguments);
        args.callee = arguments.callee;
        coreArray.insert(args, 0, this);
        return coreArray.unique.apply(this, args);
    };

    //  ���˲��ҵ�ǰ�����е�Ԫ�أ������ز��ҵĽ�������صĲ��ҽ����һ���µ����飻�ú����������²�����
    //      array: ���衣 һ���������
    //      compare: ���衣 һ������������������ĺ����� ���������е�ÿ��Ԫ�أ� filter ����������� callbackfn ����һ�Ρ�
    //          �ûص��������﷨�磺function callbackfn(value, index, array)��
    //          ����ûص��������� true�����Ԫ�ؽ��������ڷ��صļ��ϵ��С�
    //  ����ֵ��һ�������ص�����Ϊ�䷵�� true ������ֵ�������顣 ����ص�����Ϊ array ������Ԫ�ط��� false����������ĳ���Ϊ 0��
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679973(v=vs.94).aspx
    coreArray.filter = coreArray.filter ? coreArray.filter : function (array, compare, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(compare)) { return array; }
        var temps = [];
        for (var i = 0; i < array.length; i++) {
            if (compare.call(thisArg, array[i], i, array) == true) { temps.push(array[i]); }
        }
        return temps;
    };
    coreArray.prototype.filter = function (compare) { return coreArray.filter(this, compare); };

    //  �������ÿ��Ԫ�ص��ö���Ļص����������ذ�����������飻�ú����������²�����
    //      array:      ���衣 һ���������
    //      callback:   ���衣 һ������������������ĺ����� ���������е�ÿ��Ԫ�أ� map ����������� callbackfn ����һ�Ρ�
    //          �ûص������﷨�磺function callbackfn(value, index, array1)��
    //      thisArg:    ��ѡ�� ���� callbackfn ������Ϊ������ this �ؼ��ֵĶ��� ���ʡ�� thisArg���� undefined ������ this ֵ��
    //  ����ֵ�����е�ÿ��Ԫ�ؾ�Ϊ������ԭʼ����Ԫ�صĻص���������ֵ�������顣
    //  ��ע�����������е�ÿ��Ԫ�أ� map ����������� callbackfn ����һ�Σ�������������˳�򣩡� ��Ϊ������ȱ�ٵ�Ԫ�ص��øûص�������
    //      �����������֮�⣬ map �������ɾ��� length �����Ҿ����Ѱ����ֱ������������������κζ���ʹ�á�
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679976(v=vs.94).aspx
    coreArray.map = coreArray.map ? coreArray.map : function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(callback)) {
            throw "����Ĳ��� callback ����һ��������";
        }
        var temps = [];
        for (var i = 0; i < array.length; i++) {
            var item = callback.call(thisArg, array[i], i, array);
            temps.push(item);
        }
        return temps;
    };
    coreArray.prototype.map = function (callback, thisArg) { return coreArray.map(this, callback, thisArg); };

    //  ��������и�ʽת�����������е�ÿһ��ת�����µĸ�ʽ��Ȼ��ϲ���һ���µ����鲢���أ��ú����������²�����
    //  �÷���ͬ coreArray.map
    coreArray.cast = coreArray.map;
    coreArray.prototype.cast = coreArray.prototype.map;

    //  ��ȡ���������ֵ����ú����������²���:
    //      array: �����ҵ�Դ���飻
    //      compare: �ȽϺ������ú�����ѭ�����ã����ڱȽ� array ��û����Ĵ�С������һ����ѡ������
    //          �ú�����ǩ��Ϊ function (a, b) { }������ a��b �ֱ��ʾԴ�����еĴ��Ƚϴ�С����ú������뷵��һ����ֵ��ʾ�ȽϺ�Ľ����
    //              ��� a > b ���򷵻�һ�� ���� 0 ��ֵ��
    //              ��� a < b ���򷵻�һ�� С�� 0 ��ֵ��
    //              ��� a == b���򷵻� 0��
    //      ���������ò�������Ĭ�Ͻ� array �е�ÿһ������������бȽϡ�
    coreArray.max = function (array, compare) {
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0) { return undefined; }
        if (array.length == 1) { return array[0]; }
        return coreArray.reduce(coreArray.range(array, 1), function (prev, current, index, array) {
            return coreUtil.compare(prev, current, compare) >= 0 ? prev : current;
        }, array[0]);
    };
    coreArray.prototype.max = function (compare) { return coreArray.max(this, compare); };

    //  ��ȡ������ֵ�������ֵ�ļ������飻�ú����Ĳ�������� coreArray.max ��ͬ��
    //  �ú������ص���һ���µ����飬��ʹ���ҵ��Ľ��ֻ��һ�
    coreArray.maxs = function (array, compare) {
        array = coreArray.likeArray(array) ? array : [];
        var max = coreArray.max(array, compare);
        return coreArray.filter(array, function (item) {
            return coreUtil.compare(item, max, compare) == 0;
        });
    };
    coreArray.prototype.maxs = function (compare) { return coreArray.maxs(this, compare); };

    //  ��ȡ��������Сֵ����ú����Ĳ�������� coreArray.max ��ͬ��
    coreArray.min = function (array, compare) {
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0) { return undefined; }
        if (array.length == 1) { return array[0]; }
        return coreArray.reduce(coreArray.range(array, 1), function (prev, current, index, array) {
            return coreUtil.compare(current, prev, compare) >= 0 ? prev : current;
        }, array[0]);
    };
    coreArray.prototype.min = function (compare) { return coreArray.min(this, compare); };

    //  ��ȡ������ֵ������Сֵ�ļ��ϣ��ú����Ĳ�������� coreArray.max ��ͬ��
    //  �ú������ص���һ���µ����飬��ʹ���ҵ��Ľ��ֻ��һ�
    coreArray.mins = function (array, compare) {
        array = coreArray.likeArray(array) ? array : [];
        var min = coreArray.min(array, compare);
        return coreArray.filter(array, function (item) {
            return coreUtil.compare(item, min, compare) == 0;
        });
    };
    coreArray.prototype.mins = function (compare) { return coreArray.mins(this, compare); };

    //  ���������и����ۼӺ�ĺϼ�ֵ���ú����������²���:
    //      array:  Դ��������
    //      callback: ת���������ú�����ѭ�����ã����ڽ� array �е�ÿһ��ת����һ���µ���ֵ��������������ú�����������뷵��һ����ֵ���ò�����ѡ��
    //          �ú�����ǩ��Ӧ���� function (item) { }������ item ��ʾԴ�����е��
    //          ���������ò�������Ĭ�Ͻ� array �е�ÿһ��ֱ����ӡ�
    //      thisArg:    ��ѡ�� ���� callback ������Ϊ������ this �ؼ��ֵĶ��� ���ʡ�� thisArg���� undefined ������ this ֵ��
    coreArray.sum = function (array, callback, thisArg) {
        var isFunc = coreUtil.isFunction(callback),
            fn = function (previous, current) {
                return previous + (isFunc ? callback.call(thisArg, current) : current);
            };
        return coreArray.reduce(array, fn, 0);
    };
    coreArray.prototype.sum = function (convert) { return coreArray.sum(this, convert); };

    //  ���������и����ۻ����ƽ��ֵ���ú��������Ķ���� coreArray.sum һ����
    coreArray.avg = function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        var sum = coreArray.sum(array, callback, thisArg),
            avg = parseFloat(sum) / array.length;
        return avg;
    };
    coreArray.prototype.avg = function (convert) { return coreArray.avg(this, convert); };

    //  ������Ŀ�ͷ����ָ������������Ԫ�ع��ɵ������飻�ú����������²���:
    //      array: Դ�������飻
    //      count: Ҫ��ȡ��Ԫ�����������������һ�����������ò�����ѡ�����������ò��������ֵ������Χ����Ĭ��Ϊ 1��
    coreArray.take = function (array, count) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isNumeric(count) || count < 1) { count = 1; }
        var temps = [];
        for (var i = 0; i < array.length; i++) { if (i < count) { temps.push(array[i]); } }
        return temps;
    };
    coreArray.prototype.take = function (count) { return coreArray.take(this, count); };

    //  ����������ָ��������Ԫ�أ�Ȼ�󷵻�ʣ��Ԫ�ع��ɵ������飻�ú����������²�����
    //      array: Դ�������飻
    //      count: ����ʣ��Ԫ��ǰҪ������Ԫ��������������һ���Ǹ��������ò�����ѡ�����������ò��������ֵΪ��������Ĭ��Ϊ 0��
    coreArray.skip = function (array, count) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isNumeric(count) || count < 0) { count = 0; }
        var temps = [];
        for (var i = count; i < array.length; i++) { temps.push(array[i]); }
        return temps;
    };
    coreArray.prototype.skip = function (count) { return coreArray.skip(this, count); };

    // Ϊ�����е�ÿ��Ԫ��ִ��ָ���������ú����������²�����
    //      array:      ���衣 һ���������
    //      callback:   ���衣 һ������������������ĺ����� ���������е�ÿ��Ԫ�أ� forEach ������� callbackfn ����һ�Ρ�
    //          �ú����﷨�磺function callbackfn(value, index, array)��
    //      thisArg:    ��ѡ�� ���� callbackfn ������Ϊ������ this �ؼ��ֵĶ��� ���ʡ�� thisArg���� undefined ������ this ֵ��
    //  ����ֵ�����ش���Ĳ��� array ����
    //  ��ע�����������е�ÿ��Ԫ�أ� forEach ����������� callbackfn ����һ�Σ�������������˳�򣩡�
    //      �����Ҫ�˳� each ѭ����ʹ�ص��������� false����������ֵ�������ԡ�
    //      �����������֮�⣬ forEach �������ɾ��� length �����Ҿ����Ѱ����ֱ������������������κζ���ʹ�á�
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679980(v=vs.94).aspx
    coreArray.forEach = coreArray.forEach ? coreArray.forEach : function (array, callback, thisArg) {
        var isArray = coreArray.likeArray(array), temps = isArray ? array : [], i = 0, length = temps.length;
        if (temps.length == 0) { return; }
        if (!coreUtil.isFunction(callback)) { throw "����Ĳ��� callback ����һ��������"; }
        if (isArray) {
            for (; i < length; i++) { if (callback.call(thisArg, temps[i], i, temps) == false) { break; } }
        } else {
            for (i in temps) { if (callback.call(thisArg, temps[i], i, temps) == false) { break; } }
        }
        return array;
    };
    coreArray.prototype.forEach = function (callback, thisArg) { return coreArray.forEach(this, callback, thisArg); };

    //  �������е�����Ԫ�ص���ָ���Ļص������� �ûص������ķ���ֵΪ�ۻ���������Ҵ˷���ֵ����һ�ε��øûص�����ʱ��Ϊ�����ṩ���ú����������²�����
    //      array:      ���衣 һ���������
    //      callback:   ���衣 һ����������ĸ������ĺ����� ���������е�ÿ��Ԫ�أ� reduce ����������� callbackfn ����һ�Ρ�
    //          �ûص������﷨�磺function callbackfn(previousValue, currentValue, currentIndex, array)
    //      initialValue:��ѡ�� ���ָ�� initialValue��������������ʼֵ�������ۻ��� ��һ�ε��� callbackfn �����Ὣ��ֵ��Ϊ������������ֵ�ṩ��
    //  ����ֵ��ͨ�����һ�ε��ûص�������õ��ۻ������
    //  �쳣��������������һ����ʱ�������� TypeError �쳣��
    //      1��callbackfn �������Ǻ�������
    //      2�����鲻����Ԫ�أ���δ�ṩ initialValue��
    //  ��ע������ṩ�� initialValue���� reduce ������������е�ÿ��Ԫ�ص���һ�� callbackfn ����������������˳�򣩡�
    //      ���δ�ṩ initialValue���� reduce ������Դӵڶ���Ԫ�ؿ�ʼ��ÿ��Ԫ�ص��� callbackfn ������
    //      �ص������ķ���ֵ����һ�ε��ûص�����ʱ��Ϊ previousValue �����ṩ�� ���һ�ε��ûص�������õķ���ֵΪ reduce �����ķ���ֵ��
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679975(v=vs.94).aspx
    coreArray.reduce = coreArray.reduce ? coreArray.reduce : function (array, callback, initialValue) {
        if (!coreUtil.isFunction(callback)) { throw "����Ĳ��� callback ����һ��������"; }
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0 && (initialValue === undefined)) { throw "���鲻����Ԫ�أ���δ�ṩ initialValue��"; }
        var index = 0;
        if (initialValue === undefined) { initialValue = array[0]; index = 1; }
        for (var i = index; i < array.length; i++) {
            initialValue = callback.call(this, initialValue, array[i], i, array);
        }
        return initialValue;
    };
    coreArray.prototype.reduce = function (callback, initialValue) { return coreArray.reduce(this, callback, initialValue); };

    //  ������˳��������е�����Ԫ�ص���ָ���Ļص������� �ûص������ķ���ֵΪ�ۻ���������Ҵ˷���ֵ����һ�ε��øûص�����ʱ��Ϊ�����ṩ���ú����������²�����
    //      array:      ���衣 һ���������
    //      callback:   ���衣 һ����������ĸ������ĺ����� ���������е�ÿ��Ԫ�أ� reduce ����������� callbackfn ����һ�Ρ�
    //          �ûص������﷨�磺function callbackfn(previousValue, currentValue, currentIndex, array)
    //      initialValue:��ѡ�� ���ָ�� initialValue��������������ʼֵ�������ۻ��� ��һ�ε��� callbackfn �����Ὣ��ֵ��Ϊ������������ֵ�ṩ��
    //  ����ֵ��ͨ�����һ�ε��ûص�������õ��ۻ������
    //  �쳣��������������һ����ʱ�������� TypeError �쳣��
    //      1��callbackfn �������Ǻ�������
    //      2�����鲻����Ԫ�أ���δ�ṩ initialValue��
    //  ��ע������ṩ�� initialValue���� reduceRight �����ᰴ��������˳��������е�ÿ��Ԫ�ص���һ�� callbackfn ������
    //      ���δ�ṩ initialValue���� reduceRight �����ᰴ��������˳���ÿ��Ԫ�أ��ӵ����ڶ���Ԫ�ؿ�ʼ������ callbackfn ������
    //      �ص������ķ���ֵ����һ�ε��ûص�����ʱ��Ϊ previousValue �����ṩ�� ���һ�ε��ûص�������õķ���ֵΪ reduceRight �����ķ���ֵ��
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679979(v=vs.94).aspx
    coreArray.reduceRight = coreArray.reduceRight ? coreArray.reduceRight : function (array, callback, initialValue) {
        if (!coreUtil.isFunction(callback)) { throw "����Ĳ��� callback ����һ��������"; }
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0 && (initialValue === undefined)) { throw "���鲻����Ԫ�أ���δ�ṩ initialValue��"; }
        var index = array.length - 1;
        if (initialValue === undefined) { initialValue = array[array.length - 1]; index = array.length - 2; }
        for (var i = index; i > -1; i--) {
            initialValue = callback.call(this, initialValue, array[i], i, array);
        }
        return initialValue;
    };
    coreArray.prototype.reduceRight = function (callback, initialValue) { return coreArray.reduceRight(this, callback, initialValue); };

    //  ȷ����������г�Ա�Ƿ�����ָ���Ĳ��ԣ��ú����������²�����
    //      array:      ���衣 һ���������
    //      callback:   ���衣 һ������������������ĺ����� every ������Ϊ array1 �е�ÿ��Ԫ�ص��� callbackfn ������ֱ�� callbackfn ���� false����ֱ����������Ľ�β��
    //      thisArg:    ��ѡ�� ���� callbackfn ������Ϊ������ this �ؼ��ֵĶ��� ���ʡ�� thisArg���� undefined ������ this ֵ��
    //  ����ֵ����� callbackfn ����Ϊ��������Ԫ�ط��� true����Ϊ true������Ϊ false�� �������û��Ԫ�أ��� every ���������� true��
    //  ��ע�������������֮�⣬ every �������ɾ��� length �����Ҿ����Ѱ����ֱ������������������κζ���ʹ�á�
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679981(v=vs.94).aspx
    coreArray.every = coreArray.every ? coreArray.every : function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (array.length == 0) { return true; }
        if (!coreUtil.isFunction(callback)) { throw "����Ĳ��� callback ����һ��������"; }
        for (var i = 0; i < array.length; i++) {
            if (callback.call(thisArg, array[i], i, array) == false) { return false; }
        }
        return true;
    };
    coreArray.prototype.every = function (callback, thisArg) { return coreArray.every(this, callback, thisArg); };

    //  ȷ��ָ���Ļص������Ƿ�Ϊ�����е�����һ��Ԫ�ط��� true���ú����������²�����
    //      array:      ���衣 һ���������
    //      callback:   ���衣 һ������������������ĺ����� some ������Ϊ array1 �е�ÿ��Ԫ�ص��� callbackfn ������ֱ�� callbackfn ���� true����ֱ����������Ľ�β��
    //          �ú����﷨�磺function callbackfn(value, index, array1)
    //      thisArg:    ��ѡ�� ���� callbackfn ������Ϊ������ this �ؼ��ֵĶ��� ���ʡ�� thisArg���� undefined ������ this ֵ��
    //  ����ֵ����� callbackfn ����Ϊ�����е�����һ��Ԫ�ط��� true����Ϊ true������Ϊ false��
    //  �쳣����� callbackfn �������Ǻ������������� TypeError �쳣��
    //  ��ע��some �����ᰴ��������˳���ÿ������Ԫ�ص��� callbackfn ������ֱ�� callbackfn �������� true�� ����ҵ����� callbackfn ���� true ��Ԫ�أ��� some �������������� true�� ����ص������κ�Ԫ�ط��� true���� some �����᷵�� false��
    //      �����������֮�⣬ some �������ɾ��� length �����Ҿ����Ѱ����ֱ������������������κζ���ʹ�á�
    //  �ο���http://msdn.microsoft.com/ZH-CN/library/ie/ff679978(v=vs.94).aspx
    coreArray.some = coreArray.some ? coreArray.some : function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(callback)) { throw "����Ĳ��� callback ����һ��������"; }
        for (var i = 0; i < array.length; i++) {
            if (callback.call(thisArg, array[i], i, array) == true) { return true; }
        }
        return false;
    };
    coreArray.prototype.some = function (callback, thisArg) { return coreArray.some(this, callback, thisArg); };

    //  ����ָ�������е�һ�����������ж�����Ὣ�䷵�أ��ú����������²�����
    //      array:      ���衣 һ���������
    //      callback:   ��ѡ�� һ������������������ĺ����� first ������Ϊ array �е�ÿ��Ԫ�ص��� callbackfn ������ֱ�� callbackfn ���� true����ֱ����������Ľ�β��
    //          �ú����﷨�磺function callbackfn(value, index, array1)
    //      thisArg:    ��ѡ�� ���� callbackfn ������Ϊ������ this �ؼ��ֵĶ��� ���ʡ�� thisArg���� undefined ������ this ֵ��
    //  ����ֵ����������˲��� callbackfn ������ array �е�һ�����»ص����� callback ���� true ����Ŀ��
    //      ���δ������� callback���򷵻� array �еĵ�һ��Ԫ�أ�
    //      ������� array �������κ�Ԫ�أ����� callback �ص����������� array ������Ԫ�غ�ʼ��δ���� true ֵ���� first �������� null��
    //  ��ע��first �����ᰴ��������˳���ÿ������Ԫ�ص��� callbackfn ������ֱ�� callbackfn �������� true�� ����ҵ����� callbackfn ���� true ��Ԫ�أ��� first �������������ظ�Ԫ�ء� ����ص������κ�Ԫ�ط��� true���� first �����᷵�� undefined��
    //      �����������֮�⣬ first �������ɾ��� length �����Ҿ����Ѱ����ֱ������������������κζ���ʹ�á�
    coreArray.first = function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(callback)) { return array.length ? array[0] : null; }
        for (var i = 0; i < array.length; i++) {
            if (callback.call(thisArg, array[i], i, array) == true) { return array[i]; }
        }
        return undefined;
    };
    coreArray.prototype.first = function (callback, thisArg) { return coreArray.first(this, callback, thisArg); };

    //  ����ָ�����������һ�����������ж�����Ὣ�䷵�أ��ú����������²�����
    //      array:      ���衣 һ���������
    //      callback:   ��ѡ�� һ������������������ĺ����� last ������� array �Ľ���λ����Ϊ����ÿ��Ԫ�ص��� callbackfn ������ֱ�� callbackfn ���� true����ֱ�������������ʼλ�á�
    //          �ú����﷨�磺function callbackfn(value, index, array1)
    //      thisArg:    ��ѡ�� ���� callbackfn ������Ϊ������ this �ؼ��ֵĶ��� ���ʡ�� thisArg���� undefined ������ this ֵ��
    //  ����ֵ����������˲��� callbackfn ������ array �����һ�����»ص����� callback ���� true ����Ŀ��
    //      ���δ������� callback���򷵻� array �е����һ��Ԫ�أ�
    //      ������� array �������κ�Ԫ�أ����� callback �ص����������� array ������Ԫ�غ�ʼ��δ���� true ֵ���� last �������� null��
    //  ��ע��last �����ᰴ��������˳���ÿ������Ԫ�ص��� callbackfn ������ֱ�� callbackfn �������� true�� ����ҵ����� callbackfn ���� true ��Ԫ�أ��� last �������������ظ�Ԫ�ء� ����ص������κ�Ԫ�ط��� true���� last �����᷵�� undefined��
    //      �����������֮�⣬ last �������ɾ��� length �����Ҿ����Ѱ����ֱ������������������κζ���ʹ�á�
    coreArray.last = function (array, callback, thisArg) {
        array = coreArray.likeArray(array) ? array : [];
        if (!coreUtil.isFunction(callback)) { return array.length ? array[array.length - 1] : null; }
        for (var i = array.length - 1; i >= 0; i--) {
            if (callback.call(thisArg, array[i], i, array) == true) { return array[i]; }
        }
        return undefined;
    };
    coreArray.prototype.last = function (callback, thisArg) { return coreArray.last(this, callback, thisArg); };

    //  ��ȡָ�������ǰ N ��Ԫ�������ɵ�һ�������飻�ú����������²�����
    //      array:  ���衣 һ���������
    //      length: ���롣 һ�� Number ����ֵ����ʾҪ��ȡ�����������
    //  ����ֵ������ָ����������� array ��ǰ�泤��Ϊ length ��Ԫ�������ɵ�һ���µ����顣
    //      ��� length ��ֵΪ 0���򷵻�һ�������飻
    //      ��� length ��ֵ���� array.length���򷵻� array ��һ��������
    coreArray.left = function (array, length) {
        array = coreArray.likeArray(array) ? array : [];
        if (!length || !coreUtil.isNumeric(length) || length < 0) { return []; }
        if (length > array.length) { return coreArray.clone(array); }
        return coreArray.range(array, 0, length);
    };
    coreArray.prototype.left = function (length) { return coreArray.left(this, length); };

    //  ��ȡָ������ĺ� N ��Ԫ�������ɵ�һ�������飻�ú����������²�����
    //      array:  ���衣 һ���������
    //      length: ���롣 һ�� Number ����ֵ����ʾҪ��ȡ�����������
    //  ����ֵ������ָ����������� array �ĺ��泤��Ϊ length ��Ԫ�������ɵ�һ���µ����顣
    //      ��� length ��ֵΪ 0���򷵻�һ�������飻
    //      ��� length ��ֵ���� array.length���򷵻� array ��һ��������
    coreArray.right = function (array, length) {
        array = coreArray.likeArray(array) ? array : [];
        if (!length || !coreUtil.isNumeric(length) || length < 0) { return []; }
        if (length > array.length) { return coreArray.clone(array); }
        return coreArray.range(array, array.length + 1 - length);
    };
    coreArray.prototype.right = function (length) { return coreArray.right(this, length); };








    /////////////////////////////////////////////////////////////////////////////////////////////// 
    //  javascript �����ں����������䡣
    /////////////////////////////////////////////////////////////////////////////////////////////// 

    //  �ж�ָ���Ķ����Ƿ�Ϊ�Ϸ�������(Date)��ʽ����
    coreDate.isDate = function (date) { return coreUtil.isDate(date); };

    //  �ж�ָ���Ķ����Ƿ�Ϊһ������(Date)���������һ�����ڸ�ʽ���ַ�����
    coreDate.likeDate = function (date) { return coreDate.isDate(date) || coreString.isDate(date); };

    //  �� String �� Number ����ֵת���� Date ����ֵ��
    //  ����ֵ���÷�������һ���´����� Date ����ֵ��
    coreDate.toDate = function (obj) {
        return coreUtil.isDate(obj) ? obj : (coreUtil.isString(obj) ? coreString.toDate(obj) : new Date(obj));
    };

    //  �ж�ָ���������ַ����Ƿ��ǺϷ��ĳ����ڸ�ʽ��
    //  �ú��������� coreString.isLongDate ������
    coreDate.isLongDate = function (date) { return coreString.isLongDate(date); };

    //  �ж�ָ���������ַ����Ƿ��ǺϷ��Ķ����ڸ�ʽ��
    //  �ú��������� coreString.isShortDate ������
    coreDate.isShortDate = function (date) { return coreString.isShortDate(date); };

    //  �ж�ָ���������Ƿ�Ϊ���ꣻ�ú����������²�����
    //      date: ������һ�� ����(Date) ����Ҳ�����Ǳ�ʾ���ڵ��ַ�����������һ����ʾ��ݵ����֡�
    //  ����ֵ�����ָ�������������꣬�򷵻� true�����򷵻� false��
    coreDate.isLeapYear = function (date) {
        var y = 0;
        if (coreDate.isDate(date)) {
            y = new Date(date).getYear();
        } else if (coreUtil.isNumeric(date)) {
            y = date;
        } else {
            throw "����Ĳ��� date ���������ͱ���Ϊ Date��String ���� Number��";
        }
        var b = false;
        if (y >= 0) {
            b = (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
        } else {
            b = (y % 4 == 1 && y % 100 != 0) || (y % 400 == 1);
        }
        return b;
    };
    coreDate.prototype.isLeapYear = function () { return coreDate.isLeapYear(this); };

    //  ����һ���µ� ����(Date) ���󣬷��ص�ֵ�뵱ǰ ���ڶ��� ��ֵ��ͬ��
    coreDate.clone = function (date) {
        var d = coreDate.toDate(date).getTime();
        return new Date(d);
    };
    coreDate.prototype.clone = function () { return coreDate.clone(this); };

    //  �Ƚ��������ڶ���Ĵ�С���ú����������²�����
    //      date1: �� 1 �����Ƚϵ����ڶ���
    //      date2: �� 2 �����Ƚϵ����ڶ���
    //  ����ֵ����� date1 > date2���򷵻�һ������ 0 ��ֵ��
    //      ��� date1 < date2���򷵻�һ��С�� 0 ��ֵ��
    //      ��� date1 == date2���򷵻� 0��
    coreDate.compare = function (date1, date2) {
        date1 = coreDate.toDate(date1);
        date2 = coreDate.toDate(date2);
        var d1 = date1.getTime(), d2 = date2.getTime();
        return coreUtil.compare(d1, d2);
    };
    coreDate.prototype.compareTo = function (date) { return coreDate.compare(this, date); };
    coreDate.prototype.equals = function (date) { return coreDate.compare(this, date) == 0; };

    //  ��ȡָ�����ڶ���ǰ��ʾ�ļ��ȣ�0 - 3��
    coreDate.getQuarter = function (date) {
        date = coreDate.toDate(date);
        var m = date.getMonth();
        var q = 0;
        if (m >= 0 && m < 3) {
            q = 0;
        } else if (m >= 3 && m < 6) {
            q = 1;
        } else if (m >= 6 && m < 9) {
            q = 2;
        } else if (m >= 9 && m < 12) {
            q = 3;
        }
        return q;
    };
    coreDate.prototype.getQuarter = function () { return coreDate.getQuarter(this); };

    //  ��ȡ��ǰ���ڶ����ʾ�����ܵ����ڼ���0 - 6��
    coreDate.getDayOfWeek = function (date) {
        date = coreDate.toDate(date);
        return date.getDay();
    };
    coreDate.prototype.getDayOfWeek = function () { return coreDate.getDayOfWeek(this); };

    //  ��ȡ��ǰ���ڶ���������ĵڼ��ܼ�����
    coreDate.getWeek = function (date, weekStart) {
        date = coreDate.toDate(date);
        weekStart = (weekStart || 0) - 0;
        if (!coreUtil.isNumeric(weekStart) || weekStart > 6) { weekStart = 0; }
        var year = date.getFullYear(),
            firstDay = new Date(year, 0, 1),
            firstWeekDays = 7 - firstDay.getDay() + weekStart,
            dayOfYear = (((new Date(year, date.getMonth(), date.getDate())) - firstDay) / (24 * 3600 * 1000)) + 1;
        return Math.ceil((dayOfYear - firstWeekDays) / 7) + 1;
    };
    coreDate.prototype.getWeek = function (weekStart) { return coreDate.getWeek(this, weekStart); };

    //  ��ȡ��ǰ���ڶ��������µĵڼ��ܼ�����
    coreDate.getWeekOfMonth = function (date, weekStart) {
        date = coreDate.toDate(date);
        weekStart = (weekStart || 0) - 0;
        if (!coreUtil.isNumeric(weekStart) || weekStart > 6) { weekStart = 0; }
        var dayOfWeek = date.getDay(),
            day = date.getDate();
        return Math.ceil((day - dayOfWeek - 1) / 7) + ((dayOfWeek >= weekStart) ? 1 : 0);
    };
    coreDate.prototype.getWeekOfMonth = function (weekStart) { return coreDate.getWeekOfMonth(this, weekStart); };

    //  ��ָ�����ڶ������ָ���ĺ�������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      millisec: Ҫ��ӵĺ�������������һ��������
    //  ����ֵ��date ���ָ�������������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addTime = function (date, millisec) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(millisec)) { millisec = Date.parse(millisec); }
        return new Date(d + millisec);
    };
    coreDate.prototype.addTime = function (millisec) { return coreDate.addTime(this, millisec); };

    //  ��ָ�����ڶ������ָ����������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      days: Ҫ��ӵ�������������һ��������
    //  ����ֵ��date ���ָ�����������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addDays = function (date, days) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(days)) { return new Date(d); }
        var millisec = 86400000 * days;
        return new Date(d + millisec);
    };
    coreDate.prototype.addDays = function (days) { return coreDate.addDays(this, days); };

    //  ��ָ�����ڶ������ָ����Сʱ����������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      hours: Ҫ��ӵ�Сʱ����������һ��������
    //  ����ֵ��date ���ָ��Сʱ�������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addHours = function (date, hours) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(hours)) { return new Date(d); }
        var millisec = 3600000 * hours;
        return new Date(d + millisec);
    };
    coreDate.prototype.addHours = function (hours) { return coreDate.addHours(this, hours); };

    //  ��ָ�����ڶ������ָ���ĺ�������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      millisec: Ҫ��ӵĺ�������������һ��������
    //  ����ֵ��date ���ָ�������������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addMilliseconds = function (date, millisec) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(millisec)) { return new Date(d); }
        return new Date(d + millisec);
    };
    coreDate.prototype.addMilliseconds = function (millisec) { return coreDate.addMilliseconds(this, millisec); };

    //  ��ָ�����ڶ������ָ���ķ�������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      minutes: Ҫ��ӵķ�������������һ��������
    //  ����ֵ��date ���ָ�������������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addMinutes = function (date, minutes) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(minutes)) { return new Date(d); }
        var millisec = 60000 * minutes;
        return new Date(d + millisec);
    };
    coreDate.prototype.addMinutes = function (minutes) { return coreDate.addMinutes(this, minutes); };

    //  ��ָ�����ڶ������ָ��������������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      weeks: Ҫ��ӵ�����������������һ��������
    //  ����ֵ��date ���ָ���������������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addWeeks = function (date, weeks) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(weeks)) { return new Date(d); }
        var millisec = 86400000 * 7 * weeks;
        return new Date(d + millisec);
    };
    coreDate.prototype.addWeeks = function (weeks) { return coreDate.addWeeks(this, weeks); };

    //  ��ָ�����ڶ������ָ����������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      months: Ҫ��ӵ�������������һ��������
    //  ����ֵ��date ���ָ�����������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addMonths = function (date, months) {
        date = coreDate.toDate(date);
        if (!coreUtil.isNumeric(months)) { months = 0; }
        return new Date(date.getFullYear(), date.getMonth() + months, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
    };
    coreDate.prototype.addMonths = function (months) { return coreDate.addMonths(this, months); };

    //  ��ָ�����ڶ������ָ����������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      seconds: Ҫ��ӵ�������������һ��������
    //  ����ֵ��date ���ָ�����������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addSeconds = function (date, seconds) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(seconds)) { return new Date(d); }
        var millisec = 1000 * seconds;
        return new Date(d + millisec);
    };
    coreDate.prototype.addSeconds = function (seconds) { return coreDate.addSeconds(this, seconds); };

    //  ��ָ�����ڶ������ָ���İ���������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      ticks: Ҫ��ӵİ���������������һ��������
    //  ����ֵ��date ���ָ���������������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addTicks = function (date, ticks) {
        date = coreDate.toDate(date);
        var d = Date.parse(date);
        if (!coreUtil.isNumeric(ticks)) { return new Date(d); }
        var millisec = 0.000001 * ticks;
        return new Date(d + millisec);
    };
    coreDate.prototype.addTicks = function (ticks) { return coreDate.addTicks(this, ticks); };

    //  ��ָ�����ڶ������ָ����������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      years: Ҫ��ӵ�������������һ��������
    //  ����ֵ��date ���ָ�����������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addYears = function (date, years) {
        date = coreDate.toDate(date);
        if (!coreUtil.isNumeric(years)) { years = 0; }
        return new Date(date.getFullYear() + years, date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
    };
    coreDate.prototype.addYears = function (years) { return coreDate.addYears(this, years); };

    //  ��ָ�����ڶ������ָ���ļ�������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      quarters: Ҫ��ӵļ�������������һ��������
    //  ����ֵ��date ���ָ�������������ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.addQuarters = function (date, quarters) {
        date = coreDate.toDate(date);
        if (!coreUtil.isNumeric(quarters)) { quarters = 0; }
        return new Date(date.getFullYear(), date.getMonth() + quarters * 3, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
    };
    coreDate.prototype.addQuarters = function (quarters) { return coreDate.addQuarters(this, quarters); };

    //  ��ָ�����ڶ������ָ�����ڲ��ֵ�ָ������������һ���µ����ڶ��󣻸ú����������²�����
    //      date: Դ���ڶ���
    //      datepart: ָ�������ڲ��֣��ַ�����ʽ����ѡ��ֵ�޶�Ϊ��
    //          yy �� yyyy:  ��ʾ�ꣻ
    //          qq �� q:     ��ʾ���ȣ�
    //          mm �� m:     ��ʾ�£�
    //          dd �� d:     ��ʾ��(��)��
    //          wk �� ww:    ��ʾ�ܣ�
    //          hh:          ��ʾСʱ��
    //          mi �� n:     ��ʾ���ӣ�
    //          ss �� s:     ��ʾ�룻
    //          ms:          ��ʾ���룻
    //      number: Ҫ��ӵ�ָ�����ڲ��ֵ�ָ��������������һ��������
    //  ����ֵ��date ���ָ�����ڲ��ֵ�ָ�������һ����ֵ��ע�⣬�÷��������޸�Դ���ڶ��� date�����Ƿ���һ���´��������ڶ���
    coreDate.add = function (date, datepart, number) {
        if (!coreUtil.isString(datepart)) { return date; }
        if (!coreUtil.isNumeric(number)) { return date; }
        datepart = datepart.toLowerCase();
        var d = null;
        switch (datepart) {
            case "yy":
            case "yyyy":
                d = coreDate.addYears(date, number);
                break;
            case "qq":
            case "q":
                d = coreDate.addQuarters(date, number);
                break;
            case "mm":
            case "m":
                d = coreDate.addMonths(date, number);
                break;
            case "dd":
            case "d":
                d = coreDate.addDays(date, number);
                break;
            case "wk":
            case "ww":
                d = coreDate.addWeeks(date, number);
                break;
            case "hh":
                d = coreDate.addHours(date, number);
                break;
            case "mi":
            case "n":
                d = coreDate.addMinutes(date, number);
                break;
            case "ss":
            case "s":
                d = coreDate.addSeconds(date, number);
                break;
            case "ms":
                d = coreDate.addMilliseconds(date, number);
                break;
            default:
                throw "����Ĳ��� datepart Ϊ����ʶ���ֵ��";
                break;
        }
        return d;
    };
    coreDate.prototype.add = function (datepart, number) { return coreDate.add(this, datepart, number); };

    //  �Ƚ��������ڶ���ָ�����ֵĲ�����رȽϽ�����ú����������²�����
    //      date: Դ���ڶ���
    //      datepart: ָ�������ڲ��֣��ַ�����ʽ����ѡ��ֵ�޶�Ϊ��
    //          yy �� yyyy:  ��ʾ�ꣻ
    //          qq �� q:     ��ʾ���ȣ�
    //          mm �� m:     ��ʾ�£�
    //          dd �� d:     ��ʾ��(��)��
    //          wk �� ww:    ��ʾ�ܣ�
    //          hh:          ��ʾСʱ��
    //          mi �� n:     ��ʾ���ӣ�
    //          ss �� s:     ��ʾ�룻
    //          ms:          ��ʾ���룻
    //      value: Ҫ�Ƚϵ����ڶ���
    //  ����ֵ������ date ���ڶ��� �� value ���ڶ��� ָ�����ֵ���ֵ�Ĳ
    coreDate.diff = function (date, datepart, value) {
        if (!coreUtil.isString(datepart)) { return undefined; }
        date = coreDate.toDate(date);
        value = coreDate.toDate(value);
        datepart = datepart.toLowerCase();
        var d = null;
        switch (datepart) {
            case "yy":
            case "yyyy":
                d = value.getFullYear() - date.getFullYear();
                break;
            case "qq":
            case "q":
                var quarter = coreDate.getQuarter(value);
                d = quarter + ((value.getFullYear() - date.getFullYear()) * 3) - quarter;
                break;
            case "mm":
            case "m":
                d = (value.getMonth() + 1) + ((value.getFullYear() - date.getFullYear()) * 12) - (date.getMonth() + 1);
                break;
            case "dd":
            case "d":
                d = parseInt((value.getTime() - date.getTime()) / 86400000);
                break;
            case "wk":
            case "ww":
                d = parseInt((value.getTime() - date.getTime()) / (86400000 * 7));
                break;
            case "hh":
                d = parseInt((value.getTime() - date.getTime()) / 3600000);
                break;
            case "mi":
            case "n":
                d = parseInt((value.getTime() - date.getTime()) / 60000);
                break;
            case "ss":
            case "s":
                d = parseInt((value.getTime() - date.getTime()) / 1000);
                break;
            case "ms":
                d = value.getTime() - date.getTime();
                break;
            default:
                throw "����Ĳ��� datepart Ϊ����ʶ���ֵ��";
                break;
        }
        return d;
    };
    coreDate.prototype.diff = function (datepart, value) { return coreDate.diff(this, datepart, value); };

    //  ����ָ�����ڶ����ָ�����ֵ�ֵ���ú����������²�����
    //      date: Դ���ڶ���
    //      datepart: ָ�������ڲ��֣��ַ�����ʽ����ѡ��ֵ�޶�Ϊ��
    //          yy �� yyyy:  ��ʾ�ꣻ
    //          qq �� q:     ��ʾ���ȣ�
    //          mm �� m:     ��ʾ�£�
    //          dd �� d:     ��ʾ��(��)��
    //          wk �� ww:    ��ʾ�ܣ�
    //          hh:          ��ʾСʱ��
    //          mi �� n:     ��ʾ���ӣ�
    //          ss �� s:     ��ʾ�룻
    //          ms:          ��ʾ���룻
    //  ����ֵ������ָ�����ڶ����ָ�����ֵ�ֵ��
    coreDate.part = function (date, datepart) {
        if (!coreUtil.isString(datepart)) { return undefined; }
        date = coreDate.toDate(date);
        datepart = datepart.toLowerCase();
        var d = null;
        switch (datepart) {
            case "yy":
            case "yyyy":
                d = date.getFullYear();
                break;
            case "qq":
            case "q":
                d = coreDate.getQuarter(date);
                break;
            case "mm":
            case "m":
                d = date.getMonth();
                break;
            case "dd":
            case "d":
                d = date.getDate();
                break;
            case "wk":
            case "ww":
                d = date.getWeek();
                break;
            case "hh":
                d = date.getHours();
                break;
            case "mi":
            case "n":
                d = date.getMinutes();
                break;
            case "ss":
            case "s":
                d = date.getSeconds();
                break;
            case "ms":
                d = date.getMilliseconds();
                break;
            default:
                throw "����Ĳ��� datepart Ϊ����ʶ���ֵ��";
                break;
        }
        return d;
    };
    coreDate.prototype.part = function (datepart) { return coreDate.part(this, datepart); };

    //  ���ص�ǰ���ڶ���ĸ�ʽ���ַ�ֵ���ú����������²�����
    //      date:   Ҫ���и�ʽ�������ڶ���
    //      format: �����ַ�����ʽ���壻����ò��������룬��Ĭ��ֵΪ "yyyy-MM-dd"
    coreDate.format = function (date, format) {
        date = coreDate.toDate(date);
        format = coreUtil.isEmptyObjectOrNull(format) ? "yyyy-MM-dd" : format;
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };
    coreDate.prototype.format = function (format) { return coreDate.format(this, format); };

    //  ��ȡ��ǰ����ʱ��ĳ��ַ�����ʽ�����ص�����ʱ���ַ�����ʽ�� ��2013��05��16�� ������ �ļ�, ���� 15:38:11��
    coreDate.toLongDateTimeString = function (date) {
        date = coreDate.toDate(date);
        var year = date.getFullYear(),
            month = date.getMonth(),
            day = date.getDate(),
            hours = date.getHours(),
            minutes = date.getMinutes(),
            seconds = date.getSeconds(),
            week = date.getDay(),
            quarter = coreDate.getQuarter(date),
            hoursArray = ["��ҹ", "�賿", "����", "����", "����", "����", "����", "����"],
            weekArray = ["������", "����һ", "���ڶ�", "������", "������", "������", "������"],
            //monthArray = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
            quarterArray = ["��", "��", "��", "��"],
            hourSpan = hoursArray[Math.floor(parseFloat(hours) / 3)],
            weekSpan = weekArray[week],
            //monthSpan = monthArray[month],
            quarterSpan = quarterArray[quarter];
        return coreString.format(
            "{0}��{1}��{2}�� {3} {4}��, {5} {6}:{7}:{8}",
            year,
            ("" + (month + 101)).substr(1),
            ("" + (day + 100)).substr(1),
            weekSpan,
            quarterSpan,
            hourSpan,
            ("" + (hours + 100)).substr(1),
            ("" + (minutes + 100)).substr(1),
            ("" + (seconds + 100)).substr(1));
    };
    coreDate.prototype.toLongDateTimeString = function () { return coreDate.toLongDateTimeString(this); };





    var _html5ValidateCache = {};
    //  ����ָ���� html ��ǩ�Ƿ�߱���Ӧ�����ԣ��ú����������²�����
    //      propName:   �����Ե���������
    //      tagName:    �����Ե� html ��ǩ����
    //  ����ֵ�����ָ���ı�ǩ��(tagName)����ʾ�� html ��ǩ�߱���Ӧ�������� propName���򷵻� true�����򷵻� false��
    coreHtml5.testProp = function (propName, tagName) {
        propName = coreString.trim(propName);
        tagName = coreString.trim(tagName);
        if (propName) { propName = propName.toLowerCase(); }
        if (tagName) { tagName = tagName.toLowerCase(); }
        var tag = _html5ValidateCache[tagName];
        if (!tag) { _html5ValidateCache[tagName] = tag = document.createElement(tagName); }
        return propName in tag ? true : false;
    };



    //  �ж�ָ���Ķ����Ƿ�Ϊһ�� HTML-DOM ���󣻸ú����������²�����
    //      obj��    Ҫ�жϵĶ���
    //      doc��    �ò�����ѡ����ʾ obj ����ҳ��� document�����������ò�������Ĭ��ʹ�õ�ǰҳ��� document��
    //  ����ֵ����� obj ��һ�� HTML-DOM �����Ҵ�����ָ���� document �У��򷵻� true�����򷵻� false��
    coreUtil.isDOM = function (obj, doc) {
        if (!obj) { return false; }
        doc = doc || document;
        return obj.nodeName && obj.nodeType == 1 && obj.ownerDocument == doc;
    };

    //  �ж�ָ���Ķ����Ƿ�Ϊһ�� HTML-DOM ������� jQuery ���󣻸ú����������²�����
    //      obj��    Ҫ�жϵĶ���
    //      doc��    �ò�����ѡ����ʾ obj ����ҳ��� document�����������ò�������Ĭ��ʹ�õ�ǰҳ��� document��
    //  ����ֵ����� obj ��һ�� HTML-DOM ���� jQuery �����Ҵ�����ָ���� document �У��򷵻� true�����򷵻� false��
    coreUtil.isJqueryDOM = function (obj, doc) {
        if (!doc) {
            return coreUtil.isJqueryObject(obj) || coreUtil.isDOM(obj);
        } else {
            if (coreUtil.isJqueryObject(obj)) {
                var flag = true;
                for (var i = 0; i < obj.length; i++) {
                    var item = obj[i];
                    if (!item || !item.nodeName || item.nodeType != 1 || item.ownerDocument != doc) {
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    return coreUtil.isDOM(obj);
                }
            } else {
                return coreUtil.isDOM(obj);
            }
        }
    };

    //  �����Ա�ʾ�Ƿ�����������Զ������� DOM Ԫ������Ψһ��ʶ���Ĺ��ܡ�
    var _enableUniqueID = false;
    //  �����Ա�ʾ������Զ������� DOM Ԫ�����ӵ�Ψһ��ʶ�������ơ�
    coreUtil.uniqueIdName = "uniqueID";

    //  ��ȡ HTML DOM ����� GUID ֵ���ú����������²�����
    //      element:    ���룬��ʾ��Ҫ��ȡ uniqueID ���Ե� DOM ����
    //  ����ֵ������ element Ԫ�ص� uniqueID ֵ�������Ԫ��δ�����ֵ���򷵻� undefined��
    coreUtil.getElementUniqueID = function (element) {
        return element != undefined && element != null && element.getAttribute ? element.getAttribute(coreUtil.uniqueIdName) : null;
    };

    //  �ж� HTML DOM �����Ƿ���� uniqueID ���ԣ��ú����������²�����
    //      element:    ���룬��ʾ��Ҫ�ж��Ƿ���� uniqueID ���Ե� DOM ����
    //  ����ֵ����� element Ԫ�ؾ��� uniqueID ���ԣ��򷵻� true�����򷵻� false��
    coreUtil.hasUniqueID = function (element) {
        return !coreString.isNullOrWhiteSpace(coreUtil.getElementUniqueID(element));
    };

    //  �� HTML DOM ��������һ���µ� uniqueID ֵ���ú����������²�����
    //      element:    ���룬��ʾ��Ҫ���� uniqueID ���Ե� DOM ����
    //      uniqueID:   ��ѡ����ʾҪ�� element Ԫ�����õ� uniqueID ֵ������������ֵ�������� coreUtil.guid �����Զ�����һ�� uniqueID ֵ��
    coreUtil.setElementUniqueID = function (element, uniqueID) {
        if (element == undefined || element == null || !element.setAttribute) { return; }
        var nodeName = (element.nodeName || coreUtil.uniqueIdName) + "_";
        uniqueID = coreString.isNullOrWhiteSpace(uniqueID) ? nodeName + coreUtil.guid("N") : uniqueID;
        element.setAttribute(coreUtil.uniqueIdName, uniqueID, 0);
    };

    //  ��ʼ�� HTML DOM ����� uniqueID ֵ���ú����������²�����
    //      element:    ���룬��ʾ��Ҫ��ʼ�� uniqueID ���Ե� DOM ����
    //  ��ע���÷����ж� element Ԫ���Ƿ���� uniqueID ����ֵ��������򲻽����κθ��ģ����û����Ϊ������һ���µ� uniqueID ֵ��
    coreUtil.initElementUniqueID = function (element) {
        if (!coreUtil.hasUniqueID(element)) {
            coreUtil.setElementUniqueID(element);
        }
    };

    coreUtil._createElement = document.createElement;
    coreUtil._createDocumentFragment = document.createDocumentFragment;

    //  ��д document.createElement ������ʹ֮�ڴ��� dom �����ͬʱ�����������һ��Ψһ��ʶ�� uniqueID��
    coreUtil.createElement = function () {
        var element = coreUtil._createElement.apply(this, arguments);
        if (!_enableUniqueID) { return element; }
        coreUtil.initElementUniqueID(element);
        return element;
    };

    //  ��д document.createDocumentFragment ������ʹ֮�ڴ����ĵ���Ƭ�����ĵ���Ƭ����ӽڵ�ʱ���ӽڵ����һ��Ψһ��ʶ�� uniqueID��
    coreUtil.createDocumentFragment = function () {
        var documentFragment = coreUtil._createDocumentFragment.apply(this, arguments);
        if (!_enableUniqueID) { return documentFragment; }
        var appendChild = documentFragment.appendChild;
        documentFragment.appendChild = function (child) {
            $("*", child).add(child).each(function () { coreUtil.initElementUniqueID(this); });
            return appendChild.apply(this, arguments);
        };
        return documentFragment;
    };

    //  ���û��߽���������Զ��� DOM Ԫ������ȫ��Ψһ��ʶ�����ܣ��ú����������²�����
    //      enableUniqueID�����룬��������ֵ����ʾ���û���øù��ܣ�
    coreUtil.setEnableUniqueID = function (enableUniqueID) {
        enableUniqueID = coreUtil.isBoolean(enableUniqueID) ? enableUniqueID : false;
        _enableUniqueID = enableUniqueID;
        if (enableUniqueID) {
            document.createElement = coreUtil.createElement;
            document.createDocumentFragment = coreUtil.createDocumentFragment;
            $("*").each(function () { coreUtil.initElementUniqueID(this); });
        } else {
            document.createElement = coreUtil._createElement;
            document.createDocumentFragment = coreUtil._createDocumentFragment;
        }
    };

    //  ����������Զ��� DOM Ԫ������ȫ��Ψһ��ʶ�����ܣ�
    coreUtil.enableUniqueID = function () { coreUtil.setEnableUniqueID(true); };

    //  ����������Զ��� DOM Ԫ������ȫ��Ψһ��ʶ�����ܣ�
    coreUtil.disableUniqueID = function () { coreUtil.setEnableUniqueID(false); };

    //  ��ȡ������Ƿ��������Զ��� DOM Ԫ������ȫ��Ψһ��ʶ�����ܣ�
    coreUtil.getEnableUniqueID = function () { return _enableUniqueID; };



    //  �ж�ָ���� window �����Ƿ���пɷ��ʵĸ���ҳ�棻
    //  ����ֵ������һ�� Boolean ֵ��
    //      ��ǰҳ����һ�� FRAME/IFRAME ���Ҹ���ҳ��͵�ǰҳ��ͬ���򷵻� true��
    //      ��ǰҳ�治����һ�� FRAME/IFRAME �л򸸼�ҳ��͵�ǰҳ�治ͬ���򷵻� false��
    coreUtil.hasParentWindow = function (win) {
        var ret = false;
        try {
            var p = win.parent;
            ret = p && coreUtil.isWindow(p) && coreUtil.isObject(p.document) ? true : false;
        } catch (ex) { }
        return ret;
    };
    //  ��ȡ��ǰҳ��Ŀɷ���(ͬ��)�Ķ���ҳ�棻
    //  ����ֵ������һ�� window ����
    coreUtil.getTop = function () {
        var w = window;
        while (coreUtil.hasParentWindow(w) && w != w.parent) { w = w.parent; }
        return w;
    };
    //  ��ȡ��ǰҳ��Ŀɷ���(ͬ��)�ĸ���ҳ�棻
    //  ����ֵ������һ�� window ����
    coreUtil.getParent = function () {
        var w = window;
        if (coreUtil.hasParentWindow(w) && w != w.parent) { w = w.parent; }
        return w;
    };

    //  ��ȡ��ǰҳ�����ڶ���ҳ��� window �����������ҳ�治�ɷ��ʣ��򷵻شμ�ҳ��� window �����Դ����ơ�
    //  �����ǰҳ��Ϊ����ҳ���ǰҳ��ĸ���ҳ��͵�ǰҳ�治��ͬһ�����£��򷵻ص�ǰҳ��� window ����
    top = coreUtil.top = coreUtil.getTop();

    //  ��ȡ��ǰҳ�����ڸ���ҳ��� window �����������ҳ�治�ɷ��ʣ��򷵻ص�ǰҳ��� window ����
    //  �����ǰҳ��Ϊ����ҳ���ǰҳ��ĸ���ҳ��͵�ǰҳ�治��ͬһ�����£��򷵻ص�ǰҳ��� window ����
    parent = coreUtil.parent = coreUtil.getParent();

    //  �жϵ�ǰҳ���Ƿ�Ϊ��������ҳ��(������ IFRAME �ж�)��
    coreUtil.checkTopMost = function () {
        var ret = false;
        try {
            ret = window == window.top ? true : false;
        } catch (ex) { }
        return ret;
    };
    coreUtil.isTopMost = coreUtil.isTop = coreUtil.checkTopMost();

    //  �жϵ�ǰҳ���Ƿ�Ϊͬ���µĶ�������ҳ��
    coreUtil.isUtilTop = coreUtil.isUtilTopMost = window == coreUtil.top;

    //  �ж�ָ���� window �Ƿ���ڿɷ��ʵĸ���ҳ�棨�����жϵ� window ���� IFRAME ���Һ͸���ҳ�洦��ͬһ���У����Ҹ���ҳ����Ҳ������ jQuery��
    coreUtil.hasParentJquery = function (win) {
        var ret = false;
        try {
            var p = win.parent;
            ret = p && coreUtil.isWindow(p) && coreUtil.isObject(p.document) && coreUtil.isFunction(p.jQuery) ? true : false;
        } catch (ex) { }
        return ret;
    };
    coreUtil.getTopJquery = function () {
        if (coreUtil.isUtilTop) { return $; }
        var w = window;
        while (coreUtil.hasParentJquery(w) && w != w.parent) { w = w.parent; }
        return w.jQuery;
    };

    //  ��ȡ��ǰҳ�����ڶ������ڵ� jQuery ��������������ڲ����� jQuery ������� jQuery �����޷�����(������������) �򷵻شμ� jQuery �����Դ����ƣ�
    coreUtil.$ = coreUtil.jQuery = coreUtil.topJquery = coreUtil.getTopJquery();

    //  ��ȡ������ǰҳ��� iframe ����
    //  �����ǰҳ��Ϊ����ҳ���ǰҳ��ĸ���ҳ��͵�ǰҳ�治��ͬһ�����£��򷵻� null��
    coreUtil.currentFrame = null;

    //  ��ȡ������ǰҳ��� iframe ����� id��
    //  �����ǰҳ��Ϊ����ҳ���ǰҳ��ĸ���ҳ��͵�ǰҳ�治��ͬһ�����£��򷵻� null��
    coreUtil.currentFrameId = null;

    //  ��ȡ������ǰҳ��� iframe ����� uniqueID��
    //  �����ǰҳ��Ϊ����ҳ���ǰҳ��ĸ���ҳ��͵�ǰҳ�治��ͬһ�����£��򷵻� null��
    coreUtil.currentFrameUniqueID = null;
    coreUtil.getCurrentFrame = function () {
        if (coreUtil.isUtilTop) { return undefined; }
        var result = null;
        var frames = coreArray.merge([], top.document.getElementsByTagName("iframe"), top.document.getElementsByTagName("frame"));
        var find = function (frame) {
            var win = frame.contentWindow;
            if (win === window) { return frame; }
            try {
                if (!win || !coreUtil.isObject(win.document)) { return undefined; }
                var fs = coreArray.merge([], win.document.getElementsByTagName("iframe"), win.document.getElementsByTagName("frame"));
                $.each(fs, function () { result = find(this); return result == null; });
            } catch (ex) { }
            return result;
        };
        $.each(frames, function () { result = find(this); return result == null; });
        return result;
    };
    coreUtil.currentFrame = coreUtil.getCurrentFrame();
    if (coreUtil.currentFrame) { coreUtil.currentFrameId = coreUtil.currentFrame.id; }
    if (coreUtil.currentFrame) { coreUtil.currentFrameUniqueID = coreUtil.getElementUniqueID(coreUtil.currentFrame); }

    //  ��ȡ��ǰ�������
    coreUtil.getActiveElement = function () { return $(document.activeElement); };

    //  ��ȡ�����õ�ǰ window ����Ĵ�С���ú��������������أ�
    //  1��function ()����ȡ��ǰ window �Ĵ����С���÷�������һ����ʽ�� { width, height } �� JSON-Object��
    //  2��function (valType)����ȡ��ǰ window �Ĵ����С��ָ������ֵ���ú����������²�����
    //      valType: String ����ֵ���ò�����ֵ����Ϊ "width" �� "height" ����֮һ����ʾ���ص�ǰ window �����С���ĸ�����ֵ��
    //  3��function (size)�����õ�ǰ window �Ĵ����С���ú����������²�����
    //      size: JSON-Object ����ֵ���ò�����ʽ����Ϊ { width, height }����ʾҪ����ǰ�������õ��µĳߴ磻
    //  4��function (width, height)�����õ�ǰ window �Ĵ����С���ú����������²�����
    //      width : Number ����ֵ����ʾ������¿�ȣ�
    //      height: Number ����ֵ����ʾ������¸߶ȣ�
    //  5��function (valType, val)������ָ��������ֵ���ô���ĳߴ��С���ú����������²�����
    //      valType: String ����ֵ���ò�����ֵ����Ϊ "width" �� "height" ����֮һ����ʾҪ�����ĸ����ԣ�
    //      val: ��ʾ������¿�Ȼ����¿�ȣ���Ӧ valType ָʾ�����ԣ�
    coreUtil.windowSize = function () {
        var length = arguments.length, arg1, arg2, arg1Type, arg2Type,
            getSize = function () {
                var win = $(window);
                return { width: window.innerWidth ? window.innerWidth : win.width(), height: window.innerHeight ? window.innerHeight : win.height() };
            },
            size = getSize();
        if (length == 0) { return size; }
        arg1 = arguments[0];
        arg1Type = coreUtil.type(arg1);
        if (length == 1) {
            arg1 = arguments[0];
            if (arg1Type == "string") { return size[arg1]; }
            if (coreUtil.isPlainObject(arg1) || arg1Type == "function") { coreUtil.windowSize(arg1.width || size.width, arg1.height || size.height); }
        }
        if (length >= 2) {
            arg2 = arguments[1];
            arg2Type = coreUtil.type(arg2);
            if (arg1Type == "string" && arg2Type == "number") {
                var newSize = $.extend({}, size);
                newSize[arg1] = arg2;
                if (size.width != newSize.width || size.height != newSize.height) { window.resizeTo(newSize.width, newSize.height); }
            }
            if (arg1Type == "number" && arg2Type == "number") { window.resizeTo(arg1, arg2); }
        }
    };

    //  ��ȡ�����õ�ǰ window �����λ�ã��÷��������������أ�
    //  1��function ()����ȡ��ǰ window �Ĵ���λ�ã��÷�������һ����ʽ�� { left, top } �� JSON-Object��
    //  2��function (valType)����ȡ��ǰ window �Ĵ���λ�õ�ָ������ֵ���ú����������²�����
    //      valType: String ����ֵ���ò�����ֵ����Ϊ "left" �� "top" ����֮һ����ʾ���ص�ǰ window ����λ�õ��ĸ�����ֵ��
    //  3��function (left, top)�����õ�ǰ window �Ĵ���λ�ã��ú����������²�����
    //      left: Number ����ֵ����ʾ������λ�õ� left ֵ��
    //      top : Number ����ֵ����ʾ������λ�õ� top ֵ��
    //  4��function (valType, val)�����õ�ǰ window �Ĵ���λ�ã��ú����������²�����
    //      valType: String ����ֵ����ʾҪ���õ�ֵ�����ͣ��� left ���� top���ò�����ֵ����Ϊ "left" �� "top" ����֮һ��
    //      val:     Number ����ֵ����ʾ valType ��Ӧ��ֵ��
    coreUtil.windowOffset = function () {
        var length = arguments.length, arg1, arg2, arg1Type, arg2Type,
            getOffset = function () { return { left: window.screenLeft || window.screenX, top: window.screenTop || window.screenY }; },
            offset = getOffset();
        if (length == 0) { return offset; }
        arg1 = arguments[0];
        arg1Type = coreUtil.type(arg1);
        if (length == 1) {
            arg1 = arguments[0];
            if (arg1Type == "string") { return offset[arg1]; }
            if (coreUtil.isPlainObject(arg1) || arg1Type == "function") { coreUtil.windowOffset(arg1.left || offset.left, arg1.top || offset.top); }
        }
        if (length >= 2) {
            arg2 = arguments[1];
            arg2Type = coreUtil.type(arg2);
            if (arg1Type == "string" && arg2Type == "number") {
                var newOffset = $.extend({}, offset);
                newOffset[arg1] = arg2;
                if (offset.left != newOffset.left || offset.top != newOffset.top) { window.moveTo(newSize.left, newSize.top); }
            }
            if (arg1Type == "number" && arg2Type == "number") { window.moveTo(arg1, arg2); }
        }
    };

    //  ��ȡ�����õ�ǰ window ����Ĵ�С��λ�ã��ú��������������أ�
    //  1��function ()����ȡ��ǰ window �Ĵ����С��λ�ã��÷�������һ����ʽ�� { left, top, width, height } �� JSON-Object��
    //  2��function (valType)����ȡ��ǰ window �Ĵ����С��λ�õ�ָ������ֵ���ú����������²�����
    //      valType: String ����ֵ���ò�����ֵ����Ϊ "left"��"top"��"width" �� "height" ����֮һ����ʾ���ص�ǰ window �����С��λ�õ��ĸ�����ֵ��
    //  3��function (pos)��
    //  4��function (valType, val)��
    //  5��function (width, height)��
    //  6��function (width, height, left, top)��
    coreUtil.windowPosition = function () {
        var length = arguments.length, arg1, arg2, arg3, arg4, arg1Type, arg2Type,
            getPosition = function () { return $.extend(coreUtil.windowSize(), coreUtil.windowOffset()); },
            position = getPosition();
        if (length == 0) { return position; }
        arg1 = arguments[0];
        arg1Type = coreUtil.type(arg1);
        if (length == 1) {
            arg1 = arguments[0];
            if (arg1Type == "string") { return position[arg1]; }
            if (coreUtil.isPlainObject(arg1) || arg1Type == "function") { coreUtil.position(arg1.width || position.width, arg1.height || position.height, arg1.left || position.left, arg1.top || position.top); }
        }
        if (length == 2) {
            arg2 = arguments[1];
            arg2Type = coreUtil.type(arg2);
            if (arg1Type == "string" && arg2Type == "number") {
                var newPosition = $.extend({}, position);
                newPosition[arg1] = arg2;
                if (position.width != newPosition.width || position.height != newPosition.height || position.left != newPosition.left || position.top != newPosition.top) {
                    window.moveTo(newSize.left, newSize.top);
                    coreUtil.windowPosition(newPosition.width, newPosition.height, newPosition.left, newPosition.top);
                }
            }
            if (arg1Type == "number" && arg2Type == "number") { coreUtil.windowSize(arg1, arg2); }
        }
        if (length >= 3) {
            arg2 = arguments[1];
            arg3 = arguments[2];
            arg4 = arguments.length > 3 ? arguments[3] : null;
            coreUtil.windowSize(arg1, arg2);
            coreUtil.windowOffset(arg3, arg4);
        }
    };

    //  ��������������ֵ�����أ��ú����������²�����
    //      callback:   ��Ҫ�����ĺ�����������һ��ֵ��Ҳ�����Ǻ���������Ǻ�������÷������ظú���������ֵ��
    //      args:       ��ʾ��Ҫ���뺯�� callback �Ĳ�������һ��������󣬸ò�����ѡ��
    //      thisArg:    ��ʾ���뺯�� callback ���ڵ� this ���ö��󣬸ò�����ѡ��
    //  ����ֵ��������� callback ��һ������������� callback.apply(thisArg, args) ����󲢽��䷵�أ�����ֱ�ӽ��䷵�ء�
    coreUtil.parseFunction = function (callback, args, thisArg) {
        var val = callback, arrayArgs = [];
        if (coreUtil.isFunction(callback)) {
            if (arguments.length == 1) {
                args = [];
                thisArg = this;
            } else if (arguments.length == 2) {
                if (coreUtil.likeArrayNotString(args)) {
                    thisArg = this;
                } else {
                    thisArg = args;
                    args = [];
                }
            }
            coreArray.copy(arrayArgs, args);
            arrayArgs.callee = callback;
            if (arguments.caller) {
                arrayArgs.caller = callback.caller ? callback.caller : arguments.caller;
            }
            val = callback.apply(thisArg, arrayArgs);
        }
        return val;
    };

    //  ������ֵ�Ը�ʽ�����м�ֵ��ʽΪ key: function �� JSON ��ʽ����ĺ�������ֵ�����ؽ���������ݣ��ú����������²�����
    //      obj     : ��Ҫ�����Ķ��󣻸ö�����ÿ�����������һ�����������������Ժ�������ִ�У���������ִ�з���ֵ��������Ϊ�����Ե���ֵ��
    //      args    : ������ִ�� obj ������ÿ�����Ժ���ʱ��Ϊ�����б��룻�ò�����ѡ��
    //      thisArg : ������ִ�� obj ������ÿ�����Ժ���ʱ��Ϊ this ���ò������룻�ò�����ѡ��
    //  ����ֵ�����ض��������е� key: function �е� function �����Ľ���� key ������ϳɵ��µĶ��󸱱���
    //  ʾ���� var obj = { arg: 20, sex: function () { return "��"; } };
    //         coreUtil.parseMapFunction(obj); 
    //      ��ʱ��obj ��ֵΪ��{ arg: 20, sex: "��" }��
    coreUtil.parseMapFunction = function (obj, args, thisArg) {
        obj = obj || {};
        var val = {};
        var type = coreUtil.type(obj);
        if (type == "object" || type == "function") {
            for (var key in obj) {
                val[key] = coreUtil.parseFunction(obj[key], args, thisArg);
            }
        }
        return val;
    };

    //  ��ͨ�� SOA(���� ASP.NET һ�㴦����� ���� WebService) ��ʽ���ص�����ת���� JSON ��ʽ���ݡ�
    coreUtil.parseJSON = function (data) {
        var val = null;
        var isString = coreUtil.isString(data);
        if (coreUtil.isPlainObject(data) || (coreUtil.likeArrayNotString(data))) {
            val = coreUtil.isPlainObject(data.d) ? coreUtil.parseJSON(data.d) : data;
        } else {
            val = $.parseJSON(isString ? coreString.toJSONString(data) : $(data).text());
        }
        return val;
    };

    //  ����ͬ������ ajax ����ķ�ʽ����ָ���Ĳ�������Զ�����ݲ����أ��ú����������²�����
    //      url:    �����Զ�̷����ַ��
    //      args:   ������Զ�̷�������ݣ��ڷ�������֮ǰ�ò������ᱻ���л���
    //  ����ֵ������Զ����������ݣ�
    //  ��ע���÷���Ϊ $.ajax �����Ŀ�ݵ��ã����� post ��ʽ�ύ������ async ��������Ϊ false��
    //      �����Ҫ���ӷḻ�� ajax ���ã���ʹ�� $.ajax ������
    coreUtil.requestAjaxData = function (url, args) {
        var val = null;
        args = coreUtil.parseMapFunction(args);
        $.ajax({
            url: url, type: "POST", data: args, async: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                throw XMLHttpRequest.responseText;
            }, success: function (data, textStatus, jqXHR) { val = data; }
        });
        return val;
    };

    //  ����ͬ������ ajax ����ķ�ʽ����ָ���Ĳ�������Զ�̲����������ݲ����أ��ú����������²�����
    //      url:    �����Զ�̷����ַ��
    //      args:   ������Զ�̷�������ݣ��ڷ�������֮ǰ�ò������ᱻ���л���
    //  ����ֵ������Զ������Ĳ������ݣ�
    //  ��ע���÷���Ϊ $.ajax �����Ŀ�ݵ��ã����� post ��ʽ�ύ������ async ��������Ϊ false��
    //      �����Ҫ���ӷḻ�� ajax ���ã���ʹ�� $.ajax ������
    coreUtil.requestAjaxBoolean = function (url, args) {
        var data = coreUtil.requestAjaxData(url, args), type = typeof data;
        if (type == "object" || (type == "string" && data.charAt(0) === "<" && data.charAt(data.length - 1) === ">" && data.length >= 3)) { data = $(data).text(); }
        return coreString.toBoolean(data);
    };

    //  ����ͬ������ ajax ����ķ�ʽ����ָ���Ĳ�������Զ�����ݲ�����ת���� JSON ��ʽ�󷵻أ��ú����������²�����
    //      url:    �����Զ�̷����ַ��
    //      args:   ������Զ�̷�������ݣ��ڷ�������֮ǰ�ò������ᱻ���л���
    //  ����ֵ������ת���� JSON ��ʽ���Զ����������ݣ�
    //  ��ע���÷���Ϊ $.ajax �����Ŀ�ݵ��ã����� post ��ʽ�ύ������ async ��������Ϊ false��
    //      �����Ҫ���ӷḻ�� ajax ���ã���ʹ�� $.ajax ������
    coreUtil.requestAjaxJson = function (url, args) {
        var data = coreUtil.requestAjaxData(url, args);
        return coreUtil.parseJSON(data);
    };

    //  ��ָ���� CSS ���ݴ���һ�� CSS ���󣻸ú����������²�����
    //      context: ��ʾ css ���ݣ�
    //      target:  �ò�����ѡ����ʾ������ css �� style ��ǩ����ӵ���Ŀ��λ�ã�������һ�� DOM ������� jQuery ����
    //  ����ֵ�����ش����� CSS ��ǩ�� jQuery DOM ����
    //  ��ע���÷������� target ��ʾ�� DOM �����ǰҳ��� head �����һ�� <style type="text/css"></style> ��ǩ����ǩ�е����ݼ�Ϊ����� context ֵ��
    coreUtil.addCss = function (context, target) {
        return $("<style>" + context + "</style>").attr("type", "text/css").appendTo(target ? target : "head");
    };

    //  ͨ�� javascript ǰ�˱��ط�ʽ���� Excel ���ݣ��ú����������²�����
    //      options: JSON Object ���ͣ����嵼�����ݵĲ�����Ϣ���ö������������ԣ�
    //          file:   String ���ͣ���ʾҪ�������ļ�����
    //          columns: Array ���ͣ���ʾҪ������ Excel ���ݵ�����Ϣ����������ÿһ��Ԫ�ض���һ�� JSON Object���� Object �����������ԣ�
    //              field:  ��ʾ���ݲ��� rows �ж�Ӧ�����ֶ�����
    //              title:  ��ʾ field ��Ӧ���еı���(��������ʾ������)��Ĭ��Ϊ field ��ֵ��
    //              width:  ��ʾ field ��Ӧ���е��п�(��λ������)��Ĭ��Ϊ 140��
    //              type:   ��ʾ field ��Ӧ���е��������ͣ���ѡ��ֵ�� "boolean", "number", "string", "date"��Ĭ��Ϊ "string"��
    //              formatter:  ��ʾ field ��Ӧ�������ݵ���ʱ�ĸ�ʽ�������������Ľ����ʾ����Ϊ�ú�������󷵻صĽ����Function ���ͣ���������б����£�
    //                  field:
    //                  rowIndex:
    //                  rowData:
    //                  array:
    //                  Ĭ��ֵΪ function (field, rowIndex, rowData, array) { return rowData[field]; }
    //          data: ʵ��Ҫ���������ݣ���һ��������������е�ÿһ��Ԫ�ض���һ�� JSON Object ���󣬱�ʾһ�����ݣ��� JSON Object �е�ÿһ�����Զ���ʾһ�����ֶ�ֵ��
    //              �������ݵ����ֶ�������Ϣ�� columns �������壻
    //  ����ֵ��
    //  ע�⣺�ú�����֧�� ie6��
    coreUtil.exportExcel = function (options) { $.error("δʵ��"); };

    //  ��ȡ�����õ�ǰ���ʽƥ�䵽��Ԫ�صĸ���Ԫ�ص��ڳߴ��С�������ǰԪ���� HTML BODY �Ķ���Ԫ�أ���ֱ�ӷ������ڳߴ��С��
    //  ����ֵ������һ����ʽ�� { width: number, height: number } �� JSON-Object����ʾ�丸��Ԫ�ص��ڳߴ��С��
    coreJquery.prototype.parentSize = function () {
        if (!this.length) { return coreUtil.windowSize(); }
        var t = this[0],
            p = (t.tagName == "BODY" ? t : this.parent()[0]);
        return { width: $(p).width(), height: $(p).height() }
    };


    //  ��ȡ�����õ�ǰ���ʽƥ�䵽��Ԫ�صı߿�ֵ(css-border ����)���÷����ṩ�������أ�
    //      function()              : ��ȡ��ǰ���ʽƥ�䵽�ĵ�һ��Ԫ�صı߿�ֵ���ú�������һ����ʽ�� { top: number, left: number, right: number, bottom: number } �� JSON ����
    //      function("region")      : ��ȡ��ǰ���ʽƥ�䵽�ĵ�һ��Ԫ�ص� region ��ʾλ�õı߿�ֵ���ú�������һ�� Number ��ֵ��ʾ��λ�õı߿�ֵ��
    //      function(val)           : ���õ�ǰ���ʽƥ�䵽������Ԫ�ص�����λ�ñ߿�ֵΪ val���ú������ص�ǰ jquery ��ʽ����
    //      function("region", val) : ���õ�ǰ���ʽƥ�䵽������Ԫ�ص� region λ�ñ߿�ֵΪ val���ú������ص�ǰ jquery ��ʽ����
    //  ���������У�region ������ʾ�߿��λ�ã�String ����ֵ����ѡ��ֵ�޶�Ϊ "top"��"left"��"right" �� "bottom"
    //              val ������ʾҪ�趨�ı߿��ֵ��Number ����ֵ������Ϊ������
    coreJquery.prototype.border = function (region, val) {
        if (!arguments.length) {
            return { top: getRegionBorder("top"), left: getRegionBorder("left"), right: getRegionBorder("right"), bottom: getRegionBorder("bottom") };
        }
        var thisArg = this;
        if (arguments.length == 1) {
            if (coreUtil.isNumeric(region) || coreString.endsWith(region, "px")) {
                return thisArg.css("border", region);
            } else {
                var locale = coreString.toLowerCase(region);
                return coreArray.contains(["top", "left", "right", "bottom"], locale) ? getRegionBorder(locale) : null;
            }
        } else {
            var locale = coreString.toLowerCase(region);
            if (coreArray.contains(["top", "left", "right", "bottom"], locale)) {
                return thisArg.css("border-" + locale + "-width", val);
            } else {
                return thisArg;
            }
        }
        function getRegionBorder(locale) {
            var str = thisArg.css("border-" + locale + "-width");
            return toNumber(str);
        };
        function toNumber(str) {
            str = coreString.isNullOrEmpty(str) ? "" : String(str).toLowerCase();
            str = str.replace("px", "");
            return str ? coreString.toInteger(str) : 0;
        };
    };




    //  ��ȡ��ǰ���ʽƥ�䵽������Ԫ���еĵ�һ��Ԫ���Ƿ���� uniqueID ����ֵ��
    //  ����ֵ���������ǰ���ʽƥ�䵽������Ԫ���еĵ�һ��Ԫ�ؾ��� uniqueID ����ֵ���򷵻� true�����򷵻� false��
    coreJquery.prototype.hasUniqueID = function () { return this.length ? coreUtil.hasUniqueID(this[0]) : false; };

    //  ��ȡ��ǰ���ʽƥ�䵽������Ԫ���еĵ�һ��Ԫ�ص� uniqueID ����ֵ��
    //  ����ֵ�������ǰ���ʽû��ƥ���Ԫ�أ��򷵻� null�����򷵻� ����Ԫ���еĵ�һ��Ԫ�ص� uniqueID ����ֵ��
    coreJquery.prototype.getUniqueID = function () { return this.length ? coreUtil.getElementUniqueID(this[0]) : null; };

    //  ���õ�ǰ���ʽƥ�䵽������Ԫ�ص� uniqueID ����ֵ���ú����������²�����
    //      uniqueID: ��ѡ����ʾҪ����Ϊ�� uniqueID ����ֵ������������ֵ����Ĭ���� coreUtil.guid() Ϊ�䴴��һ�����ֵ��
    //  ����ֵ�����ص�ǰ jquery ��������á�
    coreJquery.prototype.setUniqueID = function (uniqueID) { return this.each(function () { coreUtil.setElementUniqueID(this, uniqueID); }); };

    //  ��ʼ����ǰ���ʽƥ�䵽������Ԫ�ص� uniqueID ����ֵ��
    //  ����ֵ�����ص�ǰ jquery ��������á�
    //  ��ע���÷���ѭ���ж�ÿһ��Ԫ���Ƿ���� uniqueID ����ֵ��������򲻽����κθ��ģ����û������������һ���µ� uniqueID ֵ��
    coreJquery.prototype.initUniqueID = function () { return this.each(function () { coreUtil.initElementUniqueID(this); }); };

    //  ��ȡ�����õ�ǰ���ʽԪ�ص� uniqueID ����ֵ���ú��������������أ�
    //      1��function()�������ر�ʾ����ȡ��ǰ���ʽƥ�䵽������Ԫ���е�һ��Ԫ�ص� uniqueID ����ֵ����Ч�� coreJquery.prototype.getUniqueID ������
    //      2��function(uniqueID)�������ر�ʾ�����õ�ǰ���ʽƥ�䵽������Ԫ�ص� uniqueID ����ֵ����Ч�� coreJquery.prototype.setUniqueID ������
    coreJquery.prototype.uniqueID = function (uniqueID) {
        return arguments.length == 0 ? this.getUniqueID() : this.setUniqueID(uniqueID);
    };

    //  �жϵ�ǰƥ�䵽��Ԫ���Ƿ���н��㣻
    coreJquery.prototype.isFocus = function () {
        var elements = $("*", this).add(this);
        for (var i = 0; i < elements.length; i++) { if (document.activeElement == elements[i]) { return true; } }
        return false;
    };

    //  ���Ե�ǰ jQuery �����Ƿ������һ�� DOM ���󣻸ú����������²�����
    //      this: �������ڵ� this �������ã���ʾ��ǰ jQuery ����
    //      item: DOM�ڵ㣬���ܱ�����Ԫ��������
    //  ����ֵ����� item DOM�ڵ������ this ָ��ĵ�ǰ jQuery �����У��򷵻� true�����򷵻� false��
    coreJquery.prototype.contains = function (item) {
        var b = false;
        this.each(function () { if ($.contains(this, item)) { b = true; return false; } });
        return b;
    };

    //  �����ǰ jQuery ���󲻰���ָ�����ʽƥ���Ԫ�أ��������ʽƥ���Ԫ����ӵ�jQuery�����С�������������������ӷֱ����������ʽƥ���Ԫ�ؽ�������ú����������²�����
    //      this: �������ڵ� this �������ã���ʾ��ǰ jQuery ����
    //      ��������ͬ jQuery �Ĺٷ� API ���� jQuery.fn.add ��ͬ��
    //  ����ֵ�����ش����� this �����á�
    coreJquery.prototype.attach = function () {
        var t = this;
        $.apply(this, arguments).each(function () {
            if (!t.contains(this)) { core_push.call(t, this); }
        });
        return t;
    };

    //  ��ȡƥ��Ԫ����Թ�����������ƫ�ưٷֱ�
    coreJquery.prototype.scrollTopP = function () {
        var height = this.height();
        height = height <= 0 ? parseFloat(height) : parseFloat(1);
        return this.scrollTop() / height;
    };

    //  ��ȡƥ��Ԫ����Թ���������ƫ�ưٷֱ�
    coreJquery.prototype.scrollLeftP = function () {
        var width = this.width();
        width = width <= 0 ? parseFloat(width) : parseFloat(1);
        return this.scrollLeft() / width;
    };

    //  ����ǰ���ʽƥ�䵽������Ԫ�ؼ�����Ԫ�����л��� JSON ���󲢷��أ��ú��������������͵����ط�ʽ��
    //      1��Function(Object)�����в��� Object �������������ԣ�
    //          onlyEnabled:    ��ʾ���صĽ���������Ƿ����������(disabled == false)�� HTML ���ؼ���Boolean ����ֵ��Ĭ��Ϊ false��
    //          transcript :    ��ʾ����Χ�ڴ�������(name ��ͬʱ)�� DOM Ԫ��ʱ�����ظ�Ԫ�ص�ȡֵ����
    ///                 ����һ�� String ����ֵ����ѡ��ֵ�޶������·�Χ��
    //              cover  :    ���Ƿ�ʽ��ֻȡ����Ԫ�� ��ֵ������ǰ��Ԫ�ص�ֵ��Ĭ��ֵ��
    //              discard:    ��������Ԫ�ص�ֵ��ֻȡǰ��Ԫ�ص�ֵ��
    //              overlay:    ������Ԫ�ص�ֵ���е��ӣ�
    //          overtype   :    Ԫ�ص��ӷ�ʽ���� transcript ��ֵ����Ϊ "overlay" ʱ�������Է���Ч��
    //                  ����һ�� String ����ֵ����ѡ��ֵ�޶������·�Χ��
    //              array  :    �������ظ���Ԫ�ص���Ϊһ�����飻
    //              append :    �����е��ظ�Ԫ�ص���Ϊһ���ַ�����Ĭ��ֵ��
    //          separator  :    Ԫ�ص��ӵķָ��������彫��������Ԫ�ص���Ϊһ���ַ���ʱ����ƴ���ַ����ķָ�����
    //                  ����һ�� String ����ֵ��Ĭ��Ϊ ","���� transcript ��ֵ����Ϊ "overlay" �� overtype ��ֵ����Ϊ "append" ʱ�������Է���Ч��
    //      2��Function(String)�����в��� String ��ʾ����Χ�ڴ�������(name ��ͬʱ)�� DOM Ԫ��ʱ�����ظ�Ԫ�ص�ȡֵ����
    //          ��ȡֵ��Χ�͵�������ʽΪ JSON-Object ʱ������ transcript һ����
    //  ����ֵ���÷�������һ�� JSON Object�����ض����е�ÿ�����ݶ���ʾһ�����ؼ�ֵ��
    coreJquery.prototype.serializeObject = function (options) {
        var rCRLF = /\r?\n/g,
	        rsubmitterTypes = /^(?:submit|button|image|reset)$/i,
	        rsubmittable = /^(?:input|select|textarea|keygen)/i,
            //rsubmittable_radio = /^(?:radio)$/i,
            //rsubmittable_checkbox = /^(?:checkbox)$/i,
            rsubmittable_radiocheckbox = /^(?:checkbox|radio)$/i,
            list, names, ret = {};
        options = options || {};

        var defaults = { onlyEnabled: false, transcript: "cover", overtype: "append", separator: "," },
            opts = $.extend({}, defaults, (typeof options == "string") ? { transcript: options } : options || {});
        if (!coreArray.contains(["cover", "discard", "overlay"], opts.transcript)) {
            opts.transcript = defaults.transcript;
        }
        if (!coreArray.contains(["array", "append"], opts.overtype)) {
            opts.overtype = defaults.overtype;
        }

        list = this.map(function () {
            var elements = jQuery.prop(this, "elements");
            return $.merge([], elements ? $.makeArray(elements) : $(this).find("*"));
        }).filter(function () {
            return this.name && (!opts.onlyEnabled || !$(this).is(":disabled")) &&
				rsubmittable.test(this.nodeName) && !rsubmitterTypes.test(this.type);
        }).map(function (i, elem) {
            var name = elem.name, id = elem.id, type = this.type, val = $(this).val(),
                checked = (this.checked == undefined || this.checked == null) ? null : this.checked;
            return {
                name: name || id, type: type, checked: checked,
                val: $.isArray(val) ? $.map(val, function (val) { return val ? val.replace(rCRLF, "\r\n") : val; })
                    : (val ? val.replace(rCRLF, "\r\n") : val)
            };
        });
        names = coreArray.distinct(list.map(function (i, elem) { return elem.name; }));
        $.each(names, function (i, name) {
            var elems = list.filter(function (i, elem) { return elem.name == name; }),
                val = elems.length == 1 ? getElemVal(elems[0]) : getElemsVal(elems);
            ret[name] = (val == undefined || val == null) ? null : val;
        });
        function getElemVal(elem) {
            return rsubmittable_radiocheckbox.test(elem.type) ? elem.checked : elem.val;
        };
        function getElemsVal(elems) {
            var items = coreArray.filter(elems, function (elem) {
                return (rsubmittable_radiocheckbox.test(elem.type) && elem.checked == true) || !rsubmittable_radiocheckbox.test(elem.type);
            });
            var values = coreArray.map(items, function (val) { return val.val; });
            switch (opts.transcript) {
                case "cover": return values[values.length - 1];
                case "discard": return values[0];
                case "overlay":
                    return opts.overtype == "array"
                        ? (values.length > 1 ? values : values[0])
                        : values.join(opts.separator);
                default: return values[0];
            }
        };
        return ret;
    };


    //  �������������ռ䣻�ú����������²�����
    //      obj:        ��ʾ���ڴ��������ռ�Ķ��󣻸ò�����ѡ��Ĭ��Ϊ window ����
    //      namespace:  Ҫ�����������ռ䣬��ͬ������������÷��� "." �������벻Ҫ�����κοո�
    //      callback:   ��ѡ�������������ռ��ִ�еĻص�������
    //      thisArg:    ��ѡ��ͬ���� callback һ���壻��ʾ callback �ص�����ִ���е� this ����
    coreUtil.namespace = function (obj, namespace, callback, thisArg) {
        var index = 0, ret;
        if (typeof obj != "string") {
            ret = obj || window;
            index++;
        } else {
            ret = window;
        }
        namespace = arguments[index++];
        callback = arguments[index++];
        thisArg = arguments[index++];

        if (!namespace) { return ret; }
        var names = String(namespace).split("."), array = [], n;
        for (var i = 0; i < names.length; i++) {
            n = coreString.trim(names[i]);
            if (n != "") { array.push(n); }
        }
        $.each(array, function (i, name) {
            ret = (ret[name] == null || ret[name] == undefined) ? (ret[name] = {}) : ret[name];
        });
        if (coreUtil.isFunction(callback)) { callback.call(thisArg, ret); }
        return ret;
    };

    //  ��ȡָ��ȫ���Ƶ� JavaScript ���ͺ������󣻸ú����������²�����
    //      namespace   : Ҫ��ȡ����������ƣ���Ӧ�����ռ��޶����÷��� "." �������벻Ҫ�����κοո�
    //  ����ֵ��
    //      ��� namespace ָ�������ͺ������ڣ��򷵻ظ����ͺ�������
    //      ��� namespace ָ�������ͺ��������ڣ�namespace ֵΪ���ַ������� null/undefined�����򷵻� null��
    coreUtil.getNamespace = coreUtil.getDefined = function (namespace) {
        var index = 0, ret;
        if (typeof obj != "string") {
            ret = obj || window;
            index++;
        } else {
            ret = window;
        }
        namespace = arguments[index++];
        if (!namespace) { return ret; }
        var names = String(namespace).split("."), array = [], n;
        for (var i = 0; i < names.length; i++) {
            n = coreString.trim(names[i]);
            if (n != "") { array.push(n); }
        }
        $.each(array, function (i, name) {
            ret = (ret == null || ret == undefined || ret[name] == null || ret[name] == undefined) ? null : ret[name];
        });
        return ret;
    };

    //  ��������һ�� JavaScript �ࣻ�ú����������²�����
    //      namespace   : Ҫ�����������������Ӧ�����ռ��޶����÷��� "." �������벻Ҫ�����κοո�
    //      data        : ��ѡ��������������Ĭ�϶���ĳ�Ա���Ի򷽷�(�� prototype)��
    //      createFn    : ��ѡ�������������͵�Ĭ�Ϲ��캯����
    //  ����ֵ�����ر����������͵� Function ����
    //  ע�⣺
    //      �������Ĳ��� namespace ��ֵΪ null���򴴽������ JavaScript ��Ϊ�����ࣻ
    //      ���ָ���˶��庯��ʱ��namespace ��ָ���Ķ����Ѿ����ڣ���ö��󽫻ᱻ���ǣ�
    //      ������ coreUtil.getDefined(namespace) ���ж� namespace ��ָ���Ķ����Ƿ��Ѿ����ڣ�
    coreUtil.define = function (namespace, data, createFn) {
        if (coreUtil.isFunction(data)) {
            createFn = data;
            data = {};
        }
        var p, name, constructor, func;
        if (namespace) {
            var names = String(namespace).split("."), array = [], n;
            for (var i = 0; i < names.length; i++) {
                n = coreString.trim(names[i]);
                if (n != "") { array.push(n); }
            }
            if (array[0] != "window") { array.splice(0, 0, "window"); }
            if (array.length > 1) {
                p = coreUtil.namespace(array.slice(0, array.length - 1).join("."));
                name = array[array.length - 1];
            }
        }
        createFn = coreUtil.isFunction(createFn) ? createFn : function () { };
        constructor = function (options) {
            return createFn.call(this, options);
        };
        func = function (options) {
            return new constructor(options);
        };
        func.defaults = func.fn = func.prototype = constructor.defaults = constructor.fn = constructor.prototype;
        $.extend(func, { extend: $.extend, union: coreJquery.union, init: constructor, inst: createFn });
        $.extend(func.defaults, data, { extend: $.extend, union: coreJquery.union });
        if (p && name) {
            var old = p[name];
            p[name] = func;
            if (old) {
                coreJquery.union(func, old);
            }
        }
        return func;
    };

    //  ��ָ���Ĳ�������һ��ָ�����͵Ķ��󣻸ú����������²�����
    //      namespace   : ���룬String ����ֵ��ָ�������ͺ������ƣ�
    //      options     : ��ѡ��JSON-Object ����ֵ������ namespace ���Ͷ������õĲ�����Ĭ��Ϊ null��
    //  ����ֵ��
    //      ��� namespace ָ�������ͺ������ڣ��򷵻ظú���ͨ�� options ������ thisArgs ����������Ķ���
    //      ��� namespace ָ�������ͺ��������ڣ��򷵻� null��
    coreUtil.createDefinedObject = function (namespace, options) {
        var type = coreUtil.getDefined(namespace);
        return coreUtil.isFunction(type) ? type(options) : null;
    };



    //  ����ҳ��� window.console �ű����ܣ�
    //  ����ֵ���޷���ֵ��
    coreUtil.disableConsole = function () {
        try {
            var _console = window.console;
            if (Object.defineProperties) {
                Object.defineProperties(window, "console", {
                    get: function () {
                        if (_console._commandLineAPI) {
                            throw "��Ǹ, Ϊ���û���ȫ, ��վ�ѽ��� console �ű�����";
                        }
                        return _console;
                    },
                    set: function (val) {
                        return _console = val;
                    }
                });
            }
        } catch (e) { }
    };



    //  �¶δ����ṩ javascript ��������� ����/�˳� ȫ��ģʽ�� API��
    var fullScreen = {
        supports: false, eventName: "", prefix: "", prefixes: "webkit moz o ms khtml".split(" "),
        isFullScreen: function () { }, requestFullScreen: function () { }, cancelFullScreen: function () { }
    };
    if (typeof document.cancelFullScreen != "undefined") {
        fullScreen.supports = true;
    } else {
        for (var i = 0; i < fullScreen.prefixes.length; i++) {
            fullScreen.prefix = fullScreen.prefixes[i];
            if (typeof document[fullScreen.prefix + "CancelFullScreen"] != "undefined") {
                fullScreen.supports = true;
                break;
            }
        }
    }
    if (fullScreen.supports) {
        fullScreen.eventName = fullScreen.prefix + "fullscreenchange";
        fullScreen.isFullScreen = function () {
            switch (this.prefix) {
                case "": return document.fullScreen;
                case "webkit": return document.webkitIsFullScreen;
                default: return document[this.prefix + "FullScreen"];
            }
        };
        fullScreen.requestFullScreen = function (elem) {
            return (this.prefix === "") ? elem.requestFullScreen() : elem[this.prefix + "RequestFullScreen"]();
        };
        fullScreen.cancelFullScreen = function (elem) {
            return (this.prefix === "") ? document.cancelFullScreen() : document[this.prefix + "CancelFullScreen"]();
        };
    }

    //  �жϵ�ǰҳ���Ƿ�������ȫ��ģʽ��
    coreUtil.isFullScreen = coreJquery.isFullScreen = function () {
        return fullScreen.isFullScreen();
    };

    //  ��ָ���� jQueryObject/HtmlElement ����Ϊȫ��ģʽ��������� selector Ϊ�գ�������ҳ������Ϊȫ��ģʽ��
    coreUtil.requestFullScreen = coreJquery.requestFullScreen = function (selector) {
        if (selector == null || selector == undefined) { selector = document.documentElement; }
        selector = coreUtil.parseJquery(selector);
        return selector.each(function () {
            if (fullScreen.supports) { fullScreen.requestFullScreen(this); }
        });
    };
    coreJquery.prototype.requestFullScreen = function () { return coreJquery.requestFullScreen(this); };

    //  �˳�ָ���� jQueryObject/HtmlElement �����ȫ��ģʽ��������� selector Ϊ�գ����˳�����ҳ���ȫ��ģʽ��
    coreUtil.cancelFullScreen = coreJquery.cancelFullScreen = function (selector) {
        if (selector == null || selector == undefined) { selector = document.documentElement; }
        selector = coreUtil.parseJquery(selector);
        return selector.each(function () {
            if (fullScreen.supports) { fullScreen.cancelFullScreen(this); }
        });
    };
    coreJquery.prototype.cancelFullScreen = function () { return coreJquery.cancelFullScreen(this); };

    //  �л�ָ���� jQueryObject/HtmlElement �����ȫ��ģʽ��������� selector Ϊ�գ����л�����ҳ���ȫ��ģʽ��
    coreUtil.toggleFullScreen = coreJquery.toggleFullScreen = function (selector) {
        if (selector == null || selector == undefined) { selector = document.documentElement; }
        selector = coreUtil.parseJquery(selector);
        return selector.each(function () {
            if (fullScreen.supports) {
                if (coreUtil.isFullScreen()) {
                    fullScreen.cancelFullScreen(this);
                } else {
                    fullScreen.requestFullScreen(this);
                }
            }
        });
    };
    coreJquery.prototype.toggleFullScreen = function () { return coreJquery.toggleFullScreen(this); };

    //  ��ȡһ�� bool ����ֵ����ֵָʾ��ǰ������Ƿ�֧��ȫ�� API��
    coreUtil.supportsFullScreen = fullScreen.supports;
    //  ��� $.util.supportsFullScreen ֵΪ true��������Ի�ȡ�������ȫ�� API �¼����ƣ���� $.util.supportsFullScreen ֵΪ false��������Է��� null/undefined��
    coreUtil.fullScreenEventName = fullScreen.eventName;
    //  ��ȡ��ǰ���������ȫ�� API ��֧����Ϣ��
    coreUtil.fullScreen = fullScreen;





    //  Ԫ��������Ĭ��ʱ���������룩�������Խ����ڱ����� coreUtil.shine ���ã�
    coreUtil.shineInterval = 100;
    //  Ԫ��������Ĭ�ϴ����������Խ����ڱ����� coreUtil.shine ���ã�
    coreUtil.shineTimes = 8;
    //  ʹԪ������
    coreUtil.shine = coreJquery.shine = function (selector, interval, times) {
        if (selector == null || selector == undefined) { return selector; }
        selector = coreUtil.parseJquery(selector);
        if (!coreUtil.isNumeric(interval) || interval <= 40) { interval = coreUtil.shineInterval; }
        if (!coreUtil.isNumeric(times) || times < 4) { times = coreUtil.shineTimes; }
        var a = function () { selector.addClass("jdirk-shine"); };
        var b = function () { selector.removeClass("jdirk-shine"); };
        var run = function () {
            coreUtil.delay(a, interval / 2);
            coreUtil.delay(b, interval);
            times--;
            if (times > 0) { coreUtil.delay(run, interval); }
        };
        coreUtil.delay(run);
        return selector;
    };
    coreJquery.prototype.shine = function (interval, times) { return coreJquery.shine(this, interval, times); };


    //  ��ָ���� url �� title ��Ϊ�ղ���Ϣ�����������ղؼУ�����������֧���ղؼв����� API����ᵯ�� alert ��Ϣ��
    coreUtil.addFavorites = function (url, title) {
        var favo = { url: window.location.href, title: document.title };
        if (arguments.length == 1) { $.extend(favo, url); }
        if (arguments.length > 1) { $.extend(favo, { url: url, title: title }); }
        if (window.external && coreUtil.isFunction(window.external.AddFavorite)) {
            window.external.AddFavorite(favo.url, favo.title);
        } else {
            window.alert("�밴 Ctrl + D Ϊ������������ �ղ�/��ǩ!");
        }
    };

    //  ���ڵ�ǰ IFRAME/PAGE �� document ��Ϊ��ʼλ�ã���������ĵ�ǰ����ҳ�漰��ɷ��ʣ�ͬ�򣩵������� IFRAME/FRAME ҳ���У�ִ��һ��ָ���ĺ������÷����ṩ���²�����
    //      callback :һ��ǩ��Ϊ function (win) �ĺ��������� win ��ʾ���ڵ� IFRAME/PAGE ִ�иò���ʱ����� window ����
    //  ��ע��ֻ�е� IFRAME/PAGE ��ʾ��ҳ���������� jQuery �⣬callback ָ���ĺ������ܱ�ִ�С�
    coreUtil.pageNestingExecute = function (callback) {
        if (!coreUtil.isFunction(callback)) {
            return;
        }
        var windowList = [],
            decline = function (win) {
                if (coreArray.contains(windowList, win)) {
                    return;
                }
                callback.call(win, win);
                windowList.push(win);
                var jq = win.jQuery;
                if (jq) {
                    jq("frame,iframe").each(function () {
                        try {
                            if (this.contentWindow && this.contentWindow.document && this.contentWindow.jQuery && this.contentWindow.jQuery.fn) {
                                decline(this.contentWindow);
                            }
                        } catch (ex) { }
                    });
                }
            },
            bubble = function (win) {
                var parentWin = win.parent;
                try {
                    if (parentWin && win !== parentWin && parentWin.document && parentWin.jQuery && parentWin.jQuery.fn) {
                        decline(parentWin);
                    }
                } catch (ex) { }
            };
        decline(window);
        bubble(window);
    };

    //  ���ڵ�ǰҳ�� document ��������ǰҳ��Ƕ�׵������Ӽ��͸���ҳ��� document �������һ��ǩ��Ϊ function (win, e) �¼������������÷����ṩ���²�����
    //      types : ��ʾ��Ҫ�󶨵��¼����ƻ�����¼���Ϣ�ļ�ֵ�Լ��ϣ����ڸò����������ݸ�ʽ�ο� jQuery.fn.bind ������
    //      data  : �󶨵��¼��е����ݣ����ڸò����ο� jQuery.fn.bind �������ò�����ѡ��
    //      fn    : һ��ǩ��Ϊ function (doc, e) �ĺ��������� win ��ʾ���� iframe ִ�к�������� window ����e ��ʾ���������ѭ���������õ��¼����󣬸ú����ڵ� this ָ��ú�����������ʱ�� window ����
    //  ��ע��ֻ�е� IFRAME/PAGE ��ʾ��ҳ���������� jQuery �⣬fn ָ���ĺ������ܱ�ִ�С�
    coreUtil.bindDocumentNestingEvent = function (types, data, fn) {
        if (typeof types == "string") {
            if (arguments.length == 2 && coreUtil.isFunction(data)) {
                coreUtil.pageNestingExecute(function (win) {
                    if (!win || !win.jQuery || !win.document)
                        return;
                    win.jQuery(win.document).unbind(types).bind(types, function (e) {
                        data.call(win, this, e, types);
                    });
                });
            } else if (arguments.length > 2 && coreUtil.isFunction(fn)) {
                coreUtil.pageNestingExecute(function (win) {
                    if (!win || !win.jQuery || !win.document)
                        return;
                    win.jQuery(win.document).unbind(types).bind(types, data, function (e) {
                        fn.call(win, this, e, types, data);
                    });
                });
            }
        } else {
            coreUtil.pageNestingExecute(function (win) {
                if (!win || !win.jQuery || !win.document)
                    return;

                for (var name in types) {
                    var eventName = name, eventData = data, callback = types[name];
                    if (!eventName || !callback)
                        continue;
                    win.jQuery(win.document).unbind(eventName).bind(eventName, eventData, function (e) {
                        callback.call(win, this, e, eventName, eventData);
                    });
                }
            });
        }
    };


    //  �÷������� HTML-DOM ����ð�ݲ��ң���Ԫ�ر���ʼ�������ϼ�Ԫ��ƥ�䣬����������ƥ���Ԫ�ء�
    //  �÷����ṩ���²�����
    //      selector: ��ʾһ�� HTML-DOM �� jQuery-DOM ����jQuery ����ѡ���������� DOM ���󣬻��� jQuery ������ɣ�
    //      filter  : ��ʾһ�����Թ���Ԫ�صı��ʽ����һ������ƥ��Ԫ�ص� jQuery ���󣬻�һ������ƥ��Ԫ�ص� DOM Ԫ�أ���һ������ƥ��Ԫ�صĺ�����
    //          ����ò���Ϊ string/jQueryDOM/HTMLDOM ���ͣ���÷���ͬ jQuery.fn.closest ������
    //          ����ò���Ϊ function ���ͣ���ú�����ǩ��Ϊ function(level, item)������ item ��ʾ����ִ��ʱ�� jQuery DOM��level ��ʾ item �������ʼλ�õļ���
    //              �ú����� this ��ͬ�� item��
    //  ����ֵ��������ҵ�ָ��ƥ��������Ԫ�أ��򷵻ظ�Ԫ�ص� jQuery Object�����򷵻�һ���յ� jQuery Object��
    coreUtil.closest = function (selector, filter) {
        var t = coreUtil.parseJquery(selector);
        if (coreUtil.isFunction(filter)) {
            var i = 0;
            while (t.length && !filter.call(t, i++, t)) {
                t = t.parent();
            }
            return t;
        } else {
            return t.closest(filter);
        }
    };



    //  ��һ������������������չһ�����󣬷��ر���չ�Ķ��󣻸ú����������²�����
    //      deep:   ��ѡ�������Ϊ true����ݹ�ϲ���
    //      target: ��ѡ��һ������������ӵĶ��󱻴��ݸ������������ô���������µ����ԣ��������Ψһ�Ĳ�������չjQuery�������ռ䣻
    //      object1:���ϲ��� target �Ķ���
    //      object2:���ϲ��� target �Ķ���
    //      objectN:���ϲ��� target �Ķ���
    //      ...
    //  �ο� jquery-2.0.0.js �й��� jQuery.extend �Լ� jQuery.fn.extend �����Ķ��壻
    //  ע�⣺�÷����� jQuery.extend �Լ� jQuery.fn.extend �Ĳ�֮ͬ�����ڣ�
    //      jQuery.extend �Լ� jQuery.fn.extend������ target �������Ƿ���� object1��object2��objectN �ȴ��ϲ���������Ӧ�����ԣ����ϲ������е���Ӧ���Զ�����ϲ��� target �У�
    //      union: ��� target �����д��� object1��object2��objectN �ȴ��ϲ���������Ӧ�����ԣ�������Խ����ᱻ�ϲ��� target �С�
    var union = coreJquery.union = coreJquery.prototype.union = function () {
        var src, copyIsArray, copy, name, options, clone, target = arguments[0] || {}, i = 1, length = arguments.length, deep = false;
        if (typeof target === "boolean") { deep = target; target = arguments[1] || {}; i = 2; }
        if (typeof target !== "object" && !coreUtil.isFunction(target)) { target = {}; }
        if (length === i) { target = this; --i; }
        for (; i < length; i++) {
            if ((options = arguments[i]) != null) {
                for (name in options) {
                    src = target[name];
                    copy = options[name];
                    if (target === copy) { continue; }
                    if (deep && copy && (coreUtil.isPlainObject(copy) || (copyIsArray = coreUtil.isArray(copy)))) {
                        if (copyIsArray) {
                            copyIsArray = false;
                            clone = src && coreUtil.isArray(src) ? src : [];
                        } else {
                            clone = src && coreUtil.isPlainObject(src) ? src : {};
                        }
                        target[name] = union(deep, clone, copy);
                    } else if (copy !== undefined && copy !== null && (src === undefined || src === null)) {
                        target[name] = copy;
                    }
                }
            }
        }
        return target;
    };


    coreUtil.addCss(".jdirk-shine { filter: alpha(opacity=40); opacity: 0.4; }");

    //  _enableUniqueID = true;
    //  ��ʼ����������Զ��� DOM Ԫ�ش���ȫ��Ψһ��ʶ�� uniqueID ���ܣ�
    if (_enableUniqueID) {
        $(function () {
            if (coreUtil.currentFrame) {
                coreUtil.initElementUniqueID(coreUtil.currentFrame);
            }
            coreUtil.setEnableUniqueID(_enableUniqueID);
        });
    }




    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  ��ʼ�� JSON ���󣨼��� IE 6��7��8 ʹ֧֮�� JSON ����
    //  json2.js 2013-05-26
    //  Public Domain. NO WARRANTY EXPRESSED OR IMPLIED. USE AT YOUR OWN RISK.
    //  See http://www.JSON.org/js.html
    ///////////////////////////////////////////////////////////////////////////////////////////////
    if (typeof JSON !== 'object') { JSON = {}; }
    function f(n) { return n < 10 ? '0' + n : n; }
    if (typeof Date.prototype.toJSON !== 'function') {
        Date.prototype.toJSON = function () {
            return isFinite(this.valueOf())
                ? this.getUTCFullYear() + '-' +
                    f(this.getUTCMonth() + 1) + '-' +
                    f(this.getUTCDate()) + 'T' +
                    f(this.getUTCHours()) + ':' +
                    f(this.getUTCMinutes()) + ':' +
                    f(this.getUTCSeconds()) + 'Z'
                : null;
        };
        String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function () { return this.valueOf(); };
    }
    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = { '\b': '\\b', '\t': '\\t', '\n': '\\n', '\f': '\\f', '\r': '\\r', '"': '\\"', '\\': '\\\\' },
        rep;
    function quote(string) {
        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
            var c = meta[a];
            return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + string + '"';
    }
    function str(key, holder) {
        var i, k, v, length, mind = gap, partial, value = holder[key];
        if (value && typeof value === 'object' && typeof value.toJSON === 'function') { value = value.toJSON(key); }
        if (typeof rep === 'function') { value = rep.call(holder, key, value); }
        switch (typeof value) {
            case 'string':
                return quote(value);
            case 'number':
                return isFinite(value) ? String(value) : 'null';
            case 'boolean':
            case 'null':
                return String(value);
            case 'object':
                if (!value) { return 'null'; }
                gap += indent;
                partial = [];
                if (Object.prototype.toString.apply(value) === '[object Array]') {
                    length = value.length;
                    for (i = 0; i < length; i += 1) { partial[i] = str(i, value) || 'null'; }
                    v = partial.length === 0 ? '[]' : gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' : '[' + partial.join(',') + ']';
                    gap = mind;
                    return v;
                }
                if (rep && typeof rep === 'object') {
                    length = rep.length;
                    for (i = 0; i < length; i += 1) {
                        if (typeof rep[i] === 'string') {
                            k = rep[i];
                            v = str(k, value);
                            if (v) { partial.push(quote(k) + (gap ? ': ' : ':') + v); }
                        }
                    }
                } else {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = str(k, value);
                            if (v) { partial.push(quote(k) + (gap ? ': ' : ':') + v); }
                        }
                    }
                }
                v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
                gap = mind;
                return v;
        }
    }
    if (typeof JSON.stringify !== 'function') {
        JSON.stringify = function (value, replacer, space) {
            var i;
            gap = '';
            indent = '';
            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) { indent += ' '; }
            } else if (typeof space === 'string') {
                indent = space;
            }
            rep = replacer;
            if (replacer && typeof replacer !== 'function' && (typeof replacer !== 'object' || typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }
            return str('', { '': value });
        };
    }
    if (typeof JSON.parse !== 'function') {
        JSON.parse = function (text, reviver) {
            var j;
            function walk(holder, key) {
                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k); if (v !== undefined) { value[k] = v; } else { delete value[k]; }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }
            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) { return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4); });
            }
            if (/^[\],:{}\s]*$/
                    .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                        .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                        .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
                j = eval('(' + text + ')');
                return typeof reviver === 'function' ? walk({ '': j }, '') : j;
            }
            throw new SyntaxError('JSON.parse');
        };
    }




    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  jQuery Cookie Plugin v1.4.0
    //  https://github.com/carhartl/jquery-cookie
    //
    //  Copyright 2013 Klaus Hartl
    //  Released under the MIT license
    ///////////////////////////////////////////////////////////////////////////////////////////////
    var cookie_inst = function () {
        var pluses = /\+/g;
        function encode(s) { return config.raw ? s : encodeURIComponent(s); }
        function decode(s) { return config.raw ? s : decodeURIComponent(s); }
        function stringifyCookieValue(value) { return encode(config.json ? JSON.stringify(value) : String(value)); }
        function parseCookieValue(s) {
            if (s.indexOf('"') === 0) { s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\'); }
            try { s = decodeURIComponent(s.replace(pluses, ' ')); } catch (e) { return; }
            try { return config.json ? JSON.parse(s) : s; } catch (e) { }
        }
        function read(s, converter) {
            var value = config.raw ? s : parseCookieValue(s);
            return coreUtil.isFunction(converter) ? converter(value) : value;
        }
        var config = coreJquery.cookie = coreUtil.cookie = function (key, value, options) {
            if (value !== undefined && !coreUtil.isFunction(value)) {
                options = $.extend({}, config.defaults, options);
                if (typeof options.expires === 'string') {
                    options.expires = coreString.toNumber(options.expires);
                }
                if (typeof options.expires === 'number') {
                    var days = options.expires, t = options.expires = new Date();
                    t.setDate(t.getDate() + days);
                }
                return (document.cookie = [
                    encode(key), '=', stringifyCookieValue(value),
                    options.expires ? '; expires=' + options.expires.toUTCString() : '',
                    options.path ? '; path=' + options.path : '',
                    options.domain ? '; domain=' + options.domain : '',
                    options.secure ? '; secure' : ''
                ].join(''));
            }
            var result = key ? undefined : {}, cookies = document.cookie ? document.cookie.split('; ') : [];
            for (var i = 0, l = cookies.length; i < l; i++) {
                var parts = cookies[i].split('=');
                var name = decode(parts.shift());
                var cookie = parts.join('=');
                if (key && key === name) { result = read(cookie, value); break; }
                if (!key && (cookie = read(cookie)) !== undefined) { result[name] = cookie; }
            }
            return result;
        };
        config.defaults = {};
        coreJquery.removeCookie = coreUtil.removeCookie = function (key, options) {
            if (config(key) !== undefined) { config(key, '', $.extend({}, options, { expires: -1 })); return true; }
            return false;
        };
    };
    cookie_inst();




    union($, coreJquery);
    union($.fn, coreJquery.prototype);

    union(String, coreString);
    union(String.prototype, coreString.fn);
    union(Date, coreDate);
    union(Date.prototype, coreDate.fn);
    union(Number, coreNumber);
    union(Number.prototype, coreNumber.fn);
    union(Array, coreArray);
    union(Array.prototype, coreArray.fn);
    union(Boolean, coreBoolean);
    union(Boolean.prototype, coreBoolean.fn);

    union($.fn, Array.prototype);


})(window, jQuery);