Vue.component("nav-bar",{
    template: `
    <nav v-if="!isLogin" class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
          <a class="navbar-brand" href="#">Cloud-service provider</a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav mr-auto">
              <li class="nav-item">
              <a class="nav-link" href="#/">Home</a>
              </li>
              <li class="nav-item">
              <a class="nav-link" href="#/users">Users</a>
              </li>
              <li class="nav-item">
              <a class="nav-link" href="#/organizations">Organizations</a>
              </li>
          </ul>
          <div class="form-inline mt-2 mt-md-0">
              <ul class="navbar-nav mr-auto  navbar-right">
                  <li class="nav-item active">
                      <a class="nav-link" href="#">Logout <span class="sr-only">(current)</span></a>
                  </li>
              </ul>
          </div>
          </div>
      </nav>
    `
    ,
    data: function(){
        return {
            isLogin: false
        }
    }
})