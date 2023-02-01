
// 声明一个全局变量，注意不能与其他全局变量重复
var myParam
function generatePageInfo(res, param) {
    // console.log(res)
    // console.log(param)
    myParam = param
    // 当前页
    var currentPage = res.extend.pb.currentPage
    // 总页数
    var totalPage = res.extend.pb.totalPage


    var span = '<span>共'+totalPage+'页</span>\n' +
        ' <span>当前第'+currentPage+'页</span>'
    // 如果总页数小于或等于1，那么就没有上一页和下一页之分
    if (totalPage <= 1){
        // 总页数 小于 1，直接返回
        $('#pageInfo').html(span)
        return
    }else {
        // 总页数 大于 1
        // 分情况
        // 有上一页的条件是：当前页 - 1 大于等于 1
        // 有下一页的条件是：当前页 + 1 小于等于 总页数

        // 上一页 lastPage
        var lastPage = currentPage - 1
        if (lastPage >= 1){
            // 有上一页
            span += '<button onclick="javascript:findGameByGGId('+lastPage+', myParam)">上一页</button>'
        }
        // 下一页 nextPage
        var nextPage = currentPage + 1
        if (nextPage <= totalPage){
            // 有下一页
            span += '<button onclick="javascript:findGameByGGId('+nextPage+', myParam)">下一页</button>'
        }
        span += '<input type="number" id="pageNumber">\n' +
            '<button onclick="javascript:turnToPage(myParam, '+totalPage+')">跳转</button>'
    }

    $('#pageInfo').html(span)
}

// 根据输入的页码跳转页面
function turnToPage(param, totalPage) {
    // 根据id获取分页区的输入框元素
    var pageNumberElement = document.getElementById('pageNumber')
    // 声明要前往的页数
    var toPageNumber = pageNumberElement.value

    // 要前往的页数必须大于等于1，且小于等于总页数
    if (toPageNumber >= 1 && toPageNumber <= totalPage){
        // 符合条件
        findGameByGGId(toPageNumber, param)
    }else {
        // 提示错误信息
        alert('错误，要前往的第'+ toPageNumber +'页，不存在！')
    }
}

// 由于游戏名称查询的结果分页条与游戏分类查询的结果分页条不一样，所以需要重写
var myGameName
function generatePageInfo2(res, gameName) {
    // console.log(res)
    // console.log(gameName)
    myGameName = gameName
    // 当前页
    var currentPage = res.extend.pb.currentPage
    // 总页数
    var totalPage = res.extend.pb.totalPage


    var span = '<span>共'+totalPage+'页</span>\n' +
        ' <span>当前第'+currentPage+'页</span>'
    // 如果总页数小于或等于1，那么就没有上一页和下一页之分
    if (totalPage <= 1){
        // 总页数 小于 1，直接返回
        $('#pageInfo').html(span)
        return
    }else {
        // 总页数 大于 1
        // 分情况
        // 有上一页的条件是：当前页 - 1 大于等于 1
        // 有下一页的条件是：当前页 + 1 小于等于 总页数

        // 上一页 lastPage
        var lastPage = currentPage - 1
        if (lastPage >= 1){
            // 有上一页
            span += '<button onclick="javascript:findGameByGameName('+lastPage+', myGameName)">上一页</button>'
        }
        // 下一页 nextPage
        var nextPage = currentPage + 1
        if (nextPage <= totalPage){
            // 有下一页
            span += '<button onclick="javascript:findGameByGameName('+nextPage+', myGameName)">下一页</button>'
        }
        span += '<input type="number" id="pageNumber">\n' +
            '<button onclick="javascript:turnToPage2(myGameName, '+totalPage+')">跳转</button>'
    }

    $('#pageInfo').html(span)
}

function turnToPage2(param, totalPage) {
    // 根据id获取分页区的输入框元素
    var pageNumberElement = document.getElementById('pageNumber')
    // 声明要前往的页数
    var toPageNumber = pageNumberElement.value

    // 要前往的页数必须大于等于1，且小于等于总页数
    if (toPageNumber >= 1 && toPageNumber <= totalPage){
        // 符合条件
        findGameByGameName(toPageNumber, param)
    }else {
        // 提示错误信息
        alert('错误，要前往的第'+ toPageNumber +'页，不存在！')
    }
}

// 同样的文章查询也需要重写，这里暂时想不到方法去改造得通用一些
var myTitle
function generatePageInfo3(res, title) {
    myTitle = title
    // 当前页
    var currentPage = res.extend.pb.currentPage
    // 总页数
    var totalPage = res.extend.pb.totalPage


    var span = '<span>共'+totalPage+'页</span>\n' +
        ' <span>当前第'+currentPage+'页</span>'
    // 如果总页数小于或等于1，那么就没有上一页和下一页之分
    if (totalPage <= 1){
        // 总页数 小于 1，直接返回
        $('#pageInfo').html(span)
        return
    }else {
        // 总页数 大于 1
        // 分情况
        // 有上一页的条件是：当前页 - 1 大于等于 1
        // 有下一页的条件是：当前页 + 1 小于等于 总页数

        // 上一页 lastPage
        var lastPage = currentPage - 1
        if (lastPage >= 1){
            // 有上一页
            span += '<button onclick="javascript:findArticleByTitle('+lastPage+', myTitle)">上一页</button>'
        }
        // 下一页 nextPage
        var nextPage = currentPage + 1
        if (nextPage <= totalPage){
            // 有下一页
            span += '<button onclick="javascript:findArticleByTitle('+nextPage+', myTitle)">下一页</button>'
        }
        span += '<input type="number" id="pageNumber">\n' +
            '<button onclick="javascript:turnToPage3(myTitle, '+totalPage+')">跳转</button>'
    }

    $('#pageInfo').html(span)
}
function turnToPage3(param, totalPage) {
    // 根据id获取分页区的输入框元素
    var pageNumberElement = document.getElementById('pageNumber')
    // 声明要前往的页数
    var toPageNumber = pageNumberElement.value

    // 要前往的页数必须大于等于1，且小于等于总页数
    if (toPageNumber >= 1 && toPageNumber <= totalPage){
        // 符合条件
        findArticleByTitle(toPageNumber, param)
    }else {
        // 提示错误信息
        alert('错误，要前往的第'+ toPageNumber +'页，不存在！')
    }
}

// 目前一个猜测，将这些方法里面的 findGameByGameName()， findArticleByTitle()，
// 这些不同的放到一个新的方法里面，新的方法接收一个参数method，根据参数method字符串来
// 判断执行什么样的操作。目前这是一个思路，还未测试过，不知道是否可行