// 同样的文章查询也需要重写，这里暂时想不到方法去改造得通用一些
var myGid
function generatePageInfo(res, gid) {
    myGid = gid
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
            span += '<button onclick="javascript:loadGameCommentByGId('+lastPage+', myGid)">上一页</button>'
        }
        // 下一页 nextPage
        var nextPage = currentPage + 1
        if (nextPage <= totalPage){
            // 有下一页
            span += '<button onclick="javascript:loadGameCommentByGId('+nextPage+', myGid)">下一页</button>'
        }
        span += '<input type="number" id="pageNumber">\n' +
            '<button onclick="javascript:turnToPage(myGid, '+totalPage+')">跳转</button>'
    }

    $('#pageInfo').html(span)
}
// 跳转到第几页，并且携带参数param
function turnToPage(param, totalPage) {
    // 根据id获取分页区的输入框元素
    var pageNumberElement = document.getElementById('pageNumber')
    // 声明要前往的页数
    var toPageNumber = pageNumberElement.value

    // 要前往的页数必须大于等于1，且小于等于总页数
    if (toPageNumber >= 1 && toPageNumber <= totalPage){
        // 符合条件
        loadGameCommentByGId(toPageNumber, param)
    }else {
        // 提示错误信息
        alert('错误，要前往的第'+ toPageNumber +'页，不存在！')
    }
}