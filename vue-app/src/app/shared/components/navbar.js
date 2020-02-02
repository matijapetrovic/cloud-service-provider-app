Vue.component("nav-bar",{
    template: `
    <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
      <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#/">Cloud Provider Service</a>
      <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
          <logout-button></logout-button>
        </li>
      </ul>
    </nav>
    `
    ,
    data: function(){
        return {
            isLogin: false
        }
    }
})