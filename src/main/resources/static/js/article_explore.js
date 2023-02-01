window.onload = function () {
    checkLogin();

    var currentPage = getParameter('currentPage')

    var searchBtn = document.getElementById('search_btn');
    var title = null
    searchBtn.onclick = function (){
        // 获取输入框元素的值, 原生 js 获取元素方法
        var searchInput = document.getElementById('search_input');
        // console.log(searchInput.value); //获取成功后，继续往后面写
        title = searchInput.value;
        load(1, title)
    }

    load(currentPage, title)

}

// 获取文章列表信息
function load(currentPage, title) {
    // 发起ajax请求，获取文章列表数据
    $.get('/article/findArticleByTitleWithPage', {currentPage: currentPage, title: title}, function (res) {
        console.log(res) //获取成功

        var list = res.extend.pb.list
        var lis = ''

        for (var i = 0; i < list.length; i++){
            var li = '<li class="article">\n' +
                ' <h3 class="title"><a href="article_detail.html?aid='+list[i].aid+'">'+list[i].title+'</a></h3>\n' +
                ' <p class="content">'+list[i].content+'</p>\n' +
                ' <div class="bottom">\n' +
                ' <a href="article_detail.html?aid='+list[i].aid+'" class="read_btn">阅读文章</a>\n' +
                ' <p class="info">修改时间:<span>'+list[i].gmtModified+'</span>&nbsp;作者:<span>'+list[i].user.username+'</span></p>\n' +
                ' </div>\n' +
                '</li>'
            lis += li
        }

        $('#gameArticle').html(lis)

        // 构建分页条信息
        generatePageInfo(res, title)
    })
}

var myTitle = ''
// 分页条数据渲染函数
function generatePageInfo(res, title) {
    myTitle = title
    // 分页条数据渲染
    $('#totalPage').html(res.extend.pb.totalPage); //总页数
    $('#totalCount').html(res.extend.pb.totalCount); //总记录数


    var lis = '';
    var firstPage = '<li><a href="javascript:void(0);" onclick="javascript:load(1, myTitle)">首页</a></li>';

    // 计算上一页的页码
    var beforeNum = res.extend.pb.currentPage - 1;
    if (beforeNum <= 0) {
        beforeNum = 1;
    }
    var beforePage = '<li><a onclick="javascript:load('+beforeNum+', myTitle)">上一页</a></li>';

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
            li = '<li onclick="javascript:load('+i+', myTitle)"><a class="currPage">'+i+'</a></li>'
        }else {
            li = '<li onclick="javascript:load('+i+', myTitle)"><a>'+i+'</a></li>'
        }
        lis += li;
    }

    // 计算下一页的页码
    var nextNum = res.extend.pb.currentPage + 1;
    if (nextNum > res.extend.pb.totalPage){
        nextNum = res.extend.pb.totalPage;
    }
    var nextPage = '<li><a onclick="javascript:load('+nextNum+', myTitle)">下一页</a></li>';
    var lastPage = '<li><a onclick="javascript:load('+res.extend.pb.totalPage+', myTitle)">末页</a></li>';

    lis += nextPage;
    lis += lastPage;

    $('#pageListInfo').html(lis);
}
