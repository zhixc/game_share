// 这是一个判断浏览器是否为移动端、并且重定向的函数
function browserRedirect() {
    // 另一种写法
    if (
        navigator.userAgent.match(/Mobi/i) ||
        navigator.userAgent.match(/Android/i) ||
        navigator.userAgent.match(/iPhone/i)
    ) {
        // 当前设备是移动设备
        // console.log('是移动端.....')
        // 重定向到移动端版的页面
        window.location.href = 'mobile/index_mobile.html'
    }
}