window.onload = function () {
    initHTML()
}

// 页面初始化
function initHTML() {

    addClickListenerToNav()
    findGameGenres()
    // 显示或隐藏密码
    var showPassword = document.getElementById('showPassword')
    var passwordElement = document.getElementById('password')

    var showPassword2 = document.getElementById('showPassword2')
    var passwordElement2 = document.getElementById('password2')

    showOrHidePassword(showPassword, passwordElement)
    showOrHidePassword(showPassword2, passwordElement2)
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

// 注册函数
function register() {
    // console.log('OK')
    var username = $('#username').val();
    var password = $('#password').val();
    var password2 = $('#password2').val();
    var checkCode = $('#checkCode').val();

    // 发起ajax请求，提交表单信息
    $.post('/user/regist', {username: username, password: password, password2: password2, checkCode: checkCode}, function (data) {
        // console.log(data)
        if (data.code == 200){
            // 注册成功, 跳转到登录页面
            alert('注册成功, 即将前往登录界面!');
            location.href = 'login_mobile.html'
        }else {
            alert(data.msg)
            // 刷新验证码
            var id_img_checkCode = document.getElementById('id_img_checkCode')
            changeCheckCode(id_img_checkCode)
        }
    })
}