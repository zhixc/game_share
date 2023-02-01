window.onload = function () { // 页面一加载完执行这个函数
    this.searchInput();

    browserRedirect()

    checkLogin();
    findFourGenres();

    findGameAndSetToLatest()
    findGameAndSetToACT()
    findGameAndSetRoleGameList()
    findGameAndSetToFight()
};

// 搜索框查询游戏函数
function findGameByName() {
    // jQuery获取输入框元素的值
    var searchInput = $('#search_input').val();
    // alert(searchInput); // 弹出成功，继续往后面写
    location.href = 'game_explore.html?gameName=' + searchInput;
    // console.log('我还能继续写代码码？');// 结果显然不行，
    // 因为本函数是属于 index.html页面的，跳转到另外的页面后
    // 代码的生命周期就完结了，不能继续起作用
    // 所以我将获取出来的元素框的值作为参数，拼接到跳转的页面链接上
    // 将后面要执行的操作交给新的页面js函数去处理
}

// 加载最新游戏模块列表，查询10条记录
function findGameAndSetToLatest() {
    findGameAndSetToBox(null, 10)
}

// 动作类精选游戏模块列表渲染函数, 查询8条记录即可
function findGameAndSetToACT() {
    // 发起 ajax请求，获取动作类前8条数据
    // $.get('/game/findGameByggId', {ggId: 1, pageSize: 8}, function (res) {
    //     // console.log(res);
    //     var lis = res.extend.pb.list;
    //     var gameList = '';
    //     var li = '';
    //     /*
    //
    //     <li><a href="game_detail.html"><img src="img/box_bd_img_01.jpeg" alt=""></a>
    //                             <h4>战神-斯巴达幽灵,战神-斯巴达幽灵,战神-斯巴达幽灵,战神-斯巴达幽灵,战神-斯巴达幽灵</h4>
    //                             <p>动作</p>
    //                         </li>
    //
    //      */
    //     for (var i = 0; i < lis.length; i++){
    //         li = '<li><a href="game_detail.html?gid='+lis[i].gid+'" class="box-bd-small-box"><img src="'+lis[i].cover+'" alt=""></a>\n' +
    //             '      <h4>'+lis[i].gname+'</h4>\n' +
    //             '      <p>'+lis[i].genres.ggName+'</p>\n' +
    //             '</li>';
    //         gameList += li;
    //     }
    //     $('#actGameList').html(gameList)
    //
    //     mouseOnBox()
    // })
    findGameAndSetToBox(1, 8)
}

// 加载角色扮演类精选游戏模块列表函数，查询10条记录
function findGameAndSetRoleGameList() {

    // $.get('/game/findGameByggId', {ggId: 2, pageSize: 10}, function (res) {
    //     // console.log(res);
    //     var lis = res.extend.pb.list;
    //     var gameList = '';
    //     var li = '';
    //
    //     for (var i = 0; i < lis.length; i++){
    //         li = '<li><a href="game_detail.html?gid='+lis[i].gid+'" class="box-bd-small-box"><img src="'+lis[i].cover+'" alt=""></a>\n' +
    //             '      <h4>'+lis[i].gname+'</h4>\n' +
    //             '      <p>'+lis[i].genres.ggName+'</p>\n' +
    //             '</li>';
    //         gameList += li;
    //     }
    //     $('#roleGameList').html(gameList)
    //
    //     mouseOnBox()
    // })
    findGameAndSetToBox(2, 10)
}
// 加载格斗类游戏，10条记录
function findGameAndSetToFight() {
    findGameAndSetToBox(3, 10)
}

// 改造上面的函数
function findGameAndSetToBox(ggId, pageSize) {
    $.get('/game/findGameByggId', {ggId: ggId, pageSize: pageSize}, function (res) {
        // console.log(res);
        var lis = res.extend.pb.list;
        var gameList = '';
        var li = '';

        for (var i = 0; i < lis.length; i++){
            li = '<li><a href="game_detail.html?gid='+lis[i].gid+'" class="box-bd-small-box"><img src="'+lis[i].cover+'" alt=""></a>\n' +
                '      <h4>'+lis[i].gname+'</h4>\n' +
                '      <p>'+lis[i].genres.ggName+'</p>\n' +
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
        }else if (ggId == 3){
            // 格斗类游戏
            $('#fightGameList').html(gameList)
        }
        // 调用小盒子绑定鼠标事件函数
        mouseOnBox()
    })
}

// 给box_bd里面的盒子a标签绑定鼠标悬浮和离开事件
function mouseOnBox() {
    var lis = document.getElementsByClassName('box-bd-small-box')
    // console.log(lis)

    for (var i = 0; i < lis.length; i++){
        // 给每一个小li绑定鼠标悬浮事件
        lis[i].onmouseover = function () {
            // console.log('鼠标悬浮事件OK!')
            this.parentNode.className = 'mouseOn'
        }
        // 给每一个小li绑定鼠标离开事件
        lis[i].onmouseleave = function () {
            this.parentNode.className = ''
        }
    }
}