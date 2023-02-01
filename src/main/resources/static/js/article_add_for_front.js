window.onload = function () {

    // 监听表单提交事件
    $('#addForm').submit(function () {
        var title = $('#title').val();
        var content = $('#content').val();
        $.post('/article/addArticleForFront', {title: title, content: content}, function (res) {
            if (res.code == 200){
                // 添加成功
                alert(res.msg)
                // 跳转到首页
                location.href = 'article_explore_my.html'
            }else {
                alert('添加失败！' + res.msg)
            }
        });
        return false;
    })
}
