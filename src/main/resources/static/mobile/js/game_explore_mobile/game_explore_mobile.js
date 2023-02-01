window.onload = function () {
    // 给导航绑定监听事件
    addClickListenerToNav()

    addClickListenerToTabItem()

    checkLogin()

    // 分类列表监听事件
    addClickListenerToGenresList()

    // 查询所有游戏分类
    findGameGenres()

    // 搜索框绑定点击事件
    addClickToSearchBtn()

    // 从url路径上面获取 ggId
    var ggId = getParameter('ggId')

    var keywords = getParameter('keywords');
    // 由于中文可能存在乱码的问题，这里要处理一下
    var gameName = decodeURI(keywords);
    if (gameName.length > 0 && gameName !== null && gameName != 'null'){
        findGameByGameName(null, gameName);
    }else {
        findGameByGGId(null, ggId)
    }

}

// 根据游戏类别ggId查询游戏
function findGameByGGId(currentPage, ggId) {
    // 发起ajax请求，查询
    $.get('/game/findGameByggId', {ggId: ggId, currentPage: currentPage, pageSize:3}, function (res) {
        // 查询成功后调用函数将结果写到页面上
        loadGameList(res)
        generatePageInfo(res, ggId)
    })
}

//  根据游戏名称，模糊查询游戏
function findGameByGameName(currentPage, gameName) {
    // 发起ajax请求，查询
    $.get('/game/findGameByName', {currentPage: currentPage, gameName: gameName, pageSize:3}, function (res) {
        // 加载游戏列表
        loadGameList(res)
        // 加载分页条
        generatePageInfo2(res, gameName)
    })
}

// 根据查询结果res，加载游戏列表
function loadGameList(res) {
    var list = res.extend.pb.list
    var lis = ''
    for (var i = 0; i < list.length; i++){
        lis += '<li>\n' +
            '       <a href="game_detail_mobile.html?gid='+list[i].gid+'" class="game-info">\n' +
            '       <img src="'+list[i].cover+'" alt="">\n' +
            '       <div class="game-detail">\n' +
            '         <h4>'+list[i].gname+'</h4>\n' +
            '         <p>游戏简介：'+list[i].gameReview+'</p>\n' +
            '       </div>\n' +
            '       </a>\n' +
            '</li>'
    }
    $('#gameList').html(lis)
}

// 根据文章标题查询文章
function findArticleByTitle(currentPage, title) {
    $.get('/article/findArticleByTitleWithPage', {currentPage: currentPage, title: title, pageSize:3}, function (res) {
        // console.log(res)
        var list = res.extend.pb.list
        var lis = ''
        // 后面还有，继续写.....
        for (var i = 0; i < list.length; i++){
            lis += '<li>\n' +
                '       <a href="article_detail_mobile.html?aid='+list[i].aid+'" class="article-info">\n' +
                '       <h4>'+list[i].title+'</h4>\n' +
                '       <p>'+list[i].content+'</p>\n' +
                '       </a>\n' +
                '</li>'
        }
        // 将构建好的文章列表lis放到页面里
        $('#articleList').html(lis)
        // 构建分页条
        generatePageInfo3(res, title)
    })
}