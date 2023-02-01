window.onload = function () { // 页面一加载完就执行这个函数

    checkLogin(); // 调用检查用户登录状态的函数

    // 获取请求路径参数ggId, 将ggId作为参数发起ajax请求
    // 查询游戏列表数据
    var currentPage = getParameter('currentPage');

    load(currentPage)
};

// 加载收藏页面
function load(currentPage) {
    // console.log('当前页是'+currentPage)
    // 发起ajax请求, 查询当前用户收藏的游戏，分页查询
    $.get('/favorite/favoriteListWithPage', {currentPage: currentPage}, function (res) {
        if (res.code == 403){
            alert(res.msg)
        } else if (res.code == 200){
            // 查询成功
            // console.log(res)
            writeGameListAndPage(res)
        }
    })
}

// 取消收藏函数
function cancelFavorite(gid) {
    // console.log(gid);
    // 发起ajax请求
    $.get('/favorite/removeFavorite', {gid: gid}, function (res) {
        if (res.code == 403){
            alert(res.msg)
        } else if (res.code == 200){
            // 移除成功，重新加载数据
            load()
        }
    })
}

// 渲染游戏数据和分页条的方法
function writeGameListAndPage(res) {
    if (res.code == 200) {
        // 查询成功
        // 1。将游戏列表渲染出来
        var gameList = '';
        for (var i = 0; i < res.extend.pb.list.length; i++) {
            gameList += '<li class="clearfix">\n' +
                '                <div class="game-poster"><a href="game_detail.html?gid=' + res.extend.pb.list[i].game.gid + '"><img src="' + res.extend.pb.list[i].game.cover + '" alt=""></a>\n' +
                '                </div>\n' +
                '                <div class="game-info">\n' +
                '                <div class="game-title"><a href="game_detail.html?gid=' + res.extend.pb.list[i].game.gid + '">' + res.extend.pb.list[i].game.gname + '</a></div>\n' +
                '            <div class="game-review">' + res.extend.pb.list[i].game.gameReview + '</div>\n' +
                '                </div>\n  ' +
                '<div>\n' +'<button class="cacel_favorite" onclick="cancelFavorite('+res.extend.pb.list[i].game.gid+')">取消收藏</button>\n' + '</div>  '
                + '</li>'
        }
        $('#id_game_list').html(gameList);

        // 2。将分页条渲染出来
        $('#totalPage').html(res.extend.pb.totalPage); //总页数
        $('#totalCount').html(res.extend.pb.totalCount); //总记录数

        // 这里重新从当前路径中获取 ggId, currentPage, pageSize

        var lis = '';
        var firstPage = '<li><a href="javascript:void(0);" onclick="javascript:load(1)">首页</a></li>';

        // 计算上一页的页码
        var beforeNum = res.extend.pb.currentPage - 1;
        if (beforeNum <= 0) {
            beforeNum = 1;
        }
        var beforePage = '<li><a href="javascript:void(0);" onclick="javascript:load(' + beforeNum + ')">上一页</a></li>';

        lis += firstPage;
        lis += beforePage;
        /*
        展示10个页码：达到前5后4效果
        1.如果前面不够5个，后面补齐达到总的10个
        2.如果后面不够4个，前面补齐达到总的10个
        如果总的页码小于或等于10，直接显示10个页码
     */
        var start;   // 起始位置
        var end;     // 结束位置
        if (res.extend.pb.totalPage <= 10){
            // 总页数少于10
            start = 1;
            end = res.extend.pb.totalPage;
        }else {
            // 总的页数大于10
            start = res.extend.pb.currentPage - 5;
            end = res.extend.pb.currentPage + 4;
            // 1.如果前面不够5个，后面补齐达到总的10个
            if (start < 1){
                start = 1;
                end = start + 9;
            }
            // 2.如果后面不够4个，前面补齐达到总的10个
            if (end > res.extend.pb.totalPage){
                end = res.extend.pb.totalPage;
                start = end - 9;
            }
        }
        // for循环遍历
        for (var i = start; i <= end; i++){
            var li = '';
            // 判断当前页码是否等于 i
            if (res.extend.pb.currentPage == i){
                li = '<li onclick="javascript:load('+i+')"><a class="currPage">'+i+'</a></li>'
            }else {
                li = '<li onclick="javascript:load('+i+')"><a>'+i+'</a></li>'
            }
            lis += li;
        }

        // 计算下一页的页码
        var nextNum = res.extend.pb.currentPage + 1;
        if (nextNum > res.extend.pb.totalPage){
            nextNum = res.extend.pb.totalPage;
        }
        var nextPage = '<li onclick="javascript:load('+nextNum+')"><a href="javascript:void(0);">下一页</a></li>';
        var lastPage = '<li onclick="javascript:load('+res.extend.pb.totalPage+')" ><a href="javascript:void(0);">末页</a></li>';

        lis += nextPage;
        lis += lastPage;

        $('#pageListInfo').html(lis)
    }
}
