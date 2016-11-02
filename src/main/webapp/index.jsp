<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>paydemo支付</title>
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src=""></script>
</head>
<body>
<div id="header">
    <div class="header_wrapper clearfix">
    	paydemo
    </div>
</div>
<div id="container" class="pay_wrapper">
    <div class="pay_box" style="display: block;">
        <ul class="pay_tab clearfix">
            <li class="active">下单购买</li>
        </ul>
        <div class="pay_cont">
            <div class="pay_choose pay_num">
                <h4>选择商品</h4>
                <ul class="clearfix">
                    <li data-index="83" data-diamond="60" data-money="6" class="active"><p class="diamond">
                        <span>60</span> 钻石</p>

                        <p class="money">¥
                            <sapn>6</sapn>
                            元
                        </p>
                    </li>
                    <li data-index="84" data-diamond="300" data-money="30"><p class="diamond"><span>300</span> 钻石</p>

                        <p class="money">¥
                            <sapn>30</sapn>
                            元
                        </p>
                    </li>
                    <li data-index="85" data-diamond="980" data-money="98"><p class="diamond"><span>980</span> 钻石</p>

                        <p class="money">¥
                            <sapn>98</sapn>
                            元
                        </p>
                        <p class="act"><span>送10钻</span></p></li>
                    <li data-index="86" data-diamond="2980" data-money="298"><p class="diamond"><span>2980</span> 钻石</p>

                        <p class="money">¥
                            <sapn>298</sapn>
                            元
                        </p>
                        <p class="act"><span>送90钻</span></p></li>
                    <li data-index="87" data-diamond="5880" data-money="588"><p class="diamond"><span>5880</span> 钻石</p>

                        <p class="money">¥
                            <sapn>588</sapn>
                            元
                        </p>
                        <p class="act"><span>送180钻</span></p></li>
                    <li data-index="123" data-diamond="29990" data-money="2999"><p class="diamond"><span>29990</span> 钻石
                    </p>

                        <p class="money">¥
                            <sapn>2999</sapn>
                            元
                        </p>
                        <p class="act"><span>送800钻</span></p></li>
                </ul>
            </div>
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
                <span id="topay">立即购买</span>
                <span class="distopay">立即购买</span>
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
<!--     <div class="footer_menu"> -->
<!--         <span><a href="/privacy/privacy.html">隐私政策</a>|</span> -->
<!--         <span><a href="/privacy/service.html">服务条款</a>|</span> -->
<!--         <span><a href="/privacy/contact.html">联系我们</a></span> -->
<!--     </div> -->
<!--     <p><a href="">北京蜜莱坞网络科技有限公司</a></p> -->

<!--     <div>Copyright 2015-2016 inke.cn All rights reserved. 湘ICP备16012585号-1 京网文[2016]0857-082号 增值业务许可证B2-20090206 -->
<!--         网络视听许可证1909352号 -->
<!--     </div> -->
</div>
    <script src="/js/index.js"></script>
</body>
</html>