var Search = {
	
	keyword: '',
	originKeyword: '',
	
	cache : {},//翻页缓存
	q: 12,//分页数
	
	selector : "",
    selectedAttr : [],
	
		
	init: function(){
		
		var me = this;
		
		me.load();

		var keyword = this.getParam("keyword");
		
		if(this.isEmpty(keyword)){
			me.renderEmpty("");
			return;
		}		
		var originKeyword = decodeURI(keyword,"UTF-8"); //注意解码顺序
		var p = this.getParam("p");
		me.originKeyword = originKeyword;
		me.keyword = encodeURI(keyword,"UTF-8"); //仅进入页面时初始化keyword
		
		
		me.bindEvents();
		
//		me.cache={};//刷页面时清空缓存
		
		me.search(p);
	},
	
	load: function(){
		
		var me = this;
		
		$('#header').load('/load/header.html',function(){
			$('#keyword').val(me.originKeyword);//搜索框赋值
		});
		$('#header2').load('/load/header2.html');
		$('#foot').load('/load/foot.html');
		
		me.loadSort();
		me.loadSelector();
	},
	/**
	 * 加载排序条件
	 */
	loadSort: function(){
		var me = this;
		var sort = me.getParam("sort");
		var conds = $('.search-cond .cond');
		
		conds.removeClass('current');
		conds.removeClass('up');
		conds.removeClass('down');
		var cur = null;
		if(sort == '1'){
			cur = $(".search-cond").children().eq(1);
			cur.addClass('up');
			cur.attr('orderby',sort);
		}else if(sort == '2'){
			cur = $(".search-cond").children().eq(1);
			cur.addClass('down');
			cur.attr('orderby',sort);
		}else{
			cur = $(".search-cond").children().eq(0);
		}
		cur.addClass('current');
	},
	/**
	 * 加载过滤条件
	 */
	loadSelector: function(){
		var me = this;
		var originKeyword = me.originKeyword;
		var keyword = me.keyword;
		var selector = me.getParam("sl");
		var magicKey = me.getCookie("keyID");
		var url = '/esserver/shopProduction/loadSelector.action';
		
		var barHtml = '';
		var selectorHtml = '';
		
		jQuery.ajax({
            type : "POST",
            dataType : "json",
            url : url,
            data : {"keyword":keyword,"sl":selector,"magicKey":magicKey},
            success : function(result) {
            	
                if(result){
                	
                	if(result.barItems){                		
                		barHtml = me.generateBarHtml(result.barItems);                		
                	}
                	if(result.selectorItems){                		
                		selectorHtml = me.generateSelectorHtml(result.selectorItems);                		
                	}                	
                	$('#conditionBar').prop('outerHTML', barHtml);
            		$('#conditionSelector').prop('outerHTML', selectorHtml);
                }
                
            },
            error:function(XMLHttpResponse ){
            	$('#conditionBar').prop('outerHTML', me.generateBarHtml(null));
        		$('#conditionSelector').prop('outerHTML', me.generateSelectorHtml(null));
            }
        });	
	},
	
	generateBarHtml: function(barItems){
		var me = this;
		var html = '';
		if(barItems){
			for(var i=0; i<barItems.length; i++){
				var item = barItems[i];
				var href = me.fetchBarItemUrl(item.key);
				html = html 
				+ '<i class="condition-arrow">&gt;</i>'
				+ '<a class="condition-select-item" href="'+href+'" rel="nofollow" title="'+item.name+'">'
				+ '<b>'+item.title+'：</b><em>'+item.name+'</em><i></i></a>';
			}
		}
		var barHtml = '<div id="conditionBar" class="condition-bar">'
			+ '<div class="condition-nav">'
			+ '<div class="condition-nav-main clearfix">'
			+ '<div class="condition-nav-item">'
			+ '<div class="condition-first"><a href="#">全部结果</a></div></div>'
			+ html
			+ '</div></div></div>';
		return barHtml;
	},
	/**
	 * conditionBar item 的url，即从sl删除
	 * @param key
	 */
	fetchBarItemUrl:function(key){
		var me = this;
		var url = '/manager/search.html?keyword='+me.originKeyword;
		var sort = me.getParam('sort');
		if(sort){
			url += '&sort='+sort;
		}
		var selector = me.getParam('sl');
		var at = encodeURIComponent('@');
		if(selector){
			selector = decodeURIComponent(selector);
			if(selector.indexOf(key)>-1){
				var tmp = '';
				var arr = selector.split('@');
				if(arr.length>1){
					for(var i=0; i<arr.length; i++){
						if(arr[i].indexOf(key) == -1){								
							if(tmp == ''){
								tmp = tmp + arr[i];
							}else{
								tmp = tmp + arr[i] + at;
							}
						}
					}
				}
				if(tmp!=''){
					url += '&sl='+tmp;
				}					
			}				
		}
		return url;
	},
	
	generateSelectorHtml: function(selectorItems){
		var me = this;
		var html = '';
		
		var lines = '';		
		
		if(selectorItems){
			
			for(var i=0; i<selectorItems.length; i++){
				var item = selectorItems[i];
				var key = item.key;
				var title = item.title;
				var vList = item.vList;
				var lis = '';
				for(var j=0;j<vList.length;j++){
					var li = vList[j];
					lis = lis + '<li><a href="'+ me.fetchSelectorUrl(key,li.value) +'" rel="nofollow"><i></i>'+li.name+'</a></li>';
				}
				var ext = '';
				if(key == 'ps'){
					ext = '<div class="sl-price">'
						+ '<input class="input-txt" id="priceMin" title="最低价" maxlength="6" onkeyup="Search.filter(this);">'
						+ '<em> - </em>'
						+ '<input class="input-txt" id="priceMax" title="最高价" maxlength="8">'
						+ '<a class="s-btn" id="priceBtn">确定</a>'
						+ '</div>';
				}
				var line = '<div class="s-line">'
					+ '<div class="sl-wrap">'
					+ '<div class="sl-key"><span>'+title+'：</span></div>'
					+ '<div class="sl-value">'
					+ '<div class="sl-v-list">'
					+ '<ul>'
					+ lis
					+ '</ul>'
					+ ext
					+ '</div></div></div></div>';
				lines = lines + line;
			}
		}
		
		html = '<div id="conditionSelector" class="selector">'
			+ lines
			+ '</div>';
		return html;
	},
	
	/**
	 * 过滤条件 url,即添加到sl
	 * @param key
	 * @param value
	 * @returns
	 */
	fetchSelectorUrl: function(key,value){
		var me = this;
		var url = '/manager/search.html?keyword='+me.originKeyword;
		var sort = me.getParam('sort');
		if(sort){
			url += '&sort='+sort;
		}
		var selector = me.getParam('sl');
		var at = encodeURIComponent('@');
		
		if(selector){
			selector = decodeURIComponent(selector);				
			if(selector.indexOf(key) == -1){
				url += '&sl='+ selector + at + key + '_' + value;
			}			
		}else{
			url += '&sl='+key+'_'+value;
		}

		return url;
	},
	
	bindEvents: function(){
		
		var me = this;
		
		//price btn
		$(document).on('click', '#conditionSelector #priceBtn', function(){
			
			var b = $("#priceMin").val();
            var c = $("#priceMax").val(); 
            ("" != b || "" != c) && (("" == b || 0 >= b) && (b = "0"), 
                	b = "M" + parseInt(b), 
                	c = "" == c || 0 >= c ? "": "L" + parseInt(c), 
                	me.selectedAttr = [], me.selector = "ps", 
                	me.selectedAttr.push(b + c), 
                	window.location = me.getAttrUrl())
		});
		
		//cond tab
		$(document).on('click', '.search-cond .cond', function(){
			var cur = $(this);
			me.switchCondTab(cur);			
		});
		
		//goto btn bind event
		$(document).on('click', '.page-btn', function(){
			var goPage = $(".page-go input").val(); //跳转页数
				
			var np = $(".allPage").html();//总页数
			if(goPage > np){
				goPage = np;
			}
			Search.page(goPage);
			me.search(goPage);
			//清空用户跳转页数
			$(".page-go input").val("");
    	});
	},
	
	switchCondTab: function(cur){
		var me = this;
		
		var conds = $('.search-cond .cond');
		
		conds.removeClass('current');
		cur.addClass('current');
		
		var p = me.getParam("p");
		var orderby = cur.attr('orderby');
		var sort = '0';
		if('1' == orderby
				|| '2' == orderby){
			
			//默认取升序
			var cl = 'up';
			sort = '1';				
			if(cur.hasClass('up')){
				cl = 'down';
				sort = '2';
			}
			cur.attr('orderby',sort);				
			
			conds.removeClass('up');
			conds.removeClass('down');
			cur.addClass(cl);
		}
		me.page(null,sort);
    	me.search(1);//排序页码归1
	},
	
	search: function(p){
		
		var me = this;
		
//		var url = me.iurl + '/searchData.action';
		var url = '/esserver/shopProduction/searchData.action';
		
		var reg = /^[1-9][0-9]*$/;
		if (!reg.test(p)) {
			p=1;
		}
		var sort = me.getParam("sort");
		var sl = me.getParam("sl");
		var q = me.q;
		var magicKey = me.getCookie("keyID");
		
		//先查缓存
//		if(me.cache[p]!=null){
//			var cacheData = me.cache[p];
//			me.renderSearch(p,cacheData.total,cacheData.html);
//			return;
//		}
		
		jQuery.ajax({
            type : "POST",
            dataType : "json",
            url : url,
            data : {"keyword":me.keyword,"p":p,"q":q,"s":sort,"sl":sl,"magicKey":magicKey},
            success : function(result) {
            	
                if(result){
                	
                	if(result.isEmpty){
                		me.renderEmpty(me.originKeyword);
                		return;
                	}
                	
                	result = result.queryResult;
                	
                	var total = result.total;//总记录数
                	var pojos = result.pojos;//
                	
                	var html = me.refreshHtml(pojos);
                	//加载页面
                	me.renderSearch(p,total,html);
                	//缓存数据
//                	try{
//	                	var cacheData = {};
//	                	cacheData.total = total;
//	                	cacheData.html = html;
//	                    me.cache[p] = cacheData;
//                	}catch(e){
//                		
//                	}
                }
                
            },
            error:function(XMLHttpResponse ){
            }
        });
	},
	/**
	 * 查询结果为空
	 * @param originKeyword
	 */
	renderEmpty: function(originKeyword){
		
		var emptyHtml = 
			'<div class="empty-container">'+
				'<div class="empty-msg">'+
					'<span>抱歉，没有找到符合条件的与“<em>'+originKeyword+'</em>”相关的艺术品</span>'+
				'</div>'+
			'</div>';
		$('.product-box').html(emptyHtml);
	},
	/**
	 * 查询结果
	 * @param p
	 * @param total
	 * @param html
	 */
	renderSearch: function(p,total,html){
		
		var me = this;
		
		$('.product-box').html(html);
		
		$("#Pagination").pagination(total, {
            num_edge_entries: 1,
            num_display_entries: 3,
            items_per_page:me.q,
            current_page:p-1,
            callback: function(targetPage){
            	//页面跳转回调
            	//targetPage从0开始计数
            	//翻页 keyword不变
            	me.search(targetPage+1);
            },
        });
		
    	window.scrollTo(0,0);
	},
	/**
	 * 刷新查询结果html
	 * @param pojos
	 */
	refreshHtml: function(pojos){
		
		var html = '';

		for(var index in pojos){
			
			var pojo = pojos[index];
			
			var id = pojo.id;
			var name = pojo.name;
			var price = pojo.price;
			var mainPic = pojo.mainPic;
			
			var href = "/manager/production/query_for_edit.action?productionID="+id;	

			var single = 
				'<li class="spanimage">'+
					'<div class="lineimage">'+
						'<a href="'+href+'" target="_blank" ><img src="'+mainPic+'" title="'+name+'"></a>'+
					'</div>'+
					'<span class="project-details">'+name+'</span>'+
				'</li>';

			html+=single;
		}

		html = 
			'<div>'+
				'<ul class="gallery-post-grid">'+
					html +
				'</ul>'+
			'</div>';

		return html;
	},
	/**
	 * url变更
	 * @param p
	 * @param s
	 */
	page: function  (p,s) {
		var me = this;
		var keyword = me.originKeyword;
		
        var obj = new Object();
        obj['keyword'] = keyword;
        obj.rand = Math.random();
        
        var url = '?keyword='+keyword;
        if(p){
        	url = url + '&p=' + p;
        	obj['p'] = p;
        }
        s = s || me.getParam('sort');
        if(s && s!='0'){
        	url = url + '&sort=' + s;
        	obj['sort'] = s;
        }
        var sl = me.getParam('sl');
        if(sl){
        	url = url + '&sl=' + sl;
        	obj['sl'] = sl;
        }
        
        history.replaceState(obj, '', url);
    },
    
    getAttrValue : function(a, b) {
	    var c = new RegExp("(^|\\?|&)" + a + "=([^&#]*)(\\s|#|&|$)", "i");
	    var d = "";
	    if(c.test(b) && (d = c.exec(b))){
	    	d = d[2];
	    }
	    return d;
	},  
	getAttrUrl : function() {
	    var a = window.location.href || "";
	    var b = null;
	    var c = a.replace(/(^|\\?|&)sl=([^&#]*)/g, "").replace(/#[a-zA-Z\d\-\.\_\~]*$/, "");
	    var d = this.getAttrValue("sl", window.location.href);
	    return a.match(/#([a-zA-Z\d\-\.\_\~]*)$/) && (b = a.match(/#([a-zA-Z\d\-\.\_\~]*)$/)[1]),
	    d.indexOf("@") != d.length - 1 && d.indexOf("%40") != d.length - 1 && (d += "@"),
	    c + "&sl=" + d + this.selector + "_" + this.selectedAttr.join("%7C%7C") + (b ? "#" + b: "");
	},
	filter: function(e){
    	e.value=e.value.replace(/[^0-9]/g,'');
    },
	/**
	 * 获取url参数，不解码
	 * @param name
	 * @returns
	 */
	getParam: function(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	    if (r != null) return r[2]; return null; //返回参数值 不解码
	},
	getCookie: function(name){
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	    if(arr=document.cookie.match(reg))
	        return unescape(arr[2]);
	    else
	        return null;
	},
	isEmpty: function(str) {
		return (str == null || str.replace(/^\s+|\s+$|^[\u3000]+|[\u3000]+$/g, '') == '');
	}
};

$(function(){	
	
	Search.init();    
});
