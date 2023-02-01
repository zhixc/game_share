window.onload = function () {
    //inputAndImgWithClass();

    initGenres();

    changeAvatar();

    // 游戏封面提交事件监听
    $('#updateBtn').click(function () {
        // 发起ajax请求，上传
        var formData = new FormData()
        formData.append('uploadFile', $('#uploadFile')[0].files[0])
        console.log(formData)
        $.ajax({
            type: 'post',
            url: '/game/cover/upload',
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            success: function (response) {
                if (response.code == 200){
                    // console.log('封面上传成功！');
                    // console.log(response.extend.cover);
                    $('#cover').val(response.extend.cover);
                }
                $('#gameCoverMsg').html(response.msg)
            }
        })
    })


    // 监听表单提交事件
    $('#addForm').submit(function () {
        // 用ajax请求发送到服务器
        // $.ajax({
        //     url: '/addGame',
        //     type: 'post',
        //     data: JSON.stringify($('#addForm').serialize()),
        //     contentType: 'application/json',
        //     success: function (response) {
        //         alert(response.msg)
        //         if (response.code == 200){
        //             window.location.href = 'game.html'
        //         }
        //     }
        // })
        $.post('/game/addGame', $('#addForm').serialize(), function (res) {
            if (res.code == 200){
                // 添加成功
                alert('添加游戏成功');
                // 跳转到用户列表展示页面
                location.href = 'game.html';
            }else {
                alert('添加失败！' + res.msg);
            }
        });
        return false;
    })
}

// 封面更换绑定事件
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

//初始化游戏类别函数
function initGenres() {
    // 发起ajax请求，查询有哪些游戏类别
    $.get('/findGenres', {currentPage: 1, pageSize: 10}, function (response) {
        console.log(response);
        var list;
        list = response.extend.pb.list
        var options = ''
        for (var i = 0; i < list.length; i++){
            options += '<option value="'+list[i].ggId+'">'+list[i].ggName+'</option>'
        }
        $('#id_genres').html(options)
    })

}

// 给表单元素绑定样式和操作
// function inputAndImgWithClass(){
//     // 获取登录页面的输入框元素
//     var inps = document.querySelectorAll('.input_class');
//
//     // 1.2给输入框绑定点击事件(对上面的改进)
//     for (var i = 0; i < inps.length - 1; i++) {
//         inps[i].onclick = function () {
//             this.className = 'input_clsss input_click'
//         };
//         inps[i].onblur = function () {
//             this.className = 'input_clsss'
//         };
//     }
// }