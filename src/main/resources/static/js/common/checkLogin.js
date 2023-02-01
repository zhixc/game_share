// 检查用户登录状态的函数
function checkLogin() {
    // 发起 ajax请求，检查当前用户登录状态
    $.get('/user/checkLogin', {}, function (data) {
        // console.log(data);
        var a = '';
        if (data.code == 403) {
            // 用户未登录
            a += '<a href="login.html">登录</a>\n' +
                ' <a href="register.html">注册</a>';
        }
        if (data.code == 200) {
            // console.log(data)
            var rid = data.extend.user.role.rid
            if (rid == 1 || rid == 2 || rid ==3){
                // 管理员级别的用户登录，显示跳转到后台管理的登录页面
                a += '<a href="login_system.html" >后台管理</a>'
            }
            // 用户已经登录
            a += '<a href="article_explore_my.html">我的文章</a>' +
                '<a href="article_add_for_front.html">发表文章</a>' + '<a href="game_favorite.html">我的游戏收藏</a>'+
                '<a href="avatar_front.html" class="avatar"><img src="'+data.extend.user.avatar+'" alt=""></a>' +
                '<a href="personal_info_update.html?uid='+data.extend.user.uid+'">' + data.extend.user.username + '</a>' +
                '<a href="javascript:logout();">退出</a>'
        }
        $('#id_user_btn').html(a)
    })
}

// 退出函数
function logout() {
    // 发起ajax请求，退出
    $.get('/user/logout', {}, function (res) {
        // if (res.code == 200){
        //     // 退出成功
        //     alert('退出成功！');
        //     location.href = 'index.html'; // 跳转到首页
        // }
        alert(res.msg)
        location.href = 'index.html'; // 跳转到首页
    })
}