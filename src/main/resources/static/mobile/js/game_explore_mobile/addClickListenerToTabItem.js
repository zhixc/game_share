
// 给tab-item绑定点击，切换内容事件函数
function addClickListenerToTabItem() {
    // 获取tab项元素数组
    var tabItems = document.querySelectorAll('.tab-item')

    // 获取tab内容项元素数组
    var tabContainers = document.querySelectorAll('.tab-container')

    // 获取列表信息元素数组
    var listInfos = document.querySelectorAll('.list-info')

    // console.log(tabItems[0]);
    // console.log(tabItems[1]);
    // for循环遍历
    for (var i = 0; i < tabItems.length; i++) {

        // 给tab-container的div绑定自定义属性
        tabItems[i].setAttribute('index', i)

        tabItems[i].onclick = function () {
            // console.log('ok');

            // 先清除所有的tabItem项目的类名current
            for (var j = 0; j < tabItems.length; j++) {
                // console.log('进入了');

                tabItems[j].className = 'tab-item'
            }
            // console.log('出来了');
            // 让当前被点击的元素样式类名为 tab-item current
            this.className = 'tab-item current'

            // 获取当前元素的自定义属性值
            var index = this.getAttribute('index')
            // console.log(index);

            // 隐藏其他的tabContainer元素
            hideElementList(tabContainers)
            // 让当前要显示的显示
            tabContainers[index].style.display = 'block'

            // 隐藏其他listInfo元素
            hideElementList(listInfos)

            listInfos[index].style.display = 'block'

            // 接下来这里调用特定的方法
            // 如果index等于0，那么说明点击的是游戏列表，如果index是1，那么说明点击的是文章列表
            var keywords = getParameter('keywords');
            // 由于中文可能存在乱码的问题，这里要处理一下
            var gameName = decodeURI(keywords);

            if (gameName == null || gameName == 'null'){
                // console.log('未赋值前：'+gameName)
                gameName = null // 让它为真正的null，而不是字符串的 null
            }
            if (index == 0){
                // console.log('赋值后：'+gameName)
                findGameByGameName(1, gameName)
            }else if (index == 1){
                // console.log(gameName)
                findArticleByTitle(1, gameName)
            }
        }
    }

}