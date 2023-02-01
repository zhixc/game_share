window.onload = function () {
    addClickListenerToNav()
    checkLogin()
    findGameGenres()

    var aid = getParameter('aid')
    findArticleByAId(aid)
}

// 根据aid查询文章
function findArticleByAId(aid) {
    // 发起ajax请求，获取文章列表数据
    $.get('/article/findArticleByAIdForArticleDetail', {aid: aid}, function (res) {
        // console.log(res) //获取成功
        var article = res.extend.article

        $('#title').html(article.title)

        var div = '<h4>'+article.title+'</h4>\n' +
            '        <p>'+article.content+'</p>\n' +
            '        <div class="author">\n' +
            '            <span>编辑时间：'+article.gmtModified+'</span>\n' +
            '            <span>作者：'+article.user.username+'</span>\n' +
            '        </div>'

        $('#articleInfo').html(div)
    })
}