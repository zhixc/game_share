window.onload = function () {
    addClickListenerToNav()
    findGameGenres()
    checkLogin()

    loadFavoriteGameList(1)
}

// 加载收藏的游戏列表
function loadFavoriteGameList(currentPage) {
    // 发起ajax请求, 查询当前用户收藏的游戏，分页查询
    $.get('/favorite/favoriteListWithPage', {currentPage: currentPage, pageSize: 3}, function (res) {
        if (res.code == 403){
            alert(res.msg)
        } else if (res.code == 200){
            // 查询成功
            // console.log(res)
            var list = res.extend.pb.list
            var lis = ''
            for (var i = 0; i < list.length; i++){
                lis += '<li class="game-info">\n' +
                    '            <a href="game_detail_mobile.html?gid='+list[i].game.gid+'">\n' +
                    '                <img src="'+list[i].game.cover+'" alt="">\n' +
                    '            </a>\n' +
                    '            <div class="game-detail">\n' +
                    '                <a href="game_detail_mobile.html?gid='+list[i].game.gid+'">\n' +
                    '                    <h4>'+list[i].game.gname+'</h4>\n' +
                    '                </a>\n' +
                    '                <button onclick="cancelFavorite('+list[i].game.gid+')">取消收藏</button>\n' +
                    '            </div>\n' +
                    '        </li>'
            }

            $('#gameList').html(lis)

            // 构建分页条
            generatePageInfo(res)
        }
    })
}