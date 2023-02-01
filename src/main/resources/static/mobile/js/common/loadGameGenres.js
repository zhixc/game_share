function loadGameGenres(res) {
    var list = res.extend.pb.list
    var lis = ''
    for (var i = 0; i < list.length; i++){
        lis += '<li><a href="game_explore_mobile.html?ggId='+list[i].ggId+'">'+list[i].ggName+'</a></li>'
    }
    lis += '<li><a href="game_explore_mobile.html">更多游戏</a></li>'
    $('#genresList').html(lis)
}