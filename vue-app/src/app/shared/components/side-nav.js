Vue.component("side-nav",{
  template: `
  <nav class="col-md-2 d-none d-md-block bg-light sidebar">
        <div class="sidebar-sticky">
          <ul class="nav flex-column">
            <li class="nav-item">
              <router-link class="nav-link" to="/profile">
                <span data-feather="home"></span>
                Profile
              </router-link>
            </li>
            <li class="nav-item" v-if="!$root.isDefaultUser">
              <router-link class="nav-link" to="/users">
                <span data-feather="user"></span>
                Users
              </router-link>
            </li>
            <li class="nav-item" v-if="$root.isSuperAdmin">
              <router-link class="nav-link" to="/organizations">
                <span data-feather="users"></span>
                Organizations
                </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/virtualmachines">
                <span data-feather="layers"></span>
                Virtual Machines
                </router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/drives">
                <span data-feather="database"></span>
                Drives
                </router-link>
            </li>
            <li class="nav-item" v-if="$root.isSuperAdmin">
              <router-link class="nav-link" to="/categories">
                <span data-feather="folder"></span>
                VM Categories
              </router-link>
            </li>
          </ul>
          <div v-if="$root.isAdmin">
            <h6 class="sidebar-heading d-flex
            justify-content-between
            align-items-center
            px-3 mt-4 mb-1 text-muted"
            >
              <span>Saved reports</span>
              <a class="d-flex align-items-center text-muted" href="#">
                <span data-feather="plus-circle"></span>
              </a>
            </h6>
            <ul class="nav flex-column mb-2">
              <li class="nav-item">
                  <router-link class="nav-link" to="/report">
                    <span data-feather="file-text"></span>
                    Bills report
                  </router-link>
                </a>
              </li>
            </ul>
        </div>  
        </div>
      </nav>
  `,
  mounted() {
    feather.replace();
  }
})