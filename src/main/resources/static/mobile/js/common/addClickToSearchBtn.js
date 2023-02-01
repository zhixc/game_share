
// 给搜索按钮绑定点击事件
function addClickToSearchBtn() {
    // 根据id获取searchBtn元素
    var  searchBtn = document.getElementById('searchBtn')
    // 根据id获取keywords元素
    var keywords = document.getElementById('keywords')

    searchBtn.onclick = function () {
        window.location.href = 'game_explore_mobile.html?keywords='+keywords.value
    }
}