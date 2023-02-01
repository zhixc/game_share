// 这个与公共的 loadGameGenres有些区别，
// 除了加载导航那里的游戏分类，还要加载
function loadGameGenres(res) {
    var list = res.extend.pb.list
    var lis = ''

    // 从 url 上面获取 ggId
    var ggId = getParameter('ggId')
    var lis2 = ''
    if (ggId == null || ggId == ''){
        // ggId为空，默认全部分类
        lis2 += '<li><a class="current-default" href="game_explore_mobile.html">全部</a></li>'
    }else {
        lis2 += '<li><a href="game_explore_mobile.html">全部</a></li>'
    }
    for (var i = 0; i < list.length; i++){
        lis += '<li><a href="game_explore_mobile.html?ggId='+list[i].ggId+'">'+list[i].ggName+'</a></li>'

        if (ggId == list[i].ggId){
            lis2 += '<li><a class="current-default" href="game_explore_mobile.html?ggId='+list[i].ggId+'">'+list[i].ggName+'</a></li>'
        }else {
            lis2 += '<li><a href="game_explore_mobile.html?ggId='+list[i].ggId+'">'+list[i].ggName+'</a></li>'
        }
    }
    lis += '<li><a href="game_explore_mobile.html">更多游戏</a></li>'
    $('#genresList').html(lis)

    $('#genresList2').html(lis2)
}