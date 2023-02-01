window.onload = function () {
    //initGenres()

    changeAvatar();

    findGameByGid();

    // 游戏封面提交事件监听
    $('#updateBtn').click(function () {
        // 发起ajax请求，上传头像
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
        $.post('/game/updateGame', $('#addForm').serialize(), function (res) {
            if (res.code == 200){
                // 添加成功
                alert('修改游戏成功');
                // 跳转到用户列表展示页面
                location.href = 'game.html';
            }else {
                alert('修改失败！' + res.msg);
            }
        });
        return false;
    })
}

// 查询游戏信息并回回显数据
function findGameByGid() {
    // 从地址栏路径获取gid
    var gid = getParameter('gid');
    // console.log(gid)
    // if (gid == null || gid == ''){
    //     console.log('gid为null或者空字符串')
    //     // gid为null，跳转到游戏管理页面
    //     // location.href = 'game.html'
    // }
    // 从地址栏获取 isDeleted
    var isDeleted = getParameter('isDeleted');
    // 发起ajax请求，查询游戏信息
    $.get('/game/findGameByGid', {gid: gid, isDeleted: isDeleted}, function (res) {
        var game = res.extend.game;
        $('#gid').val(game.gid);
        $('#gName').val(game.gname);
        $('#cover').val(game.cover);
        $('#developer').val(game.developer);
        $('#publisher').val(game.publisher);
        $('#gameScore').val(game.gameScore);
        $('#gameReview').val(game.gameReview);
        $('input[name=isDeleted]').val([game.isDeleted]);
        $('#releaseTime').val(game.releaseTime);

        // 渲染游戏分类
        // 发起ajax请求获取所有游戏分类
        $.get('/findGenres', {currentPage: 1, pageSize: 10}, function (response) {
            // console.log(response);
            // console.log(game.genresId)
            var list;
            list = response.extend.pb.list
            var options = ''
            for (var i = 0; i < list.length; i++){
                var option = '';
                if (list[i].ggId == game.genresId){
                    option  = '<option value="'+list[i].ggId+'" selected>'+list[i].ggName+'</option>';
                }else {
                    option = '<option value="'+list[i].ggId+'">'+list[i].ggName+'</option>';
                }
                options += option;
            }
            $('#id_genres').html(options)
        })
        // 游戏封面数据回显
        document.getElementById("file_img").src = game.cover;//file_img是图片展示载体
        // console.log(game.cover)
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
// function initGenres() {
//     // 发起ajax请求，查询有哪些游戏类别
//     $.get('/findGenres', {currentPage: 1, pageSize: 10}, function (response) {
//         // console.log(response);
//         var list;
//         list = response.extend.pb.list
//         var options = ''
//         for (var i = 0; i < list.length; i++){
//             options += '<option value="'+list[i].ggId+'">'+list[i].ggName+'</option>'
//         }
//         $('#id_genres').html(options)
//     })
//
// }