window.onload = function () { // 页面一加载就执行这个函数
    this.inputAndImgWithClass();

    checkLogin()

    // 给登录按钮绑定点击事件, 完成登录功能
    $('#submit_btn').click(function () {
        // 获取表单元素值，将这些参数封装后提交到服务器
        var username = $('#username').val();
        var password = $('#id_password').val();
        var checkCode = $('#checkCode').val();
        // 先查看是否能获取到值，记得先清理浏览器缓存
        // 因为我发现有时候浏览器缓存会严重阻碍测试
        // console.log(username)
        // console.log(password)
        // console.log(checkCode)
        // 取值成功后，发起ajax请求，登录
        $.post('/user/login', {username:username, password: password, checkCode: checkCode},
            function (data) {
            // console.log(data);
            // 在这个回调函数内查看服务器相应的结果
            if (data.code == 200){
                // 登录成功, 弹出消息提示，并跳转到首页
                // alert('登录成功！');
                if (data.extend.roleId == 1){
                    // 管理员
                    location.href = 'user.html'
                }else if (data.extend.roleId == 2){
                    // 游戏管理员
                    location.href = 'game.html'
                }else if (data.extend.roleId == 3){
                    // 游戏测评文章管理员
                    location.href = 'article.html'
                }else {
                    // 游客
                    location.href = 'index.html'
                }
            } else {
                // 冲置样式与内容
                removeMsg();
                // 登录失败
                // 获取验证码图片元素，并修改src属性以切换验证码图片
                var img_checkCode = document.getElementById('id_img_checkCode')
                // console.log(img_checkCode) //取到元素后，继续往下面写
                // img_checkCode.src = "/getCheckCode?" + new Date().getTime()
                changeCheckCode(img_checkCode);
                if (data.code == 4011){
                    // 验证码错误，这里我规定 4011状态码为验证码错误
                    $('#msg_checkCode').html(data.msg);
                    $('#msg_checkCode').addClass('error_msg')
                }else if (data.code == 4012 || data.code == 4018){
                    // 密码为空或密码错误
                    $('#msg_password').html(data.msg);
                    $('#msg_password').addClass('error_msg')
                }else if (data.code == 4015 || data.code == 4017){
                    // 用户名为空 或 用户名不存在
                    $('#msg_username').html(data.msg);
                    $('#msg_username').addClass('error_msg')
                }else {
                    alert(data.msg)
                }
            }
        })
    })
};

// 检查用户是否登录
function checkLogin() {
    // 发起 ajax请求，检查当前用户登录状态
    $.get('/user/checkLogin', {}, function (data) {
        if (data.code == 200) {
            if (data.extend.user.role.rid == 1){
                // 管理员
                location.href = 'user.html'
            }else if (data.extend.user.role.rid == 2){
                // 游戏管理员
                location.href = 'game.html'
            }else if (data.extend.user.role.rid == 3){
                // 游戏测评文章管理员
                location.href = 'article.html'
            }else {
                // 游客
                location.href = 'index.html'
            }
        }
    })
}

// 移除消息样式与内容
function removeMsg() {
    // 移除错误消息样式
    $('#msg_checkCode').removeClass('error_msg');
    $('#msg_password').removeClass('error_msg');
    $('#msg_username').removeClass('error_msg');
    // 移除内容
    $('#msg_checkCode').html('');
    $('#msg_password').html('');
    $('#msg_username').html('');
}

// 图片验证码切换函数
function changeCheckCode(checkCodeImg) {
    checkCodeImg.src = "/getCheckCode?" + new Date().getTime();
}
// 给表单元素绑定样式和操作
function inputAndImgWithClass(){
    // 获取登录页面的输入框元素
    var inps = this.document.querySelectorAll('input');

    // 1.1给输入框绑定点击事件
    // inps[0].onclick = function(){
    //     this.className = 'input_clsss input_click'
    // }
    // inps[0].onblur = function(){
    //     this.className = 'input_clsss'
    // }
    // inps[1].onclick = function(){
    //     this.className = 'input_clsss input_click'
    // }
    // inps[1].onblur = function(){
    //     this.className = 'input_clsss'
    // }
    // inps[2].onclick = function(){
    //     this.className = 'check_code input_clsss input_click'
    // }
    // inps[2].onblur = function(){
    //     this.className = 'check_code input_clsss'
    // }

    // 1.2给输入框绑定点击事件(对上面的改进)
    for (var i = 0; i < inps.length - 1; i++) {
        inps[i].onclick = function () {
            this.className = 'input_clsss input_click'
        };
        inps[i].onblur = function () {
            this.className = 'input_clsss'
        };
    }
    inps[inps.length - 2].onclick = function () {
        this.className = 'check_code input_clsss input_click'
    };
    inps[inps.length - 2].onblur = function () {
        this.className = 'check_code input_clsss'
    };

    // 获取显示或隐藏密码元素
    var img1 = this.document.getElementById('password-btn')
    //console.log(img1);
    var flag = false; //用来标记按钮切换状态
    // 绑定密码监听按钮事件
    img1.onclick = function () {
        // 切换图片
        if (!flag) {
            // this指向的是函数的调用者，即 img 对象
            this.src = 'img/open.png';
            inps[1].type = 'text';
            flag = true
        } else {
            this.src = 'img/close.png';
            inps[1].type = 'password';
            flag = false
        }
    }
}