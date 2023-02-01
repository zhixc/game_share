window.onload = function(){
    this.inputAndImgWithClass();

    // 用jquery内置的函数，给表单绑定提交事件
    $('#submit_btn').click(function () {
        // 获取表单元素值
        var username = $('#username').val();
        var passoword = $('#id_password').val();
        var passoword2 = $('#id_password2').val();
        var checkCode = $('#checkCode').val();
        // console.log(username) // 测试成功
        // console.log(passoword);
        // 发送ajax请求，提交表单数据
        $.get('user/regist', {username: username, password: passoword, password2: passoword2, checkCode: checkCode},
            function (data) {
                // console.log(data)
                if (data.code == 200){
                    // 注册成功, 跳转到登录页面
                    alert('注册成功, 前往登录界面!');
                    location.href = 'login.html'
                }else {
                    // 重置样式和内容
                    removeMsg();
                    // 注册失败
                    // 获取验证码图片元素，并修改src属性以切换验证码图片
                    var img_checkCode = document.getElementById('id_img_checkCode');
                    // console.log(img_checkCode) //取到元素后，继续往下面写
                    // img_checkCode.src = "/getCheckCode?" + new Date().getTime()
                    changeCheckCode(img_checkCode);
                    if (data.code == 4011){
                        // 验证码错误，这里我规定 4011状态码为验证码错误
                        $('#msg_checkCode').html(data.msg);
                        $('#msg_checkCode').addClass('error_msg')
                    }else if (data.code == 4012){
                        // 密码为空
                        $('#msg_password').html(data.msg);
                        $('#msg_password').addClass('error_msg')
                    }else if (data.code == 4013 || data.code == 4014){
                        // 确认密码为空, 或确认密码与密码不一致
                        $('#msg_password2').html(data.msg);
                        $('#msg_password2').addClass('error_msg')
                    }else if (data.code == 4015 || data.code == 4016){
                        // 用户名为空 或 用户名已存在
                        $('#msg_username').html(data.msg);
                        $('#msg_username').addClass('error_msg')
                    }
                }
            })

    })
};

// 移除消息样式与内容的函数
function removeMsg() {
    // 移除错误消息样式
    $('#msg_checkCode').removeClass('error_msg');
    $('#msg_password').removeClass('error_msg');
    $('#msg_password2').removeClass('error_msg');
    $('#msg_username').removeClass('error_msg');
    // 移除内容
    $('#msg_checkCode').html('');
    $('#msg_password').html('');
    $('#msg_password2').html('');
    $('#msg_username').html('');
}

// 图片验证码切换函数
function changeCheckCode(checkCodeImg) {
    checkCodeImg.src = "/getCheckCode?" + new Date().getTime();
}

// 给表单元素绑定样式和操作(注意，这个是注册页面特有的函数，
// 虽然名称与注册页面的一样，但是内容不一样，不能作为公用的函数)
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
        }
    }
    inps[inps.length - 2].onclick = function () {
        this.className = 'check_code input_clsss input_click'
    };
    inps[inps.length - 2].onblur = function () {
        this.className = 'check_code input_clsss'
    };

    // 获取显示或隐藏密码元素
    var img1 = this.document.getElementById('password-btn');
    var img2 = this.document.getElementById('password2-btn');
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
    };
    // 绑定确认密码监听按钮事件
    img2.onclick = function () {
        // 切换图片
        if (!flag) {
            // this指向的是函数的调用者，即 img 对象
            this.src = 'img/open.png';
            inps[2].type = 'text';
            flag = true
        } else {
            this.src = 'img/close.png';
            inps[2].type = 'password';
            flag = false
        }
    }
}