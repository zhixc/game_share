window.onload = function () {

    showOrHidePassword()

    // 获取url路径的uid参数
    var uid = getParameter('uid')

    findPersonInfoByUid(uid)

    updatePersonInfo()

}

// 监听表单提交事件
function updatePersonInfo() {
    $('#updateForm').submit(function () {
        // 发起ajax请求，将表单信息序列化提交到后台
        $.post('/updatePersonInfo', $('#updateForm').serialize(), function (res) {
            if (res.code == 200){
                // 修改成功，跳转到首页
                location.href = 'index.html'
            }else {
                // 修改失败，提示原因
                alert(res.msg)
            }
        })
        // 阻止表单提交刷新页面
        return false
    })
}

// 显示或隐藏密码函数
function showOrHidePassword() {
    // 获取点击的元素
    var passwordBtn = document.getElementById('passwordBtn')

    var passwordFlag = false
    var pwd = document.getElementById('pwd')
    // 给元素绑定点击事件
    passwordBtn.onclick = function () {
        if (!passwordFlag){
            // 隐藏 ==>  显示
            passwordBtn.src = 'img/open.png'
            pwd.type = 'text'
            passwordFlag = true
        }else {
            // 显示 ==>  隐藏
            passwordBtn.src = 'img/close.png'
            pwd.type = 'password'
            passwordFlag = false
        }
    }
}

// 根据uid查询要修改的用户信息
// 并回填到页面上
function findPersonInfoByUid(uid) {
    $.get('/findPersonInfoByUid', {uid: uid}, function (res) {
        // alert('OK')
        console.log(res)
        var user = res.extend.user
        $('#uid').val(user.uid);
        $('#username').val(user.username);
        $('#pwd').val(user.password);
        $('input[name=sex]').val([user.sex]);
        $('#age').val(user.age);
    })
}