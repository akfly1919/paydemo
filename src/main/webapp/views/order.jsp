<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>收银台</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div id="header">
    <div class="header_wrapper clearfix">
      	 收银台
    </div>
</div>
<div id="container" class="pay_wrapper">
    <div class="pay_box" style="display: block;">
        <ul class="pay_tab clearfix">
            <li class="active">收银台</li>
        </ul>
        <div class="pay_cont">
            <div class="pay_choose pay_method">
                <h4>选择支付方式</h4>
                <ul class="clearfix">
                    <li class="active" data-method="wx"><span class="weixin">微信支付</span></li>
                    <li data-method="zfb"><span class="zhifubao">支付宝钱包</span></li>
                    <li data-method="yl"><span class="yinlian">银联支付</span></li>
                </ul>
            </div>
            <div class="to_pay clearfix">
                <p>支付金额：<em>6</em> 元</p>
                <span id="topay">确认支付</span>
                <span class="distopay">确认支付</span>
            </div>
        </div>
    </div>
    <!-- 数据加载中 -->
    <div class="loading_wrapper" style="display: none;">
        <ul>
            <li class="load_1"></li>
            <li class="load_2"></li>
            <li class="load_3"></li>
            <li class="load_4"></li>
            <li class="load_5"></li>
        </ul>
    </div>

</div>
<div id="footer">
</div>
</body>
</html>