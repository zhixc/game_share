window.onload = function () {

    findGameCommentById()

    // 监听表单提交事件
    $('#addForm').submit(function () {
        $.post('/gameComment/updateGameComment', $('#addForm').serialize(), function (res) {
            if (res.code == 200){
                // 添加成功
                alert(res.msg)
                // 跳转到用户列表展示页面
                location.href = 'game_comment.html'
            }else {
                alert('修改失败！' + res.msg)
            }
        })
        return false
    })
}

// 查询游戏信息并回回显数据
function findGameCommentById() {
    // 从地址栏路径获取gameCommentId
    var gameCommentId = getParameter('gameCommentId')
    // 从地址栏获取 isDeleted
    var isDeleted = getParameter('isDeleted');
    // 发起ajax请求，查询游戏信息
    $.get('/gameComment/findGameCommentById', {gameCommentId: gameCommentId, isDeleted: isDeleted}, function (res) {
        console.log(res)
        var gameComment = res.extend.gameComment;
        $('#gameCommentId').val(gameComment.gameCommentId);
        $('#gid').val(gameComment.game.gid);
        $('#gName').val(gameComment.game.gname);
        $('#uid').val(gameComment.user.uid);
        $('#username').val(gameComment.user.username);
        $('#commentContent').val(gameComment.commentContent);
        $('input[name=isDeleted]').val([gameComment.isDeleted]);
    })
}
