window.onload = function () {
    addClickListenerToNav()
    findGameGenres()
    initHTML()
}

// 页面初始化
function initHTML() {

    // 显示或隐藏密码
    var showPassword = document.getElementById('showPassword')
    var passwordElement = document.getElementById('password')

    showOrHidePassword(showPassword, passwordElement)

}

// 显示或隐藏密码函数
function showOrHidePassword(showPassword, passwordElement) {
    var passwordFlag = false // 默认关闭状态
    showPassword.onclick = function () {
        if (!passwordFlag) {
            // 处于关闭状态，打开
            passwordElement.type = 'text'
            passwordFlag = true
            showPassword.src = 'images/open.png'
        } else {
            // 处于打开状态，关闭
            passwordElement.type = 'password'
            passwordFlag = false
            showPassword.src = 'images/close.png'
        }
    }
}

// 图片验证码切换函数
function changeCheckCode(checkCodeImg) {
    checkCodeImg.src = "/getCheckCode?" + new Date().getTime();
}

// 登录函数
function login() {
    var username = $('#username').val();
    var password = $('#password').val();
    var checkCode = $('#checkCode').val();

    // 发起ajax请求
    $.post('/user/login', {username: username, password: password, checkCode: checkCode}, function (data) {
        // console.log(data);
        // 在这个回调函数内查看服务器相应的结果
        if (data.code == 200) {
            // 登录成功, 弹出消息提示，并跳转到首页
            // alert('登录成功！');
            location.href = 'index_mobile.html'
        } else {
            // 登录失败，弹出提示消息
            alert(data.msg)
            // 获取验证码图片元素，并修改src属性以切换验证码图片
            var img_checkCode = document.getElementById('id_img_checkCode')
            // console.log(img_checkCode) //取到元素后，继续往下面写
            changeCheckCode(img_checkCode);
        }
    })
}