// 给搜索框绑定事件，切换样式
function searchInput(){
    var search = this.document.querySelector('.search');
    // console.log(search);
    // console.log(search.children[0]);
    var inp = search.children[0];
    inp.onclick = function(){ //给输入框添加点击事件
        search.className = 'search search_click';
    };
    inp.onblur = function(){ // 给输入框失去焦点事件
        search.className = 'search';
    };
}