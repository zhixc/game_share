window.onload = function () { // 页面一加载完就执行这个函数
    this.searchInput(); // 调用函数给搜索输入框绑定样式

    checkLogin(); // 调用检查用户登录状态的函数
    findFourGenres(); // 调用查询前四条游戏分类的函数

    findAllGenres(); // 调用查询所有游戏分类的函数

    // 获取请求路径参数ggId, 将ggId作为参数发起ajax请求
    // 查询游戏列表数据
    var ggId = getParameter('ggId');
    var currentPage = getParameter('currentPage');

   // 改进如下
    var gameNameParam = getParameter('gameName');
    // 由于中文可能存在乱码的问题，这里要处理一下
    var gameName = decodeURI(gameNameParam);
    // console.log(gameName); //这里发现 gameName有可能为空字符串或null
    // alert(gameName);
    // js中字符串判断为空有：'', 'null', null
    // 好家伙，居然还有'null'这中情况，这是我经过浏览器控制台debug看到的结果
    if (gameName.length > 0 && gameName !== null && gameName != 'null'){
        findGameByName(null, gameName);//经过浏览器控制台debug，发现没有发出去，nice
    }else {
        load(ggId, currentPage);
    }

    // 给搜索按钮绑定监听事件
    var searchBtn = document.getElementById('search_btn');
    searchBtn.onclick = function (){
        // 获取输入框元素的值, 原生 js 获取元素方法
        var searchInput = document.getElementById('search_input');
        // console.log(searchInput.value); //获取成功后，继续往后面写
        var gameName = searchInput.value;
        // 调用函数，查询游戏
        findGameByName(null, gameName);
    }

};


// 渲染游戏数据和分页条的方法，这个函数是为了游戏分类查询然后渲染分页条
function writeGameList(res) {
    if (res.code == 200) {
        // 查询成功
        // 1。将游戏列表渲染出来
        var gameList = '';
        for (var i = 0; i < res.extend.pb.list.length; i++) {
            gameList += '<li class="clearfix">\n' +
                '                <div class="game-poster"><a href="game_detail.html?gid=' + res.extend.pb.list[i].gid + '"><img src="' + res.extend.pb.list[i].cover + '" alt=""></a>\n' +
                '                </div>\n' +
                '                <div class="game-info">\n' +
                '                <div class="game-title"><a href="game_detail.html?gid=' + res.extend.pb.list[i].gid + '">' + res.extend.pb.list[i].gname + '</a></div>\n' +
                '            <div class="game-review">' + res.extend.pb.list[i].gameReview + '</div>\n' +
                '                </div>\n' +
                '                </li>'
        }
        $('#id_game_list').html(gameList);

    }
}
// 加载分页条，游戏分类查询后加载分页条
function writePageListInfoForGGID(res) {
    // 将分页条渲染出来
    $('#totalPage').html(res.extend.pb.totalPage); //总页数
    $('#totalCount').html(res.extend.pb.totalCount); //总记录数

    // 这里重新从当前路径中获取 ggId, currentPage, pageSize
    var ggId2 = getParameter('ggId');
    var lis = '';
    var firstPage = '<li><a href="javascript:void(0);" onclick="javascript:load(' + ggId2 + ', 1)">首页</a></li>';

    // 计算上一页的页码
    var beforeNum = res.extend.pb.currentPage - 1;
    if (beforeNum <= 0) {
        beforeNum = 1;
    }
    var beforePage = '<li><a href="javascript:void(0);" onclick="javascript:load(' + ggId2 + ', ' + beforeNum + ')">上一页</a></li>';

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
        // 总页数少于5
        start = 1;
        end = res.extend.pb.totalPage;
    }else {
        // 总的页数大于5
        start = res.extend.pb.currentPage - 5;
        end = res.extend.pb.currentPage + 4;
        // 1.如果前面不够5个，后面补齐达到总的5个
        if (start < 1){
            start = 1;
            end = start + 9;
        }
        // 2.如果后面不够4个，前面补齐达到总的5个
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
            li = '<li onclick="javascript:load('+ggId2+', '+i+')"><a class="currPage">'+i+'</a></li>'
        }else {
            li = '<li onclick="javascript:load('+ggId2+', '+i+')"><a>'+i+'</a></li>'
        }
        lis += li;
    }

    // 计算下一页的页码
    var nextNum = res.extend.pb.currentPage + 1;
    if (nextNum > res.extend.pb.totalPage){
        nextNum = res.extend.pb.totalPage;
    }
    var nextPage = '<li onclick="javascript:load('+ggId2+', '+nextNum+')"><a href="javascript:void(0);">下一页</a></li>';
    var lastPage = '<li onclick="javascript:load('+ggId2+', '+res.extend.pb.totalPage+')" ><a href="javascript:void(0);">末页</a></li>';

    lis += nextPage;
    lis += lastPage;


    // console.log(ggId2);
    $('#pageListInfo').html(lis)
}

var myGameName = ''
// 加载分页条，根据游戏名称查询后加载分页条
function writePageListInfoForGName(res, gameName) {
    myGameName = gameName
    // 将分页条渲染出来
    $('#totalPage').html(res.extend.pb.totalPage); //总页数
    $('#totalCount').html(res.extend.pb.totalCount); //总记录数

    var lis = '';
    var firstPage = '<li><a href="javascript:void(0);" onclick="javascript:findGameByName(1, myGameName)">首页</a></li>';

    // 计算上一页的页码
    var beforeNum = res.extend.pb.currentPage - 1;
    if (beforeNum <= 0) {
        beforeNum = 1;
    }
    var beforePage = '<li><a href="javascript:void(0);" onclick="javascript:findGameByName(' + beforeNum + ', myGameName)">上一页</a></li>';

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
        // 总页数少于5
        start = 1;
        end = res.extend.pb.totalPage;
    }else {
        // 总的页数大于5
        start = res.extend.pb.currentPage - 5;
        end = res.extend.pb.currentPage + 4;
        // 1.如果前面不够5个，后面补齐达到总的5个
        if (start < 1){
            start = 1;
            end = start + 9;
        }
        // 2.如果后面不够4个，前面补齐达到总的5个
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
            li = '<li onclick="javascript:findGameByName('+i+', myGameName)"><a class="currPage">'+i+'</a></li>'
        }else {
            li = '<li onclick="javascript:findGameByName('+i+',myGameName)"><a>'+i+'</a></li>'
        }
        lis += li;
    }

    // 计算下一页的页码
    var nextNum = res.extend.pb.currentPage + 1;
    if (nextNum > res.extend.pb.totalPage){
        nextNum = res.extend.pb.totalPage;
    }
    var nextPage = '<li onclick="javascript:findGameByName('+nextNum+', myGameName)"><a href="javascript:void(0);">下一页</a></li>';
    var lastPage = '<li onclick="javascript:findGameByName('+res.extend.pb.totalPage+', myGameName)" ><a href="javascript:void(0);">末页</a></li>';

    lis += nextPage;
    lis += lastPage;


    // console.log(ggId2);
    $('#pageListInfo').html(lis)
}

// 根据 ggId查询游戏数据, 带上当前页
function load(ggId, currentPage) {
    // 根据游戏类型 ggId查询游戏信息
    // alert(ggId)
    $.get('/game/findGameByggId', {ggId: ggId, currentPage: currentPage}, function (res) {
        writeGameList(res);
        // 加载分页条
        writePageListInfoForGGID(res)
    })
}

// 根据游戏名称查询游戏数据函数
function findGameByName(currentPage, gameName) {
    // 发起ajax请求，将搜索参数传递给后台
    $.get('/game/findGameByName', {currentPage: currentPage, gameName: gameName}, function (res) {
        writeGameList(res);
        writePageListInfoForGName(res, gameName)
    })
}


// 查询所有游戏分类数据函数
function findAllGenres() {
    // 发起ajax请求，获取所有游戏分类，并渲染到页面中
    $.get('/findGenres?pageSize=10', {}, function (res) {
        // console.log(res);
        if (res.code == 200) {
            // 查询成功
            var lis = '<li><a href="game_explore.html">全部类型：</a></li>';
            for (var i = 0; i < res.extend.pb.list.length; i++) {
                lis += '<li><a href="game_explore.html?ggId=' + res.extend.pb.list[i].ggId + '">' + res.extend.pb.list[i].ggName + '</a></li>';
            }
            $('#id_game_explore_genres').html(lis);
        }
    })
}


// 监听回车键Enter，执行搜索功能
// function pressEnterToSearch() {
//     var searchInput = document.getElementById('search_input');
//     searchInput.onkeydown
// }
