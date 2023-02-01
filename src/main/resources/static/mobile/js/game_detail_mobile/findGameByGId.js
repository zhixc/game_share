// 根据游戏id查询游戏
function findGameByGId(gid) {
    // 发起ajax请求，查询游戏
    $.get('/game/findGameByGid', {gid: gid}, function (res) {
        if (res.code == 200) {
            var game = res.extend.game
            $('#title').text(game.gname + '详情页')
            var gameElement = '<img  src="' + game.cover + '" alt="游戏封面" title="游戏封面">\n' +
                '            <div class="game-detail">\n' +
                '                <span >游戏名称：' + game.gname + '</span>\n' +
                '                <span >游戏类型：' + game.genres.ggName + '</span>\n' +
                '                <span>游戏评分：' + game.gameScore + '分</span>\n' +
                '                <button id="favorite">收藏</button>\n' +
                '            </div>'
            $('#game').html(gameElement)
            $('#gameReview').text(game.gameReview); // 游戏简介

            // 获取收藏按钮元素
            var favoriteBtn = document.getElementById('favorite')

            // 发起ajax请求，查询当前游戏是否已经收藏
            $.post('/favorite/isFavorite', {gid: gid}, function (response) {
                // console.log(response);
                if (response.code == 200) {
                    // 游戏已经收藏
                    favoriteBtn.className = 'hasFavorite'
                    favoriteBtn.innerHTML = '已收藏'
                } else {
                    // 游戏未收藏
                    // 给收藏按钮绑定一个自定义属性值
                    favoriteBtn.setAttribute('gid', gid);
                    // 给收藏按钮绑定点击事件
                    favoriteBtn.onclick = function () {
                        // console.log('收藏按钮点击')
                        // 发起ajax请求，收藏功能
                        $.post('/favorite/addFavorite', {gid: gid}, function (response2) {
                            if (response2.code == 200) {
                                // alert('收藏成功');
                                favoriteBtn.className = 'hasFavorite'
                                favoriteBtn.innerHTML = '已收藏'
                            } else {
                                console.log('response2=' + response2)
                                alert(response2.msg)
                            }
                        })
                    }
                }
            })
        }
    })
}