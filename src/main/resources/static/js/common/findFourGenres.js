
// 查询游戏分类前4条数据函数
function findFourGenres() {
    // 发起 ajax请求，查询游戏分类前四条数据
    $.get('/findGenres?pageSize=4', {}, function (res) {
        // console.log(res)
        if (res.code == 200) {
            // 查询成功
            // 渲染数据
            var lis = ''
            for (var i = 0; i < 4; i++) {
                lis += '<li><a href="game_explore.html?ggId='+res.extend.pb.list[i].ggId+'">' + res.extend.pb.list[i].ggName + '</a></li>'
            }
            lis += '<li><a href="game_explore.html">更多</a></li>'
            $('#id_genres').html(lis)
        }
    })
}