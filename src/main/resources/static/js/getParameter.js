//根据传递过来的参数name获取对应的值
// 这个函数可以根据参数名获取网页链接后面的参数值
function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);
    if (r!=null) return (r[2]); return null;
}