(function($){
    $.fn.menulist = function(o){
        //默认配置
        var def = {
            type: 1,//菜单类型(1：右键菜单，2：点击菜单）
            location:1,//定位方式（1：相对定位，2：绝对定位）
            removemodels: [],//点击哪些document对象菜单不消失
            offset: {top:0, left:0},//偏移
            $menu: $("<div><div>")//jquery对象菜单
        };
        $.extend(def, o);
        
        return this.each(function(){
            var $me = $(this);
            var $menu = def.$menu;
            var $refer = $("<span class='jquery_menulist_refer' style='z-index:99'></span>");
            
            function Main(){
                $refer.css({"border-width":"0px", "height":0, "position":"relative"});
                if($me.attr("tagName") == "TR")
                    $me.find("td:first").prepend($refer);
                else
                    $me.prepend($refer);
                $menu.css("position","absolute");
                if(def.type == 1){
                    $me.bind("contextmenu", function(e){
                        $(document).trigger("click");
                        var offset = {top: e.pageY, left: e.pageX};
                        offset = GetOffset(offset);
                        $menu.css(offset);
                        $refer.append($menu);
                        $menu.show();
                        $(document).bind("click", {models: def.removemodels}, CloseMenu);
                        return false;
                    });
                }
                else if(def.type == 2){
                    $me.bind("click", function(e){
                        var offset = $me.offset();
                        offset = GetOffset(offset);
                        offset.top = offset.top + $me.outerHeight();
                        offset.left = offset.left + ($me.outerWidth() - $me.width());
                        $menu.css(offset);
                        $refer.append($menu);
                        $menu.show();
                        var ms = def.removemodels.concat($me.get(0));
                        $(document).bind("click", {models: ms}, CloseMenu);
                    });
                }
            }
            
            //关闭菜单
            function CloseMenu(e){
                var $m = $(e.target);
                var removes = e.data.models;
                while($m.attr("tagName") != "HTML" && $m.attr("tagName") != null){
                    if($.inArray($m.get(0), removes)){
                        return ;
                    }
                    $m = $m.parent();
                }
                $menu.hide();
                $(document).unbind("click", CloseMenu);
            }
            //获取坐标
            function GetOffset(offset){
                var os = $refer.offset();
                var top = offset.top - os.top + def.offset.top;
                var left = offset.left - os.left + def.offset.left;
                return {top:top, left:left};
            }
            
            Main();
        });
    }
})(jQuery);