window.onload = function () { // 页面一加载就执行这个函数
    this.searchInput();

    this.tabListClick(); // 调用函数

    findFourGenres();
    checkLogin();
    checkLoginAndSetFavorite();

    // 获取当前页面 url 路径上面的参数 gid
    var gid = getParameter('gid')

    // alert(gid); //弹出成功, 获取值正确
    findGameByGid(gid)

    findGameVideoByGid(gid)

    addGameComment(gid)

    load(1, 5, gid)

};

// 监听发表评论函数
function addGameComment(gid) {
    // 获取发表按钮元素
    var addBtn = document.getElementById('addBtn')
    addBtn.onclick = function () {
        // 获取评论内容
        var commentContent =  $('#commentContent').val()

        // alert(commentContent.length)
        // 发起ajax请求，添加评论
        $.post('/gameComment/addGameComment', {gid: gid, commentContent: commentContent}, function (res) {
            if (res.code == 200){
                // 清空内容
                document.getElementById('commentContent').value = ''
                listenCommentLength()
                // 添加成功， 重新刷新评论区
                load(1, 5, gid)
            }else {
                alert(res.msg)
            }
        })
    }
}
// 监听评论内容字数
function listenCommentLength() {
    // 获取评论内容
    var commentContent =  $('#commentContent').val()
    // console.log(commentContent)
    $('#commentContentLength').html(commentContent.length)
}

// 根据游戏id 查询游戏评论，并加载到页面上
function load(currentPage, pageSize, gid) {
    // 发起ajax请求，获取游戏评论列表，带分页信息
    $.get('/gameComment/findGameCommentByGameIdWithPage', {currentPage: currentPage, pageSize:pageSize,gid: gid}, function (res) {
        // console.log(res)
        var list = res.extend.pb.list
        var div = '<h3>最新评论</h3>'
        for (var i = 0; i < list.length; i++){
            div += '<div class="comment_show_middle">\n' +
                '                <a href="#" class="comment-avatar">\n' +
                '                    <img src="'+list[i].user.avatar+'" alt="">\n' +
                '                </a>\n' +
                '                <a href="#" class="username">'+list[i].user.username+'</a>\n' +
                '                <span>'+list[i].gmtCreate+'</span>\n' +
                '            </div>\n' +
                '            <p>'+list[i].commentContent+'</p>'
        }
        $('#commentShow').html(div)

        generatePageInfo(res, gid)
    })
}

// 分页条数据渲染函数
function generatePageInfo(res, gid) {
    // 分页条数据渲染
    $('#totalPage').html(res.extend.pb.totalPage); //总页数
    $('#totalCount').html(res.extend.pb.totalCount); //总记录数

    var lis = '';
    var firstPage = '<li><a href="javascript:void(0);" onclick="javascript:load(1, null, '+gid+')">首页</a></li>';

    // 计算上一页的页码
    var beforeNum = res.extend.pb.currentPage - 1;
    if (beforeNum <= 0) {
        beforeNum = 1;
    }
    var beforePage = '<li><a onclick="javascript:load('+beforeNum+', null, '+gid+')">上一页</a></li>';

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
            li = '<li onclick="javascript:load('+i+', null, '+gid+')"><a class="currPage">'+i+'</a></li>'
        }else {
            li = '<li onclick="javascript:load('+i+', null, '+gid+')"><a>'+i+'</a></li>'
        }
        lis += li;
    }

    // 计算下一页的页码
    var nextNum = res.extend.pb.currentPage + 1;
    if (nextNum > res.extend.pb.totalPage){
        nextNum = res.extend.pb.totalPage;
    }
    var nextPage = '<li><a onclick="javascript:load('+nextNum+', null, '+gid+')">下一页</a></li>';
    var lastPage = '<li><a onclick="javascript:load('+res.extend.pb.totalPage+', null, '+gid+')">末页</a></li>';

    lis += nextPage;
    lis += lastPage;

    $('#pageListInfo').html(lis);
}

// 根据 gid 查询游戏预览视频函数
function findGameVideoByGid(gid) {
    $.get('/gameVideo/findGameVideoByGid', {gid: gid}, function (res) {
        // console.log(res)
        if (res.code == 200){
            // 查询成功
            var gameVideoList = res.extend.gameVideoList
            // 将gameVideo里面的url赋值给 video 标签的 src属性
            // 即可实现视频预览播放
            var div = ''
            for (var i = 0; i < gameVideoList.length; i++){
                div  += '<div class="video_box">\n' +
                    '                    <div class="preview">\n' +
                    '                        <video src="'+gameVideoList[i].url+'" controls="controls" ></video>\n' +
                    '                    </div>\n' +
                    '                </div>'
            }
            $('#gameVideo').html(div)
        }
    })
}

// 根据游戏主键gid 查询游戏信息函数
function findGameByGid(gid) {
    // 发起 ajax请求，根据游戏主键gid查询游戏信息
    $.get('/game/findGameByGid', {gid: gid}, function (res) {
        // console.log(res);
        if (res.code == 200) {
            // 查询成功
            var game = res.extend.game;
            // console.log(game);
            $('#title').text(game.gname+'详情页')
            $('#gName').text(game.gname); // 游戏标题
            $('#cover').prop('src', game.cover); // 游戏封面
            $('#ggName').text(game.genres.ggName); // 游戏类型
            $('#developer').text(game.developer); // 开发商
            $('#publisher').text(game.publisher); //发行商
            $('#releaseTime').text(game.releaseTime); // 发行时间
            $('#gameScore').text(game.gameScore); // 游戏评分
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
                            }else {
                                console.log('response2='+response2)
                                alert(response2.msg)
                            }
                        })
                    }
                }
            })
        }
    })
}

// 检查用户登录状态，如果未登录，隐藏收藏按钮
function checkLoginAndSetFavorite() {
    // 发起 ajax请求，检查当前用户登录状态
    $.get('/user/checkLogin', {}, function (data) {
        if (data.code == 403) {
            // 用户未登录, 隐藏收藏按钮
            $('#favorite').hide()
        }
    })
}

// 游戏简介、攻略、视频预览模块绑定事件函数
function tabListClick() {
    // 获取游戏简介、攻略、视频预览模块里面的标签列表tab_list的li元素
    var lis = this.document.querySelector('.tab_list').querySelectorAll('li')
    // console.log(lis);
    // 获取游戏简介、攻略、视频预览模块里面的标签内容tab_con的div元素
    var items = this.document.querySelector('.tab_con').querySelectorAll('.item')
    // console.log(items);

    // 这里使用for循环给 li 绑定点击时间
    for (var i = 0; i < lis.length; i++) {
        // 给 li 添加自定义属性 index
        lis[i].setAttribute('index', i)

        lis[i].onclick = function () {
            // 先将所有li的 current样式清除掉
            // 使用for循环
            for (var j = 0; j < lis.length; j++) {
                lis[j].className = ''
            }
            // 再让当前的li样式赋值为 current
            // 哪个li调用this，this指向的就是哪个li
            this.className = 'current'

            // 接下来获取自定义属性index
            // console.log(this.getAttribute('index'));
            var index = this.getAttribute('index')
            // 先将其他的item隐藏起来
            for (var k = 0; k < items.length; k++) {
                items[k].className = 'item_hide'
            }
            // 再让希望展示的item显示
            items[index].className = 'item_show'
        }
    }
}

// 搜索框查询游戏函数
function findGameByName() {
    // jQuery 获取输入框元素的值
    var searchInput = $('#search_input').val();
    // alert(searchInput); // 弹出成功，继续往后面写
    location.href = 'game_explore.html?gameName=' + searchInput;
    // console.log('我还能继续写代码码？');// 结果显然不行，
    // 因为本函数是属于 index.html页面的，跳转到另外的页面后
    // 代码的生命周期就完结了，不能继续起作用
    // 所以我将获取出来的元素框的值作为参数，拼接到跳转的页面链接上
    // 将后面要执行的操作交给新的页面js函数去处理
}