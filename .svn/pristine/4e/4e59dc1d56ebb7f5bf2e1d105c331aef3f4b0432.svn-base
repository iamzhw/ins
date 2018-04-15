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
	  var _menus = {"menus":[
						{"menuid":"1","icon":"icon-sys","menuname":"巡检管理",
							"menus":[{"menuname":"计划管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xj/jh.jsp"},
									{"menuname":"周期管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xj/cycle.jsp"},
									{"menuname":"任务管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xj/task.jsp"},
									{"menuname":"巡检问题管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xj/problem.jsp"},
									{"menuname":"实时跟踪监控","icon":"icon-nav","url":"<%=path%>/jsp/design/xj/monitor.jsp"}
								]
						},{"menuid":"8","icon":"icon-sys","menuname":"盯防管理",
							"menus":[{"menuname":"施工点管理","icon":"icon-nav","url":"<%=path%>/jsp/design/df/sgd.jsp"},
									{"menuname":"任务管理","icon":"icon-nav","url":"<%=path%>/jsp/design/df/dftask.jsp"},
									{"menuname":"盯防上报","icon":"icon-nav","url":"<%=path%>/jsp/design/df/dfsb.jsp"},
									{"menuname":"实时跟踪监控","icon":"icon-nav","url":"<%=path%>/jsp/design/df/dfmonitor.jsp"}
								]
						},{"menuid":"56","icon":"icon-sys","menuname":"客户端（手机截图）",
							"menus":[{"menuname":"坐标采集","icon":"icon-nav","url":"<%=path%>/jsp/design/instructions.jsp"},
									{"menuname":"巡检模块","icon":"icon-nav","url":"<%=path%>/jsp/design/instructions.jsp"},
									{"menuname":"盯防模块","icon":"icon-nav","url":"<%=path%>/jsp/design/instructions.jsp"}
								]
						},{"menuid":"28","icon":"icon-sys","menuname":"资源管理",
							"menus":[{"menuname":"采集点坐标管理","icon":"icon-nav","url":"<%=path%>/jsp/design/zy/cjd.jsp"},
									{"menuname":"资源信息采集","icon":"icon-nav","url":"<%=path%>/jsp/design/zy/resourcecj.jsp"},
									{"menuname":"资源信息管理","icon":"icon-nav","url":"<%=path%>/jsp/design/zy/resourcegl.jsp"},
									{"menuname":"光缆管理","icon":"icon-nav","url":"<%=path%>/jsp/design/zy/cablegl.jsp"},
									{"menuname":"巡检点管理","icon":"icon-nav","url":"<%=path%>/jsp/design/zy/xjd.jsp"},
									{"menuname":"图层显示设置","icon":"icon-nav","url":"<%=path%>/jsp/design/zy/tcshow.jsp"},
									{"menuname":"鹰眼地图","icon":"icon-nav","url":"<%=path%>/jsp/design/zy/yingyanditu.jsp"},
								]
						},{"menuid":"39","icon":"icon-sys","menuname":"维护用料管理",
							"menus":[{"menuname":"维护用料管理","icon":"icon-nav","url":"<%=path%>/jsp/design/cl/clgl.jsp"}
								]
						},{"menuid":"56","icon":"icon-sys","menuname":"系统管理",
							"menus":[{"menuname":"用户管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xt/user.jsp"},
									{"menuname":"角色管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xt/role.jsp"},
									{"menuname":"代维公司资质管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xt/company1.jsp"},
									{"menuname":"代维公司车辆管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xt/company2.jsp"},
									{"menuname":"代维公司仪表管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xt/company3.jsp"},
									{"menuname":"代维公司人员管理","icon":"icon-nav","url":"<%=path%>/jsp/design/xt/company4.jsp"},
									{"menuname":"数据备份","icon":"icon-nav","url":"<%=path%>/jsp/design/xt/databf.jsp"},
									{"menuname":"数据清理","icon":"icon-nav","url":"<%=path%>/jsp/design/xt/dataclear.jsp"}
								]
						},{"menuid":"56","icon":"icon-sys","menuname":"统计报表",
							"menus":[{"menuname":"巡线率查询统计","icon":"icon-nav","url":"<%=path%>/jsp/design/instructions.jsp"},
									{"menuname":"巡检考核评比","icon":"icon-nav","url":"<%=path%>/jsp/design/instructions.jsp"},
									{"menuname":"巡检计划查询统计","icon":"icon-nav","url":"<%=path%>/jsp/design/instructions.jsp"},
									{"menuname":"盯防报表","icon":"icon-nav","url":"<%=path%>/jsp/design/instructions.jsp"},
									{"menuname":"任务查询统计","icon":"icon-nav","url":"<%=path%>/jsp/design/instructions.jsp"}
								]
						},{"menuid":"56","icon":"icon-sys","menuname":"客户经理管理考核系统",
							"menus":[{"menuname":"客户信息定位","icon":"icon-nav","url":"<%=path%>/jsp/design/kh/khxxdw.jsp"},
									{"menuname":"拜访记录管理","icon":"icon-nav","url":"<%=path%>/jsp/design/kh/bfjlgl.jsp"},
									{"menuname":"商机管理","icon":"icon-nav","url":"<%=path%>/jsp/design/kh/sjgl.jsp"},
									{"menuname":"客户预警","icon":"icon-nav","url":"<%=path%>/jsp/design/kh/khyj.jsp"},
									{"menuname":"客户拜访统计","icon":"icon-nav","url":"<%=path%>/jsp/design/kh/bftj.jsp"},
									{"menuname":"全省商机信息统计","icon":"icon-nav","url":"<%=path%>/jsp/design/kh/sjtj.jsp"},
								]
						}	
						
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
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体"> <span style="float:right; padding-right:20px;" class="head"><a href="#" id="editpass" style="text-decoration:none">修改密码</a>|<a href="#" id="loginOut" style="text-decoration:none">注销</a></span> <span style="padding-left:10px; font-size: 16px; "><img src="images/blocks.gif" width="20" height="20" align="absmiddle" /> 海南联通综合光缆线路巡检管理
</span> </div>
<div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
  <div class="footer">中博科学技术研究院有限公司</div>
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
