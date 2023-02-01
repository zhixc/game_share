// 检查用户登录状态的函数
function checkLogin() {
    // 发起 ajax请求，检查当前用户登录状态
    $.get('/user/checkLogin', {}, function (data) {
        // console.log(data);
        var a = '';
        if (data.code == 403) {
            // 用户未登录
            a += '<a href="login_mobile.html">登录</a>\n' +
                ' <a href="register_mobile.html">注册</a>';
        }
        if (data.code == 200) {
            // console.log(data)

            // 用户已经登录
            a += '<a href="#"><img src="'+data.extend.user.avatar+'" alt=""></a>\n' +
                '            <a class="more-btn" id="moreBtn">更多</a>\n' +
                '            <!-- 登录成功后的小菜单 -->\n' +
                '            <ul class="more" id="moreBtnDetail">\n' +
                '                ' +
                '                <a href="game_favorite_mobile.html"><li>我的游戏收藏</li></a>\n' +
                '                <a onclick="logout()"><li>退出</li></a>\n' +
                '            </ul>'
        }
        $('#id_user_btn').html(a)

        if (data.code == 200){
            // 调用给头部更多按钮绑定事件函数
            addClickToMoreBtn()
        }
    })
}


// 退出函数
function logout() {
    // 发起ajax请求，退出
    $.get('/user/logout', {}, function (res) {
        alert(res.msg)
        location.href = 'index_mobile.html'; // 跳转到首页
    })
}

// 给头部更多按钮绑定点击事件
function addClickToMoreBtn() {
    var moreBtn = document.getElementById('moreBtn')

    var moreBtnDetail = document.getElementById('moreBtnDetail')

    var moreBtnFlag = false // 默认隐藏
    moreBtn.onclick = function () {
        if (!moreBtnFlag){
            // 隐藏，要显示
            moreBtnDetail.style.display = 'block'
            moreBtnFlag = true
        }else {
            // 显示，要隐藏
            moreBtnDetail.style.display = 'none'
            moreBtnFlag = false
        }
    }
}