// 发起ajax请求查询所有游戏分类
function findGameGenres() {
    $.get('/findGenres', {currentPage:1, pageSize: 10}, function (res) {
        // console.log(res)
        loadGameGenres(res)
    })
}