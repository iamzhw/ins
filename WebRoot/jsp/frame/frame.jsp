<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title></title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/default.css"   />
<link type="text/css" rel="stylesheet" href="<%=path%>/js/themes/default/easyui.css" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.pack.js"></script>
<script type="text/javascript" src="<%=path%>/js/outlook2.js"> </script>
<script type="text/javascript">
$(function(){
	InitLeftMenu();
	tabClose();
	tabCloseEven();
})

	 var _menus = {"menus":[
						{"menuid":"1","icon":"icon-sys","menuname":"系统管理",
							"menus":[{"menuname":"架构说明","menuno":"1","icon":"icon-nav","url":"<%=path%>/jsp/other/instructions.html"},
									 {"menuname":"表单","menuno":"2","icon":"icon-nav","url":"<%=path%>/jsp/other/staff-index.html"},
									 {"menuname":"按钮和标签","menuno":"3","icon":"icon-nav","url":"<%=path%>/jsp/other/basic.html"},
									 {"menuname":"布局","menuno":"4","icon":"icon-nav","url":"<%=path%>/jsp/other/basic1.html"},
									 {"menuname":"布局2","menuno":"5","icon":"icon-nav","url":"<%=path%>/jsp/other/basic2.html"},
									 {"menuname":"消息提示","menuno":"6","icon":"icon-nav","url":"<%=path%>/jsp/other/message.html"},
									 {"menuname":"图形的tab页","menuno":"7","icon":"icon-nav","url":"<%=path%>/jsp/other/tabimage.html"},
									 {"menuname":"百度地图","menuno":"8","icon":"icon-nav","url":"<%=path%>/jsp/baidu/map.html"},
									 {"menuname":"baidu","menuno":"9","icon":"icon-nav","url":"<%=path%>/jsp/baidu/baidu.jsp"}
									]}
						,{"menuid":"2","icon":"icon-sys","menuname":"功能案例",
							"menus":[{"menuname":"easyUI之dataGrid","menuno":"1","icon":"icon-nav","url":"<%=path%>/jsp/case/dataGrid.jsp"}
									]}
						
						
						
				]};
        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }
        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }
            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }
            $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })
        }
        $(function() {
            openPwd();
            //
            $('#editpass').click(function() {
                $('#w').window('open');
            });
            $('#btnEp').click(function() {
                serverLogin();
            })
			$('#btnCancel').click(function(){closePwd();})
            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
                    if (r) {
                        location.href = '/ajax/loginout.ashx';
                    }
                });
            })
        });
        

    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;"> <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' /> </div>
</noscript>
<div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(<%=path%>/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体"> <span style="float:right; padding-right:20px;" class="head"><a href="#" id="editpass" style="text-decoration:none">修改密码</a>|<a href="#" id="loginOut" style="text-decoration:none">注销</a></span> <span style="padding-left:10px; font-size: 16px; "><img src="images/blocks.gif" width="20" height="20" align="absmiddle" /> 爱运维HD</span> </div>
<div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
  <div class="footer">By ayw.hd</div>
</div>
<div region="west" split="true" title="导航菜单" style="width:180px;" id="west">
  <div class="easyui-accordion" fit="true" border="false">
    <!--  导航内容 -->
  </div>
</div>
<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
  <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
    <div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
      <h1>Welcome to jQuery UI!</h1>
    </div>
  </div>
</div>
<!--修改密码窗口-->
<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
  <div class="easyui-layout" fit="true" >
    <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
      <table cellpadding=3>
        <tr>
          <td>新密码：</td>
          <td><input id="txtNewPass" type="password" class="txt01" /></td>
        </tr>
        <tr>
          <td>确认密码：</td>
          <td><input id="txtRePass" type="password" class="txt01" /></td>
        </tr>
      </table>
    </div>
    <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;"> <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" > 确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a> </div>
  </div>
</div>
<!--  
<div id="mm" class="easyui-menu" style="width:150px;">
  <div id="mm-tabclose">关闭</div>
  <div id="mm-tabcloseall">全部关闭</div>
  <div id="mm-tabcloseother">除此之外全部关闭</div>
  <div class="menu-sep"></div>
  <div id="mm-tabcloseright">当前页右侧全部关闭</div>
  <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
  <div class="menu-sep"></div>
  <div id="mm-exit">退出</div>
</div>
-->
</body>
</html>
