// 根据 gid 查询游戏预览视频函数
function findGameVideoByGid(gid) {
    $.get('/gameVideo/findGameVideoByGid', {gid: gid}, function (res) {
        // console.log(res)
        if (res.code == 200) {
            // 查询成功
            var gameVideoList = res.extend.gameVideoList
            // 将gameVideo里面的url赋值给 video 标签的 src属性
            // 即可实现视频预览播放
            var div = ''
            for (var i = 0; i < gameVideoList.length; i++) {
                div += '<div class="preview">\n' +
                    '        <video src="' + gameVideoList[i].url + '" controls="controls" ></video>\n' +
                    '    </div>\n'
            }
            $('#gameVideo').html(div)
        }
    })
}