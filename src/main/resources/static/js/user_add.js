window.onload = function(){
    this.initUserAddHTML();

    // 初始化角色信息
    initRoleInfo();

    changeAvatar();

    // 头像提交事件监听
    $('#updateBtn').click(function () {
        // 发起ajax请求，上传头像
        var formData = new FormData()
        formData.append('uploadFile', $('#uploadFile')[0].files[0])
        console.log(formData)
        $.ajax({
            type: 'post',
            url: '/user/avatar/add',
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (response) {
                if (response.code == 200){
                    $('#avatar').val(response.extend.avatar);
                    $('#avatarMsg').html(response.msg)
                }
            }
        })
    })

    // 用户表单信息全部提交监听
    $('#addForm').submit(function () {
        // alert('ok');
        $.post('/user/addUser', $('#addForm').serialize(), function (res) {
           // console.log(res);
            if (res.code == 200){
                // 添加成功
                alert('添加用户成功');
                // 跳转到用户列表展示页面
                location.href = 'user.html';
            }else {
                alert('添加失败！' + res.msg);
            }
        });
        return false;
    });
};

// 头像更换绑定事件
function changeAvatar(){
    var uploadFileElement = document.getElementById('uploadFile');
    uploadFileElement.onchange = function () {
        var file = this.files[0];
        if (!!file) {
            var reader = new FileReader(); // 图片文件转换为base64
            reader.readAsDataURL(file);//用文件加载器加载文件
            reader.onload = function () { // 显示图片
                document.getElementById("file_img").src = this.result;//file_img是图片展示载体
            }
        }
    }
}

// 初始化角色信息函数
function initRoleInfo() {
    // 发送 ajax请求，查询角色表信息，并渲染到页面上
    $.get('/findAllRole', {}, function (res) {
        console.log(res);
        var roleList = res.extend.roleList;
        var options = '';
        for (var i = 0; i< roleList.length; i++){
            var option = '<option value="'+roleList[i].rid+'">'+roleList[i].roleName+'</option>';
            options += option;
        }
        $('#id_role').html(options);
    })
}

// 添加用户页面初始化函数
function initUserAddHTML(){
    // 单选按钮模拟选中
// var sex = document.getElementById('sex_boy')
// console.log(sex.getAttribute('value'));
// 页面一加载就选中男
    var s = '女'; // 假设这个是从后端拿过来的值，根据这个值，选中性别
// 根据 id 获取性别框元素
    var sex2 = document.getElementById('id_sex');
// 再从性别框元素里面，根据input标签，获取性别框里面所有input标签
// 然后会返回列表
    var lis = sex2.querySelectorAll('input');
// console.log(lis);
// for循环遍历列表，判断哪个值等于模拟的后端传过来的值
// 哪个就置为选中状态
    for(var i = 0; i < lis.length; i++){
        // console.log(lis[i].getAttribute('value'));
        if(lis[i].getAttribute('value') == s){
            // lis[i].checked = 'checked'
            lis[i].checked = 'true'// 两种写法结果一样
        }
    }

// 模拟下拉选项选中
    var roleId = 3;
// 根据 id 获取下拉选项的所有子元素，返回一个列表
// childNodes这个属性会获取包括空格
// var role_list = document.getElementById('id_role').childNodes
// 而children属性则不会
    var role_list = document.getElementById('id_role').children;
// console.log(role_list);
    for(var i = 0; i < role_list.length; i++){
        // console.log(role_list[i]);
        // console.log(role_list[i].getAttribute('value'));

        if(role_list[i].getAttribute('value') == roleId){
            // role_list[i].selected = 'selected'
            role_list[i].selected = 'true'// 两种写法效果一样
        }
    }
// getElementsByTagName('标签名')返回的也是元素列表，不会包括空格
// var role_list2 = document.getElementById('id_role').getElementsByTagName('option')
// console.log(role_list2);


    // 给显示或隐藏密码图标绑定点击事件
    var passwordBtn = document.getElementById('passwordBtn')
    var passwordFlag = false // 默认标记为隐藏
    var pwd = document.getElementById('pwd')
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