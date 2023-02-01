window.onload = function () {

    checkLogin();

    var currentPage = getParameter('currentPage');
    load(currentPage);


    // 获取审核状态元素
    var isChecked = document.getElementById('isChecked')

    // 获取逻辑删除元素
    var isDeleted = document.getElementById('isDeleted');
    // console.log(isDeleted.value); // 取到值了
    // 获取搜索框元素
    var searchKeyword = document.getElementById('searchKeyword');

    // 获取搜索按钮元素
    var searchBtn = document.getElementById('searchBtn');
    // 给搜索按钮绑定点击事件
    searchBtn.onclick = function () {
        // console.log(searchKeyword.value); // 取值成功
        var title = searchKeyword.value;
        load(null, title, isDeleted.value, isChecked.value);
    }

};

// 获取登录状态
// 检查用户登录状态的函数
function checkLogin() {
    // 发起 ajax请求，检查当前用户登录状态
    $.get('/user/checkLogin', {}, function (data) {
        // console.log(data);
        var lis;
        if (data.code == 403) {
            // 用户未登录
            // 跳转到登录页面
            // window.location.href = 'login_system.html'
        }
        if (data.code == 200) {
            // console.log(data.extend.user)
            // 用户已经登录
            lis = '<li><img src="' + data.extend.user.avatar + '" alt="头像"></li>\n' +
                ' <li><span>' + data.extend.user.username + '</span></li>\n' + '<li>\n' +
                ' <a href="javascript:logout();" class="delete_btn">退出</a>\n' +
                ' </li>'
            // 根据用户权限渲染菜单按钮信息
            var menuList = ''
            if (data.extend.user.role.rid == 1){
                // 管理员
                menuList += '<li><a href="user.html">用户管理</a></li>\n' +
                    '            <li><a href="game.html">游戏管理</a></li>\n' +
                    '            <li><a href="article.html">测评文章管理</a></li>'+
                    '<li><a href="game_comment.html">游戏评论管理</a></li>'
            }else if (data.extend.user.role.rid == 3){
                // 游戏测评文章管理员
                menuList += '<li><a href="article.html">测评文章管理</a></li>'
            }
            // 将lis放到菜单里面
            $('#menuList').html(menuList)
        }
        $('#login_info').html(lis)
    })
}

// 退出函数
function logout() {
    // 发起ajax请求，退出
    $.get('/user/logout', {}, function (res) {
        // if (res.code == 200) {
        //     // 退出成功
        //     alert('退出成功！');
        //     location.href = 'login_system.html'; // 跳转到系统登录页面
        // }
        alert(res.msg)
        location.href = 'login_system.html'; // 跳转到系统登录页面
    })
}

// 解除逻辑删除函数
function unDeleted(aid, isDeleted, isChecked) {
    // console.log(gid);
    // 发送ajax请求，修改用户逻辑删除字段信息
    $.post('/article/unDeleted', {aid: aid}, function (res) {
        if (res.code == 200) {
            alert(res.msg);
            // 获取搜索框元素
            var searchKeyword = document.getElementById('searchKeyword');
            console.log(searchKeyword.value)
            load(1, searchKeyword.value, isDeleted, isChecked);
        }
    })
}

// 关闭或开启删除确认图层函数
function closeOrOpenDeleteConfirm(aid) {
    // 获取删除确认图层元素
    var deleteConfirm = document.getElementById('deleteConfirm');
    var deleteBg = document.getElementById('deleteBg');

    var cancelBtn = document.getElementById('cancel');
    var yesBtn = document.getElementById('yes');

    // 获取自定义属性 flag
    var flag = deleteConfirm.getAttribute("flag"); // '0'表示图层关闭，'1'表示图层打开

    if (flag == '0') {
        // 图层为关闭状态，现在打开
        deleteConfirm.style.display = 'block';
        deleteBg.style.display = 'block';
        // 设置自定义属性 flag = 1, 实际为字符串类型
        deleteConfirm.setAttribute("flag", 1);
    } else {
        // 图层为打开状态，现在关闭
        deleteConfirm.style.display = 'none';
        deleteBg.style.display = 'none';
        // 设置自定义属性 flag = 0, 在页面中会自动变成字符串 '0'
        deleteConfirm.setAttribute("flag", 0);
    }

    cancelBtn.onclick = function () {
        // 图层为打开状态，现在关闭
        deleteConfirm.style.display = 'none';
        deleteBg.style.display = 'none';
        // 设置自定义属性 flag = 0, 在页面中会自动变成字符串 '0'
        deleteConfirm.setAttribute("flag", 0);
    };

    yesBtn.onclick = function () {
        // console.log(gid); // 成功拿到 gid
        // var gids = []; // 声明一个空数组
        // 如果是批量删除按钮调用，这里的gid是一个数组，如果是删除按钮调用，这里的gid是一个值
        if (typeof aid === 'number') {
            // 单个删除
            // console.log('后台单个删除！');
            // 发送ajax请求，根据 uid删除用户
            $.post('/article/deleteArticleByAId', {aid: aid}, function (res) {
                // console.log(res);
                if (res.code == 200) {
                    // 删除成功
                    alert(res.msg);
                    // 关闭删除确认图层
                    // 图层为打开状态，现在关闭
                    deleteConfirm.style.display = 'none';
                    deleteBg.style.display = 'none';
                    // 设置自定义属性 flag = 0, 在页面中会自动变成字符串 '0'
                    deleteConfirm.setAttribute("flag", 0);
                    load(); // 重新加载页面数据
                } else {
                    alert(res.msg);
                }
            })
        } else if (aid.length <= 0) {
            // 没有选中要删除的行
            alert('错误！没有选中要删除的行！');
        } else {
            // 遍历数组aid，将其转为字符串，不知道什么原因，
            // 作为数组传递给后台时，后台接收到的东西为null
            // console.log('后台批量删除！');
            $.post('/article/deleteArticleByAIds', {aid: aid}, function (res) {
                if (res.code == 200) {
                    // 删除成功
                    alert(res.msg);
                    // 关闭删除确认图层
                    // 图层为打开状态，现在关闭
                    deleteConfirm.style.display = 'none';
                    deleteBg.style.display = 'none';
                    // 设置自定义属性 flag = 0, 在页面中会自动变成字符串 '0'
                    deleteConfirm.setAttribute("flag", 0);
                    load(); // 重新加载页面数据
                } else {
                    alert(res.msg);
                }
            })
        }
        // console.log(typeof uid === 'number'); //数字类型是true，数组的话是false
    }
}

// 删除游戏函数
function deleteArticleByAId(aid) {
    // console.log(uid);
    // console.log(uid.length == false);
    closeOrOpenDeleteConfirm(aid);
}

// 渲染游戏列表数据函数
function load(currentPage, title, isDeleted, isChecked) {
    // 去掉全选框的勾选
    var id_checkbox_all = document.getElementById('id_checkbox_all')
    id_checkbox_all.checked = false
    // 发起 ajax请求，分页查询用户列表信息
    // 然后加载到页面上
    $.get('/article/findArticleByTitleWithPage', {currentPage: currentPage, title: title, isDeleted: isDeleted, isChecked:isChecked}, function (res) {
        // console.log(res); //获取成功，继续写
        // 1.将用户列表数据渲染出来
        var list = res.extend.pb.list;
        var userTrs = '';
        for (var i = 0; i < list.length; i++) {
            var tr = '<tr>';

            var checked = '';
            // 审核状态
            if (list[i].isChecked == 1){
                // 审核通过
                checked = '通过'
            }else if (list[i].isChecked == 2){
                // 审核中
                checked = '审核中'
            }else {
                // 审核不通过
                checked = '未通过'
            }

            // 逻辑删除判断
            var logic = '';
            if (list[i].isDeleted == 0) {
                logic = '否';
                tr += '<td>\n' +
                    '                    <!-- checked属性为false，表示未选中 -->\n' +
                    '                    <input type="checkbox" class="tbody_checkbox" name="aid" value="'+list[i].aid+'">\n' +
                    '                </td>\n' +
                    '                <td>'+list[i].aid+'</td>\n' +
                    '                <td class="title">'+list[i].title+'</td>\n' +
                    '                <td><p class="content">'+list[i].content+'</p></td>\n' +
                    '                <td>'+logic+'</td>\n' +
                    '                <td>'+checked+'</td>\n' +
                    '                <td>'+list[i].gmtCreate+'</td>\n' +
                    '                <td>'+list[i].gmtModified+'</td>\n' +
                    '                <td>'+list[i].user.username+'</td>\n' +
                    '                <!-- 操作列, 修改或删除 -->\n' +
                    '                <td class="update_and_delete">\n' +
                    '                    <a href="article_update.html?aid='+list[i].aid+'&isDeleted='+list[i].isDeleted+'&isChecked='+list[i].isChecked+'" class="edit_btn">修改</a>\n' +
                    '                    <button class="delete_btn" onclick="deleteArticleByAId('+list[i].aid+')">删除</button>\n' +
                    '                </td>'
            } else {
                logic = '是';
                tr += '<td>\n' +
                    '                    <!-- checked属性为false，表示未选中 -->\n' +
                    '                    <input type="checkbox" class="tbody_checkbox" name="aid" value="'+list[i].aid+'">\n' +
                    '                </td>\n' +
                    '                <td>'+list[i].aid+'</td>\n' +
                    '                <td  class="title">'+list[i].title+'</td>\n' +
                    '                <td><p class="content">'+list[i].content+'</p></td>\n' +
                    '                <td>'+logic+'</td>\n' +
                    '                <td>'+checked+'</td>\n' +
                    '                <td>'+list[i].gmtCreate+'</td>\n' +
                    '                <td>'+list[i].gmtModified+'</td>\n' +
                    '                <td>'+list[i].user.username+'</td>\n' +
                    '                <!-- 操作列, 修改或删除 -->\n' +
                    '                <td class="update_and_delete">\n' +
                    '                    <a href="article_update.html?aid='+list[i].aid+'&isDeleted='+list[i].isDeleted+'&isChecked='+list[i].isChecked+'" class="edit_btn">修改</a>\n' +
                    '                    <button class="un_deleted" onclick="unDeleted(' + list[i].aid + ', '+list[i].isDeleted+', '+list[i].isChecked+')">解除删除</button>\n' +
                    '                </td>'
            }

            tr += '</tr>';
            userTrs += tr;
        }
        $('#id_tbody').html(userTrs);

        // 调用分页条渲染函数进行渲染
        generatePageInfo(res, title, isDeleted, isChecked);

        // 初始化页面信息，这个必须在整个页面数据渲染完毕后再运行，
        // 才能获取和绑定完所有事件，当勾选全选按钮后，
        // 取消选中表格主体中的某一项时，全选按钮才会取消勾选，
        // 也就是事件绑定成功, 如果这个函数先于表格主体部分数据渲染前运行
        // 那么绑定会失效
        initUserHTML(isDeleted, isChecked)

    })
}


// 游戏列表展示页面初始化函数
function initUserHTML(isDeleted, isChecked) {
    var menuBtn = document.querySelector('.menu_button');
    var menuContainer = document.querySelector('.menu_container');
// console.log(menuBtn);
    var flag = false; // 标记菜单按钮未打开的状态，false未打开，true打开
    menuBtn.onclick = function(){
        // alert('ok');
        if(!flag){
            // 未打开
            menuContainer.style.display = 'block';
            flag = true;
        }else{
            menuContainer.style.display = 'none';
            flag = false;
        }
    }


// 获取全选按钮元素
    var selectAll = document.getElementById('id_checkbox_all')
// console.log(selectAll) // 获取成功
// 获取表格主体的所有选择框元素
    var selectLis = document.getElementsByClassName('tbody_checkbox')

// 全选和取消全选按钮
// 1.获取全选按钮元素
    var selectAll = document.getElementById('id_checkbox_all');
// console.log(selectAll); // 获取成功
// 2.获取表格主体的所有选择框元素
    var selectLis = document.getElementsByClassName('tbody_checkbox');
// console.log(selectLis);
// 3.给全选按钮绑定事件
    selectAll.onclick = function () {
        // console.log(this.checked); // 被选中时，返回true，否则返回false
        for (var i = 0; i < selectLis.length; i++) {
            selectLis[i].checked = this.checked;
        }
    }
// 4.给所有表格主体部分的选择按钮绑定事件，用for循环来给每一个选择框绑定事件
    for (var i = 0; i < selectLis.length; i++) {
        selectLis[i].onclick = function () {
            // 每次点击都要循环检查表格主体部分所有选择框的选中状态是否全部被选中
            // 所以这里需要再来一次for循环遍历
            // 使用 flag来标记全选框状态
            var flag = true;
            for (var j = 0; j < selectLis.length; j++) {
                if (!selectLis[j].checked) {
                    // 若有一个未选中
                    // 则全选框应该赋值false
                    flag = false;
                    break; //既然有一个没有选中，那么全选框就可以标记为 false
                }
            }
            // 遍历完后，将 flag 值赋值给全选框
            selectAll.checked = flag;
        }
        // 给每个选择框都设置自定义属性与属性值
        // selectLis[i].setAttribute('data-index', i+1);
    }

    // 获取批量删除按钮元素
    var deleteById = document.getElementById('id_deleteById_btn')
    // 给批量删除按钮绑定点击事件函数
    deleteById.onclick = function () {
        // 通过自定义属性与值，选取特定元素, 同样需要用for循环
        var arr = [] // 这里要声明一个空数组
        for (var i = 0; i < selectLis.length; i++) {
            if (selectLis[i].checked) {
                // 当前选择框被选中了
                // 由于属性获取出来的值是属于字符串，所以需要转换成Int数字类型
                // console.log(selectLis[i].value)
                arr.push(parseInt(selectLis[i].value))
            }
        }
        // console.log(arr); //遍历完后看一下结果, 如果需要批量删除，这里的数组就可以作为参数提交给后台了
        closeOrOpenDeleteConfirm(arr) // 这里有点神奇，居然将数组也能传递过去
    }

    // console.log("逻辑删除是"+isDeleted)
    // 获取批量解除逻辑删除按钮元素
    var id_unDeleted_btn = document.getElementById('id_unDeleted_btn')
    if (isDeleted == 1){
        // 已经是逻辑删除，隐藏批量删除按钮
        deleteById.style.display = 'none'
        // 让批量解除逻辑删除按钮元素显示
        id_unDeleted_btn.className = 'unDelete_multiple_btn_show'
        // 给批量解除逻辑删除按钮元素绑定点击事件
        id_unDeleted_btn.onclick = function () {
            // console.log('批量解除逻辑删除按钮元素被点击了！')
            var arr = []; // 这里要声明一个空数组
            for (var i = 0; i < selectLis.length; i++) {
                if (selectLis[i].checked) {
                    // 当前选择框被选中了
                    // 由于属性获取出来的值是属于字符串，所以需要转换成Int数字类型
                    // console.log(selectLis[i].value);
                    arr.push(parseInt(selectLis[i].value));
                }
            }
            // 到这里就可以将数组发送给后台了
            // 发起ajax请求批量解除逻辑删除
            $.post('/article/unDeletedMultiple', {arr: arr}, function (res) {
                console.log(res)
                if (res.code == 200){
                    alert(res.msg)
                    // 批量解除删除成功，刷新当前页面
                    load(null, null, isDeleted, isChecked)
                }else {
                    // 异常情况
                    alert(res.msg)
                }
            })
        }
    }else {
        // 批量删除按钮元素显示
        deleteById.style.display = 'inline-block'
        // 批量解除删除按钮元素隐藏
        id_unDeleted_btn.className = 'unDelete_multiple_btn_hide'
    }
}

var myTitle = ''
// 分页条数据渲染函数
function generatePageInfo(res, title, isDeleted, isChecked) {
    myTitle = title
    // 分页条数据渲染
    $('#totalPage').html(res.extend.pb.totalPage); //总页数
    $('#totalCount').html(res.extend.pb.totalCount); //总记录数


    var lis = '';
    var firstPage = '<li><a href="javascript:void(0);" onclick="javascript:load(1, myTitle, '+isDeleted+', '+isChecked+')">首页</a></li>';

    // 计算上一页的页码
    var beforeNum = res.extend.pb.currentPage - 1;
    if (beforeNum <= 0) {
        beforeNum = 1;
    }
    var beforePage = '<li><a onclick="javascript:load('+beforeNum+', myTitle, '+isDeleted+', '+isChecked+')">上一页</a></li>';

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
            li = '<li onclick="javascript:load('+i+', myTitle, '+isDeleted+', '+isChecked+')"><a class="currPage">'+i+'</a></li>'
        }else {
            li = '<li onclick="javascript:load('+i+', myTitle, '+isDeleted+', '+isChecked+')"><a>'+i+'</a></li>'
        }
        lis += li;
    }

    // 计算下一页的页码
    var nextNum = res.extend.pb.currentPage + 1;
    if (nextNum > res.extend.pb.totalPage){
        nextNum = res.extend.pb.totalPage;
    }
    var nextPage = '<li><a onclick="javascript:load('+nextNum+', myTitle, '+isDeleted+', '+isChecked+')">下一页</a></li>';
    var lastPage = '<li><a onclick="javascript:load('+res.extend.pb.totalPage+', myTitle, '+isDeleted+', '+isChecked+')">末页</a></li>';

    lis += nextPage;
    lis += lastPage;

    $('#pageListInfo').html(lis);
}