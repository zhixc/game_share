// 给导航按钮绑定点击事件
function addClickListenerToNav() {
    var navItems = document.getElementById('navItems')
    var nav = this.document.getElementById('nav')
    var navItemsFlag = false
    nav.onclick = function () {
        if (!navItemsFlag) {
            // 说明现在导航菜单是关闭状态，那么打开
            navItems.style.display = 'block'
            navItemsFlag = true
        } else {
            // 否则就是打开了，那么关闭
            navItems.style.display = 'none'
            navItemsFlag = false
        }
    }
}