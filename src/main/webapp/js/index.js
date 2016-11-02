$(function() {
    var _uid = getCookie('INKEUSERINFO') ? JSON.parse(getCookie('INKEUSERINFO')).view_id : '',
        _userInfo = {},
        $hostInfo = $('.host_info'),
        $refill = $('.refill'),
        $changeUser = $('.changeuser'),
        $payNum = $('.pay_num ul'),
        $payMethod = $('.pay_method li'),
        $topay = $('#topay'),
        $distopay = $('.distopay'),
        $payok = $('#payok'),
        $num = $('.to_pay p em'),
        $errorTxt = $('#refill_panel .txt'),
        wx_html = '',
        zfb_html = '',
        yl_html = '',
        _id = '',
        _money = '',
        _payNumIndex = 0, // 当前选中金额索引
        _payMethod = 'wx';

    // 获取初始化信息
    getPayInitInfo().done(function(res) {
        // 用户信息
        if (res.status == 0 && res.data && res.data.user_info) {
            _userInfo = res.data.user_info;
        }

        // step1：映客号是否登录，step2：其他登录方式是否登录
        if (_userInfo.id) {
            renderUserHtml(_userInfo);
        } else {
            if (_uid) {
                userLogin(_uid).done(function(res) {
                    if (res.status == 0) {
                        getPayInitInfo().done(function(result) {
                            if (result.status == 0 && result.data && result.data.user_info) {
                                renderUserHtml(result.data.user_info);
                            }
                        });
                    } else {
                        console.log('错误提示：' + res.errormsg);
                    }
                }).fail(function() {
                    console.error('错误提示：映客号登录出错！');
                });
            }
        }

        // 充值金额
        if (res.status == 0 && res.data && res.data.payment_info) {
            _id = res.data.payment_info.alipay[0].id;
            _money = res.data.payment_info.alipay[0].money;

            // 支付宝(*********微信同支付宝*********)
            if (res.data.payment_info.alipay) {
                var zfbNum = res.data.payment_info.alipay;
                for (var i = 0; i < zfbNum.length; i++) {
                    wx_html = zfb_html += renderPayNumHtml(zfbNum[i]);
                }
            }

            // 银联
            if (res.data.payment_info.ebank) {
                var ylNum = res.data.payment_info.ebank;
                for (var i = 0; i < ylNum.length; i++) {
                    yl_html += renderPayNumHtml(ylNum[i]);
                }
            }
        } else {
            console.log('错误信息：' + res.errormsg);
        }

        $payNum.html(wx_html);
        $payNum.find('li:first').addClass('active');

        // 支付金额
        $num.html(_money);

        // 页面显示
        $('.pay_box').show();
        $('.loading_wrapper').hide();
    }).fail(function() {
        console.error('错误提示：获取充值初始化信息出错！');
    });

    // 选择充值金额
    $payNum.on('click', 'li', function() {
        _payNumIndex = $(this).index();
        _id = $(this).data('index');
        _money = $(this).data('money');

        $payNum.find('li').removeClass('active');
        $(this).addClass('active');

        $num.html(_money);
    });

    // 选择支付方式 
    $payMethod.on('click', function() {
        _payMethod = $(this).data('method');

        if (_payMethod == 'wx') {
            $payNum.html(wx_html);
        } else if (_payMethod == 'zfb') {
            $payNum.html(zfb_html);
        } else if (_payMethod == 'yl') {
            $payNum.html(yl_html);
        }

        _money = $payNum.find('li').eq(_payNumIndex).data('money');

        $payNum.find('li').removeClass('active');
        $payNum.find('li').eq(_payNumIndex).addClass('active');

        $payMethod.removeClass('active');
        $(this).addClass('active');

        $num.html(_money);
    });

    // 确认支付按钮
    $topay.on('click', function() {
        if (_payMethod == 'wx') {
            window.location.href = '/Index/index/wxpay?id=' + _id + '&m=' + _money;
        } else if (_payMethod == 'zfb') {
            window.location.href = '/Index/index/alipay?id=' + _id + '&m=' + _money;
        } else if (_payMethod == 'yl') {
            $.ajax({
                url: '/Index/EBank/pay',
                type: 'POST',
                data: JSON.stringify({
                    channel: 'ebank',
                    amount: _money * 100,
                    id: _id
                }),
                dataType: 'json',
                success: function(res) {
                    pingppPc.createPayment(JSON.stringify(res), function(result, err) {});
                },
                error: function() {}
            });
        }
    });

    // 切换映客号
    $changeUser.click(function() {
        changeUser();

        showRefillPanel();
        renderNoUserHtml();
    });

    // 映客号充值
    $refill.click(function() {
        showRefillPanel();
    });

    // 确认充值
    $payok.click(function() {
        var id = $('#refill_panel input').val();

        if (id) {
            userLogin(id).done(function(res) {
                if (res.status == 0) {
                    getPayInitInfo().done(function(res) {
                        // 用户信息
                        if (res.status == 0 && res.data && res.data.user_info) {
                            renderUserHtml(res.data.user_info);
                        }
                    });

                    $('.mask_layer').remove();
                    $('#refill_panel').hide();
                } else {
                    $errorTxt.html(res.errormsg);
                }
            }).fail(function() {
                console.error('错误提示：获取映客号登录出错！');
            });
        } else {
            $errorTxt.html('请输入映客号');
        }
    });

    // 登录
    $('.host_code').on('click', 'b', function() {
        renderLoginPanel();
    });

    function renderUserHtml(_data) {
        $refill.hide();
        $changeUser.show();

        $topay.show();
        $distopay.hide();

        $hostInfo.find('img').attr('src', _data.portrait);
        $hostInfo.find('.host_name span').html(_data.nick);
        $hostInfo.find('.sex').addClass(_data.gender == 0 ? 'sex_woman' : 'sex_man');
        $hostInfo.find('.level').css({
            'backgroundImage': 'url(' + _data.level_img + ')',
            'backgroundRepeat': 'no-repeat',
            'backgroundSize': '100% 100%'
        });
        $hostInfo.find('.host_code').html('映客号：<span>' + _data.id + '</span>');
    }

    function renderNoUserHtml() {
        $refill.show();
        $changeUser.hide();

        $topay.hide();
        $distopay.show();

        $hostInfo.find('img').attr('src', '/Public/pay/pc/images/default_user.png');
        $hostInfo.find('.host_name span').html('您还没有登录');
        $hostInfo.find('.sex').removeClass().addClass('sex');
        $hostInfo.find('.level').css({
            'background': 'none'
        });
        $hostInfo.find('.host_code').html('登录后即可充值<b>登录</b>');
    }

    function renderPayNumHtml(_data) {
        return '<li data-index="' + _data.id + '" data-diamond="' + _data.num + '" data-money="' + _data.money + '">' +
            '<p class="diamond"><span>' + _data.num + '</span> 钻石</p>' +
            '<p class="money">¥ <sapn>' + _data.money + '</sapn> 元</p>' +
            (_data.send_gold ? '<p class="act"><span>送' + _data.send_gold + '钻</span></p>' : '') +
            '</li>'
    }
});

/**
 * 获取充值初始化信息
 */

function getPayInitInfo() {
    return $.ajax({
        url: '/Index/index/getPaymentInfoNew',
        data: {},
        dataType: 'json'
    });
}

/**
 * 映客号登录
 * @param uid 用户id
 */
function userLogin(uid) {
    return $.ajax({
        url: '/user/index/login',
        type: 'POST',
        data: {
            uid: uid
        },
        dataType: 'json'
    });
}

// 切换账号登出
function changeUser() {
    return $.ajax({
        url: '/user/index/logout',
        type: 'POST',
        dataType: 'json'
    });
}

function showRefillPanel() {
    renderMaskLayer();
    $('#refill_panel').show();

    // 关闭映客号充值面板
    $('.close_refill_panel').one('click', function() {
        $('.mask_layer').remove();
        $('#refill_panel').hide();
    });
}