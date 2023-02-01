window.onload = function () {
    changeAvatar();

    // 头像数据回显
    $.get('/user/checkLogin', {}, function (res) {
        if (res.code == 200){
            // 获取登录状态成功
            document.getElementById('file_img').src = res.extend.user.avatar
        }
    });

    // 上传头像
    $('#updateBtn').click(function () {
        // 发起ajax请求，上传头像
        var formData = new FormData();
        formData.append('uploadFile', $('#uploadFile')[0].files[0]);
        console.log(formData);
        $.ajax({
            type: 'post',
            url: '/user/avatar/upload',
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (response) {
                if (response.code == 200){
                    alert('头像修改成功！');
                    window.location.href = 'index.html'
                }else {
                    $('#errorMsg').html(response.msg)
                }
            }
        })
    })

    // $('#cancel').click(function () {
    //     window.location.href = 'user.html'
    // })
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