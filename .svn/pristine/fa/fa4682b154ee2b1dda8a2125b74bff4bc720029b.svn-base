 

// #####an########################### 功能实现 ################################
        var JSONFormat = (function () {
            var _toString = Object.prototype.toString;

            function format(object, indent_count) {
                var html_fragment = '';
                switch (_typeof(object)) {
                    case 'Null':
                        html_fragment = _format_null(object);
                        break;
                    case 'Boolean':
                        html_fragment = _format_boolean(object);
                        break;
                    case 'Number':
                        html_fragment = _format_number(object);
                        break;
                    case 'String':
                        html_fragment = _format_string(object);
                        break;
                    case 'Array':
                        html_fragment = _format_array(object, indent_count);
                        break;
                    case 'Object':
                        html_fragment = _format_object(object, indent_count);
                        break;
                }
                return html_fragment;
            };
            function _format_null(object) {
                return '<span class="json_null">null</span>';
            }

            function _format_boolean(object) {
                return '<span class="json_boolean">' + object + '</span>';
            }

            function _format_number(object) {
                return '<span class="json_number">' + object + '</span>';
            }

            function _format_string(object) {
                if (0 <= object.search(/^http/)) {
                    object = '<a href="' + object + '" target="_blank" class="json_link">' + object + '</a>'
                }
                return '<span class="json_string">"' + object + '"</span>';
            }

            function _format_array(object, indent_count) {
                var tmp_array = [];
                for (var i = 0,
                             size = object.length; i < size; ++i) {
                    tmp_array.push(indent_tab(indent_count) + format(object[i], indent_count + 1));
                }
                return '[<br/>' + tmp_array.join(',<br/>') + '<br/>' + indent_tab(indent_count - 1) + ']';
            }

            function _format_object(object, indent_count) {
                var tmp_array = [];
                for (var key in object) {
                    tmp_array.push(indent_tab(indent_count) + '<span class="json_key">"' + key + '"</span>:' + format(object[key], indent_count + 1));
                }
                return '{<br/>' + tmp_array.join(',<br/>') + '<br/>' + indent_tab(indent_count - 1) + '}';
            }

            function indent_tab(indent_count) {
                return (new Array(indent_count + 1)).join("&nbsp;&nbsp;");
            }

            function _typeof(object) {
                var tf = typeof object,
                        ts = _toString.call(object);
                return null === object ? 'Null' : 'undefined' == tf ? 'Undefined' : 'boolean' == tf ? 'Boolean' : 'number' == tf ? 'Number' : 'string' == tf ? 'String' : '[object Function]' == ts ? 'Function' : '[object Array]' == ts ? 'Array' : '[object Date]' == ts ? 'Date' : 'Object';
            };
            function loadCssString() {
                var style = document.createElement('style');
                style.type = 'text/css';
                var code = Array.prototype.slice.apply(arguments).join('');
                try {
                    style.appendChild(document.createTextNode(code));
                } catch (ex) {
                    style.styleSheet.cssText = code;
                }
                document.getElementsByTagName('head')[0].appendChild(style);
            }

            loadCssString('.json_key{ color: purple;}', '.json_null{color: red;}', '.json_string{ color: #077;}', '.json_link{ color: #717171;}', '.json_array_brackets{}');
            var _JSONFormat = function (origin_data) {
                this.data = 'string' != typeof origin_data ? origin_data : JSON && JSON.parse ? JSON.parse(origin_data) : eval('(' + origin_data + ')');
            };
            _JSONFormat.prototype = {
                constructor: JSONFormat,
                toString: function () {
                    return format(this.data, 1);
                }
            }
            return _JSONFormat;
        })();
        //根据URL装载默认数据
        function h(obj) {
            if (DATA_MAP[obj]) {
                //var url = '/' + obj.replace(/\_/g, '/') + '.htm';
                var url = BaseURL + obj.replace(/\__/g, '/');
                $('#htmlForm').attr('action', url);
                $("#submit_url").val(url);
                $('#data').val(DATA_MAP[obj].data);
                showResponse(DATA_MAP[obj].data,'data_view');
                //加密数据
                AjaxJson(jmURL,encodeURI(DATA_MAP[obj].data), function (p, status) {
                 	$('#req_h_data').val(JSON.stringify(p));
            	 });
                $("#ret").html('');
            } else {
                $('#htmlForm').attr('action', obj);
            }
        }
        //装载指定数据
        function load_data(obj){
        	data = $('#'+obj).val();
        	AjaxJson(jmURL,encodeURI(data), function (res, status) {
                 	$('#req_h_data').val(JSON.stringify(res));
                    //清除返回值
            });
		    showResponse(data,'data_view');
        }
        $(function () {
            // 初始化action列表
            for (var key in DATA_MAP) {
                $('#action_select').append('<option value="' + key + '">' + key + '[' + DATA_MAP[key].info + ']' + '</option>');
            }
        });
        
        
        //点击提交按钮
        function submit_function(obj) {
        	load_data('data');
            var data = $('#req_h_data').val();//实际json数据
            var form_action = $('#submit_url').val();
            //数据加密
            AjaxJson(form_action,data, function (res, status) {
                if(res!=null&&res.sign!=null) {
                    AjaxJson(jm2URL, JSON.stringify(res), function (jm2, status) {
                        showResponse(jm2.ciphertext, 'ret');
                    });
                }else{
                    showResponse("系统异常", 'ret');
                }
            });
        }
        
         function showResponse(responseText,view) {
                var o;
                try {
                    if (typeof responseText === 'string') {
                        o = new JSONFormat(eval('(' + responseText + ')')).toString();
                    } else {
                        o = new JSONFormat(responseText).toString();
                    }
                } catch (e) {
                    o = e.message + "&nbsp:&nbsp:&nbsp:input:<br/>" + responseText;
                }
                $("#"+view).html(o);
         }
         
         function changeAction(obj){
        	 url = $(obj).val();
        	 BaseURL = url;
             jmURL = BaseURL+"test/encryption";//加密url
     		 jm2URL = BaseURL+"test/decrypt";//解密url
         }
         
         function AjaxJson(url, postData, callBack) {
        	    try {
        	        $.ajax({
        	            url:url,
        	            type: "post",
        	            data: postData,
        	            dataType: "json",
        	            async: false,
        	            success: function (data) {
        	                    callBack(data);
        	            },
        	            error: function (data,b,c) {
        	                alert("系统出错！");
        	            },
        	            headers: {
        	                "Access-Control-Allow-Origin":"*",
        	                "Access-Control-Allow-Headers":"X-Requested-With",
        	                "Content-Type"  : "application/json",
        	                "Accept" : "application/json",
        	                "Access-Control-Allow-Methods": "POST, GET, OPTIONS, HEAD"
        	            }
        	        });
        	    } catch (e) {

        	    }
        	}