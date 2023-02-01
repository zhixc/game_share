window.onload = function () {
    addClickListenerToNav()

    addClickToSearchBtn()

    checkLogin()

    findGameGenres()

    findLatestGame()
    findACTGame()
    findRoleGame()
}

// 查询最新前6条游戏数据
function findLatestGame() {
    findGameAndSetToBox(null, 6)
}
// 查询动作类前6条游戏数据
function findACTGame() {
    findGameAndSetToBox(1, 6)
}
// 查询角色扮演类前6条游戏数据
function findRoleGame() {
    findGameAndSetToBox(2, 6)
}
// 根据游戏类别查询游戏
function findGameAndSetToBox(ggId, pageSize) {
    $.get('/game/findGameByggId', {ggId: ggId, pageSize: pageSize}, function (res) {
        var lis = res.extend.pb.list;
        var gameList = '';
        var li = '';

        for (var i = 0; i < lis.length; i++){
            li = '<li>\n' +
                '  <a href="game_detail_mobile.html?gid='+lis[i].gid+'">\n' +
                '   <img src="'+lis[i].cover+'" alt="">\n' +
                '   <h4>'+lis[i].gname+'</h4>\n' +
                '  </a>\n' +
                '</li>';
            gameList += li;
        }
        if (ggId == 1){
            // 动作类
            $('#actGameList').html(gameList)
        } else if (ggId == 2){
            // 角色扮演类
            $('#roleGameList').html(gameList)
        }else if (ggId == null){
            // 最新游戏
            $('#latestGameList').html(gameList)
        }
    })
}
