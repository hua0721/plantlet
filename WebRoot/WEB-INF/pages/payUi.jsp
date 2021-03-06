<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Pay</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/pay.css">
<link href="css/admin/animation.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" media="all" />
<style type="text/css">
	.error{
		border:1px solid #F00;
	}
</style>
</head>
<body>
	<div class="kjzf_head">
		<div class="topqj">
			<div class="yhlogo">
				<a href="index"><img src="img/logo2.png" width="116" height="41"></a>
			</div>
			<div class="yhmc"><s:text name="checkoutCounter"/></div>
			<div class="denglzc"></div>
		</div>
	</div>
	<div class="kjzf_usermes">
		<div class="touxiang" style="margin:20px 0 0 150px;">
		<s:if test="actionErrors!=null&&actionErrors.size>0&&actionErrors[0]!=null">
			<span class="glyphicon glyphicon-remove-sign" style="font-size:100px;color:#DD4B39;"style="margin:60px 10px 0 0;"></span>
		</s:if>
		<s:else>
			<span class="glyphicon glyphicon-info-sign" style="font-size:100px;color:#FF6600;"style="margin:60px 10px 0 0;"></span>
		</s:else>
		</div>
		<div class="usermes hasClear">
			<s:if test="actionErrors!=null&&actionErrors.size>0&&actionErrors[0]!=null">
				<h2><s:text name="paymentFailed"/>!!<small><s:text name="cause"/><s:property value="actionErrors[0]"/></small></h2><h3><s:text name="goToSelectMoPay"/><a href='myCenter?function=2'><s:text name="clickGo"/></a></h3>
			</s:if>
			<s:else>
			<h2>
				<s:text name="youPayingOrder"/><br/>
			</h2>
			<s:iterator value="#request.orderForms">
				<h4><s:text name="theOrderNo"/>:<strong style="padding:0 10px;color:#FF6600">${id}</strong><s:text name="amount"/><strong style="color:#F00;"><span class="price"><s:i18n name="format"><s:text name="struts.percent"><s:param value="goods.price*buyNum"/></s:text></s:i18n></span><s:text name="yuan"/></strong></h4>
			</s:iterator>
			<h2 id="h21" style="display: none; color:#f60;"></h2>
			<h2 id="h22" style="display: none;color:#f60"></h2>
			<h2 id="h23" style="display: none;color:#f60"></h2>
			<h2 id="h24" style="display: none;color:#f60"></h2>
			<h2 id="h25" style="display: none;color:#f60"></h2>
			<div class="xxlist">
				<s:text name="totalPaymentAmount"/><span class="jinge"><s:i18n name="format"><s:text name="struts.percent"><s:param value="sumPrice"/></s:text></s:i18n><s:text name='yuan'/></span>
			</div>
			</s:else>
		</div>
	</div>
	<s:if test="actionErrors==null||actionErrors!=null&&actionErrors.size<1">
	<div class="kjzf_zhifufangs hasCloase">
		<span class="wenzxx" style="width:160px;"><s:text name="useTheBalancePayment"/></span> 
		<input type="password" name="password" placeholder="<s:text name='inputPayPassword'/>" style="width:300px;height:30px;margin-top:10px;" data-toggle="popover" 
								data-placement="top" data-content=""/>
		<span style="font-size:15px;"><s:text name="yourAvailableBalance"/><span style="color:#F00;"><s:property value="#session.user.safe.balance"/>元</span></span>
		<div class="cjzhifanniu" style="float:right;margin-right:200px;cursor:pointer;" id="pay"><a><s:text name="immediatePayment"/></a></div>
	</div>

	<div class="kjzf_zhifufangs2 hasCloase">
		<div class="biaotixx">
			<s:text name="quickPayment"/><span class="tsxx" style="font-size: 14px;"><s:text name="certification,NeedNotOnlineBanking"/></span>
		</div>
		<div class="xuanxiangklist">
			<ul id="bankOnline">
				<li name="bankOnline"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C758807642804A99760A9D6998C9B6B50538AC637297E880271458A55BE577B993B78491386DE7202D153DE41277898DD7F1A3519D269FA875E7B53ADEA75AF924734FC1DDE3F59D5AD95371"><span
					class="imgtb"><img src="img/payImg/ALIPAY.png"></span><span
					class="wenz"><s:text name="alipay"/></span></li>
				<li name="bankOnline"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076E9870F5A6D9028A2BEAF4E9F1819BD5912AA974D42D7DAFA5BE577B993B78491D416A69388837E574AAF6F2FD653BE87F8A26CA7585D7514CDF81FC49AF27E8CE8BDB452A0C524EA"><span
					class="imgtb"><img src="img/payImg/EPAYACC.png"></span><span
					class="wenz"><s:text name="bestoay"/></span></li>
				<li name="bankOnline"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C75880764D3BCA27E3F5762670DE2832A1FA10F7CA35A5FBB3DE1CCDA3E64D89422FD77F1DC67C58AFA482C632B48C0C91B3553A6FB2BB5C7A79E0BACAC230B55486658A57BA41AAA08E52B9"><span
					class="imgtb"><img src="img/payImg/UPQP.png"></span><span
					class="wenz"><s:text name="unionpay"/></span></li>
			</ul>
		</div>
	</div>


	<div class="kjzf_zhifufangs2 hasCloase">
		<div class="biaotixx">
			<s:text name="e-currencyPayment"/><span class="tsxx" style="font-size: 14px;">（<s:text name="notOpenNetSilver"/>）</span>
		</div>
		<div class="xuanxiangklist">
			<ul id="interBank">
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C75880764AA371D81027693A2984722BA98B63B3B2C97F4E956D3946CA35A5FBB3DE1CCD6A4212FC7F3E999DE009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8CD8C97FFEA20CF221"><span
					class="imgtb"><img src="img/payImg/CEB.png"></span><span
					class="wenz"><s:text name="CEB"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076E5E769BE1BD6B49F6AE527F2B5B1FC89B2C97F4E956D3946CA35A5FBB3DE1CCD05EF5940F3714404D183DA7AEB0CF73232B48C0C91B3553A6FB2BB5C7A79E0BA7BFB45EC8816F4D157BA41AAA08E52B9"><span
					class="imgtb"><img src="img/payImg/ICBC.png"></span><span
					class="wenz"><s:text name="ICBC"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076E5E769BE1BD6B49F527C44DF0DBD8C7AB2C97F4E956D3946CA35A5FBB3DE1CCD9092355A1D818E6CE009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8CD76B029BC2758EAE"><span
					class="imgtb"><img src="img/payImg/CCB.png"></span><span
					class="wenz"><s:text name="CCB"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076F8A1D7BE365B903099E00D42E1A6BFC5B2C97F4E956D3946CA35A5FBB3DE1CCD0418CDDD347D9DBBA819445D5FE6ACD832B48C0C91B3553A6FB2BB5C7A79E0BA72CEFBF4CF50E76157BA41AAA08E52B9"><span
					class="imgtb"><img src="img/payImg/BCOM.png"></span><span
					class="wenz"><s:text name="BOCOM"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C758807673B39F9C1C8F4D1B3015223100F33152B2C97F4E956D3946CA35A5FBB3DE1CCD3BC880DC03C5C9BDD183DA7AEB0CF73232B48C0C91B3553A6FB2BB5C7A79E0BA72CEFBF4CF50E76157BA41AAA08E52B9"><span
					class="imgtb"><img src="img/payImg/CMBC.png"></span><span
					class="wenz"><s:text name="CMBC"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C75880764AA371D81027693A701FB584CF8B319BB2C97F4E956D3946CA35A5FBB3DE1CCD500AED65CAA31BAC4AAF6F2FD653BE87F8A26CA7585D7514CDF81FC49AF27E8C4AC89ED1D8CEAE7F"><span
					class="imgtb"><img src="img/payImg/ABC.png"></span><span
					class="wenz"><s:text name="ABC"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C75880764AA371D81027693AC1E212445B1227ECB2C97F4E956D3946CA35A5FBB3DE1CCD071F29DB64D15C09E009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8C4AC89ED1D8CEAE7F"><span
					class="imgtb"><img src="img/payImg/CIB.png"></span><span
					class="wenz"><s:text name="CIB"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076F8A1D7BE365B90309A0050E64D3B6C3BB2C97F4E956D3946CA35A5FBB3DE1CCD4FC47F590FB592864AAF6F2FD653BE87F8A26CA7585D7514CDF81FC49AF27E8C4AC89ED1D8CEAE7F"><span
					class="imgtb"><img src="img/payImg/BOC.png"></span><span
					class="wenz"><s:text name="BOC"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076F8A1D7BE365B90308D2B52389750F03BEA59757696ED32C8FA231D7E5FDCA610B2C97F4E956D3946CA35A5FBB3DE1CCDDFD2B797E8E9BC7AD0687605F0B6815332B48C0C91B3553A6FB2BB5C7A79E0BA4047B3BEA11BDBD057BA41AAA08E52B9"><span
					class="imgtb"><img src="img/payImg/POST.png"></span><span
					class="wenz"><s:text name="PSBC"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076106E243D7A556BD43B96C1253D200BF0B2C97F4E956D3946CA35A5FBB3DE1CCD3BC880DC03C5C9BDE009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8CB0088CEDDB9086F8"><span
					class="imgtb"><img src="img/payImg/CMB.png"></span><span
					class="wenz"><s:text name="CMB"/></span></li>
				<li name="persion"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076F8A1D7BE365B90300495953046395729B2C97F4E956D3946CA35A5FBB3DE1CCD071F29DB64D15C092BACB2994936E5A715DF3ABDBF4803000F9E42566E02130BA125C0C23491590CED32BFEEFC5BEC48"><span
					class="imgtb"><img src="img/payImg/CITIC.png"></span><span
					class="wenz"><s:text name="CITIC"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C75880764AA371D81027693A8AD21E7411C79495B2C97F4E956D3946CA35A5FBB3DE1CCD4FC47F590FB59286E009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8C9C06375F5071EF79"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/BOB.png"></span><span class="wenz"><s:text name="BB"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076E5E769BE1BD6B49FF65B1BC5729EEBB866B3843B67DD5CBFB2C97F4E956D3946CA35A5FBB3DE1CCD2AFE6CA2C2A11620E009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8C9C06375F5071EF79"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/GDB.png"></span><span class="wenz"><s:text name="CGB"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076F8A1D7BE365B9030C905AC130F4B7313B2C97F4E956D3946CA35A5FBB3DE1CCD4FC47F590FB59286FF02975F8BA8FB7C32B48C0C91B3553A6FB2BB5C7A79E0BA56088EEEF6AC9C3A57BA41AAA08E52B9"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/BOSH.png"></span><span class="wenz"><s:text name="BOS"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076CBF8A5C6FA7937E79589B4C1201CCCE3B2C97F4E956D3946CA35A5FBB3DE1CCDD446467C3A9D0422595D28444A95349E32B48C0C91B3553A6FB2BB5C7A79E0BA3BBAC622DEC177F757BA41AAA08E52B9"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/NBCB.png"></span><span class="wenz"><s:text name="NBBC"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C75880764AA371D81027693A2ADED6788D7E0C290BEFC989FC3907A5B2C97F4E956D3946CA35A5FBB3DE1CCD6D2C02461962191E93618E9F760559E115DF3ABDBF4803000F9E42566E02130BAB962A83C2DC9D19ED32BFEEFC5BEC48"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/BJRCB.png"></span><span class="wenz"><s:text name="BBB"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C758807642804A99760A9D6952876E2CB238D259B2C97F4E956D3946CA35A5FBB3DE1CCDD725184D06DE86E4E009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8C91C9E3AC27DE6426"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/JCB.png"></span><span class="wenz"><s:text name="JCB"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C758807690EBE16AA2CEDA376CB2FF15D8BE6EBE3990F9A5E698873FA350412D8A1E5C9240FF92BE64281364F8179BCCE819B5EF5BE577B993B78491117DAEF055F77905F6007D917DB113C8B4FF5DD908D63D2155797021E99951A2824E395359EB0DB9"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/ZHRCC.png"></span><span class="wenz"><s:text name="ZRCC"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076E5E769BE1BD6B49FD234B6BC4C031559B2C97F4E956D3946CA35A5FBB3DE1CCDE882E7E0C80A614FE009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8CC7F471F9EB1FCF4D"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/PAB.png"></span><span class="wenz"><s:text name="PAB"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C7588076E5E769BE1BD6B49F24F83CE1CCB94BDCB2C97F4E956D3946CA35A5FBB3DE1CCDC3487D5994584983E009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8CC7F471F9EB1FCF4D"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/HSB.png"></span><span class="wenz"><s:text name="WBB"/></span></li>
				<li name="more"
					param="F8EC2F455A4E58F646F38AF9241A87C609E72E69C758807642804A99760A9D699904D57A7B7769C2B2C97F4E956D3946CA35A5FBB3DE1CCDEC05CE669439A991E009067258C42FB1F8A26CA7585D7514CDF81FC49AF27E8CC7F471F9EB1FCF4D"
					style="display: none;"><span class="imgtb"><img
						src="img/payImg/HZB.png"></span><span class="wenz"><s:text name="URCB"/></span></li>
				<li id="more" onclick="dispayMore()"><span class="imgtb"></span><span
					class="wenz">+<s:text name="more"/></span></li>
			</ul>
		</div>
	</div>

	<div class="cztjanniu hasCloase">
		<a href="javascript:;" onclick="alert('<s:text name="onOpen"/>！！')"
			trkbtn="trkBtnCount_sc_chf-wyzf-ljzf-buybtn"><s:text name="immediatePayment"/></a>
	</div>
	</s:if>
	<div class="footbox">版权所有&#169;2012 小苗基地公司 [ 业务经营许可证
		A8.B8.B8-88888888 ] ICP 证号:京 ICP 备 8888888号</div>
	<script type="text/javascript" src="js/jquery/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("ul li").click(function() {
				$("ul li").removeClass("active");
				$("ul li").css("border-color", "#f1f1f1");
				$(this).css("border-color", "#F00");
				$(this).addClass("active");
			})
			$("ul li").bind("mouseenter", function() {
				$(this).css("border-color", "#F00");
			})
			$("ul li").bind("mouseleave", function() {
				if ($(this).attr("class") != 'active')
					$(this).css("border-color", "#f1f1f1");
			})
			$("input[name=password]").keyup(function(e){
				if(e.keyCode==13){
					$("#pay").trigger("click");
				}
			})
			$("#pay").click(function(){
				if($("input[name=password]").val()=='')return;
				$.post("json/pay_pay","key=${key}&pp="+$("input[name=password]").val(),function(data){
					if(data!=null){
						if(data=='input'){
							window.location.href="loginUi";
							return;
						}
						data = eval("("+data+")");
						if(data.message){
							$(".glyphicon").removeClass("glyphicon-info-sign");
							$(".glyphicon").addClass("glyphicon-ok-sign");
							$(".glyphicon").css("color","#00A65A");
							$(".hasCloase").remove();
							$(".hasClear").html("<h2><s:text name='paySuccess'/></h2><h3><s:text name='iwtoboby'/><a href='myCenter?function=2'><s:text name='clickGo'/></a></h3>");
							return;
						}else{
							if(data.cause=='noPassword'){
								if(confirm("<s:text name='youNoPayPassword'/>")){
									window.location.href="myCenter?function=6";
									return;
								}
								$(".glyphicon").removeClass("glyphicon-info-sign");
								$(".glyphicon").addClass("glyphicon-remove-sign");
								$(".glyphicon").css("color","#DD4B39");
								$(".hasCloase").remove();
								$(".hasClear").html("<h2><s:text name='paymentFailed'/>！！<small><s:text name='cause'/><s:text name='noPayPasswordError'/></small></h2><h3><s:text name='goToSelectMoPay'/><a href='myCenter?function=2'><s:text name='clickGo'/></a></h3>");
								return;
							}
							if(data.password!=null){
								$("input[name=password]").addClass("error");
								$("input[name=password]").attr("data-content",data.cause);
								$("input[name=password]").popover("show");
								setTimeout(function() {
									$("input[name=password]").popover("destroy");
								}, 3000);
								return;
							}
							$(".glyphicon").removeClass("glyphicon-info-sign");
							$(".glyphicon").addClass("glyphicon-remove-sign");
							$(".glyphicon").css("color","#DD4B39");
							$(".hasCloase").remove();
							$(".hasClear").html("<h2><s:text name='paymentFailed'/><small><s:text name='cause'/>"+data.cause+"</small></h2><h3><s:text name='goToSelectMoPay'/><a href='myCenter?function=2'><s:text name='clickGo'/></a></h3>");
							return;
						}
					}
					$(".glyphicon").removeClass("glyphicon-info-sign");
					$(".glyphicon").addClass("glyphicon-remove-sign");
					$(".glyphicon").css("color","#DD4B39");
					$(".hasCloase").remove();
					$(".hasClear").html("<h2><s:text name='paymentFailed'/>!!</h2><h3><s:text name='goToSelectMoPay'/><a href='myCenter?function=2'><s:text name='clickGo'/></a></h3>");
				})
			})
		})
		var flag = true;
		function dispayMore() {
			if (flag) {
				$("li[name='more']").show();
				$("#more .wenz").html("-<s:text name='packUp'/>");
				flag = false;
			} else {
				$("li[name='more']").hide();
				$("#more .wenz").html("+<s:text name='more'/>")
				flag = true;
			}
		}
	</script>
</body>
</html>
