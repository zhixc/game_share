// 取消收藏函数
function cancelFavorite(gid) {
    // console.log(gid);
    // 发起ajax请求
    $.get('/favorite/removeFavorite', {gid: gid}, function (res) {
        if (res.code == 403){
            alert(res.msg)
        } else if (res.code == 200){
            // 移除成功，重新加载数据
            loadFavoriteGameList()
        }
    })
}