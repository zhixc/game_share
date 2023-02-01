window.onload = function () {
    // 获取url路径上面的aid参数
    var aid = getParameter('aid')

    findArticleByAid(aid)

    // 监听表单提交事件
    $('#addForm').submit(function () {
        var title = $('#title').val();
        var content = $('#content').val();
        $.post('/article/updateArticleForFront', {aid: aid, title: title, content: content}, function (res) {
            if (res.code == 200){
                // 修改成功
                alert(res.msg)
                // 跳转到我的文章列表页面
                location.href = 'article_explore_my.html'
            }else {
                alert('失败！' + res.msg)
            }
        });
        return false;
    })
}
// 根据url地址栏是否有 aid参数来判断是否是文章编辑
// 如果有 aid参数，那么应该是文章编辑
function findArticleByAid(aid) {

    //发起ajax请求查询文章
    $.get('/article/findArticleByAIdForArticleUpdate', {aid: aid}, function (res) {
        console.log(res)
        var article = res.extend.article

        $('#title').val(article.title)
        // $('#title').html(article.title)
        $('#content').val(article.content)
    })
}