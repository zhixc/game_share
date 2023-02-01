window.onload = function () {
    checkLogin();

    var aid = getParameter('aid')

    // 发起ajax请求，获取文章列表数据
    $.get('/article/findArticleByAIdForArticleDetail', {aid: aid}, function (res) {
        // console.log(res) //获取成功
        var article = res.extend.article

        $('#title').html(article.title)
        $('#content').html(article.content)
        $('#gmtModified').html(article.gmtModified)
        $('#author').html(article.user.username)

    })
}