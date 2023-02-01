window.onload = function () {
    addClickListenerToNav()

    addClickListenerToTabItem()

    findGameGenres()
    checkLogin()

    // 获取当前页面 url 路径上面的参数 gid
    var gid = getParameter('gid')
    findGameByGId(gid)
    findGameVideoByGid(gid)
    loadGameCommentByGId(1, gid)

    addGameComment(gid)
}

// 监听评论内容字数
function listenCommentLength() {
    // 获取评论内容
    var commentContent = $('#commentContent').val()
    // console.log(commentContent)
    $('#commentContentLength').html(commentContent.length)
}

// 监听发表评论函数
function addGameComment(gid) {
    // 获取发表按钮元素
    var addBtn = document.getElementById('addBtn')
    addBtn.onclick = function () {
        // 获取评论内容
        var commentContent = $('#commentContent').val()

        // alert(commentContent.length)
        // 发起ajax请求，添加评论
        $.post('/gameComment/addGameComment', {gid: gid, commentContent: commentContent}, function (res) {
            if (res.code == 200) {
                // 清空内容
                document.getElementById('commentContent').value = ''
                listenCommentLength()
                // 添加成功， 重新刷新评论区
                loadGameCommentByGId(1, gid)
            } else {
                alert(res.msg)
            }
        })
    }
}

// 加载评论区
function loadGameCommentByGId(currentPage, gid) {
    // 发起ajax请求，获取游戏评论列表，带分页信息
    $.get('/gameComment/findGameCommentByGameIdWithPage', {
        currentPage: currentPage,
        pageSize: 3,
        gid: gid
    }, function (res) {
        // console.log(res)
        var list = res.extend.pb.list
        var div = '<h3>最新评论</h3>'
        for (var i = 0; i < list.length; i++) {
            div += '<div class="comment-show-middle">\n' +
                '                <a href="#" class="comment-avatar">\n' +
                '                    <img src="' + list[i].user.avatar + '" alt="">\n' +
                '                </a>\n' +
                '                <a href="#" class="username">' + list[i].user.username + '</a>\n' +
                '                <span>' + list[i].gmtCreate + '</span>\n' +
                '   </div>\n' +
                '            <p>' + list[i].commentContent + '</p>'
        }
        $('#commentShow').html(div)

        // 构建分页条
        generatePageInfo(res, gid)
    })
}